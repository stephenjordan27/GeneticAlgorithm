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

    public GA_Population initPopulation(int chromosomeLength, int numPlanes, int condition_day){
        GA_Population population = new GA_Population(this.populationSize, chromosomeLength, numPlanes, condition_day);
        return population;
    }

    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }

    public double calcFitness(GA_Individual individual, int[] crews_total_days_left){
        Schedule schedule = new Schedule(individual, crews_total_days_left);
        double fitness = schedule.calcFitnessScore();
        individual.setFitness(fitness); // set individual fitness
        return fitness;
    }

    public void evalPopulation(GA_Population population, int[] crews_total_days_left){
        for (GA_Individual individual : population.getIndividuals()) {
            this.calcFitness(individual,crews_total_days_left);
        }
    }

    public GA_Individual selectParent(GA_Population population) {
        GA_Population tournament = new GA_Population(this.tournamentSize);
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            GA_Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }
        return tournament.getFittest(0);
    }


    public GA_Population crossoverPopulation(GA_Population population,int numPlanes, int condition_day){
        GA_Population newPopulation = new GA_Population(population.size());
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            GA_Individual parent1 = population.getFittest(populationIndex);
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
                GA_Individual offspring = new GA_Individual(parent1.getChromosomeLength(),numPlanes,condition_day,populationIndex);
                GA_Individual parent2 = selectParent(population);
                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
                    if (0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            }
            else {
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;
    }

    public GA_Population mutatePopulation(GA_Population population){
        GA_Population newPopulation = new GA_Population(this.populationSize);
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            GA_Individual individual = population.getFittest(populationIndex);
            for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if (populationIndex > this.elitismCount) {
                    if (this.mutationRate > Math.random()) {
                        int newGene = 1;
                        if (individual.getGene(geneIndex) == 1) {
                            newGene = 0;
                        }
                        individual.setGene(geneIndex, newGene);
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }



}