import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class AirplaneCrewSchedulingProblem {

    public static void main(String[] args) {
        // Parameter input untuk masalah ini
        Scanner sc = new Scanner (System.in);
        System.out.print("Jumlah hari penjadwalan: ");
        int totalWorkingDay = sc.nextInt(); // totalWorkingDay = 25
        System.out.print("Jumlah kru maskapai yang tersedia: ");
        int numCrews = sc.nextInt(); // numCrews = 6
        System.out.print("Jumlah pesawat yang tersedia: ");
        int numPlanes = sc.nextInt(); // numPlanes = 4
        System.out.print("Batas hari kru maskapai dapat bekerja : ");
        int condition_day = sc.nextInt(); // condition_day = 2;
        System.out.println();
        System.out.println("Hasil Penjadwalan: ");

        // Membuat tabel sisa hari bekerja
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

            // Mencari jadwal yang paling cocok berdasarkan nilai fitness tertinggi
            best = population.getFittest(0);
            for (int i = 0; i < best.getChromosome().length; i++) {
                temp_working_days_in_a_row[i] += best.getChromosome()[i];
            }


            // Melakukan cek apakah penjadwalan masih tersedia atau tidak
            int numPlanesFill = 0;
            for (int i = 0; i < temp_working_days_in_a_row.length; i++) {
                if(temp_working_days_in_a_row[i] < numPlanes){
                    numPlanesFill++;
                }
            }

            // Jika totalWorkingDay masih tersisa, tapi kemungkinan penjadwalan tidak lagi tersedia
            if(numPlanesFill < numPlanes) break;

            // Mencari jadwal yang paling cocok apabila sudah melebihi kapisitas hari seorang kru bekerja
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

            // Print hasil penjadwalan ke layar
            System.out.println("Day "+generation+", Fitness score: " + population.getPopulationFitness() +
                    ", Chromosome: " + Arrays.toString(best.getChromosome()));

            // Mengurangi kapasitas hari bekerja bagi kru yang hanya diassign ke masing-masing penerbangan
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
        while (ga.isTerminationConditionMet(generation, totalWorkingDay) == false);

    }
}