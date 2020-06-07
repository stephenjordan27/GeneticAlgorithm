import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GA_Population {
    private GA_Individual population[];
    private double populationFitness = -1;

    public GA_Population(int populationSize) {
        this.population = new GA_Individual[populationSize];
    }

    public GA_Population(int populationSize, int chromosomeLength, int numPlanes, int condition_day) {
        this.population = new GA_Individual[populationSize];
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            GA_Individual individual = new GA_Individual(chromosomeLength, numPlanes, condition_day,individualCount);
            this.population[individualCount] = individual;
        }
    }

    public GA_Individual getFittest(int offset) {
        Arrays.sort(this.population, new Comparator<GA_Individual>() {
            @Override
            public int compare(GA_Individual o1, GA_Individual o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }
        });
        setPopulationFitness(this.population[offset].getFitness());
        return this.population[offset];
    }

    public GA_Individual getFittest2(int offset,int[] temp_working_days_in_a_row) {
        Arrays.sort(this.population, new Comparator<GA_Individual>() {
            @Override
            public int compare(GA_Individual o1, GA_Individual o2) {
                for (int i = 0; i < temp_working_days_in_a_row.length; i++) {
                    if(temp_working_days_in_a_row[i] == 3){
                        if (o1.getChromosome()[i] == 1 && o2.getChromosome()[i] != 1) {
                            return 1;
                        }
                        else if (o1.getChromosome()[i] != 1 && o2.getChromosome()[i] == 1) {
                            return -1;
                        }
                        else{
                            return 1;
                        }
                    }
                }
                return 0;
            }
        });
        setPopulationFitness(this.population[offset].getFitness());
        return this.population[offset];
    }

    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            GA_Individual a = population[index];
            population[index] = population[i];
            population[i] = a;
        }
    }

    public GA_Individual[] getIndividuals() { return this.population; }
    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }
    public double getPopulationFitness() {
        return this.populationFitness;
    }
    public int size() {
        return this.population.length;
    }
    public GA_Individual setIndividual(int offset, GA_Individual individual) {
        return population[offset] = individual;
    }
    public GA_Individual getIndividual(int offset) {
        return population[offset];
    }



}