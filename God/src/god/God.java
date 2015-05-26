package god;

public class God extends Human{
    public God(String name, double energy, double size, double weight, Point2D position, double strength, State state) {
        super(name, energy, size, weight, position, strength, state);
        this.entity = EntityType.entity;
    }
    public God() {
        super();
        RandomNumberGenerator rng = new RandomNumberGenerator();
        this.name=rng.generateName(EntityType.god);
        this.entity=EntityType.god;
    }
    public void createPlanet(){
        
    }
    public void addPopulation(Planet pl){
    }
    public void destroyPopulation(Planet pl){
       pl.destroyPopulation();
    }
}
