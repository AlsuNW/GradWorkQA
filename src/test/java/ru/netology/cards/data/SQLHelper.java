package ru.netology.cards.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;


public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.user"), System.getProperty("db.password"));
    }

    @SneakyThrows
    public static void cleanTables() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");

    }

    @SneakyThrows
    public static ResultSet sortThroughPaymentEntity() {
        var connection = getConn();
        String query = "SELECT status\n" +
                "FROM payment_entity\n" +
                "ORDER BY created DESC\n" +
                "LIMIT 1;\n";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    @SneakyThrows
    public static ResultSet sortThroughCreditEntity() {
        var connection = getConn();
        String query = "SELECT status\n" +
                "FROM credit_request_entity\n" +
                "ORDER BY created DESC\n" +
                "LIMIT 1;\n";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }


}
