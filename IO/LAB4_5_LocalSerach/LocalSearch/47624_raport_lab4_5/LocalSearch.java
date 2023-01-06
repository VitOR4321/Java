import java.io.File;
import java.io.IOException;
import java.util.*;

// localSearch1
// 1. wczytanie pliku wyznaczenie trasy i zamiana losowo miejsc do pierwszej lepszej trasy
// 2. wczytanie pliku wyznaczenie trasy i zamiana losowo miejsc wszystkich i wyznaczenie max trasy nalepszej, sprawdzenie kazdego elementu z każdym
// 3. wczytanie pliku losowo miejsca wyznaczyć i zamienić do pierwszej lepszej trasy
// 4. wczytanie pliku losowo miejsca wyznaczyć i zamiana losowo miejsc wszystkich i wyznaczenie max trasy nalepszej, sprawdzenie kazdego elementu z każdym
// 5. wczytanie trasy przy użyciu greed search i do pierwszej lepszej zmiany
// 6. wczytanie trasy przy użyciu greed search i zamiana losowo miejsc wszystkich i wyznaczenie max trasy nalepszej, sprawdzenie kazdego elementu z każdym
// localSearch2
// iteracja 1000 razy każdego rozwiązania
public class LocalSearch {
    public static String fileName = "berlin52tsp.sec";
    static Point[] cities;
    public static Point[] randomCities = new Point[52];

