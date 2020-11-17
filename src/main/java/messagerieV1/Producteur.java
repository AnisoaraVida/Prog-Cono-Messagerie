package messagerieV1;

import java.util.List;

class Producteur implements Runnable {
    private final List<String> nosJobs;
    private final int maxJobs=4;
    private final String iD;
    public Producteur(List<String> nosJobs, String iD)
    {
        this.nosJobs = nosJobs;
        this.iD=iD;
    }

    private void prod(int i) throws InterruptedException
    {
        synchronized (nosJobs)
        {
            while (nosJobs.size() == maxJobs)
            {
                System.out.format("File pleine %s is waiting\n",iD);
                nosJobs.wait();
            }

            String njob= String.format("T_%d_of_%s",i,iD);
            nosJobs.add(njob);
            System.out.format("%s Produit %s\n", iD, njob);

            System.out.print("File :");
            for (String it: nosJobs)
                System.out.format(" %s ",it);
            System.out.println("");

            nosJobs.notify();
        }
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try
            {
                i++;
                prod(i);
                Thread.sleep((long)  (2000* Math.random()));

            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }

    }
}