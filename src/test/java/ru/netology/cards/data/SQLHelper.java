package ru.netology.cards.data;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    };

    public static Connection getConn() throws SQLException {
        Properties credentials= readCredentials();
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", credentials.getProperty("username"), credentials.getProperty("password"));
    }

    public static Properties readCredentials() {
        Properties p=new Properties();
        try {
            InputStream s=SQLHelper.class.getResourceAsStream("/credentials.properties");
            p.load(s);
        } catch (IOException e) {
        }
        return p;
    }

    @SneakyThrows
    public static void cleanTables() {
        var  connection = getConn();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");

    }
    @SneakyThrows
    public static ResultSet sortThroughPaymentEntity() {
        var  connection = getConn();
        String query = "SELECT status\n" +
                    "FROM payment_entity\n" +
                        "ORDER BY created DESC\n" +
                        "LIMIT 1;\n";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery();
                     return resultSet;
    }

    @SneakyThrows
    public static void sortThroughCreditEntity() {
        var  connection = getConn();
        runner.execute(connection, "SELECT status\n" +
                "FROM credit_request_entity\n" +
                "ORDER BY created DESC\n" +
                "LIMIT 1;\n");
    }
}
