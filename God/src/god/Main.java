package god;

public class Main {
    public static void main(String[] args) {
        final Simulator simulator = new Simulator();
        Thread t1 = new Thread(new Runnable(){

            @Override
            public void run() {
                simulator.Run();
            }
            
        });
        Thread t2 = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    simulator.Update();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        });
        t1.start();
        t2.start();
    }
}