    public static Point[] BestCities = new Point[52];
    public static int[] trace = new int[52];
    public static int[] traceBetter = new int[52];
    public static int[] traceTheBest = new int[52];
    public static int sumTrace = 0;
    public static int sumTraceBetter = 0;
    public static int sumTraceTheBest = 0;
    public static void readFile(String fileName) throws IOException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        //pominięcie 3 lini nieznaczących
        scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        // pobranie ilość punktów
        N = Integer.parseInt(scanner.nextLine().split(" ")[1]);
        //dalej nieznaczące
        scanner.nextLine();
        scanner.nextLine();
        //System.out.println("Liczba miast: "+N);
        cities = new Point[N];
        // odczytanie koordynatów i wrzucenie do tablicy cities
        int i = 0;
        while (i<N){
            String line = scanner.nextLine();
            String[] vals = line.split(" ");
            double x = Double.parseDouble(vals[1]);
            double y = Double.parseDouble(vals[2]);
            Point c = new Point(x,y);
            cities[i++]=c;
        }
        /*for(int j=0;j<cities.length;j++){
            System.out.println(cities[j]);
        }

         */
    }
    static double euclideanDistance(Point m1, Point m2){
        return (Math.sqrt(Math.pow((m2.x-m1.x),2)+Math.pow((m2.y-m1.y),2)));
    }
    static int[][] MakeTraces(){
        int[][] trace= new int[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                trace[i][j] = (int) euclideanDistance(cities[i],cities[j]);
            }
        }
        return trace;
    }

    static int Sum(int[] tab,int s){
        for(int i=0;i< tab.length;i++){
            s = s+ tab[i];
        }
        return s;
    }

    static void VC_One(){
        int j = 1;
        for(int i=0;i<cities.length;i++){
            if(i == 51) {
                trace[i] = (int) euclideanDistance(cities[i], cities[0]);
                j=0;
                //System.out.println(" do: "+i+" do: "+j+" wynosi: "+trace[i]);
            }
            else {
                trace[i] = (int) euclideanDistance(cities[i], cities[j]);
                //System.out.println(" do: " + i + " do: " + j + " wynosi: " + trace[i]);
                j++;
            }
        }
        //System.out.println("Trasa wynosi: "+Sum(trace,sumTrace));
        //System.out.println("///////////////////////////////////////////////////////////");
    }

    static void VN_One(){
        Random r = new Random();
        int temp;
        int a = 0;
        int b = 0;
        Point object = new Point(0 ,0);

        for (; ; ) {
            a = r.nextInt(0, 51);
            b = r.nextInt(0, 51);
            if (a != b) {
                break;
            }
        }
        object = cities[a];
        cities[a] = cities[b];
        cities[b] = object;

        for(int i=0;i<cities.length;i++){
            for(int j=0;j<cities.length;j++){
                if(cities[i].odwiedzone == false && cities[i].odwiedzone == false){
                    temp = (int) euclideanDistance(cities[i], cities[j]);
                    if (traceBetter[i] == 0 || traceBetter[i] > temp) {
                        traceBetter[i] = temp;
                    }
                }
            }
            cities[i].odwiedzone = true;
        }
    }
    static void VN_Two(){
        int temp = 0;
        int first = 0;
        Point object = new Point(0 ,0);
        for(int i=0;i<cities.length-1;i++){
            // zamiana miejscami elementów aby szukało do kolejnego punktu
            object = cities[i];
            cities[i] = cities[first];
            cities[first] = object;

            for(int j=0;j<cities.length-1;j++){
                // jeśli punkt nie jest odwiedzony
                if(cities[i].odwiedzone == false && cities[j].odwiedzone == false){
                    temp = (int) euclideanDistance(cities[i], cities[j]);
                    if (traceTheBest[i] == 0 || traceTheBest[i] > temp) {
                        // wstawienie do tablicy wartości euklidesowej najkrótrzej
                        traceTheBest[i] = temp;
                        // wyznaczenie kolejnego pierwszego punktu
                        first = j;
                    }
                }
            }
            cities[i].odwiedzone = true;
        }
            //System.out.println("Lepsze rozwiązanie: "+Sum(traceTheBest, sumTraceTheBest)+" od: "+Sum(trace,sumTrace));
            //System.out.println("///////////////////////////////////////////////////////////");
    }

    static void VC_Two(){
        // losowe punkty
        for(int i=0 ;i<trace.length;i++) {
            while (true) {
                int r = new Random().nextInt(trace.length);
                if (cities[r].odwiedzone == false){
                    randomCities[i] = cities[r];
                    cities[r].odwiedzone = true;
                    break;
                }
            }
        }
        int j = 1;
        for(int i=0;i<cities.length;i++){
            if(i == 51) {
                trace[i] = (int) euclideanDistance(randomCities[i], randomCities[0]);
                j=0;
                //System.out.println(" do: "+i+" do: "+j+" wynosi: "+trace[i]);
            }
            else {
                trace[i] = (int) euclideanDistance(randomCities[i], randomCities[j]);
                //System.out.println(" do: " + i + " do: " + j + " wynosi: " + trace[i]);
                j++;
            }
        }
        //System.out.println("Trasa wynosi: "+Sum(trace,sumTrace));
        //System.out.println("///////////////////////////////////////////////////////////");
    }

    static void VN_Three(){
        Random r = new Random();
        int temp;
        int a = 0;
        int b = 0;
        Point object = new Point(0 ,0);

        for (; ; ) {
            a = r.nextInt(0, 51);
            b = r.nextInt(0, 51);
            if (a != b) {
                break;
            }
        }
        object = randomCities[a];
        randomCities[a] = randomCities[b];
        randomCities[b] = object;

        for(int i=0;i<cities.length;i++){
            for(int j=0;j<cities.length;j++){
                if(randomCities[i].odwiedzone == true && randomCities[i].odwiedzone == true){
                    temp = (int) euclideanDistance(randomCities[i], randomCities[j]);
                    if (traceBetter[i] == 0 || traceBetter[i] > temp) {
                        traceBetter[i] = temp;
                    }
                }
            }
            randomCities[i].odwiedzone = false;
        }
    }
    static void VN_Four(){
        // w tym przypadku potrzebna nam odwrotna logika, zamiana warunku odwiedzone
        int temp = 0;
        int first = 0;
        Point object = new Point(0 ,0);
        for(int i=0;i<cities.length-1;i++){
            // zamiana miejscami elementów aby szukało do kolejnego punktu
            object =  randomCities[i];
            randomCities[i] =  randomCities[first];
            randomCities[first] = object;

            for(int j=0;j<cities.length-1;j++){
                // jeśli punkt nie jest odwiedzony
                if(randomCities[i].odwiedzone == true && randomCities[j].odwiedzone == true){
                    temp = (int) euclideanDistance(randomCities[i], randomCities[j]);
                    if (traceTheBest[i] == 0 || traceTheBest[i] > temp) {
                        // wstawienie do tablicy wartości euklidesowej najkrótrzej
                        traceTheBest[i] = temp;
                        // wyznaczenie kolejnego pierwszego punktu
                        first = j;
                    }
                }
            }
            randomCities[i].odwiedzone = false;
        }
        //System.out.println("Lepsze rozwiązanie: "+Sum(traceTheBest, sumTraceTheBest)+" od: "+Sum(trace,sumTrace));
        //System.out.println("///////////////////////////////////////////////////////////");
    }

    static void VC_Three(int[][] tsp){
        int sum = 0;
        int counter = 0;
        int j = 0, i = 0;
        int min = Integer.MAX_VALUE;
        // lista zawierająca punkty trasy
        List<Integer> visitedRouteList = new ArrayList<>();

        // Start z pierwszego punktu (indexu 0)
        visitedRouteList.add(0);
        int[] route = new int[tsp.length];

        // Traverse the adjacency
        // tablica traceToAllPoints
        while (i <= tsp.length && j <= tsp[i].length) {

            // róg tablicy
            if (counter >= tsp[i].length - 1) {
                break;
            }

            // Jeśli brak scieżki lub jest mniejsza to zamiana.
            if (j != i && !(visitedRouteList.contains(j))) {
                if (tsp[i][j] < min) {
                    min = tsp[i][j];
                    //System.out.println(min);
                    route[counter] = j + 1;
                }
            }
            j++;

            // Sprawdzenie wszystkich scieżek
            if (j == tsp[i].length) {
                sum += min;
                min = Integer.MAX_VALUE;
                visitedRouteList.add(route[counter] - 1);
                j = 0;
                i = route[counter] - 1;
                counter++;
            }
        }

        // Uaktualnienie ostatniego miasta
        i = route[counter - 1] - 1;

        for (j = 0; j < tsp.length; j++) {

            if ((i != j) && tsp[i][j] < min) {
                min = tsp[i][j];
                route[counter] = j + 1;
            }
        }
        int k=1;
        // Wypisanie
        for(int n=0;n<visitedRouteList.size();n++){
                trace[n] = visitedRouteList.get(n);
              //  System.out.println(trace[n]);
        }
        sumTrace = sum;
        //System.out.print("Trasa wynosi : ");
        //System.out.println(sumTrace);
    }
    static void VN_Five(){
        Random r = new Random();
        int temp;
        int a = 0;
        int b = 0;
        Point object = new Point(0 ,0);

            for (; ; ) {
                a = r.nextInt(0, 51);
                b = r.nextInt(0, 51);
                if (a != b) {
                    break;
                }
            }
            object = cities[trace[a]];
            cities[trace[a]] = cities[trace[b]];
            cities[trace[b]] = object;

            for(int i=0;i<cities.length;i++){
                for(int j=0;j<cities.length;j++){
                    if(cities[i].odwiedzone == false && cities[i].odwiedzone == false){
                        temp = (int) euclideanDistance(cities[trace[i]], cities[trace[j]]);
                        if (traceBetter[i] == 0 || traceBetter[i] > temp) {
                            traceBetter[i] = temp;
                        }
                    }
                }
                cities[i].odwiedzone = true;
            }
        }

    static void VN_Six(){
        int temp = 0;
        int first = 0;
        Point object = new Point(0 ,0);
        for(int i=0;i<cities.length-1;i++){
            // zamiana miejscami elementów aby szukało do kolejnego punktu
            object = cities[trace[i]];
            cities[trace[i]] = cities[trace[first]];
            cities[trace[first]] = object;

            for(int j=0;j<cities.length-1;j++){
                // jeśli punkt nie jest odwiedzony
                if(cities[i].odwiedzone == false && cities[i].odwiedzone == false){
                    temp = (int) euclideanDistance(cities[trace[i]], cities[trace[j]]);
                    if (traceTheBest[i] == 0 || traceTheBest[i] > temp) {
                        // wstawienie do tablicy wartości euklidesowej najkrótrzej
                        traceTheBest[i] = temp;
                        // wyznaczenie kolejnego pierwszego punktu
                        first = j;
                    }
                }
            }
            cities[i].odwiedzone = true;
        }
    }
    static int N = 0;// ilość punktów w grafie pobierany z pliku

    // klasa obiektów które zawierają parametry miast
    static class Point{
        double x;
        double y;
        boolean odwiedzone;
        public Point(double x,double y){
            this.x=x;
            this.y=y;
            odwiedzone = false;
        }

        @Override
        public String toString() {
            return "Współrzędne "+x+", "+y;
        }
    }
    public static void main(String[] args) throws IOException {
        readFile(fileName); // fukcja do odczytywania pliku

        int minTrace=0;
        int allTraceCount=0;
        int maxTrace=0;
        int maxGlobal=0;
        int minGlobal=0;
        int midTrace=0;
        // tablica zawierająca wszystkie możliwe odległości które będą porównywane
        int[][] traceToAllPoints = MakeTraces();

        // przed uruchomieniem trzeba odpalić odpowiednie funkcjie
        // aby to zrobić trzeba odpomentować funkcje które chcemy
        // zawsze jedno VC i jedno VN
        // i odkomentować lub zakomentować odpowiednie instrukcje w pętli
        for(int j=0;j<1000;j++) {

            for (int i = 0; i < 10; i++) {
                //VC_One();
                //VN_One();
                //VN_Two();
                ///////////
                VC_Two();
                VN_Three();
                //VN_Four();
                //////////
                VC_Three(traceToAllPoints);
                //VN_Five();
                VN_Six();
                int temp = Sum(traceBetter, sumTraceBetter);// odpalamy przy pierwszej najlepszej opcji
                //int temp = Sum(traceTheBest,sumTraceTheBest); // odpalamy przy pełnym przeszukaniu
                allTraceCount = allTraceCount + temp;
                if (minTrace == 0 || temp < minTrace) {
                    minTrace = temp;
                }
                if (maxTrace == 0 || temp > maxTrace) {
                    maxTrace = temp;
                }
            // należy zmienić na randomCities gdy chcemy odpalić VC_Two i VN_Three lub VN_Four
            for(int k=0 ;k<trace.length;k++){
                //randomCities[k].odwiedzone = false;
                cities[k].odwiedzone = false;
            }
            }
        }
        midTrace = (allTraceCount) / 10000;
        if(maxGlobal==0||maxGlobal<maxTrace){
            maxGlobal=maxTrace;
            System.out.println("max global: " + maxGlobal);
        }
        if(minGlobal==0||minGlobal>minTrace){
            minGlobal=minTrace;
            System.out.println("min global: " + minGlobal);
        }
        System.out.println("mid global: " + midTrace);

    }
}
