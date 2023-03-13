package com.example.demo.dao;

import com.example.demo.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    private volatile static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            synchronized (PersonDao.class) {
                if (connection == null) {
                    connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test", "user01", "123");
                }
            }
        }
        return connection;
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            synchronized (PersonDao.class) {
                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            }
        }
    }

    public static void createTable(String name) throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();
        boolean isCreated = statement.execute("CREATE TABLE " + name + "(id bigint, name VARCHAR(50), age int)");
        if (isCreated) {
            System.out.println("Table created Successfully");
        }
        closeConnection();
    }

    public static void insert(Person person) throws SQLException {
        getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person values (?,?,?)");
        preparedStatement.setLong(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setInt(3, person.getAge());
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Record inserted successfully");
        }
        closeConnection();
    }

    public static List<Person> getPersons() throws SQLException {
        List<Person> persons = new ArrayList<>();
        getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person");
        while (resultSet.next()) {
            Person person = new Person(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3));
            persons.add(person);
        }
        closeConnection();
        return persons;
    }
}
