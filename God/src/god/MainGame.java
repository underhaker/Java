package god;

public class MainGame {
    
    public void StartGame(){
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
                    e.printStackTrace();
                }
            }
        
        });
        t1.start();
        t2.start();
        }
    //runs up the game with 2 threads:1 for menu and 1 for updates
}
