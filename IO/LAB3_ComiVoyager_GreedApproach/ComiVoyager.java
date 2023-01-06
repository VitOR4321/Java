import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ComiVoyager {
    public static String fileName = "berlin52tsp.sec";
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
        System.out.println("Liczba miast: "+N);
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
        for(i = 0; i<cities.length; i++){
            System.out.println(i+": "+cities[i]);
        }
    }

    static double euclideanDistance(Point m1, Point m2){
        return (Math.sqrt(Math.pow((m2.x-m1.x),2)+Math.pow((m2.y-m1.y),2)));
    }
    static int[][] MakeTraces(){
        int[][] trace= new int[N][N];
        Random r = new Random();
        int randStart = r.nextInt(51);
        Point temp = new Point(0,0);
        // losowy punkt startowy
        //cities[0] = temp;
        //cities[randStart] = cities[0];
        //cities[randStart] = temp;
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                trace[i][j] = (int) euclideanDistance(cities[i],cities[j]);
            }
        }
        return trace;
    }

    static void findMinRoute(int[][] tsp)
    {
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
        sum += min;

        // Wypisanie
        for(int n=0;n<visitedRouteList.size();n++){
            if(n!=visitedRouteList.size()-1){
                System.out.println("start: "+visitedRouteList.get(n)+" stop: "+visitedRouteList.get(n+1));
            }
            else{
                System.out.println("start: "+visitedRouteList.get(n)+" stop: "+visitedRouteList.get(0));
            }
        }
        System.out.print("Minimum Cost is : ");
        System.out.println(sum);

    }

    static Point[] cities;
    static int N = 0;// ilość punktów w grafie pobierany z pliku

    // klasa obiektów które zawierają parametry miast
    static class Point{
        double x;
        double y;
        //boolean odwiedzone;
        public Point(double x,double y){
            this.x=x;
            this.y=y;
            //odwiedzone = false;
        }

        @Override
        public String toString() {
            return "Współrzędne "+x+", "+y;
        }
    }

    public static void main(String[] args) throws IOException {
        readFile(fileName); // fukcja do odczytywania pliku

        // tablica zawierająca wszystkie możliwe odległości które będą porównywane
        int[][] traceToAllPoints = MakeTraces();

        // zrobić wyświetlanie tablicy taceToAllPoints

        findMinRoute(traceToAllPoints);
    }
}

