import model.Bid;
import model.GameStatus;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import static java.lang.System.in;

/**Clase que se encargará de la comunicación con cada cliente
 * Escuchara sus pujas y se enviará las actualizaciones*/
public class ClientHandler implements Runnable {
    private SSLSocket clientSocket;
    private ObjectOutputStream out;
    public List<ClientHandler> clients;
    public ClientHandler(SSLSocket clientSocket, List<ClientHandler> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
    }
    public void sendGameStatus(GameStatus status){
        try{
            if (out != null) {
                out.writeObject(status);
                out.flush();
                out.reset();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run(){
        try{
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while(true){
                Object obj = in.readObject();
                if(obj instanceof Bid) {
                    Bid puja = (Bid) obj;
                    synchronized (Auction_Server.class) {
                        if (puja.getAmount() > Auction_Server.currentStatus.getCurrentHighestBid()) {
                            Auction_Server.currentStatus.setCurrentHighestBid(puja.getAmount());
                            Auction_Server.broadcast(Auction_Server.currentStatus);
                        }
                    }
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
