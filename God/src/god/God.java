package god;


public class God extends Human{
    public God(String name, double energy, double size, double weight, Point2D position, double strength, State state) {
        super(name, energy, size, weight, position, strength, state);
        this.entity = EntityType.entity;
    }
    //constructor with parameter values
    public God() {

        super();
        RandomNumberGenerator rng = new RandomNumberGenerator();
        this.name=rng.generateName(EntityType.god);
        this.entity=EntityType.god;
    }
    //default constructor with random values
//    public void createPlanet(List<Planet> planets){
//        Planet planet = new Planet();
//        if(planet.getName()!=null)
//        planets.add(planet);
//        System.out.println("a new planet " + planet.getName() + " has been created...");
//    }
//    public void addPopulation(List<Planet> planets,Planet pl){
//        
//    }
//    public void destroyPopulation(Planet pl){
//       pl.destroyPopulation();
//    }
}
