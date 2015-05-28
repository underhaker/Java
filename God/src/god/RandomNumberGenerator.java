package god;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumberGenerator {
    private Random rand;
    private List<String> visited;
    //ArrayList for which planet names are currently taken
    public RandomNumberGenerator(){
        this.visited = new ArrayList<String>();
        this.rand = new Random();
    }
    //default constructor
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
    //generates a planet name from an array of names
    public int generateNumberRange(int range){
        return rand.nextInt(range);
    }
    //generates a number from 0 to range
    public int generateCoordinate(){
        return rand.nextInt(50);
    }
    //generates a coordinate from 0 to 50
    public int generateNumber(){
        return rand.nextInt(100);
    }
    //generates a number from 0 to 100
    public String generateName(EntityType et){
        int num = rand.nextInt(1000);
        return et.toString()+num;
    }
    //generates an entity name by joining the entity type and a random number
    public double generateStrength(){
        double strength = rand.nextInt(200);
        return strength;
    }
    //generates a strength number from 0 to 200
}
