package dao;

import connection.ConnectionFactory;
import model.Client;
import model.Product;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care asigura persistenta datelor - extrage din baza de date obiectele tabelei clients
 */
public class ClientDAO {

    /**
     * Lista de clienti corespunzatoare tuturor iniilor din tabelul clients din baza de date
     */
    private List<Client> clients = null;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    static ClientDAO clientsList = new ClientDAO();
    static{
        clientsList.setClients(new ArrayList<Client>());
    }

    /**
     * Query-urile folosite pentru a lucra cu baza de date
     */
    private final static String readQuery = "Select * from clients";
    private static final String insertQuery = "INSERT INTO clients (id, name, email, address)" + " VALUES (?,?,?,?)";
    private static final String deleteQuery = "DELETE FROM clients where id = ?";
    private static final String updateQuery = "UPDATE clients SET id = ?, name = ?, email = ?, address = ? WHERE id = ?";

    /**
     * Metoda care extrage toti clienti din tabela clients din baza de date si ii pune intr-o lista
     * @return clientii din baza de date
     * @throws SQLException
     */
    public static ClientDAO readFromDB() throws SQLException
    {
        clientsList.getClients().clear();
        try{
            Connection connection = new ConnectionFactory().getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(readQuery);
            while(rs.next()){
                Client p = new Client(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                clientsList.getClients().add(p);
            }
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }

        return clientsList;
    }

    /**
     * Metoda de adaugare a unui nou client in baza de date
     * @param c - clientul care trebuie adaugat
     */
    public static void addIntoDB(Client c ) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, c.getId());
            insertStatement.setString(2, c.getName());
            insertStatement.setString(3, c.getEmail());
            insertStatement.setString(4, c.getAddress());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda care sterge o linie corespunzatoare unui client din baza de date
     * @param c - clientul care trebuie sters
     */
    public static void deleteFromDB(Client c) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteQuery, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, c.getId());
            deleteStatement.execute();
            ResultSet rs = deleteStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda de update a unui obiect de tip client din baza de date
     * @param p1 - clientul care trebuie updatat
     * @param p2 - clientul updatat
     */
    public static void updateDB(Client p1, Client p2) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, p2.getId());
            updateStatement.setString(2, p2.getName());
            updateStatement.setString(3, p2.getEmail());
            updateStatement.setString(4, p2.getAddress());
            updateStatement.setInt(5, p1.getId());
            updateStatement.executeUpdate();
            ResultSet rs = updateStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
