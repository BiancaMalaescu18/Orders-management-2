package start;


import presentation.ViewClient;
import presentation.ViewOrder;
import presentation.ViewProduct;

/**
 * Clasa implementata pentru pornirea aplicatiei
 */
public class App
{
    /**
     * Metoda care porneste aplicatia instantiand cele 3 view-uri pentru clienti, comenzi si produse
     *
     */
    public static void main( String[] args )
    {
        ViewClient v1 = new ViewClient();
        ViewOrder v2 = new ViewOrder();
        ViewProduct v3 = new ViewProduct();
    }
}
