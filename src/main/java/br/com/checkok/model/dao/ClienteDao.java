package br.com.checkok.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;

import br.com.checkok.model.entities.Cliente;
import br.com.checkok.repository.ClienteMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ClienteDao {

	@Inject
	Jdbi jdbi;

	public List<Cliente> findAll(String nomeFiltro, Date dataCriacaoFiltro, int offset, int limit) {
	    StringBuilder query = new StringBuilder("SELECT c.id, c.nome, c.telefone, c.email, c.cpf, c.data_criacao, e.id as endereco_id, e.rua, e.cidade, e.estado, e.cep FROM clientes c LEFT JOIN enderecos e ON c.id = e.cliente_id");
	    
	    boolean whereClauseAdded = false;

	    if (nomeFiltro != null || dataCriacaoFiltro != null) {
	        query.append(" WHERE");
	        
	        if (nomeFiltro != null) {
	            query.append(" c.nome LIKE :nome");
	            whereClauseAdded = true;
	        }
	        
	        if (dataCriacaoFiltro != null) {
	            if (whereClauseAdded) {
	                query.append(" AND");
	            }
	            query.append(" DATE(c.data_criacao) = :dataCriacao");
	        }
	    }
	    
	    query.append(" LIMIT :limit OFFSET :offset");

	    return jdbi.withHandle(handle -> {
	        var q = handle.createQuery(query.toString());
	        
	        if (nomeFiltro != null) {
	            q.bind("nome", "%" + nomeFiltro + "%");
	        }
	        
	        if (dataCriacaoFiltro != null) {
	            q.bind("dataCriacao", dataCriacaoFiltro);
	        }

	        q.bind("limit", limit);
	        q.bind("offset", offset);

	        return q.map(new ClienteMapper()).list();
	    });
	}


	public Optional<Cliente> findById(Long id) {
		return jdbi.withHandle(handle -> handle.createQuery(
				"SELECT c.id, c.nome, c.telefone, c.email, c.cpf, c.data_criacao, e.id as endereco_id, e.rua, e.cidade, e.estado, e.cep FROM clientes c LEFT JOIN enderecos e ON c.id = e.cliente_id WHERE c.id = :id")
				.bind("id", id).map(new ClienteMapper()).findOne());
	}

	public Long insert(String nome, String telefone, String email, String cpf, Date dataCriacao) {
		return jdbi.withHandle(handle -> handle.createUpdate(
				"INSERT INTO clientes (nome, telefone, email, cpf, data_criacao) VALUES (:nome, :telefone, :email, :cpf, :dataCriacao)")
				.bind("nome", nome).bind("telefone", telefone).bind("email", email).bind("cpf", cpf)
				.bind("dataCriacao", dataCriacao).executeAndReturnGeneratedKeys("id").mapTo(Long.class).one());
	}

	public void update(Long id, String nome, String telefone, String email) {
		jdbi.useHandle(handle -> handle
				.createUpdate("UPDATE clientes SET nome = :nome, telefone = :telefone, email = :email WHERE id = :id")
				.bind("id", id).bind("nome", nome).bind("telefone", telefone).bind("email", email).execute());
	}

	public boolean delete(Long id) {
		return jdbi.withHandle(
				handle -> handle.createUpdate("DELETE FROM clientes WHERE id = :id").bind("id", id).execute() > 0);
	}

}
