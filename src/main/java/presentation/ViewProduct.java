package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import dao.ProductDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Fereastra corespunzatoare tabelei de produse; permite operatii de tip CRUD pe produse
 */
public class ViewProduct {

    private ProductBLL pbll;
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel form;
    private JPanel form2;
    private JPanel pdel;
    private JLabel title = new JLabel("-- Products table --");
    private JLabel space = new JLabel("                                                                              ");
    private JLabel space2 = new JLabel("                                                                             ");
    private JLabel add = new JLabel("Add/update product:");
    private JLabel id = new JLabel("Id:");
    private JTextField tfid = new JTextField();
    private JLabel name1 = new JLabel("Name:");
    private JTextField tfname = new JTextField();
    private JLabel quantity = new JLabel("Quantity:");
    private JTextField tfquantity = new JTextField();
    private JLabel price = new JLabel("Price:");
    private JTextField tfprice = new JTextField();

    private JButton button = new JButton("Add");
    private JButton bupd = new JButton("Update");
    private JButton show = new JButton("Show");
    private JButton bdel = new JButton("Delete");
    private JLabel del = new JLabel("Delete selected product from table:");
    private JTable table;

    /**
     * Constructorul view-ului ; modelarea tuturor elementelor de interfata grafica
     */
    public ViewProduct() {

        pbll = new ProductBLL(this);

        mainFrame = new JFrame("Warehouse - Products");
        mainFrame.getContentPane().setBackground(new Color(229, 204, 255));
        mainFrame.setBounds(950, 0, 950, 500);
        mainFrame.setLayout(new FlowLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        title.setFont(new Font("Serif", Font.PLAIN, 25));
        title.setSize(200, 20);

        del.setFont(new Font("Serif", Font.PLAIN, 25));
        del.setSize(200, 20);

        statusLabel = new JLabel("",JLabel.CENTER);
        statusLabel.setSize(500,100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        bdel.setBackground(new Color(204, 153, 255));
        bdel.setFont(new Font("Serif", Font.PLAIN, 20));
        bdel.setSize(200, 50);

        add.setFont(new Font("Serif", Font.PLAIN, 25));
        add.setSize(200, 20);

        id.setFont(new Font("Serif", Font.PLAIN, 20));

        tfid.setFont(new Font("Serif", Font.PLAIN, 20));
        tfid.setPreferredSize(new Dimension(150,25));

        name1.setFont(new Font("Serif", Font.PLAIN, 20));

        tfname.setFont(new Font("Serif", Font.PLAIN, 20));
        tfname.setPreferredSize(new Dimension(150,25));

        quantity.setFont(new Font("Serif", Font.PLAIN, 20));

        tfquantity.setFont(new Font("Serif", Font.PLAIN, 20));
        tfquantity.setPreferredSize(new Dimension(150,25));

        price.setFont(new Font("Serif", Font.PLAIN, 20));

        tfprice.setFont(new Font("Serif", Font.PLAIN, 20));
        tfprice.setPreferredSize(new Dimension(150,25));

        button.setBackground(new Color(204, 153, 255));
        button.setFont(new Font("Serif", Font.PLAIN, 20));
        button.setSize(200, 50);

        bupd.setBackground(new Color(204, 153, 255));
        bupd.setFont(new Font("Serif", Font.PLAIN, 20));
        bupd.setSize(200, 50);

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
                ViewProduct v = new ViewProduct();
                v.showTableDemo(ProductBLL.view());
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pbll.add();
            }
        });

        bdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pbll.delete();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        bupd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pbll.update();

            }
        });

        pdel.add(space);
        pdel.add(del);
        pdel.add(bdel);
        pdel.add(space2);
        form.add(id);
        form.add(tfid);
        form.add(name1);
        form.add(tfname);
        form.add(quantity);
        form.add(tfquantity);
        form.add(price);
        form.add(tfprice);
        form2.add(button);
        form2.add(bupd);

        mainFrame.add(title);
        mainFrame.add(show);
        mainFrame.add(controlPanel);
        mainFrame.add(pdel, BorderLayout.CENTER);
        mainFrame.add(add, BorderLayout.CENTER);
        mainFrame.add(form);
        mainFrame.add(form2);
        mainFrame.setVisible(true);
    }

    /**
     * Metoda care extrage obiectele de tip produs pentru a le afisa in tabelul din interfata
     * @param p - persistenta datelor din baza de date (tabelul produselor)
     */
    public void showTableDemo(ProductDAO p) {
        String[] columnNames = {"Id", "Name", "Quantity", "Price"};

        table = new JTable(pbll.tableData(p), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));
        table.setFillsViewportHeight(true);
        controlPanel.add(scrollPane);
        mainFrame.setVisible(true);
    }

    /**
     * Metoda care extrage campurile corespunzatoare unui rand selectat din tabelul de produse
     * @return un String care contine toate atributele produsului din randul selectat din tabel
     */
    public String[] getRand() {
        String[] valori = new String[5];
        int randSelectat = table.getSelectedRow();

        for(int i = 0; i < table.getColumnCount(); i++) {
            valori[i] = String.valueOf(table.getValueAt(randSelectat, i));
        }

        return valori;
    }

    /**
     *Getters
     */
    public Double getTfprice() {
        return Double.parseDouble(tfprice.getText());
    }

    public Integer getTfquantity() {
        return Integer.parseInt(tfquantity.getText());
    }

    public String getTfname() {
        return tfname.getText();
    }

    public Integer getTfid() {
        return Integer.parseInt(tfid.getText());
    }

    public void orderMessage() {
        JOptionPane.showMessageDialog(mainFrame, "Product contained in order! Can not delete it! ");
    }
}
