package br.com.checkok.config;

import jakarta.inject.Inject;

import org.jdbi.v3.core.Jdbi;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class JdbiConfig {

    @Inject
    AgroalDataSource dataSource;

    @Produces
    public Jdbi createJdbi() {
        return Jdbi.create(dataSource);
    }
}
