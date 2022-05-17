package presentation;

import bll.ClientBLL;
import dao.ClientDAO;
import dao.ProductDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Fereastra corespunzatoare tabelei de clienti; permite operatii de tip CRUD pe clienti
 */
public class ViewClient {

    private ClientBLL cbll;
    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JPanel form;
    private JPanel form2;
    private JPanel pdel;
    private JLabel title = new JLabel("-- Clients table --");
    private JLabel space = new JLabel("                                                                              ");
    private JLabel space2 = new JLabel("                                                                             ");
    private JLabel add = new JLabel("Add/update clients:");
    private JLabel id = new JLabel("Id:");
    private JTextField tfid = new JTextField();
    private JLabel name1 = new JLabel("Name:");
    private JTextField tfname = new JTextField();
    private JLabel email = new JLabel("Email:");
    private JTextField tfemail = new JTextField();
    private JLabel address = new JLabel("Address:");
    private JTextField tfaddress = new JTextField();

    private JButton button = new JButton("Add");
    private JButton bupd = new JButton("Update");
    private JButton show = new JButton("Show");
    private JButton bdel = new JButton("Delete");
    private JLabel del = new JLabel("Delete selected client from table:");
    private JTable table;

    /**
     * Constructorul view-ului ; modelarea tuturor elementelor de interfata grafica
     */
    public ViewClient() {

        cbll = new ClientBLL(this);

        mainFrame = new JFrame("Warehouse - Clients");
        mainFrame.getContentPane().setBackground(new Color(229, 204, 255));
        mainFrame.setBounds(0, 0, 950, 500);
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

        email.setFont(new Font("Serif", Font.PLAIN, 20));

        tfemail.setFont(new Font("Serif", Font.PLAIN, 20));
        tfemail.setPreferredSize(new Dimension(150,25));

        address.setFont(new Font("Serif", Font.PLAIN, 20));

        tfaddress.setFont(new Font("Serif", Font.PLAIN, 20));
        tfaddress.setPreferredSize(new Dimension(150,25));

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
                ViewClient v = new ViewClient();
                v.showTableDemo(ClientBLL.view());


            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbll.add();
            }
        });

        bdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cbll.delete();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        bupd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbll.update();

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
        form.add(email);
        form.add(tfemail);
        form.add(address);
        form.add(tfaddress);
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
     * Metoda care extrage obiectele de tip client pentru a le afisa in tabelul din interfata
     * @param p - persistenta datelor din baza de date
     */
    public void showTableDemo(ClientDAO p) {
        String[] columnNames = {"Id", "Name", "Email", "Address"};

        table = new JTable(cbll.tableData(p), columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 200));
        table.setFillsViewportHeight(true);
        controlPanel.add(scrollPane);
        mainFrame.setVisible(true);
    }

    /**
     * Metoda care extrage campurile corespunzatoare unui rand selectat din tabelul de clienti
     * @return un String care contine toate atributele clientului din randul selectat din tabel
     */
    public String[] getRand() {
        String[] valori = new String[4];
        int randSelectat = table.getSelectedRow();

        for(int i = 0; i < table.getColumnCount(); i++) {
            valori[i] = String.valueOf(table.getValueAt(randSelectat, i));
        }

        return valori;
    }

    /**
     *Getters
     */
    public String getTfaddress() {
        return tfaddress.getText();
    }

    public String getTfemail() {
        return tfemail.getText();
    }

    public String getTfname() {
        return tfname.getText();
    }

    public Integer getTfid() {
        return Integer.parseInt(tfid.getText());
    }

    /**
     * Pop-up-ul care apare cand un client nu se poate sterge din baza de date deoarece are o comanda asociata in tabelul de comenzi
     */
    public void orderMessage() {
        JOptionPane.showMessageDialog(mainFrame, "Client associated with order! Can not delete it! ");
    }
}
