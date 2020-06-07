import java.util.Random;

public class GA_Individual {

    private int[] chromosome;
    private double fitness = -1;

    public GA_Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public GA_Individual(int chromosomeLength, int numPlanes, int condition_day, int index) {
        Random r = new Random();
        Kombinasi k = new Kombinasi(chromosomeLength,3);
        k.generateKombinasi();
        this.chromosome = k.final_result.get(index);
        this.fitness = numPlanes*condition_day;

    }

    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene] + ",";
        }
        return output;
    }

    public boolean containsGene(int gene) {
        for (int i = 0; i < this.chromosome.length; i++) {
            if (this.chromosome[i] == gene) {
                return true;
            }
        }
        return false;
    }

    public int[] getChromosome() {
        return this.chromosome;
    }

    public int getChromosomeLength() {
        return this.chromosome.length;
    }
    public void setGene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }
    public int getGene(int offset) {
        return this.chromosome[offset];
    }
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public double getFitness() {
        return this.fitness;
    }

}