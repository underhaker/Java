package god;


public class Human extends Animal{
    public Human(){
        super();
        RandomNumberGenerator rng = new RandomNumberGenerator();
        this.name=rng.generateName(EntityType.human);
        this.entity=EntityType.human;
    }
    public Human(String name, double energy, double size, double weight, Point2D position, double strength, State state) {
        super(name, energy, size, weight, position, strength, state);
        this.entity=EntityType.human;
    }
    public void Analyze(){
        this.setState(State.Analyzing);
        System.out.println("status:" + this.getName() + " is " + this.getState() + "...");
    }

}
