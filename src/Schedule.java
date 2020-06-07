/**
 * The main Evaluation class for the TSP. It's pretty simple -- given an
 * Individual (ie, a chromosome) and a list of canonical cities, calculate the
 * total distance required to travel to the cities in the specified order. The
 * result returned by getDistance() is used by GeneticAlgorithm.calcFitness.
 *
 * @author bkanber
 *
 */

public class Schedule {
    private int[] chromosome,crews_total_days_left;
    private int fitness_score;

    /**
     * Initialize Route
     *
     * @param individual
     *            A GA individual
     * @param crews_total_days_left
     *            The crews referenced
     */
    public Schedule(GA_Individual individual, int[] crews_total_days_left) {
        // Get individual's chromosome
        this.chromosome = individual.getChromosome();
        this.crews_total_days_left = crews_total_days_left;
        this.fitness_score = 20;
    }

    /**
     * Get route distance
     *
     * @return distance The route's distance
     */
    public int calcFitnessScore() {
        int total = 0;
        for (int i = 0; i < this.chromosome.length; i++) {
            if(chromosome[i] == 1){
                total += crews_total_days_left[i];
            }
        }
        this.fitness_score = total;
        return total;
    }

    public int getFitnessScore() {
        return fitness_score;
    }

    public int[] getChromosome() {
        return chromosome;
    }
}