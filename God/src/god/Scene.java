package god;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scene {
    private List<Planet> planets = new CopyOnWriteArrayList<Planet>();
    //concurrent ArrayList
    public List<Planet> getPlanets(){
        return planets;
    }
    //returns an ArrayList of all planets
    public void createCreature(Planet pl,EntityType et){
        RandomNumberGenerator rng = new RandomNumberGenerator();
        switch(et){
            case entity:
            pl.addPopulation(new Entity(rng.generateName(EntityType.entity), 2*rng.generateStrength(), rng.generateNumber(), rng.generateNumber(),new Point2D(rng.generateCoordinate(),rng.generateCoordinate()), rng.generateStrength(), State.Unknown));break;
            case animal:
            pl.addPopulation(new Animal(rng.generateName(EntityType.animal), 2*rng.generateStrength(), rng.generateNumber(), rng.generateNumber(),new Point2D(rng.generateCoordinate(),rng.generateCoordinate()), rng.generateStrength(), State.Unknown));break;
            case human:
            pl.addPopulation(new Human(rng.generateName(EntityType.human), 2*rng.generateStrength(), rng.generateNumber(), rng.generateNumber(),new Point2D(rng.generateCoordinate(),rng.generateCoordinate()), rng.generateStrength(), State.Unknown));break;
            case god:
            pl.addPopulation(new God(rng.generateName(EntityType.god), 2*rng.generateStrength(), rng.generateNumber(), rng.generateNumber(),new Point2D(rng.generateCoordinate(),rng.generateCoordinate()), rng.generateStrength(), State.Unknown));break;
            default:
                break;
        }
        
    }
    //creates a new entity(or subclass unit) with random variables and adds it to the planet's population
    public void createPlanet(){
        Planet planet = new Planet();
        if(planet.getName()!=null)
        planets.add(planet);
        System.out.println("a new planet " + planet.getName() + " has been created...");
    }
    //adds a new planet(created with random variables) to the ArrayList
    public void destroyPlanet(Planet pl){
        planets.remove(pl);
        pl.destroyPopulation();
        pl.setDestroyed();
        System.out.println(pl.getName() + " has been destroyed..");
    }
    //removes the given planet from the ArrayList
}
