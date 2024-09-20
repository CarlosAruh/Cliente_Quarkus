package br.com.checkok.resource;

import java.util.List;
import java.util.Optional;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.com.checkok.model.dao.EnderecoDao;
import br.com.checkok.model.entities.Endereco;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoController {
    @Inject
    EnderecoDao enderecoDao;

    @GET
    public List<Endereco> listarEnderecos() {
        return enderecoDao.findAll();
    }

    @GET
    @Path("/{id}")
    public Response consultarEnderecoPorId(@PathParam Long id) {
        return enderecoDao.findById(id)
                         .map(endereco -> Response.ok(endereco).build())
                         .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/cliente/{clienteId}")
    public List<Endereco> listarEnderecosPorCliente(@PathParam Long clienteId) {
        return enderecoDao.findByClienteId(clienteId);
    }

    @POST
    public Response criarEndereco(Endereco endereco) {
        enderecoDao.insert(endereco);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarEndereco(@PathParam Long id, Endereco endereco) {
        Optional<Endereco> existingEndereco = enderecoDao.findById(id);
        if (existingEndereco.isPresent()) {
            enderecoDao.update(id, endereco);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirEndereco(@PathParam Long id) {
        Optional<Endereco> existingEndereco = enderecoDao.findById(id);
        if (existingEndereco.isPresent()) {
            enderecoDao.delete(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}