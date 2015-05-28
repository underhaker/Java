package god;


public class Entity {
    protected String name;
    protected double energy;
    protected double size;
    protected double weight;
    protected Point2D position;
    protected double strength;
    protected State state;
    protected boolean isAlive = true;
    protected EntityType entity;
    final static private int PLANET_LENGHT = 400;
    //upper bound for entities to move
    public Entity() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        this.name = rng.generateName(EntityType.entity);
        this.energy = rng.generateStrength() * 2;
        this.size = rng.generateNumber();
        this.weight = rng.generateNumber();
        this.position = new Point2D(rng.generateCoordinate(), rng.generateCoordinate());
        this.strength = rng.generateStrength();
        this.state = State.Unknown;
        this.isAlive = true;
        this.entity = EntityType.entity;
    }
    //default constructor with random values
    public Entity(String name, double energy, double size, double weight, Point2D position, double strength, State state) {
        this.name = name;
        this.energy = energy;
        this.size = size;
        this.weight = weight;
        this.position = position;
        this.strength = strength;
        this.state = state;
        this.entity = EntityType.entity;
    }
    //constructor with parameter values
    public String getName() {
        return name;
    }
    //returns name of entity
    public void setName(String name) {
        this.name = name;
    }
    //sets name of entity
    public EntityType getEntity() {
        return entity;
    }
    //returns the entity's type
    public double getEnergy() {
        return energy;
    }
    //returns energy of entity
    public void setEnergy(double energy) {
        this.energy = energy;
    }
    //sets energy of entity
    public double getSize() {
        return size;
    }
    //returns size of entity
    public void setSize(double size) {
        this.size = size;
    }
    //sets size of entity
    public double getWeight() {
        return weight;
    }
    //returns weight of entity
    public void setWeight(double weight) {
        this.weight = weight;
    }
    //sets weight of entity
    public Point2D getPosition() {
        return position;
    }
    //returns position of entity
    public void setPosition(Point2D position) {
        this.position = position;
    }
    //sets position(x and y coordinate) of entity
    public double getStrength() {
        return strength;
    }
    //returns strength of entity
    public void setStrength(double strength) {
        this.strength = strength;
    }
    //sets strength of entity
    public State getState() {
        return state;
    }
    //gets state 
    public void setState(State state) {
        this.state = state;
    }
    //sets state from enum State
    public void Attack(Entity ent) {
        this.setState(State.Attacking);
        System.out.println("status:" + this.getName() + " attacked " + ent.getName() + " for " + this.getStrength()
                + " damage..");
        ent.setEnergy(ent.getEnergy() - this.getStrength());
        if (ent.getEnergy() <= 0) {
            System.out.println("status:" + ent.getName() + " has died...");
            isAlive = false;
        }
    }
    //void method which lowers the energy of the given entity
    public void Move() {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        int moveRand = rng.generateNumberRange(2);
        int deltaPositionX = 0;
        int deltaPositionY = 0;
        if (moveRand % 2 == 0) {
            deltaPositionX = position.getX() + rng.generateCoordinate();
            deltaPositionY = position.getY() + rng.generateCoordinate();
            if(deltaPositionX<=PLANET_LENGHT && deltaPositionY<=PLANET_LENGHT){
                position.setX(deltaPositionX);
                position.setY(deltaPositionY);
            }
        } 
        else if (moveRand % 2 == 1){
            deltaPositionX = position.getX() - rng.generateCoordinate();
            deltaPositionY = position.getY() - rng.generateCoordinate();
            if(deltaPositionX>=0 && deltaPositionY>=0){
                position.setX(deltaPositionX);
                position.setY(deltaPositionY);
            }
        }
        this.setState(State.Moving);
    }
    //void method which moves the entity to X position(X is randomly generated)
}
