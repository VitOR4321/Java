import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class TabuBP {
    public static int MAX_ITER = 10000;
    public static int M ; // liczba elementów
    public static int P ; // Pojemność plecaka
    public static int MV = 0 ; // Aktualna wartość plecaka
    public static int AP = 0 ; // Aktualna pojemność plecaka

    public static TabuObject[] tabuTable = new TabuObject[50]; // tablica TABU o kadencji 50 i wielkości 50
    public static int finalMV = 0; // ostateczny wynik jednej iteracji

    public static List<Element> Backpack = new ArrayList<Element>(); // tablica która reprezentuje plecak
    // odczyt danych z pliku
    public static void LoadDataLarge(){
        try {
            List<String> lines = Files.readAllLines(Paths.get("large_instances/knapPI_1_200_1000_1"));
            // liczba linijek w pliku
            int linesCount = lines.size();
            String[] firstLine = lines.get(0).split(" ");
            M = parseInt(firstLine[0]);
            P = parseInt(firstLine[1]);
            // wrzucenie informacji o początkowych elementach w plecaku z pliku
            String[] tab = lines.get(linesCount-1).split(" ");
            int[] tabElementInBackpack = new int[M];
            for(int j=0; j<tabElementInBackpack.length;j++){
                tabElementInBackpack[j] = parseInt(tab[j]);
                //System.out.println(tabElementInBackpack[j]);
            }
            tabElements = new Element[M];
            // tworzenie tablicy elementów
            for(int i=1;i<linesCount-1;i++){
                String line = lines.get(i);
                String[] val = line.split(" ");
                Element e = new Element(i, parseInt(val[0]), parseInt(val[1]), tabElementInBackpack[i - 1]);
                tabElements[i-1] = e;
            }


            // sprawdzenie danych
            for(int n=0 ;n<tabElements.length;n++){
                //System.out.println(tabElements[n]);
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void LoadDataSmall(){
        try {
            List<String> lines = Files.readAllLines(Paths.get("small_instances/f9_l-d_kp_5_80"));
            // liczba linijek w pliku
            int linesCount = lines.size();
            String[] firstLine = lines.get(0).split(" ");
            M = parseInt(firstLine[0]);
            P = parseInt(firstLine[1]);
            tabElements = new Element[M];
            // tworzenie tablicy elementów
            for(int i=1;i<linesCount;i++){
                String line = lines.get(i);
                String[] val = line.split(" ");
                Element e = new Element(i, parseInt(val[0]), parseInt(val[1]), 0);
                tabElements[i-1] = e;
            }


            // sprawdzenie danych
            for(int n=0 ;n<tabElements.length;n++){
                //System.out.println(tabElements[n]);
            }

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // początkowe załadowanie plecaka large
    public static void startPackingLarge(){
       for(int i=0;i<tabElements.length;i++){
           if(tabElements[i].inBackpack == 1){
               Backpack.add(tabElements[i]);
           }
       }
        // liczenie pojemności plecaka
        // liczenie wartości elementów w plecaku
        for (int j=0;j<Backpack.size();j++){
            AP = AP+Backpack.get(j).size;
            MV = MV+Backpack.get(j).value;
        }
        // wyświetlanie elementów w plecaku
        for (int k=0;k<Backpack.size();k++){
           //System.out.println(Backpack.get(k));
        }

        //System.out.println(AP);
        //System.out.println(MV);

    }

    // początkowe załadowanie plecaka large
    // dokończyć startowy small i dodać opcje z ilorazem
    public static void startPackingRandSmall(){
        Random rand = new Random();
        int randomIndex = 0;// losowy przedmiot w zbiorze
        int tempAP = 0;
        double[] WS = new double[tabElements.length];
        /*
        while(true){
            boolean sameIndex = false;
            boolean notFullPack = false;// zmienna do sprawdzenia czy jest jeszcze miejsce na element w plecaku
            if(AP<=P){
                AP = 0;
                randomIndex = rand.nextInt(tabElements.length-1);
                    if(tabElements[randomIndex].inBackpack == 1) {
                        sameIndex = true;
                    }
                if(sameIndex==false){
                    Backpack.add(tabElements[randomIndex]);
                    for(int n=0;n<Backpack.size();n++){
                        AP = AP + Backpack.get(n).size;
                    }
                    Backpack.get(Backpack.size()-1).inBackpack = 1;
                    tabElements[randomIndex].inBackpack = 1;
                    tempAP = P - AP;
                    // zabezpieczenie przed przepuszczeniem wartości mniejszej od zera
                    if(tempAP<0){
                        sameIndex = true;
                    }
                    for(int l=0;l<tabElements.length;l++){
                        if(tabElements[l].inBackpack == 0){
                            if(tabElements[l].size<=tempAP){
                                notFullPack = true;
                            }
                        }
                    }
                }
            }else {
                tabElements[randomIndex].inBackpack = 0;
                Backpack.remove(Backpack.size()-1);
                break;

            }
            if(sameIndex == false && notFullPack == false){
                break;
            }
        }
         */
        for(int i=0;i<tabElements.length;i++) {
            WS[i] = tabElements[i].value / tabElements[i].size;
        }
        Arrays.sort(WS);
        AP = 0;
        // liczenie wartości elementów w plecaku
        for (int j=0;j<Backpack.size();j++){
            MV = MV+Backpack.get(j).value;
            AP = AP + Backpack.get(j).size;
        }
        /*
        // wyświetlanie elementów w plecaku
        for (int k=0;k<Backpack.size();k++){
            System.out.println(Backpack.get(k));
        }

        System.out.println(AP);
        System.out.println(MV);
        for (int a=0;a<tabElements.length;a++){
            System.out.println(tabElements[a]);
        }

         */
    }

    // dodawanie do listy tabu elementów
    public static void tabuSearch(int i, double j,int a,int b){
        TabuObject temp = new TabuObject(0,0);
        TabuObject ob = new TabuObject(a,b);
        if(j>=1){
            tabuTable[i] = temp;
        }
            tabuTable[i]=ob;
    }

    public static void startTabuTable(){
        TabuObject temp = new TabuObject(0,0);
        for(int i=0;i<50;i++){
            tabuTable[i] = temp;
        }
    }


    // wariant 1 2 3 4 dla Large
    public static void randomChangePackingLarge(){
            LoadDataLarge();
            // początkowa wartość plecaka
            startPackingLarge();
            // początkowe napełnienie tablicy Tabu
            startTabuTable();
            // zmienna do sprawdzenia czy jest wielokrotność kadencji tablicy tabu
            double modMaxIter = 0;
            int tabuIndeks = 0;
            Random rand = new Random();
            for(int i=0;i<MAX_ITER;i++) {
                int randomIndexBackpack;
                int randomElementIndex;
                while (true) {
                    // zmienna do sprawdzenia czy wylosowany indeks Elementu nie znajduje się już w plecaku
                    boolean sameIndeks = false;
                    randomIndexBackpack = rand.nextInt(Backpack.size()-1);
                    randomElementIndex = rand.nextInt(tabElements.length-1);
                    // warunek listy tabu
                    for(int m=0;m<tabuTable.length;m++){
                        if(tabuTable[m].one == randomIndexBackpack && tabuTable[m].two == randomElementIndex){
                            sameIndeks = true;
                        }
                    }
                    for (int n = 0; n < Backpack.size(); n++) {
                        if (Backpack.get(n).id == randomElementIndex) {
                            sameIndeks = true;
                        }
                    }
                    if (sameIndeks == false) {
                        // zmienić zmienne do warotści Value i size w plecaku
                        // tymczasowa zajętość plecaka
                        int TempP = AP-Backpack.get(randomIndexBackpack).size;
                        // tymczasowa wartość elementów w plecaku
                        int TempMV = MV-Backpack.get(randomIndexBackpack).value;
                        TempP = TempP+tabElements[randomElementIndex].size;
                        TempMV = TempMV+tabElements[randomElementIndex].value;
                        // warunek zamiany elementów z lepszą wartością funkcji celu
                        if(TempP <= P && TempMV >= MV){
                            MV = TempMV;
                            AP = TempP;
                            break;
                        }
                        /*
                        // warunek zamiany elementów bez lepszej wartości funkcji celu
                        if(TempP <= P && TempMV >= MV){
                            MV = TempMV;
                            AP = TempP;
                            break;
                        }
                         */
                    }
                }
                // zamiana elementu
                Element temp = new Element(Backpack.get(randomIndexBackpack).id, Backpack.get(randomIndexBackpack).value, Backpack.get(randomIndexBackpack).size, Backpack.get(randomIndexBackpack).inBackpack);
                tabElements[randomElementIndex].inBackpack = 1;
                Backpack.get(randomIndexBackpack).id = tabElements[randomElementIndex].id;
                Backpack.get(randomIndexBackpack).value = tabElements[randomElementIndex].value;
                Backpack.get(randomIndexBackpack).size = tabElements[randomElementIndex].size;
                Backpack.get(randomIndexBackpack).inBackpack = tabElements[randomElementIndex].inBackpack;
                tabElements[randomIndexBackpack] = temp;
                modMaxIter = (double) i/50;
                if(modMaxIter == (int)modMaxIter){
                    tabuIndeks = 0;
                }
                tabuSearch(tabuIndeks,modMaxIter,randomIndexBackpack,randomElementIndex);
                tabuIndeks++;
                AP = 0;
                for (int j=0;j<Backpack.size();j++){
                    AP = AP+Backpack.get(j).size;
                }
                if(finalMV == 0 || finalMV>MV){
                    finalMV = MV;
                }
            }
            System.out.println("Wynik wynosi: ");
            System.out.println(finalMV);
            System.out.println("MAX pojemność: ");
            System.out.println(P);
            System.out.println("Aktualna pojemność: ");
            System.out.println(AP);
    }


    // wariant 1 2 3 4 dla Small
    // poprawic tą funkcje i dodać jeszcze 2 wariant
    public static void randomChangePackingSmall(){
        LoadDataSmall();
        // początkowa wartość plecaka
        startPackingRandSmall();
        // początkowe napełnienie tablicy Tabu
        startTabuTable();
        // zmienna do sprawdzenia czy jest wielokrotność kadencji tablicy tabu
        double modMaxIter = 0;
        int tabuIndeks = 0;
        Random rand = new Random();
        for(int i=0;i<MAX_ITER;i++) {
            int randomElementIndex;
            int randomBackpackIndex;
            int tooMuchTry = 0;
            while (true) {
                boolean sameIndex = false;
                randomBackpackIndex = rand.nextInt(Backpack.size() - 1);
                randomElementIndex = rand.nextInt(tabElements.length - 1);
                for (int j = 0; j < tabuTable.length; j++) {
                    if (tabuTable[j].one == randomBackpackIndex && tabuTable[j].two == randomElementIndex) {
                        sameIndex = true;
                    }
                }
                for (int n = 0; n < Backpack.size(); n++) {
                    if (Backpack.get(n).id == tabElements[randomElementIndex].id) {
                        sameIndex = true;
                    }
                }
                if (sameIndex == false) {
                    int tempP = AP - Backpack.get(randomBackpackIndex).size;
                    tempP = tempP + tabElements[randomElementIndex].size;
                    int tempMV = MV - Backpack.get(randomBackpackIndex).value;
                    tempMV = tempMV + tabElements[randomElementIndex].value;
                    if (tempP <= P && tempMV >= MV) {
                        break;
                    }

                }
                else {
                    tooMuchTry++;
                    if(tooMuchTry==1000){
                        break;
                    }
                }
            }
            if(tooMuchTry==1000){
                break;
            }
            // zamiana elementów
            // sprawdzenie czy jeśli wylosowany indeks ma takie samo id to zmienna, która mówi o tym czy jest w plecaku
            // zmienia się na 0, co oznacza że element został wyciągnięty
            for(int m=0;m<tabElements.length;m++){
                if(Backpack.get(randomBackpackIndex).id == tabElements[m].id){
                    tabElements[m].inBackpack = 0;
                }
            }
            // zonaczenie że element został włożony do plecaka
            Element temp = new Element(Backpack.get(randomBackpackIndex).id, Backpack.get(randomBackpackIndex).value, Backpack.get(randomBackpackIndex).size, Backpack.get(randomBackpackIndex).inBackpack);
            tabElements[randomElementIndex].inBackpack = 1;
            Backpack.get(randomBackpackIndex).id = tabElements[randomElementIndex].id;
            Backpack.get(randomBackpackIndex).value = tabElements[randomElementIndex].value;
            Backpack.get(randomBackpackIndex).size = tabElements[randomElementIndex].size;
            Backpack.get(randomBackpackIndex).inBackpack = tabElements[randomElementIndex].inBackpack;
            tabElements[randomBackpackIndex] = temp;
            AP = 0;
            MV = 0;
            for(int b=0;b<Backpack.size();b++){
                AP = AP + Backpack.get(b).size;
                MV = MV + Backpack.get(b).value;
            }

            if (finalMV == 0 || finalMV < MV){
                finalMV = MV ;
            }
        }
        System.out.println("Wynik wynosi: ");
        System.out.println(finalMV);
        System.out.println("MAX pojemność: ");
        System.out.println(P);
        System.out.println("Aktualna pojemność: ");
        System.out.println(AP);
    }

    static Element[] tabElements;
    // klasa Element będzie zawierać informację o wartości elementu, rozmiarze elementu i czy jest na początku w plecaku
    static class Element{
        int id;
        int value;
        int size;
        int inBackpack;
        public Element(int id, int value,int size, int inBackpack){
            this.id = id;
            this.value = value;
            this.size = size;
            this.inBackpack = inBackpack;
        }

        @Override
        public String toString() {
            return id+": "+value+" "+size+": "+inBackpack;
        }
    }

    static class TabuObject{
        int one;
        int two;

        public TabuObject(int one, int two){
            this.one = one;
            this.two = two;
        }
    }

    public static void main(String[] arg)throws IOException {
        randomChangePackingLarge();
        //randomChangePackingSmall();
        //LoadDataSmall();
        //startPackingRandSmall();

    }


}