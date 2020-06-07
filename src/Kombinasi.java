import java.util.ArrayList;
import java.util.List;

public class Kombinasi {

    int[] result;
    List<int[]> final_result;
    int n, length;

    public Kombinasi(int n,int length){
        this.result = new int[length];
        this.final_result = new ArrayList<int[]>();
        this.n = n;
        this.length = length;
    }

    protected void generateKombinasiRek(int iStartValue, int curIdx){
        if(curIdx == this.result.length){
            process();
        }
        else{
            for (int i = iStartValue; i <= n-this.result.length+curIdx+1; i++) {
                this.result[curIdx]=i;
                generateKombinasiRek(i+1,curIdx+1);
            }
        }
    }

    protected void process(){
        int[] arr = new int[n];
        for (int i = 0; i < result.length; i++) {
            arr[this.result[i]-1] = 1;
        }
        this.final_result.add(arr);
    }

    public void generateKombinasi(){
        generateKombinasiRek(1,0);
    }

    public int calcNumberSolution(){
        int pembilang = 1;
        int penyebut1 = 1;
        int penyebut2 = 1;
        for (int i = n; i > 1; i--) {
            pembilang*=i;
        }
        for (int i = n-length; i > 1; i--) {
            penyebut1*=i;
        }
        for (int i = length; i > 1; i--) {
            penyebut2*=i;
        }
        return pembilang/(penyebut1*penyebut2);
    }
}
