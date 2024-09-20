package br.com.checkok.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.com.checkok.model.entities.Cliente;
import br.com.checkok.model.entities.Endereco;

public class ClienteMapper implements RowMapper<Cliente> {

    @Override
    public Cliente map(ResultSet rs, StatementContext ctx) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setNome(rs.getString("nome"));
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEmail(rs.getString("email"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setDataCriacao(rs.getTimestamp("data_criacao"));

        List<Endereco> enderecos = new ArrayList<>();
        do {
            if (rs.getString("rua") != null) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getLong("endereco_id"));
                endereco.setRua(rs.getString("rua"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setEstado(rs.getString("estado"));
                endereco.setCep(rs.getString("cep"));
                endereco.setClienteId(cliente.getId());
                enderecos.add(endereco);
            }
        } while (rs.next() && rs.getLong("id") == cliente.getId());

        cliente.setEnderecos(enderecos);
        return cliente;
    }
}
