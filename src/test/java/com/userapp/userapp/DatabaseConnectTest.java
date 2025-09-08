package com.userapp.userapp;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseConnectTest {
    
    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection () throws SQLException{
        // try (Connection conn = dataSource.getConnection()) {
        //     assertFalse(conn.isClosed());
        // }
        
        try (Connection conn = dataSource.getConnection()) {
            assertThat(conn.isValid(2)).isTrue();
            System.out.println("connected successfuly");
        }

            // try (Connection connection = dataSource.getConnection()) {
            // if (!connection.isClosed()) {
            //     assertThat(connection.isValid(2)).isTrue();
            //     System.out.println("✅ Database is connected successfully!");
            // } else {
            //     System.out.println("❌ Failed to connect to the database.");
            // }
    }
    
}

