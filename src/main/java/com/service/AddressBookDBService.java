package com.service;

import com.model.PersonInformation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class AddressBookDBService {


    private static AddressBookDBService addressBookDBService;
    private PreparedStatement contactDataStatement;

    public static AddressBookDBService getInstance() {
        if ( addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    public List<PersonInformation> readData() {
        String sql = "SELECT * FROM contacts";
        return getAddressBookDataUsingDB(sql);
    }

    private List<PersonInformation> getAddressBookDataUsingDB(String sql) {
        List<PersonInformation> contactList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt("id");
                String fname = result.getString("Name");
                String address = result.getString("Address");
                String city = result.getString("City");
                String state = result.getString("State");
                int zip = result.getInt("Zip");
                int phoneNo = result.getInt("PhoneNumber");
                String email = result.getString("Email");
                contactList.add(new PersonInformation(id, fname,address, city, state, zip, phoneNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public int updateContact(String name, String state) {
        return this.updateContactUsingStatement(name,state);
    }

    private int updateContactUsingStatement(String name, String state) {
        String sql = String.format("UPDATE contacts SET State = '%s' WHERE name = '%s';", state, name);
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<PersonInformation> getContactData(String name) {
        List<PersonInformation> contactList = null;
        if(this.contactDataStatement == null)
            this.preparedStatementForContact();
        try {
            contactDataStatement.setString(1, name);
            ResultSet resultSet = contactDataStatement.executeQuery();
            contactList = this.getContactData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    //prepared statement to execute
    private void preparedStatementForContact() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM contacts WHERE name = ?";
            contactDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<PersonInformation> getContactData(ResultSet resultSet) {
        List<PersonInformation> contactList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fname = resultSet.getString("Name");
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                int zip = resultSet.getInt("Zip");
                int phoneNo = resultSet.getInt("PhoneNumber");
                String email = resultSet.getString("Email");
                contactList.add(new PersonInformation(id, fname, address, city, state, zip, phoneNo, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbookmanagement?useSSL=false";
        String userName = "root";
        String password = "Meghnad@1827";
        Connection connection;
        System.out.println("Connecting to database: "+jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful! " +connection);
        return connection;
    }

    public List<PersonInformation> getContactData(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT * FROM contacts WHERE date_field BETWEEN '%s' AND '%s';", Date.valueOf(startDate), Date.valueOf(endDate));
        return getAddressBookDataUsingDB(sql);
    }

    public List<PersonInformation> getContactForParticularCity(String city) {
        String sql = String.format("SELECT * FROM contacts WHERE City = '%s';", city);
        return getAddressBookDataUsingDB(sql);
    }

    public List<PersonInformation> getContactForParticularState(String state) {
        String sql = String.format("SELECT * FROM contacts WHERE State = '%s';", state);
        return getAddressBookDataUsingDB(sql);
    }
}
