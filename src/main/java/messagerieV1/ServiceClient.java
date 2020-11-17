package messagerieV1;

import java.net.*;
import java.util.concurrent.TimeoutException;
import java.io.*;

public class ServiceClient implements Runnable{
    //String to finish the communication  ici c est ctrl-d
    //private static final  	String Finish="end";

    private String Finish=""+(char) 4;
    private  Socket ma_connection;
    private  String id;

    private  void terminer(){
        try{
            if (ma_connection != null)
            {System.out.format("Terminaison pour %s\n", id); ma_connection.close();}
        }
        catch (IOException e) {
            System.out.format("Terminaison pour %s\n", id);
            e.printStackTrace();
        }
        return;
    }

    public ServiceClient(Socket la_connection, String mid)
    {
        ma_connection= la_connection;
        id=mid;
        System.out.format("Thread T__%s créé pour traiter la connection\n",id);
    }

    public  void run(){
        // Phase d initialisation
        BufferedReader flux_entrant=null;
        PrintWriter ma_sortie =null;
        try{
            InputStreamReader isr = new InputStreamReader(ma_connection.getInputStream(), "UTF-8");
            flux_entrant = new BufferedReader(isr) ; // file d'entrée
            // flux de sortie en mode autoflush
            ma_sortie = new PrintWriter(ma_connection.getOutputStream() , true);
            String c_ip = ma_connection.getInetAddress().toString() ;
            int c_port= ma_connection.getPort();
            System.out.format("[%s] client admis IP %s  sur le port %d\n", id,c_ip, c_port);
            ma_sortie.format("[%s] : %s  sur le port %d, \n" ,  id, c_ip, c_port );
        }
        catch (Exception e1) {
            System.out.println("Erreur d initialisation") ;e1.printStackTrace();}

        String  message_lu = new String();
        int line_num =0 ;
        // Fin de l initialisation

        // Boucle principale //
        while ( true  )
        {
            try {
                message_lu = flux_entrant.readLine();
            }
            catch (IOException ioe) { ;}
            if (message_lu == null){
                System.out.println( "Client deconnecté\n" )  ;
                terminer();
                return; }
            System.out.format( "%s [line_%d]--> [%s]]\n", id,line_num, message_lu);
            if (message_lu.contains(Finish) ){
                System.out.format ("[%s] :  [%s] recu, Transmission finie\n",id,message_lu);
                ma_sortie.println("Stop connexion");
                terminer();
                return;
            }
            line_num++;
        }

    }}
