package br.com.checkok.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.checkok.model.entities.Endereco;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class EnderecoDaoTest {

    @Inject
    EnderecoDao enderecoDao;

    @BeforeEach
    @Transactional
    public void setUp() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Exemplo");
        endereco.setCidade("Cidade Exemplo");
        endereco.setEstado("Estado Exemplo");
        endereco.setCep("12345-678");
        endereco.setClienteId(1L);
        enderecoDao.insert(endereco);
    }

    @Test
    public void testFindAll() {
        List<Endereco> enderecos = enderecoDao.findAll();
        assertNotNull(enderecos);
        assertFalse(enderecos.isEmpty());
    }

    @Test
    public void testFindById() {
        Optional<Endereco> endereco = enderecoDao.findById(1L);
        assertTrue(endereco.isPresent());
        assertEquals("Rua Exemplo", endereco.get().getRua());
    }

    @Test
    @Transactional
    public void testInsert() {
        Endereco endereco = new Endereco();
        endereco.setRua("Nova Rua");
        endereco.setCidade("Nova Cidade");
        endereco.setEstado("Novo Estado");
        endereco.setCep("87654-321");
        endereco.setClienteId(2L);
        enderecoDao.insert(endereco);
        Optional<Endereco> insertedEndereco = enderecoDao.findById(2L);
        assertTrue(insertedEndereco.isPresent());
        assertEquals("Nova Rua", insertedEndereco.get().getRua());
    }

    @Test
    @Transactional
    public void testUpdate() {
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Atualizada");
        endereco.setCidade("Cidade Atualizada");
        endereco.setEstado("Estado Atualizado");
        endereco.setCep("11111-111");
        endereco.setClienteId(1L);
        enderecoDao.update(1L, endereco);
        Optional<Endereco> updatedEndereco = enderecoDao.findById(1L);
        assertTrue(updatedEndereco.isPresent());
        assertEquals("Rua Atualizada", updatedEndereco.get().getRua());
    }

    @Test
    @Transactional
    public void testDelete() {
        enderecoDao.delete(1L);
        Optional<Endereco> deletedEndereco = enderecoDao.findById(1L);
        assertFalse(deletedEndereco.isPresent());
    }
}