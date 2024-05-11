package com.assignment3version1.util;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Manages the database connection pool using Apache Commons DBCP. This utility class configures the connection pool
 * with parameters specified in a properties file and provides a method to retrieve connections from this pool.
 * Utilizing a connection pool improves performance by reusing existing connections instead of creating a new one
 * for every database operation.
 *
 * @author jiahong lin
 * @date Created in 24/03/2024 02:05
 * @modified By jiahong lin in 24/03/2024 02:05
 */
public class DatabaseConnection {

    private static final BasicDataSource dataSource = new BasicDataSource();
    private static final Properties properties = new Properties();

    static {
        try {
            // Load the db.properties configuration file
            InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            // Configure the connection pool with database details
            dataSource.setUrl(properties.getProperty("db.url"));
            dataSource.setUsername(properties.getProperty("db.user"));
            dataSource.setPassword(properties.getProperty("db.password"));

            // Set the minimum and maximum size of the idle connection pool
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);

            // Set the maximum number of active connections that can be allocated from this pool
            dataSource.setMaxTotal(25);

            // Set the maximum number of open prepared statements allowed
            dataSource.setMaxOpenPreparedStatements(100);

            // Ensure the database driver is loaded
            Class.forName(properties.getProperty("db.driver"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing the database connection pool", e);
        }
    }

    /**
     * Provides a database connection from the pool. This method allows the application to reuse existing connections
     * which improves efficiency and performance by reducing the overhead of establishing new connections for every
     * database operation.
     *
     * @return A Connection object representing a connection to the database.
     * @throws SQLException If an error occurs while attempting to retrieve a connection from the pool.
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
