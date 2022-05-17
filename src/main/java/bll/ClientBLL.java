package bll;

import dao.ClientDAO;
import dao.OrderDAO;
import model.Client;
import model.Order;
import presentation.ViewClient;

import java.sql.SQLException;

/**
 * Clasa care implementeaza logica de business pentru partea de clienti
 */
public class ClientBLL {

    /**
     * Lista de clienti obtinuta din baza de date
     */
    private static ClientDAO clients = new ClientDAO();
    private ViewClient v;

    /**
     * Constructorul clasei
     * @param view view-ul din care apelam metodele din clasa
     */
    public ClientBLL (ViewClient view){v = view;}

    /**
     * Metoda de vizualizare a tuturor clientilor
     * @return lista de clienti obtinuta din baza de date
     */
    public static ClientDAO view(){
        try {
            clients = ClientDAO.readFromDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clients;
    }

    /**
     * Metoda de adauggare a unui nou client
     */
    public void add(){
        Client client = new Client(v.getTfid(), v.getTfname(), v.getTfemail(), v.getTfaddress());
        ClientDAO.addIntoDB(client);
    }

    /**
     * Metoa de stergere a unui client
     * @throws SQLException
     */
    public void delete() throws SQLException {
        String[] s = v.getRand();
        Client client = new Client(Integer.parseInt(s[0]), s[1], s[2], s[3]);
        OrderDAO orders = OrderDAO.readFromDB();
        boolean inOrder = false;
        for(Order o: orders.getOrders()){
            if(o.getClientId().equals(client.getId())) {
                inOrder = true;
                break;
            }
        }
        if(inOrder){
            v.orderMessage();
        }
        else{
            ClientDAO.deleteFromDB(client);
        }
    }

    /**
     * Metoda de actualizare a unui client din baza de date
     */
    public void update(){
        String[] s = v.getRand();
        Client client1 = new Client(Integer.parseInt(s[0]), s[1], s[2], s[3]);
        Client client2 = new Client(v.getTfid(), v.getTfname(), v.getTfemail(), v.getTfaddress());
        ClientDAO.updateDB(client1, client2);

    }

    /**
     * Metoda care returneaza datele despre clienti care vor fi afisate in tabelul din interfata
     * @param clients - lista de clienti din baza de date
     * @return - datele care trebuie afisate in tabel
     */
    public Object[][] tableData(ClientDAO clients) {
        int sz = clients.getClients().size();
        Object[][] data = new Object[sz][];

        for (int i = 0; i < sz; i++) {
            data[i] = new Object[]{clients.getClients().get(i).getId(), clients.getClients().get(i).getName(), clients.getClients().get(i).getEmail(), clients.getClients().get(i).getAddress()};
        }

        return data;
    }
}
