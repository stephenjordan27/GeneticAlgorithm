import java.util.Random;

/**
 * Main, executive class for the Traveling Salesman Problem.
 *
 * We don't have a real list of cities, so we randomly generate a number of them
 * on a 100x100 map.
 *
 * The TSP requires that each city is visited once and only once, so we have to
 * be careful when initializing a random Individual and also when applying
 * crossover and mutation. Check out the GeneticAlgorithm class for
 * implementations of crossover and mutation for this problem.
 *
 * @author bkanber
 *
 */
public class AirplaneCrewSchedulingProblem {
    public static int maxGenerations = 10000;
    public static void main(String[] args) {
        // Create cities
        int numCrews = 5;
        int numDays = 10;
        int numPlanes = 3;

        Crew[] crews = new Crew[numCrews];
        Schedule[] schedules = new Schedule[numDays];
        Random random = new Random();

        for (int i = 0; i < numCrews; i++) {
            crews[i] = new Crew("Crew "+ (i+1) );
        }

        for (int i = 0; i < numDays; i++) { // untuk setiap schedule
            Plane[] planes = new Plane[numPlanes];
            for (int j = 0; j < numPlanes; j++) { // buat n pesawat dengan 1 kru
               planes[i] = new Plane(crews[random.nextInt(numCrews)]); // kru dirandom kru (0-4)
            }
            schedules[i] = new schedules(planes);
        }




        for(int dayIndex = 0; dayIndex < numDays; dayIndex++) {
            workingday[dayIndex] = random.nextInt(numCrews+1)+1;
        }

        // Loop to create random cities
        for (int crewIndex = 0; crewIndex < numCrews; crewIndex++) {

            // Add city
            crews[crewIndex] = new Crew(xPos, yPos);
        }

        // Initial GA
        GA_Algorithm ga = new GA_Algorithm(100, 0.001, 0.9, 2, 5);

        // Initialize population
        GA_Population population = ga.initPopulation(crews.length);

        // Evaluate population
        ga.evalPopulation(population, crews);

        Schedule startRoute = new Schedule(population.getFittest(0), crews);
        System.out.println("Start Distance: " + startRoute.getDistance());

        // Keep track of current generation
        int generation = 1;
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            Schedule schedule = new Schedule(population.getFittest(0), crews);
            System.out.println("G"+generation+" Best distance: " + plane.getDistance());

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population);

            // Evaluate population
            ga.evalPopulation(population, crews);

            // Increment the current generation
            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations.");
        Schedule route = new Schedule(population.getFittest(0), crews);
        System.out.println("Best distance: " + route.getDistance());

    }
}