package model;

/**
 *Modeleaza obiectele tabelei Order din baza de date
 */
public class Order {

    private Integer orderId;
    private Integer productId;
    private Integer clientId;
    private Integer quantity;
    private Double total;

    /**
     * Constructorul obiecteleor de tip comanda
     * @param orderId id-ul comenzii
     * @param productId id-ul produsului corespunzator din comanda
     * @param clientId id-ul clientului pentru care este comanda
     * @param quantity - cantitatea de produse comandata
     * @param total  - pretul total al comenzii
     */
    public Order(Integer orderId, Integer productId, Integer clientId, Integer quantity, Double total) {
        this.orderId = orderId;
        this.productId = productId;
        this.clientId = clientId;
        this.quantity = quantity;
        this.total = total;
    }

    /**
     *Getterele
     */
    public Double getTotal() {
        return total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getClientId() {
        return clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Metoda care transforma un obicet de tip order in String - pentru a facilita verificarea functionalitatilor
     * @return String-ul care descrie obiectele de tip order
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", clientId=" + clientId +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
