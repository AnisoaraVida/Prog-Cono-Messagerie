package messagerie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {

    public static void main(String[] test) {

        final ServerSocket serveurSocket  ;
        final Socket clientSocket ;
        final BufferedReader recu;
        final PrintWriter env;
        final Scanner sc=new Scanner(System.in);

        try {
            serveurSocket = new ServerSocket(9090);
            clientSocket = serveurSocket.accept();
            env = new PrintWriter(clientSocket.getOutputStream());
            recu = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
            Thread envoyer= new Thread(new Runnable() {
                String messsage;
                @Override
                public void run() {
                    while(true){
                        messsage = sc.nextLine();
                        env.println(messsage);
                        env.flush();
                    }
                }
            });
            envoyer.start();

            Thread recevoir= new Thread(new Runnable() {
                String message ;
                @Override
                public void run() {
                    try {
                        message = recu.readLine();
                        while(message!=null){
                            System.out.println("Client : " + message);
                            message = recu.readLine();
                        }
                        System.out.println("Client déconecté");
                        env.close();
                        clientSocket.close();
                        serveurSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

