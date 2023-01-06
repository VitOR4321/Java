import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class BinPacking {

    public static int B;
    public static int N;
    public static int Popt;
    public static int[] itemsList;

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("binpack1.txt");
        Scanner scanner = new Scanner(file);
        String[] line = scanner.nextLine().split(" ");
        B = Integer.parseInt(line[0]);
        N = Integer.parseInt(line[1]);
        Popt = Integer.parseInt(line[2]);
        int[] items = new int[N];
        for(int i=0; i<N; i++)
            items[i] = Integer.parseInt(scanner.nextLine());
        scanner.close();
        System.out.println("Optymalna liczba pojemników wynosi: "+Popt);
        System.out.println();

        int nextIndex = 0;
        int Psol = 0;
        int[] binIndexs = new int[N];
        itemsList = new int[N];
        int[] L = permutation(N);
        for(int i=0;i<L.length;i++){
            itemsList[i] = i; //dane w kolejności z pliku
            itemsList[i] = items[L[i]]; //dane w kolejności losowo
        }

        //Next-Fit (NF)
        NF(binIndexs,nextIndex,Psol);
        //First-Fit (FF)
        FF(binIndexs,nextIndex,Psol);
        //Best-Fit (BF)
        BF(binIndexs,nextIndex,Psol);
        //Worst-Fit (WF)
        WF(binIndexs,nextIndex,Psol);
        //First-Fit-Decreasing (FFD)
        FFD(binIndexs,nextIndex,Psol);
    }




    // binIndexs - tablica indeksów pojemników

    public static int objectFunction(int[] binIndexs){
        LinkedList<Integer> usedBins = new LinkedList<>();
        int sum = 0;
        for(int i=0;i<binIndexs.length;i++){
            if(!usedBins.contains(binIndexs[i]) && binIndexs[i]>=0){
                sum++;
                usedBins.add(binIndexs[i]);
            }
        }
        return sum;
    }
    // binIndex - index pojemnika w liście indeksów
    public static  int itemsInBinCount(int[] binIndexs, int binIndex){
        int sum=0;
        for(int i=0;i<binIndexs.length;i++){
            if(binIndexs[i]==binIndex) {
                sum += itemsList[i];
            }
        }
        return sum;
    }

    // itemSum - zmienna posiadająca sume pojemności przedmiotów,
    // zmienna potrzebna do sprawdzania ograniczenia do pojemności pojemnika
    // con - funkcja ograniczająca
    public static boolean con(int itemSum) {
        return itemSum<=B;
    }

    //permutacja liczb od 0 do 119 i zwracanie tablicy
    public static int[] permutation(int length) {
        int[] a = new int[length];
        int[] b = new int[length];
        int[] indexs = new int[length];
        boolean ok = true;
        for(int i=0; i<length; i++){
            a[i] = i;
        }
        Random random = new Random();
        for(int i=0; i<length; i++) {
            do {
                ok = true;
                indexs[i] = random.nextInt(length);
                for(int j=0; j<i; j++)
                    if(indexs[i]==indexs[j])
                        ok = false;
            } while(!ok);
        }
        for(int i=0; i<length; i++){
            b[i] = a[indexs[i]];
        }
        return b;
    }
    
    public static void NF(int[] binIndexs, int nextIndex, int Psol){
        for(int i=0; i<N; i++)
            binIndexs[i]=-1;
        for(int i=0; i<binIndexs.length; i++){
            //jeżeli po włożeniu kolejnego przedmiotu rozwmiar pojemnika jest mniejszy niż zapakowana wartość
            //zostaje zwiększony index pojemników
            if(!con(itemsInBinCount(binIndexs,nextIndex)+itemsList[i]))
                nextIndex++;
            binIndexs[i]=nextIndex;
        }
        Psol = objectFunction(binIndexs);
        System.out.println("Next-Fit (NF)");
        System.out.println("Liczba pojemników: "+Psol);
        System.out.println("Rozwiązanie o "+(100*(((double)Psol)/((double)Popt)-1))+"% gorsze");
        System.out.println();
    }

    public static void FF(int[] binIndexs, int nextIndex, int Psol){
        for(int i=0; i<N; i++)
            binIndexs[i]=-1;
        for(int i=0; i<binIndexs.length; i++){
            //jeżeli po włożeniu następnego przedmiotu się nie domknie, należy zwiększać indeks, ale tym razem
            //w każdej iteracji będzie testował od zera, a nie od ostatnio testowanego (może wrócić)
            nextIndex = 0;
            while (!con(itemsInBinCount(binIndexs,nextIndex)+itemsList[i])){
                nextIndex++;
            }
            binIndexs[i]=nextIndex;
        }
        Psol = objectFunction(binIndexs);
        System.out.println("First-Fit (FF)");
        System.out.println("Liczba pojemników: "+Psol);
        System.out.println("Rozwiązanie o "+(100*(((double)Psol)/((double)Popt)-1))+"% gorsze");
        System.out.println();

    }
    public static void BF(int[] binIndexs, int nextIndex, int Psol){
        for(int i=0; i<N; i++)
            binIndexs[i]=-1;
        for(int i=0; i<binIndexs.length; i++){
            int j=0;
            int maxLoad = 0;
            nextIndex = 0;

            do {
                //oblicza niepusty pojemnik o największym zapełnieniu, do którego jeszcze można dodać kolejny przedmiot
                if(con(itemsInBinCount(binIndexs,j)+itemsList[i]) && maxLoad<itemsInBinCount(binIndexs,j)) {
                    maxLoad=itemsInBinCount(binIndexs,j);
                    nextIndex=j;
                }
                j++;
            }
            while(itemsInBinCount(binIndexs,j)>0);

            //niepustego pojemnika, w którym wystarczy miejsca nie znaleziono - otwiera się nowy
            if(!con(itemsInBinCount(binIndexs,nextIndex)+itemsList[i]))
                nextIndex=j;
            binIndexs[i]=nextIndex;
        }
        Psol = objectFunction(binIndexs);
        System.out.println("Best-Fit (BF)");
        System.out.println("Liczba pojemników: "+Psol);
        System.out.println("Rozwiązanie o "+(100*(((double)Psol)/((double)Popt)-1))+"% gorsze");
        System.out.println();
    }
    public static void WF(int[] binIndexs, int nextIndex, int Psol){
        for(int i=0; i<N; i++)
            binIndexs[i]=-1;
        for(int i=0; i<binIndexs.length; i++){
            int j=0;
            int minLoad = Integer.MAX_VALUE;
            nextIndex = 0;

            do {
                //oblicza niepusty pojemnik o najmniejszym zapełnieniu, do którego jeszcze można dodać kolejny przedmiot
                if(con(itemsInBinCount(binIndexs,j)+itemsList[i]) && minLoad>itemsInBinCount(binIndexs,j)) {
                    minLoad=itemsInBinCount(binIndexs,j);
                    nextIndex=j;
                }
                j++;
            }
            while(itemsInBinCount(binIndexs,j)>0);

            //niepustego pojemnika, w którym wystarczy miejsca nie znaleziono - otwiera się nowy
            if(!con(itemsInBinCount(binIndexs,nextIndex)+itemsList[i]))
                nextIndex=j;
            binIndexs[i]=nextIndex;
        }
        Psol = objectFunction(binIndexs);
        System.out.println("IV. Algorytm zachłanny Worst-Fit (WF)");
        System.out.println("Liczba pojemników: "+Psol);
        System.out.println("Rozwiązanie gorsze (większe) o "+(100*(((double)Psol)/((double)Popt)-1))+"% gorsze");
        System.out.println();
    }
    public static void FFD(int[] binIndexs, int nextIndex, int Psol){
        for(int i=0; i<N; i++)
            binIndexs[i]=-1;

        //sortowanie tablicy
        int[] itemsLSorted = new int[itemsList.length];
        System.arraycopy(itemsList,0,itemsLSorted,0,itemsList.length);
        Arrays.sort(itemsLSorted);
        for(int i=0; i<itemsLSorted.length; i++)
            itemsList[i]=itemsLSorted[itemsLSorted.length-1-i];

        for(int i=0; i<binIndexs.length; i++){
            //jeżeli po włożeniu następnego przedmiotu się nie domknie, należy zwiększać indeks, ale tym razem
            //w każdej iteracji będzie testował od zera, a nie od ostatnio testowanego (może wrócić)
            nextIndex = 0;
            while(!con(itemsInBinCount(binIndexs,nextIndex)+itemsList[i])) {
                nextIndex++;
            }
            binIndexs[i]=nextIndex;
        }
        Psol = objectFunction(binIndexs);
        System.out.println("First-Fit-Decreasing (FFD)");
        System.out.println("Liczba pojemników: "+Psol);
        System.out.println("Rozwiązanie o "+(100*(((double)Psol)/((double)Popt)-1))+"% gorsze");
        System.out.println();
    }

}
