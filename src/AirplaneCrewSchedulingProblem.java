import java.util.Arrays;
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
        int numPlanes = 3;
        int condition_day = 2;

        int[] crews_total_days_left = new int[numCrews];
        for (int i = 0; i < crews_total_days_left.length; i++) {
            crews_total_days_left[i] = condition_day;
        }

        // Initial GA
        GA_Algorithm ga = new GA_Algorithm(10, 0.001, 0.9, 2, 5);

        // Initialize population
        GA_Population population = ga.initPopulation(numCrews);

        // Keep track of current generation
        int generation = 1;
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
            // Print fittest individual from population
            Schedule schedule = new Schedule(population.getFittest(0),crews_total_days_left);
            System.out.println("G"+generation+", Fitness score: " + schedule.getFitnessScore() +
                    ", Chromosome: " + Arrays.toString(schedule.getChromosome()));

            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population);

            // Evaluate population
            ga.evalPopulation(population, crews_total_days_left);

            // Increment the current generation
            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations.");
//        Schedule route = new Schedule(population.getFittest(0), crews);
//        System.out.println("Best distance: " + route.getDistance());

    }
}