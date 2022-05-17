package bll;

import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;
import presentation.ViewProduct;

import java.sql.SQLException;

/**
 * Clasa care implementeaza logica de business pentru partea de produse
 */
public class ProductBLL {

    /**
     * Lista de produse obtinuta din baza de date
     */
    private static ProductDAO products = new ProductDAO();
    private ViewProduct v;

    /**
     * Constructorul clasei
     * @param view view-ul din care apelam metodele din clasa
     */
    public ProductBLL (ViewProduct view){v = view;}

    /**
     * Metoda de vizualizare a tuturor produselor
     * @return lista de produse obtinuta din baza de date
     */
    public static ProductDAO view(){
        try {
            products = ProductDAO.readFromDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    /**
     * Metoda de adauggare a unui nou produs
     */
    public void add(){
        Product product = new Product(v.getTfid(), v.getTfname(), v.getTfquantity(), v.getTfprice());
        ProductDAO.addIntoDB(product);
    }

    /**
     * Metoa de stergere a unui produs
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String[] s = v.getRand();
        Product product = new Product(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]), Double.parseDouble(s[3]));
        OrderDAO orders = OrderDAO.readFromDB();
        boolean inOrder = false;
        for(Order o: orders.getOrders()){
            if(o.getProductId().equals(product.getId())) {
                inOrder = true;
                break;
            }
        }
        if(inOrder){
            v.orderMessage();
        }
        else{
            ProductDAO.deleteFromDB(product);
        }

    }

    /**
     * Metoda de actualizare a unui produs din baza de date
     */
    public void update(){
        String[] s = v.getRand();
        Product product1 = new Product(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]), Double.parseDouble(s[3]));
        Product product2 = new Product(v.getTfid(), v.getTfname(), v.getTfquantity(), v.getTfprice());
        ProductDAO.updateDB(product1, product2);

    }

    /**
     * Metoda care returneaza datele despre produse care vor fi afisate in tabelul din interfata
     * @param products - lista de produse din baza de date
     * @return - datele care trebuie afisate in tabel
     */
    public Object[][] tableData(ProductDAO products) {
        int sz = products.getProducts().size();
        Object[][] data = new Object[sz][];

        for (int i = 0; i < sz; i++) {
            data[i] = new Object[]{products.getProducts().get(i).getId(), products.getProducts().get(i).getName(), products.getProducts().get(i).getQuantity(), products.getProducts().get(i).getPrice()};
        }

        return data;
    }
}
