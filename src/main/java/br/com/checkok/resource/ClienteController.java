package br.com.checkok.resource;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.checkok.model.dao.ClienteDao;
import br.com.checkok.model.entities.Cliente;
import br.com.checkok.util.CpfCnpjValidator;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    public List<Cliente> listarClientes() {
        return clienteDao.findAll();
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

    @PATCH
    @Path("/{id}")
    public Response atualizarClienteParcial(@PathParam Long id, Cliente cliente) {
        clienteDao.update(id, cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
        return Response.noContent().build();
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