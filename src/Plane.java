public class Plane {
    private Crew crew;
    private double distance = 0;

    /**
     * Initialize Route
     *
     * @param individual
     *            A GA individual
     * @param crews
     *            The crews referenced
     */
    public Plane(Crew crew) {
        // Get individual's chromosome
        int chromosome[] = individual.getChromosome();
        // Create route
        this.plane = new Crew;
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
