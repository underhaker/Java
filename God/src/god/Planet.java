package god;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Planet {
    private String name;
    private List<Entity> population;
    private boolean isDestroyed;
    private RandomNumberGenerator rng = new RandomNumberGenerator();
    public Planet(){
        population = new CopyOnWriteArrayList <Entity>();
        this.isDestroyed = false;
        this.name=rng.generatePlanetName();
        
    }
    public List<Entity> getPopulation(){
        return population;
    }
    public String getName(){
        return this.name;
    }
    public int getPopulationCount(){
        return population.size();
    }
    public void setPopulation(List<Entity> l){
        this.population=l;
    }
    public boolean isDestroyed(){
        return this.isDestroyed;
    }
    public void setDestroyed(){
        this.isDestroyed = true;
    }
    public void addPopulation(Entity e){
        population.add(e);
    }
    public void destroyPopulation(){
        population.clear();
        System.out.println(this.getName() + " had its population destroyed..");
    }
}
