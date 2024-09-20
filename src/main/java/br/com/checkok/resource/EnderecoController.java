package br.com.checkok.resource;

import java.util.List;

import br.com.checkok.model.dao.EnderecoDao;
import br.com.checkok.model.entities.Endereco;
import jakarta.inject.Inject;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
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

    @POST
    public Response criarEndereco(Endereco endereco) {
        enderecoDao.insert(endereco);
        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Path("/{id}")
    public Response atualizarEndereco(@PathParam Long id, Endereco endereco) {
        enderecoDao.update(id, endereco);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response excluirEndereco(@PathParam Long id) {
        enderecoDao.delete(id);
        return Response.noContent().build();
    }
}
