package br.com.checkok.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.com.checkok.model.Endereco;

public class EnderecoMapper implements RowMapper<Endereco> {

    @Override
    public Endereco map(ResultSet rs, StatementContext ctx) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getLong("id"));
        endereco.setRua(rs.getString("rua"));
        endereco.setCidade(rs.getString("cidade"));
        endereco.setEstado(rs.getString("estado"));
        endereco.setCep(rs.getString("cep"));
        endereco.setClienteId(rs.getLong("cliente_id"));
        return endereco;
    }
}
