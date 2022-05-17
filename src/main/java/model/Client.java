package model;

/**
 *Modeleaza obiectele tabelei Client din baza de date
 */
public class Client {

    private Integer id;
    private String name;
    private String email;
    private String address;

    /**
     * Contructorul pentru obiecte de tip Client
     * @param id - id-ul clientului
     * @param name - numele clientului
     * @param email - e-mailul clientului
     * @param address - adresa clientului
     */
    public Client(Integer id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    /**
     *Getterele
     */
    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Metoda care transforma un obicet de tip Client in String - pentru a facilita verificarea functionalitatilor
     * @return String-ul care descrie obiectele de tip Client
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
