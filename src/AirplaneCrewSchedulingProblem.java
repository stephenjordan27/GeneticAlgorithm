import java.util.Arrays;
import java.util.Random;


public class AirplaneCrewSchedulingProblem {

    public static void main(String[] args) {
        int totalWorking = 25;
        int numCrews = 6;
        int numPlanes = 4;
        int condition_day = 2;

        int[] crews_total_days_left = new int[numCrews];
        for (int i = 0; i < crews_total_days_left.length; i++) {
            crews_total_days_left[i] = condition_day;
        }

        // Initial GA
        Kombinasi k = new Kombinasi(numCrews,numPlanes);
        GA_Algorithm ga = new GA_Algorithm(k.calcNumberSolution(), 0, 0, 2, 5);

        // Initialize population
        GA_Population population = ga.initPopulation(numCrews, numPlanes, condition_day);

        // Keep track of current generation
        int generation = 1;
        int[] temp_crews_total_days_left = crews_total_days_left;
        int[] temp_working_days_in_a_row = new int[numCrews];

        // Start evolution loop
        do {
            int total = 0;
            for (int i = 0; i < crews_total_days_left.length; i++) {
                total+= crews_total_days_left[i];
            }
            if(total <= 2){
                for (int i = 0; i < crews_total_days_left.length; i++) {
                    crews_total_days_left[i] = condition_day;
                }
            }

            // Evaluate population
            ga.evalPopulation(population, crews_total_days_left);

            GA_Individual best = null;

            best = population.getFittest(0);
            for (int i = 0; i < best.getChromosome().length; i++) {
                temp_working_days_in_a_row[i] += best.getChromosome()[i];
            }
            int numPlanesFill = 0;

            for (int i = 0; i < temp_working_days_in_a_row.length; i++) {
                if(temp_working_days_in_a_row[i] < numPlanes){
                    numPlanesFill++;
                }
            }
            if(numPlanesFill < numPlanes) break;

            if(generation > 2){
                boolean overlimit = false;
                for (int i = 0; i < temp_working_days_in_a_row.length; i++) {
                    if (temp_working_days_in_a_row[i] == 3) {
                        overlimit = true;
                        break;
                    }
                }
                if(overlimit) {
                    best = population.getFittest2(0, temp_working_days_in_a_row);
                    for (int i = 0; i < best.getChromosome().length; i++) {
                        temp_working_days_in_a_row[i] = best.getChromosome()[i];
                    }
                }
            }


            System.out.println("Day "+generation+", Fitness score: " + population.getPopulationFitness() +
                    ", Chromosome: " + Arrays.toString(best.getChromosome()));

            for (int i = 0; i < best.getChromosome().length; i++) {
                if(best.getChromosome()[i]==1){
                    temp_crews_total_days_left[i] -= 1;
                }
            }



            crews_total_days_left = temp_crews_total_days_left;



            // Apply crossover
            population = ga.crossoverPopulation(population,numPlanes,condition_day);

            // Apply mutation
            population = ga.mutatePopulation(population);

            // Increment the current generation
            generation++;
        }
        while (ga.isTerminationConditionMet(generation, totalWorking) == false);

    }
}