package dao;

import connection.ConnectionFactory;
import model.Order;
import model.Product;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care asigura persistenta datelor - extrage din baza de date obiectele tabelei orders
 */
public class OrderDAO {

    /**
     * Lista de comenzi corespunzatoare tuturor iniilor din tabelul orders din baza de date
     */
    private List<Order> orders = null;

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    static OrderDAO ordersList = new OrderDAO();
    static{
        ordersList.setOrders(new ArrayList<Order>());
    }

    /**
     * Query-urile folosite pentru a lucra cu baza de date
     */
    private final static String readQuery = "Select * from orders";
    private static final String insertQuery = "INSERT INTO orders (orderId, productId, clientId, quantity, total)" + " VALUES (?,?,?,?,?)";
    private static final String deleteQuery = "DELETE FROM orders where orderId = ?";
    private static final String updateQuery = "UPDATE orders SET orderId = ?, productId = ?, clientId = ?, quantity = ?, total = ? WHERE orderId = ?";


    /**
     * Metoda care extrage toate comenzile din tabela orders din baza de date si le pune intr-o lista
     * @return comenzile din baza de date
     * @throws SQLException
     */
    public static OrderDAO readFromDB() throws SQLException
    {
        ordersList.getOrders().clear();
        try{
            Connection connection = new ConnectionFactory().getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(readQuery);
            while(rs.next()){
                Order p = new Order(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getDouble(5)
                        );
                ordersList.getOrders().add(p);
            }
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }

        return ordersList;
    }

    /**
     * Metoda de adaugare a unei noi comenzi in baza de date
     * @param o - comanda care trebuie adaugata
     */
    public static void addIntoDB(Order o ) {
        Connection dbConnection = ConnectionFactory.singleInstance.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, o.getOrderId());
            insertStatement.setInt(2, o.getProductId());
            insertStatement.setInt(3, o.getClientId());
            insertStatement.setInt(4, o.getQuantity());
            insertStatement.setDouble(5, o.getTotal());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
