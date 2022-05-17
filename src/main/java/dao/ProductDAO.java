package dao;

import connection.ConnectionFactory;
import model.Product;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care asigura persistenta datelor - extrage din baza de date obiectele tabelei products
 */
public class ProductDAO {

    /**
     * Lista de produse corespunzatoare tuturor liniilor din tabelul roducts din baza de date
     */
    private List<Product> products = null;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    static ProductDAO productsList = new ProductDAO();
    static{
        productsList.setProducts(new ArrayList<Product>());
    }

    /**
     * Query-urile folosite pentru a lucra cu baza de date
     */
    private final static String readQuery = "Select * from products";
    private static final String insertQuery = "INSERT INTO products (id, name, quantity, price)" + " VALUES (?,?,?,?)";
    private static final String deleteQuery = "DELETE FROM products where id = ?";
    private static final String updateQuery = "UPDATE products SET id = ?, name = ?, quantity = ?, price = ? WHERE id = ?";
    private static final String updateQuantityQuery = "UPDATE products SET quantity = ? WHERE id = ?";


    /**
     * Metoda care extrage toat produsele din tabela products din baza de date si ii pune intr-o lista
     * @return produsele din baza de date
     * @throws SQLException
     */
    public static ProductDAO readFromDB() throws SQLException
    {
        productsList.getProducts().clear();
        try{
            Connection connection = new ConnectionFactory().getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(readQuery);
            while(rs.next()){
                Product p = new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDouble(4));
                productsList.getProducts().add(p);
            }
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }

        return productsList;
    }

    /**
     * Metoda de adaugare a unui nou produs in baza de date
     * @param produs - produsul care trebuie adaugat
     */
    public static void addIntoDB(Product produs ) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, produs.getId());
            insertStatement.setString(2, produs.getName());
            insertStatement.setInt(3, produs.getQuantity());
            insertStatement.setDouble(4, produs.getPrice());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda care sterge o linie corespunzatoare unui produs din baza de date
     * @param produs - produsul care trebuie sters
     */
    public static void deleteFromDB(Product produs) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteQuery, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, produs.getId());
            deleteStatement.execute();
            ResultSet rs = deleteStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda de update a unui obiect de tip rodus din baza de date
     * @param p1 - produsul care trebuie updatat
     * @param p2 - produsul updatat
     */
    public static void updateDB(Product p1, Product p2) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = dbConnection.prepareStatement(updateQuery, Statement.RETURN_GENERATED_KEYS);
            updateStatement.setInt(1, p2.getId());
            updateStatement.setString(2, p2.getName());
            updateStatement.setInt(3, p2.getQuantity());
            updateStatement.setDouble(4, p2.getPrice());
            updateStatement.setInt(5, p1.getId());
            updateStatement.executeUpdate();
            ResultSet rs = updateStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Metoda care actualizaeaza catitatea unui produs in urma efectuarii unei comenzi care contine produsul respectiv
     * @param p - produsul caruia ii modific cantitatea in urma unei comenzi facute
     * @param quantity - cantitatea de produs care trebuie scazuta
     */
    public static void updateQuantity(Product p, Integer quantity)  {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(updateQuantityQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, p.getQuantity() - quantity);
            insertStatement.setInt(2, p.getId());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
