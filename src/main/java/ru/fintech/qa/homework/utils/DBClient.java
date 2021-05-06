package ru.fintech.qa.homework.utils;

import java.sql.*;
import java.util.ArrayList;

public class DBClient {
    private Connection connection = null;


    public static Connection getConnection() {

        try {
            Class.forName("org.h2.Driver");
            return DriverManager.getConnection("jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1", "sa", "sa");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Не получилось подключиться к базе");
        }
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DBClient() {
        connection = getConnection();
    }

    public ArrayList<String> executeQueryColumn(String column, String sql) {
        ArrayList<String> value = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                value.add(resultSet.getString(column));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return value;
    }


    public int executeCount(String sql, String columnName) {
        int rowCount = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                rowCount = resultSet.getInt(columnName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return rowCount;
    }


    public int executeUpdate(String sql) {
        int value = 0;
        try {
            Statement statement = connection.createStatement();
            value = statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return value;
    }

}

