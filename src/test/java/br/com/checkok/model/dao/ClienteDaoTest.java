package br.com.checkok.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.checkok.model.entities.Cliente;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class ClienteDaoTest {

    @Inject
    ClienteDao clienteDao;

    @BeforeEach
    @Transactional
    public void setUp() {
        clienteDao.insert("John Doe", "123456789", "john.doe@example.com", "12345678901", new Date(System.currentTimeMillis()));
    }

    /*@Test
    public void testFindAll() {
        List<Cliente> clientes = clienteDao.findAll();
        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());
    }*/

    @Test
    public void testFindById() {
        Optional<Cliente> cliente = clienteDao.findById(1L);
        assertTrue(cliente.isPresent());
        assertEquals("John Doe", cliente.get().getNome());
    }

    @Test
    @Transactional
    public void testInsert() {
        clienteDao.insert("Jane Doe", "987654321", "jane.doe@example.com", "10987654321", new Date(System.currentTimeMillis()));
        Optional<Cliente> cliente = clienteDao.findById(2L);
        assertTrue(cliente.isPresent());
        assertEquals("Jane Doe", cliente.get().getNome());
    }

    @Test
    @Transactional
    public void testUpdate() {
        clienteDao.update(1L, "John Doe Updated", "111111111", "john.updated@example.com");
        Optional<Cliente> cliente = clienteDao.findById(1L);
        assertTrue(cliente.isPresent());
        assertEquals("John Doe Updated", cliente.get().getNome());
    }
}