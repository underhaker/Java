package god;


public class Animal extends Entity {
    public Animal(){
        super();
        RandomNumberGenerator rng = new RandomNumberGenerator();
        this.name=rng.generateName(EntityType.animal);
        this.entity=EntityType.animal;
    }
    public Animal(String name, double energy, double size, double weight, Point2D position, double strength, State state) {
        super(name, energy, size, weight, position, strength, state);
        this.entity = EntityType.entity;
    }

    public void Eat() {
        this.setState(State.Eating);
        System.out.println("status:" + this.getName() + " is " + this.getState() + "...");
    }

    public void Sleep() {
        this.setState(State.Sleeping);
        System.out.println("status:" + this.getName() + " is " + this.getState()+ "...");
    }

    public void SearchingForFood() {
        this.setState(State.SearchingForFood);
        System.out.println("status:" + this.getName() + " is " + this.getState()+ "...");
    }

    
}
