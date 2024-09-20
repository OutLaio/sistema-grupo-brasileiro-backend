package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.integration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DatabaseInitializer {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    @Autowired
    private DataSource dataSource;


    @PostConstruct
    public void initializeDatabase() {
        String[] sqlStatements = {
                "INSERT INTO \"Tb_Profiles\" (id,description) VALUES (4,'ROLE_COLLABORATOR');",
        };

        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            for (String sql : sqlStatements) {
                statement.executeUpdate(sql);
            }
            LOGGER.info("Database initialization completed successfully.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing the database", e);
        }
    }
}
