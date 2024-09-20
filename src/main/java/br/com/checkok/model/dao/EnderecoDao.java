package br.com.checkok.model.dao;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import br.com.checkok.model.entities.Endereco;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnderecoDao {
    @Inject
    Jdbi jdbi;

    public List<Endereco> findAll() {
        return jdbi.withHandle(handle -> 
            handle.createQuery("SELECT * FROM enderecos")
                  .mapToBean(Endereco.class)
                  .list());
    }

    public List<Endereco> findByClienteId(Long clienteId) {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM enderecos WHERE cliente_id = :clienteId")
                  .bind("clienteId", clienteId)
                  .mapToBean(Endereco.class)
                  .list());
    }

    public Optional<Endereco> findById(Long id) {
        return jdbi.withHandle(handle -> 
            handle.createQuery("SELECT * FROM enderecos WHERE id = :id")
                  .bind("id", id)
                  .mapToBean(Endereco.class)
                  .findOne());
    }

    public void insert(Endereco endereco) {
        jdbi.useHandle(handle -> 
            handle.createUpdate("INSERT INTO enderecos (rua, cidade, estado, cep, cliente_id) VALUES (:rua, :cidade, :estado, :cep, :clienteId)")
                  .bind("rua", endereco.getRua())
                  .bind("cidade", endereco.getCidade())
                  .bind("estado", endereco.getEstado())
                  .bind("cep", endereco.getCep())
                  .bind("clienteId", endereco.getClienteId())
                  .execute());
    }

    public void update(Long id, Endereco endereco) {
        jdbi.useHandle(handle -> 
            handle.createUpdate("UPDATE enderecos SET rua = :rua, cidade = :cidade, estado = :estado, cep = :cep WHERE id = :id")
                  .bind("id", id)
                  .bind("rua", endereco.getRua())
                  .bind("cidade", endereco.getCidade())
                  .bind("estado", endereco.getEstado())
                  .bind("cep", endereco.getCep())
                  .execute());
    }

    public void delete(Long id) {
        jdbi.useHandle(handle -> 
            handle.createUpdate("DELETE FROM enderecos WHERE id = :id")
                  .bind("id", id)
                  .execute());
    }
}