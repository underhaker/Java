package god;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class Simulator {
    private God player;
    private Scene scene;
    final static protected int PLANET_SIZE = 300;
    //maximum elements that can be added at a time to a planet
    final static protected int MAXIMUM_PLANETS = 9;
    //number of maximum planets
    public Simulator() {
        player = new God();
        scene = new Scene();
    }
    //default Constructor
    public void Run() {
        Menu();
    }
    //starts the Menu
    private void createPlanet() {
        if (scene.getPlanets().size() < MAXIMUM_PLANETS)
            scene.createPlanet();
        else
            System.out.println("No more planets can be added..");
    }
    //creates a new Planet with random variables and adds it to ArrayList
    private void destroyPopulation(String input) {
        String str[] = input.split(" ");
        String planetName = str[2];
        for (Planet pl : scene.getPlanets()) {
            if (pl.getName().equals(planetName)) {
                pl.destroyPopulation();
                break;
            }
        }
    }
    //removes population from given planet
    private void destroyPlanet(String input) {
        String str[] = input.split(" ");
        String planetName = str[1];
        for (Planet pl : scene.getPlanets()) {
            if (pl.getName().equals(planetName)) {
                scene.destroyPlanet(pl);
                break;
            }
        }
    }
    //removes given planet from ArrayList
    private void showStatistics() {
        for (Planet pl : scene.getPlanets())
            System.out.println("Planet:" + pl.getName() + " population:" + pl.getPopulationCount());
    }
    //prints each planet's population size
    private void addCreatures(String input) {
        String[] str = input.split(" ");
        String planetName = str[1];
        String entity = str[2];
        int size = Integer.parseInt(str[3]);
        for (Planet pl : scene.getPlanets()) {
            if (pl.getName().equals(planetName)) {
                switch (entity) {
                    case "entity": {
                        for (int i = 0; i < size; i++)
                            scene.createCreature(pl, EntityType.entity);
                    }
                        break;
                    case "animal": {
                        for (int i = 0; i < size; i++)
                            scene.createCreature(pl, EntityType.animal);
                    }
                        break;
                    case "human": {
                        for (int i = 0; i < size; i++)
                            scene.createCreature(pl, EntityType.human);
                    }
                        break;
                    case "god": {
                        for (int i = 0; i < size; i++)
                            scene.createCreature(pl, EntityType.god);
                    }
                        break;
                }
                break;
            }
        }
        System.out.println(size + " " + entity + "s added to " + str[1] + "...");
    }
    //adds n entities(or subclass units) to given planet
    private void showHelp() {
        System.out.println("Main Menu");
        System.out.println("Type \"create\" to create a new planet.");
        System.out.println("Type \"delete population <name of planet>\" to annihilate a planet's population.");
        System.out.println("Type \"destroy <name of planet>\" to destroy a planet.");
        System.out.println("Type \"statistic\" for statistics.");
        System.out.println("Type \"add <name of planet> <entity|animal|human|god> <count>\" to add number of entities to a planet.");
        System.out.println("Type \"help\" for instructions.");
        System.out.println("Type \"exit\" to exit.");
    }
    //shows the main menu
    private void removeDeadEntities(List<Entity> entities) {

        for (Entity et : entities) {
            if (et.isAlive == false)
                entities.remove(et);
        }
    }
    //updates the ArrayList of entities with dead ones removed
    private void moveEntities(List<Entity> entities) throws InterruptedException {
        for (Entity et : entities) {
            Thread.sleep(100);
            et.Move();
        }
    }
    //iterates through entities ArrayList and performs Move method on them
    private void executeAnAction(Planet pl, List<Entity> entities) throws InterruptedException {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        int actionRand = 0;
        int attackRand = 0;
        for (Entity et1 : entities) {
            for (Entity et2 : entities)
                if (!et1.equals(et2)) {
                    if(pl.isDestroyed() == true)break;
                    Thread.sleep(500);
                    if (new Point2D(0, 0).getDistance(et1.getPosition(), et2.getPosition()) <= 20) {
                        actionRand = rng.generateNumberRange(3);
                        if (actionRand % 3 == 0) {
                            attackRand = rng.generateNumberRange(2);
                            if (attackRand % 2 == 0)
                                et1.Attack(et2);
                            else if (attackRand % 2 == 1)
                                et2.Attack(et1);
                            if (et1.isAlive == false)
                                entities.remove(et1);
                            if (et2.isAlive == false)
                                entities.remove(et2);
                            break;
                        } 
                        else if (actionRand % 3 == 1 && (et1.isAlive == true && et2.isAlive == true)){
                            scene.createCreature(pl, et1.getEntity());
                            System.out.println("status:" + et1.getName() + " and " + et2.getName() + " have created a new creature..");
                            break;
                        }
                        else if (actionRand % 3 == 2){
                            try {
                                doStuff(et1);
                                break;
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    
                }
        }
    }
    //chooses randomly an action 
    private void doStuff(Entity entity) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException,
            InterruptedException {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        String[] methodNames = { "Analyze", "Sleep", "Eat", "SearchingForFood" };
        Thread.sleep(3000);
        int num = rng.generateNumberRange(methodNames.length);
        Method[] methods = entity.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodNames[num])) {
                method.invoke(entity.getClass().newInstance());
                break;
            }
        }
    }

    //invokes a method that does a action based on random choosing by using reflection
    public void Update() throws InterruptedException {
        while (true) {
            for (Planet pl : scene.getPlanets()) {
                List<Entity> entities = pl.getPopulation();
                if (pl.isDestroyed() == false) {
                    try {
                        moveEntities(entities);
                        executeAnAction(pl, entities);
                        removeDeadEntities(entities);
//                        doStuff(entities);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //updates each entity's position and performs an action chosen by given methods
    private boolean checkValidInput(String input) {
        String[] strArr = input.split(" ");
        List<Planet> planets = scene.getPlanets();
        if (strArr[0].equals("destroy")) {
            for (Planet pl : planets) {
                if (pl.getName().equals(strArr[1]))
                    return true;
            }
        }
        
        if (strArr[0].equals("delete") && strArr[1].equals("population")) {
            for (Planet pl : planets) {
                if (pl.getName().equals(strArr[2]))
                    return true;
            }
        }
        
        if (strArr[0].equals("add")) {
            for (Planet pl : planets) {
                if (pl.getName().equals(strArr[1])) {
                    boolean isEntity = strArr[2].equals(EntityType.entity.toString())
                            || strArr[2].equals(EntityType.animal.toString())
                            || strArr[2].equals(EntityType.human.toString())
                            || strArr[2].equals(EntityType.god.toString());
                    if (isEntity == true) {
                        int entityCount = Integer.parseInt(strArr[3]);
                        if (entityCount > 0 && entityCount < PLANET_SIZE) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    //boolean function which checks for valid input
    public void Menu() {
        Scanner in = new Scanner(System.in);
        String input;
        showHelp();
        while (true) {
            System.out.println("Enter command:");
            input = new StringBuilder(in.nextLine()).toString();
            if (input.equals("exit")) {
                in.close();
                System.out.println("Exiting game..");
                System.exit(0);
            }
            if (input.equals("create")) {
                createPlanet();
                continue;
            }
            if (input.contains("delete population")) {
                if (checkValidInput(input) == true)
                    destroyPopulation(input);
                else
                    System.out.println("Invalid Command..");
                continue;
            }
            if (input.contains("destroy")) {
                if (checkValidInput(input) == true)
                    destroyPlanet(input);
                else
                    System.out.println("Invalid Command..");
                continue;
            }
            if (input.equals("statistic")) {
                showStatistics();
                continue;
            }
            if (input.equals("help")) {
                showHelp();
                continue;
            }
            if (input.contains("add")) {
                if (checkValidInput(input) == true)
                    addCreatures(input);
                else
                    System.out.println("Invalid Command..");
                continue;
            }
            System.out.println("Invalid command!");

        }

    }
    //main menu in which a command is given from the console
}
