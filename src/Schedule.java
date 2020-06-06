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
    private Plane[] planes;
    private double distance = 0;

    /**
     * Initialize Route
     *
     * @param individual
     *            A GA individual
     * @param crews
     *            The crews referenced
     */
    public Schedule(GA_Individual individual, Crew crews[]) {
        // Get individual's chromosome
        int chromosome[] = individual.getChromosome();
        // Create route
        this.schedule = new Crew[crews.length];
        for (int geneIndex = 0; geneIndex < chromosome.length; geneIndex++) {
            this.schedule[geneIndex] = crews[chromosome[geneIndex]];
        }
    }

    /**
     * Get route distance
     *
     * @return distance The route's distance
     */
    public double getDistance() {
        if (this.distance > 0) {
            return this.distance;
        }

        // Loop over cities in route and calculate route distance
        double totalDistance = 0;
        for (int cityIndex = 0; cityIndex + 1 < this.schedule.length; cityIndex++) {
            totalDistance += this.schedule[cityIndex].distanceFrom(this.schedule[cityIndex + 1]);
        }

        totalDistance += this.schedule[this.schedule.length - 1].distanceFrom(this.schedule[0]);
        this.distance = totalDistance;

        return totalDistance;
    }
}