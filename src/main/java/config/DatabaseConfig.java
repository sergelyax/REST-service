package config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static final String PROPERTIES_FILE = "application.properties";
    private static DatabaseConfig instance;
    private Properties properties;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    private DatabaseConfig() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Не удалось найти файл конфигурации: " + PROPERTIES_FILE);
            }
            properties.load(inputStream);
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла конфигурации: " + PROPERTIES_FILE, e);
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не найден JDBC драйвер для PostgreSQL", e);
        }
    }

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void overrideConnectionUrl(String url) {
        dbUrl = url;
    }

    public void overrideUsername(String user) {
        dbUser = user;
    }

    public void overridePassword(String password) {
        dbPassword = password;
    }
}
