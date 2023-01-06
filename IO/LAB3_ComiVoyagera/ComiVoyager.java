import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ComiVoyager {

    public static String fileName = "berlin52tsp.sec";
    public static void readFile(String fileName) throws IOException{
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

    // sumowanie dystansu do poszczególnych punktów
    static double distanceLength(int[] tr){
        double trace =0;
        for(int i=0;i<N;i++)
        {
            trace = trace + tr[i];
        }

        return trace;

    }
    static int[] TSP_NearestNeighbor(){
        int[] trace = new int[N];
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
                    if (trace[i] == 0 || trace[i] > temp) {
                        // wstawienie do tablicy wartości euklidesowej najkrótrzej
                        trace[i] = temp;
                        // wyznaczenie kolejnego pierwszego punktu
                        first = j;
                    }
                }
            }
            cities[i].odwiedzone = true;
        }
        return trace;
    }


    static Point[] cities;
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

        // wynik najlepszy 7544
        // algorytm najbliższego sąsiada
        int[] trasa;
        trasa = TSP_NearestNeighbor();
        System.out.println("Trasa: ");
        for(int i=0; i<N; i++){
            System.out.println(trasa[i]+" ");
        }
        System.out.println("Długość trasy: "+distanceLength(trasa));

    }
}
