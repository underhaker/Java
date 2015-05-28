package god;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Planet {
    private String name;
    private List<Entity> population;
    //Concurrent ArrayList for population on planet
    private boolean isDestroyed;
    private RandomNumberGenerator rng = new RandomNumberGenerator();
    public Planet(){
        population = new CopyOnWriteArrayList <Entity>();
        this.isDestroyed = false;
        this.name=rng.generatePlanetName();
        
    }
    //default constructor
    public List<Entity> getPopulation(){
        return population;
    }
    //returns a list of the population
    public String getName(){
        return this.name;
    }
    //returns the name of the planet
    public int getPopulationCount(){
        return population.size();
    }
    //returns size of population
    
    public void setPopulation(List<Entity> l){
        this.population=l;
    }
    //sets this planet's population
    
    public boolean isDestroyed(){
        return this.isDestroyed;
    }
    //returns true if planet is destroyed
    
    public void setDestroyed(){
        this.isDestroyed = true;
    }
    //changes planet to destroyed
    
    public void addPopulation(Entity e){
        population.add(e);
    }
    //adds an entity to population
    
    public void destroyPopulation(){
        population.clear();
        System.out.println(this.getName() + " had its population destroyed..");
    }
    //removes population from planet
    
}
