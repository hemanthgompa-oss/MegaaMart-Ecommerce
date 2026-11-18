package com.megamart.megamart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseTest implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("================================");
            System.out.println("MYSQL DATABASE CONNECTED!");
            System.out.println("Database: " +
                    connection.getCatalog());
            System.out.println("================================");
        }
    }
}