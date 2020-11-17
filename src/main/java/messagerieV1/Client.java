package messagerieV1;

import java.io.*;
import java.net.*;
/**
 * Le processus client se connecte au site fourni dans la commande d'appel en
 * premier argument et utilise le port distant 9090.
 */
public class Client {
    static final int port = 9090;

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(args[0], port);
        System.out.println("SOCKET = " + socket);

        BufferedReader plec = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter pred =
                new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        String str = "bonjour";
        for (int i = 0; i < 10; i++) {
            pred.println(str+ ","  +i); // envoi d'un message
            str = plec.readLine(); // lecture de l'écho
        }
        System.out.println("END"); // message de terminaison
        pred.println("END");
        plec.close();
        pred.close();
        socket.close();
    }
}
