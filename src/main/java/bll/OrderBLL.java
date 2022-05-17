package bll;

import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;
import presentation.ViewClient;
import presentation.ViewOrder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Clasa care implementeaza logica de business pentru partea de comenzi
 */
public class OrderBLL {

    /**
     * Lista de comenzi obtinuta din baza de date
     */
    private static OrderDAO orders = new OrderDAO();
    private ViewOrder v;

    /**
     * Constructorul clasei
     * @param view view-ul din care apelam metodele din clasa
     */
    public OrderBLL (ViewOrder view){v = view;}

    /**
     * Metoda de vizualizare a tuturor comenzilor
     * @return lista de comenzi obtinuta din baza de date
     */
    public static OrderDAO view(){
        try {
            orders = OrderDAO.readFromDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orders;
    }

    /**
     * Metoda de adauggare a unei comenzi
     */
    public void add() throws SQLException {
        Double price = 1.0;
        boolean available = false;

        ProductDAO products = ProductDAO.readFromDB();
        for(Product  p : products.getProducts()){
            if(Objects.equals(p.getId(), v.getTfProductId())) {
                price = p.getPrice();
                if (p.getQuantity() >= v.getTfquantity()) {
                    ProductDAO.updateQuantity(p, v.getTfquantity());
                    available = true;
                }
            }
        }

        if(available){
            Order o = new Order(v.getTfid(), v.getTfProductId(), v.getTfClientId(), v.getTfquantity(), price*v.getTfquantity());
            OrderDAO.addIntoDB(o);
            generateBill(o);
        }
        else{
            v.quantityMessage();
        }
    }

    /**
     * Metoda care returneaza datele despre comenzi care vor fi afisate in tabelul din interfata
     * @param o - lista de comenzi din baza de date
     * @return - datele care trebuie afisate in tabel
     */
    public Object[][] tableData(OrderDAO o) {

        int sz = o.getOrders().size();
        Object[][] data = new Object[sz][];

        for (int i = 0; i < sz; i++) {
            data[i] = new Object[]{o.getOrders().get(i).getOrderId(), o.getOrders().get(i).getProductId(), o.getOrders().get(i).getClientId(), o.getOrders().get(i).getQuantity(), o.getOrders().get(i).getTotal()};
        }

        return data;
    }

    /**
     * Metoda care genereaza crearea unei facturi sub forma unui fisier text pentru fiecare comanda
     * @param o - comanda pentru care se genereaza factura
     */

    public void generateBill(Order o){
        FileWriter fileWriter = null;
        String fileName = "Order" + o.getOrderId() + ".txt";
        try { fileWriter = new FileWriter(System.getProperty("user.dir") + "\\" + fileName);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try {
            fileWriter.write("Order Id: " + o.getOrderId() + '\n');
            fileWriter.write("Product Id: " + o.getProductId() + '\n');
            fileWriter.write("Client Id: " + o.getClientId() + '\n');
            fileWriter.write("Quantity: " + o.getQuantity() + '\n');
            fileWriter.write("Total price: " + o.getTotal());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try {
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



}
