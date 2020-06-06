import java.util.Arrays;

public class GA_Algorithm {

    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    protected int tournamentSize;

    public GA_Algorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
                            int tournamentSize) {

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }


    /**
     * Initialize population
     *
     * @param chromosomeLength The length of the individuals chromosome
     * @return population The initial population generated
     */
    public GA_Population initPopulation(int chromosomeLength){
        // Initialize population
        GA_Population population = new GA_Population(this.populationSize, chromosomeLength);
        return population;
    }

    /**
     * Check if population has met termination condition -- this termination
     * condition is a simple one; simply check if we've exceeded the allowed
     * number of generations.
     *
     * @param generationsCount
     *            Number of generations passed
     * @param maxGenerations
     *            Number of generations to terminate after
     * @return boolean True if termination condition met, otherwise, false
     */
    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }

    /**
     * Calculate individual's fitness value
     *
     * Fitness, in this problem, is inversely proportional to the route's total
     * distance. The total distance is calculated by the Route class.
     *
     * @param individual
     *            the individual to evaluate
     * @param cities
     *            the cities being referenced
     * @return double The fitness value for individual
     */
    public double calcFitness(GA_Individual individual, int[] crews_total_days_left){
        // Get fitness
        Schedule schedule = new Schedule(individual, crews_total_days_left);
        double fitness = schedule.getFitnessScore();

        // Store fitness
        individual.setFitness(fitness);

        return fitness;
    }

    /**
     * Evaluate population -- basically run calcFitness on each individual.
     *
     * @param population the population to evaluate
     * @param cities the cities being referenced
     */
    public void evalPopulation(GA_Population population, int[] crews_total_days_left){
        double populationFitness = 0;
        int size = 0;
        // Loop over population evaluating individuals and summing population fitness
        for (GA_Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual,crews_total_days_left);
            int total = 0;
            for (int i = 0; i < crews_total_days_left.length; i++) {
                total+= crews_total_days_left[i];
            }
            if(total <= 2){
                break;
            }
            else{
                size++;
            }
        }

        double avgFitness = populationFitness / size;
        population.setPopulationFitness(avgFitness);
    }

    /**
     * Selects parent for crossover using tournament selection
     *
     * Tournament selection was introduced in Chapter 3
     *
     * @param population
     *
     * @return The individual selected as a parent
     */
    public GA_Individual selectParent(GA_Population population) {
        // Create tournament
        GA_Population tournament = new GA_Population(this.tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            GA_Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }


    /**
     * Ordered crossover mutation
     *
     * Chromosomes in the TSP require that each city is visited exactly once.
     * Uniform crossover can break the chromosome by accidentally selecting a
     * city that has already been visited from a parent; this would lead to one
     * city being visited twice and another city being skipped altogether.
     *
     * Additionally, uniform or random crossover doesn't really preserve the
     * most important aspect of the genetic information: the specific order of a
     * group of cities.
     *
     * We need a more clever crossover algorithm here. What we can do is choose
     * two pivot points, add chromosomes from one parent for one of the ranges,
     * and then only add not-yet-represented cities to the second range. This
     * ensures that no cities are skipped or visited twice, while also
     * preserving ordered batches of cities.
     *
     * @param population
     * @return The new population
     */
    public GA_Population crossoverPopulation(GA_Population population){
        // Create new population
        GA_Population newPopulation = new GA_Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            // Get parent1
            GA_Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                // Find parent2 with tournament selection
                GA_Individual parent2 = this.selectParent(population);

                // Create blank offspring chromosome
                int offspringChromosome[] = new int[parent1.getChromosomeLength()];
                Arrays.fill(offspringChromosome, -1);
                GA_Individual offspring = new GA_Individual(offspringChromosome);

                // Get subset of parent chromosomes
                int substrPos1 = (int) (Math.random() * parent1.getChromosomeLength());
                int substrPos2 = (int) (Math.random() * parent1.getChromosomeLength());

                // make the smaller the start and the larger the end
                final int startSubstr = Math.min(substrPos1, substrPos2);
                final int endSubstr = Math.max(substrPos1, substrPos2);

                // Loop and add the sub tour from parent1 to our child
                for (int i = startSubstr; i < endSubstr; i++) {
                    offspring.setGene(i, parent1.getGene(i));
                }

                // Loop through parent2's city tour
                for (int i = 0; i < parent2.getChromosomeLength(); i++) {
                    int parent2Gene = i + endSubstr;
                    if (parent2Gene >= parent2.getChromosomeLength()) {
                        parent2Gene -= parent2.getChromosomeLength();
                    }

                    // If offspring doesn't have the city add it
                    if (offspring.containsGene(parent2.getGene(parent2Gene)) == false) {
                        // Loop to find a spare position in the child's tour
                        for (int ii = 0; ii < offspring.getChromosomeLength(); ii++) {
                            // Spare position found, add city
                            if (offspring.getGene(ii) == -1) {
                                offspring.setGene(ii, parent2.getGene(parent2Gene));
                                break;
                            }
                        }
                    }
                }

                // Add child
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }

        return newPopulation;
    }

    /**
     * Apply mutation to population
     *
     * Because the traveling salesman problem must visit each city only once,
     * this form of mutation will randomly swap two genes instead of
     * bit-flipping a gene like in earlier examples.
     *
     * @param population
     *            The population to apply mutation to
     * @return The mutated population
     */
    public GA_Population mutatePopulation(GA_Population population){
        // Initialize new population
        GA_Population newPopulation = new GA_Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            GA_Individual individual = population.getFittest(populationIndex);

            // Skip mutation if this is an elite individual
            if (populationIndex >= this.elitismCount) {
                // System.out.println("Mutating population member "+populationIndex);
                // Loop over individual's genes
                for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                    // System.out.println("\tGene index "+geneIndex);
                    // Does this gene need mutation?
                    if (this.mutationRate > Math.random()) {
                        // Get new gene position
                        int newGenePos = (int) (Math.random() * individual.getChromosomeLength());
                        // Get genes to swap
                        int gene1 = individual.getGene(newGenePos);
                        int gene2 = individual.getGene(geneIndex);
                        // Swap genes
                        individual.setGene(geneIndex, gene1);
                        individual.setGene(newGenePos, gene2);
                    }
                }
            }

            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }

        // Return mutated population
        return newPopulation;
    }

}