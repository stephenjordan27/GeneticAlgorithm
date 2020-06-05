import java.util.Random;

//Individual class
class Individual {

    int fitness = 0;
    int[] genes = null;
    int geneLength = 0;

    public Individual(int geneLength) {
        Random rn = new Random();
        this.genes = new int[geneLength];
        this.geneLength = geneLength;

        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2); //Agar nilai gen ada pada rentang nilai [0,1]
        }

        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        for (int i = 0; i < geneLength; i++) {
            if (genes[i] == 1) {
                ++fitness;
            }
        }
    }

}