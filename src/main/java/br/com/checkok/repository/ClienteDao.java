package br.com.checkok.repository;

import java.util.Date;
import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.com.checkok.model.Cliente;

@RegisterBeanMapper(Cliente.class)
public interface ClienteDao {

    @SqlUpdate("INSERT INTO clientes (nome, telefone, email, cpf, data_criacao) VALUES (:nome, :telefone, :email, :cpf, :dataCriacao)")
    void insert(@Bind("nome") String nome, 
                @Bind("telefone") String telefone, 
                @Bind("email") String email, 
                @Bind("cpf") String cpf, 
                @Bind("dataCriacao") Date dataCriacao);

    @SqlQuery("SELECT * FROM clientes WHERE id = :id")
    Cliente findById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM clientes")
    List<Cliente> findAll();

    @SqlUpdate("UPDATE clientes SET nome = :nome, telefone = :telefone, email = :email WHERE id = :id")
    void update(@Bind("id") Long id, 
                @Bind("nome") String nome, 
                @Bind("telefone") String telefone, 
                @Bind("email") String email);

    @SqlUpdate("DELETE FROM clientes WHERE id = :id")
    void delete(@Bind("id") Long id);
}
