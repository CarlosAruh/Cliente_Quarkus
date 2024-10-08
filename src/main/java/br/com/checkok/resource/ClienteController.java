package br.com.checkok.resource;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.checkok.model.dao.ClienteDao;
import br.com.checkok.model.entities.Cliente;
import br.com.checkok.util.CpfCnpjValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    @Inject
    ClienteDao clienteDao;

    @GET
    public Response listarClientes(@QueryParam("nome") String nomeFiltro,
                                   @QueryParam("dataCriacao") String dataCriacaoStr,
                                   @QueryParam("limit") @DefaultValue("10") int limit,
                                   @QueryParam("offset") @DefaultValue("0") int offset) {
        Date dataCriacaoFiltro = null;
        if (dataCriacaoStr != null && !dataCriacaoStr.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                dataCriacaoFiltro = format.parse(dataCriacaoStr);
            } catch (ParseException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Formato de data inválido. Use o formato 'dd/MM/yyyy'.")
                               .build();
            }
        }
        
        List<Cliente> clientes = clienteDao.findAll(nomeFiltro, dataCriacaoFiltro, offset, limit);
        return Response.ok(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response consultarClientePorId(@PathParam Long id) {
        return clienteDao.findById(id)
                         .map(cliente -> Response.ok(cliente).build())
                         .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response criarCliente(Cliente cliente) {
    	if (CpfCnpjValidator.isDocumentoValido(cliente.getCpf(), cliente.getCnpj())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Documento inválido. Verifique o CPF ou CNPJ.")
                    .build();
        }
    	
    	Long id = clienteDao.insert(cliente.getNome(), cliente.getTelefone(), cliente.getEmail(), cliente.getCpf(), new Date());
        URI location = UriBuilder.fromResource(ClienteController.class)
        		.path("/{id}")
        		.build(id);
        return Response.created(location)
        		.entity(cliente)
        		.build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarClienteParcial(@PathParam Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clienteDao.findById(id);
        
        if (clienteExistente.isPresent()) {
            clienteDao.update(id, cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
            return Response.ok(clienteExistente.get()).build(); 
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deletarCliente(@PathParam Long id) {
        boolean deletado = clienteDao.delete(id);
        
        if (deletado) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}