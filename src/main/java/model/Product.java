package model;

/**
 *Modeleaza obiectele tabelei Product din baza de date
 */
public class Product {

    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;

    /**
     *
     * @param id - id-ul produsului
     * @param name -numele produsului
     * @param quantity - cantitatea de produs disponibila
     * @param price - pretul unui produs
     */
    public Product(Integer id, String name, Integer quantity, Double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     *Getterele
     */
    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    /**
     * Metoda care transforma un obicet de tip produs in String - pentru a facilita verificarea functionalitatilor
     * @return String-ul care descrie obiectele de tip produs
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
