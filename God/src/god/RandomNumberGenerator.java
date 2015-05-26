package god;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumberGenerator {
    private Random rand = new Random();
    private List<String> visited;
    public RandomNumberGenerator(){
        this.visited = new ArrayList<String>();
    }
    public String generatePlanetName(){
        String[] str ={"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Uranus","Neptune","Pluto","Gaspra","Eros","Ida"};
        int num = 0;
        //System.out.println(visited.size() + " " + str.length);
        if(visited.size()==str.length){System.out.println("No more planets can be added");return null;}
        while(true){
           num= rand.nextInt(str.length);
           if(visited.contains(str[num]) == false)break;
//           System.out.println(num);
        }
        String name = str[num];
//        visited.add(name);
        return name + rand.nextInt(str.length);
    }
    public int generateNumberRange(int range){
        return rand.nextInt(range);
    }
    public int generateCoordinate(){
        return rand.nextInt(50);
    }
    public int generateNumber(){
        return rand.nextInt(100);
    }
    public String generateName(EntityType et){
        int num = rand.nextInt(1000);
        return et.toString()+num;
    }
    public double generateStrength(){
        double strength = rand.nextInt(200);
        return strength;
    }
}
