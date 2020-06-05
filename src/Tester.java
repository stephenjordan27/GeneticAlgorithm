public class Tester {
    public static void main(String[] args) {
        Kombinasi k = new Kombinasi(5,3);
        k.generateKombinasi();
        for (int[] result:k.final_result) {
            for (int object:result) {
                System.out.print(object);
            }
            System.out.println();
        }
    }
}
