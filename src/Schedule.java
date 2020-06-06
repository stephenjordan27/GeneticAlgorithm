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
    }

    /**
     * Get route distance
     *
     * @return distance The route's distance
     */
    public double getFitnessScore() {
        int total = 0;
        for (int i = 0; i < this.chromosome.length; i++) {
            try {
                if(crews_total_days_left[chromosome[i]-1] == 0){
                    total = 0;
                    break;
                }
                else{
                    total+= crews_total_days_left[chromosome[i]-1];
                    crews_total_days_left[chromosome[i]-1]-=1;
                }
            }
            catch (Exception e){
                System.out.println();
            }


        }
        return total;
    }

    public int[] getChromosome() {
        return chromosome;
    }
}