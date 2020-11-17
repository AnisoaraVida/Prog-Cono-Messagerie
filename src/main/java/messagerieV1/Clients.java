package messagerieV1;

public class Clients {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String mon_id = String.format("Client_%d", i);
            Thread_Client currrent_client = new Thread_Client(mon_id);
            Thread myThread= new Thread(currrent_client);
            myThread.start();
        }

    }

}
