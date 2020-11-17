package messagerie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        final Socket clientSocket;
        final BufferedReader recu;
        final PrintWriter env;
        final Scanner sc = new Scanner(System.in);

        try {
            clientSocket = new Socket("127.0.0.1", 9090);
            env = new PrintWriter(clientSocket.getOutputStream());
            recu = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread envoyer = new Thread(new Runnable() {
                String message;

                @Override
                public void run() {
                    while (true) {
                        message = sc.nextLine();
                        env.println(message);
                        env.flush();
                    }
                }
            });
            envoyer.start();

            Thread recevoir = new Thread(new Runnable() {
                String message;

                @Override
                public void run() {
                    try {
                        message = recu.readLine();
                        while (message != null) {
                            System.out.println("Serveur : " + message);
                            message = recu.readLine();
                        }
                        System.out.println("Serveur déconecté");
                        env.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

