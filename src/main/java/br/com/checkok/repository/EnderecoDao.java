package br.com.checkok.repository;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.com.checkok.model.Endereco;

public interface EnderecoDao {

	@SqlUpdate("CREATE TABLE IF NOT EXISTS endereco (" +
	"id SERIAL PRIMARY KEY, " +
	"rua VARCHAR(255) NOT NULL, " +
	"cidade VARCHAR(255) NOT NULL, " +
	"estado VARCHAR(255) NOT NULL, " +
	"cep VARCHAR(10) NOT NULL, "+
	"cliente_id BIGINT NOT NULL, " +
	"FOREIGN KEY (cliente_id) REFERENCES cliente(id))")
	void createTable();

	@SqlUpdate("INSERT INTO endereco (rua, cidade, estado, cep, cliente_id) "
			+ "VALUES (:rua, :cidade, :estado, :cep, :clienteId)")
	@GetGeneratedKeys
	Long insert(@BindBean Endereco endereco);

	@SqlQuery("SELECT * FROM endereco WHERE id = :id")
	@RegisterRowMapper(EnderecoMapper.class)
	Endereco findById(@Bind("id") Long id);

	@SqlQuery("SELECT * FROM endereco WHERE cliente_id = :clienteId")
	@RegisterRowMapper(EnderecoMapper.class)
	List<Endereco> findByClienteId(@Bind("clienteId") Long clienteId);

	@SqlUpdate("UPDATE endereco SET rua = :rua, cidade = :cidade, estado = :estado, cep = :cep " + "WHERE id = :id")
	void update(@BindBean Endereco endereco);

	@SqlUpdate("DELETE FROM endereco WHERE id = :id")
	void delete(@Bind("id") Long id);

	@SqlUpdate("DROP TABLE IF EXISTS endereco")
	void dropTable();
}
