import model.AuctionItem;
import model.GameStatus;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Auction_Server {
    private static List<ClientHandler> listClient = new CopyOnWriteArrayList<>();
    public static GameStatus currentStatus;

    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.keyStore", "server.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");

        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        // Quitamos el try-with-resources del socket para que no se cierre prematuramente
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(8443);

        System.out.println("Secure server running on port 8443...");

        while(listClient.size() < 2) {
            SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
            ClientHandler handler = new ClientHandler(clientSocket, listClient);
            listClient.add(handler);
            new Thread(handler).start();
            System.out.println("Client connected! Total clients: " + listClient.size());
        }

        System.out.println("Maximum clients connected. Starting auction...");
        // Lanzamos la subasta en un hilo aparte para que no bloquee
        new Thread(() -> logicAuction()).start();
    }

    public static void logicAuction() {
        List<AuctionItem> items = loadItems(); // Asegúrate de tener este método creado
        for (AuctionItem item : items) {
            currentStatus = new GameStatus("New item!", item, item.getStartingPrice(), "Ninguno");
            broadcast(currentStatus);

            try {
                Thread.sleep(30000); // 30 segundos de espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentStatus.setMessage("Sold to: " + currentStatus.getWinnerName());
            broadcast(currentStatus);
        }
    }

    public static void broadcast(GameStatus status) {
        for (ClientHandler client : listClient) {
            client.sendGameStatus(status);
        }
    }

    // No olvides crear este método para que no te dé error
    private static List<AuctionItem> loadItems() {
        List<AuctionItem> list = new ArrayList<>();
        // Como tu constructor es privado, si te da error al compilar,
        // cámbialo a "public" en la clase AuctionItem.
        list.add(new AuctionItem(1, "Reloj de Oro", "Un reloj antiguo de 24 quilates", 500.0));
        list.add(new AuctionItem(2, "Cuadro Moderno", "Una obra abstracta muy colorida", 150.0));
        list.add(new AuctionItem(3, "Moneda Rara", "Moneda del imperio romano", 1000.0));
        return list;
    }
}