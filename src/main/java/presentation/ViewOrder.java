package presentation;

import bll.OrderBLL;
import bll.ProductBLL;
import dao.OrderDAO;
import dao.ProductDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Fereastra corespunzatoare tabelei de comenzi; permite crearea unr noi comenzi
 */
public class ViewOrder {

    private OrderBLL obll;
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel form;
    private JPanel form2;
    private JPanel pdel;
    private JLabel title = new JLabel("-- Orders table --");
    private JLabel add = new JLabel("Add order:    ");
    private JLabel id = new JLabel("OrderId:");
    private JTextField tfid = new JTextField();
    private JLabel productId = new JLabel("ProductId:");
    private JTextField tfProductId = new JTextField();
    private JLabel clientId = new JLabel("ClientId:");
    private JTextField tfClientId = new JTextField();
    private JLabel quantity = new JLabel("Quantity:");
    private JTextField tfquantity = new JTextField();


    private JButton button = new JButton("Add");
    private JButton show = new JButton("Show");


    private JTable table;

    /**
     * Constructorul view-ului ; modelarea tuturor elementelor de interfata grafica
     */
    public ViewOrder() {

        obll = new OrderBLL(this);

        mainFrame = new JFrame("Warehouse - Orders");
        mainFrame.getContentPane().setBackground(new Color(229, 204, 255));
        mainFrame.setBounds(450, 500, 1000, 500);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        title.setFont(new Font("Serif", Font.PLAIN, 25));
        title.setSize(200, 20);

        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setSize(500,100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        add.setFont(new Font("Serif", Font.PLAIN, 25));
        add.setSize(200, 20);

        id.setFont(new Font("Serif", Font.PLAIN, 20));

        tfid.setFont(new Font("Serif", Font.PLAIN, 20));
        tfid.setPreferredSize(new Dimension(150,25));

        productId.setFont(new Font("Serif", Font.PLAIN, 20));

        tfProductId.setFont(new Font("Serif", Font.PLAIN, 20));
        tfProductId.setPreferredSize(new Dimension(150,25));

        quantity.setFont(new Font("Serif", Font.PLAIN, 20));

        tfquantity.setFont(new Font("Serif", Font.PLAIN, 20));
        tfquantity.setPreferredSize(new Dimension(150,25));

        clientId.setFont(new Font("Serif", Font.PLAIN, 20));

        tfClientId.setFont(new Font("Serif", Font.PLAIN, 20));
        tfClientId.setPreferredSize(new Dimension(150,25));

        button.setBackground(new Color(204, 153, 255));
        button.setFont(new Font("Serif", Font.PLAIN, 20));
        button.setSize(200, 50);

        show.setBackground(new Color(204, 153, 255));
        show.setFont(new Font("Serif", Font.PLAIN, 20));
        show.setSize(200, 50);

        pdel = new JPanel();
        pdel.setLayout(new FlowLayout());

        form = new JPanel();
        form.setLayout(new FlowLayout());

        form2 = new JPanel();
        form2.setLayout(new FlowLayout());

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                mainFrame.dispose();
                ViewOrder v = new ViewOrder();
                v.showTableDemo(OrderBLL.view());
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    obll.add();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        form.add(add);
        form.add(id);
        form.add(tfid);
        form.add(productId);
        form.add(tfProductId);
        form2.add(clientId);
        form2.add(tfClientId);
        form2.add(quantity);
        form2.add(tfquantity);
        form2.add(button);

        mainFrame.add(title,BorderLayout.CENTER);
        mainFrame.add(show);
        mainFrame.add(controlPanel);
        mainFrame.add(form);
        mainFrame.add(form2);
        mainFrame.setVisible(true);
    }

    /**
     * Metoda care extrage obiectele de tip comanda pentru a le afisa in tabelul din interfata
     * @param p - persistenta datelor din baza de date (tabelul comenzi)
     */
    public void showTableDemo(OrderDAO p) {
        String[] columnNames = {"OrderId", "ProductId", "ClientId", "Quantity", "Total"};

        table = new JTable(obll.tableData(p), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));
        table.setFillsViewportHeight(true);
        controlPanel.add(scrollPane);
        mainFrame.setVisible(true);
    }

    /**
     *Getters
     */
    public void quantityMessage() {
        JOptionPane.showMessageDialog(mainFrame, "Quantity unavailable!");
    }

    public Integer getTfid() {
        return Integer.parseInt(tfid.getText());
    }

    public Integer getTfProductId() {
        return Integer.parseInt(tfProductId.getText());
    }

    public Integer getTfClientId() {
        return Integer.parseInt(tfClientId.getText());
    }

    public Integer getTfquantity() {
        return Integer.parseInt(tfquantity.getText());
    }

}
