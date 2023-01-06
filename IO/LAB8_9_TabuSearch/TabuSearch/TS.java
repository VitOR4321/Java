import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class TS {
    static int M;//liczba pracowników
    static int N;//liczba zadań
    static int[][] workTimes;// koszty przypisania pracowników do zadań
    static int MAX_ITER = 300;// ilość prób pętli zewnętrznej (jak localsearch2)
    static int MIN_ITER = 10;// ilość prób pętli wewnętrznej (jak localsearch1)
    static int t=0; // suma godzin prac
    static Person[] table = new Person[100]; // tablica na na której pracownicy będą mieć przydzielone zadania

    static TabuObject[] tabuTable = new TabuObject[10]; // tablica ruchów zakazanych

    public static void LoadData(){

        try {
            File f = new File("assign100.txt");
            Scanner scanner = new Scanner(f);
            String m = scanner.nextLine().trim();
            M = Integer.parseInt(m);
            N = Integer.parseInt(m);
            workTimes = new int[M][N];
            String dataFromFile = "";
            dataFromFile += scanner.nextLine().trim();
            while (scanner.hasNextLine()){
                String nextValue = scanner.nextLine();
                dataFromFile += nextValue;
            }
            String[] allTimes = dataFromFile.split(" ");
            int i=0;
            for (int j = 0; j < workTimes.length; j++) {
                for (int k = 0; k < workTimes.length; k++) {
                    workTimes[j][k] = Integer.parseInt(allTimes[i]);
                    //System.out.print(workTimes[j][k]+" ");
                    i++;
                }
                //System.out.println();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Blad z odczytaniem pliku.");
            e.printStackTrace();
        }
    }

    public static void StartWork(){
        int idP=0;
        int idW=0;
        // tworzenie tablicy indeksów zadań i losowe ustawienie ich
        Integer[] arr = new Integer[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        for(int i=0 ;i<M;i++) {
            idW = arr[i];
            Person p = new Person(idP,idW,workTimes[i][idW]);
            idP++;
            table[i] = p;
            // System.out.println(table[i].toString());
        }

    }

    public static void tabu(int i,int j,int a, int b) {
        // przy zmianie rozmiaru pętli MIN_ITER trzeba wymyślić inne dodawanie do tablicy
        // nie po liczbie iteracji pętli mniejszej
        TabuObject temp = new TabuObject(0,0);
        TabuObject ob = new TabuObject(a,b);
        if(i!=0){
            tabuTable[j] = temp;
        }
            tabuTable[j]=ob;
    }

    static class TabuObject{
        int one;
        int two;

        public TabuObject(int one, int two){
            this.one = one;
            this.two = two;
        }

    }

    static class Person{
        int idPerson;
        int idWork;
        int work;
        public Person(int idPerson,int idWork,int work){
            this.idPerson = idPerson;
            this.idWork = idWork;
            this.work = work;
        }

        public String toString(){
            return idPerson + ", "+idWork+", "+work;
        }

    }

    public static void main(String[] arg)throws IOException {
        int[] sumTab = new int[10]; // tablica dla sum rozwiązań
        LoadData();
        for(int m=0;m< sumTab.length;m++){
            t=0;
            Arrays.fill(table, null);
            StartWork();
            for(int i=0;i<MAX_ITER;i++){

                for(int j=0;j<MIN_ITER;j++){
                    Random r = new Random();
                    int temp = 0;
                    int a = 0;
                    int b = 0;
                    Person object = new Person(0,0,0);
                    boolean different = false;
                    boolean noInTabu = false;
                    // losowanie 2 zadań do zmiany
                    while (different == false && noInTabu==false) {
                        int allIndexInTabuCheck = 1;
                        a = r.nextInt(0, 99);
                        b = r.nextInt(0, 99);
                        // warunek if jest po to aby nie brano pod uwagę warunku tabu
                        // przy pierwszej iteracji dużej pętli
                        if(i!=0){
                            for(int x=0;x< tabuTable.length;x++){
                                if(tabuTable[x].one != a && tabuTable[x].two != b){
                                    allIndexInTabuCheck++;
                                }
                            }
                            if(allIndexInTabuCheck==tabuTable.length){
                                noInTabu = true;
                            }
                        }
                        else {
                            noInTabu = true;
                        }
                        if (a != b ) {
                            different = true;
                        }
                    }
                    different = false;
                    noInTabu = false;

                    // algorytm sąsiedztwa nr 1
                    // zrobić zamiane i wyznaczenie czasu zmienionych zadań
                    // zamiana miejscami
                    object = table[a];
                    table[a].idWork = table[b].idWork;
                    table[a].work = workTimes[a][table[b].idWork];
                    table[b].idWork = object.idWork;
                    table[b].work = workTimes[b][object.idWork];

                    /*
                    // algorytm sąsiedztwa nr 2
                    // wyznaczenie najkrótrzego czasu pracy dla danego pracownika
                    // workTimes - tablica[][] z danymi
                    // index najkrótrzej pracy
                    int workIndex=0;
                    // zmienna temp przymująca wartość czasu danej pracy
                    int temporary=0;
                    // zmienna która ma przyjąć najszybszą prace
                    int lesswork=0;
                    // pętla do znalezienia najkrótrzej pracy dla danego pracownika
                    for(int o=0;o<100;o++){
                        temporary = workTimes[a][o];
                        if(lesswork==0||temporary<lesswork){
                            lesswork = temporary;
                            workIndex = o;
                        }
                    }
                    // zamiana wartości dwóch pracowników
                    object.idPerson = a;
                    object.idWork = table[a].idWork;
                    object.work = table[a].work;
                    table[a].idWork = workIndex;
                    table[a].work = lesswork;
                    table[b].idWork = object.idWork;
                    table[b].work = workTimes[b][object.idWork];

                     */

                    // inicjacja metody tablicy tabu
                    tabu(i,j,a,b);

                    for(int n=0;n<table.length;n++){
                        temp = temp + table[n].work;
                    }

                    if(t==0 || temp<t ){
                        t=temp;
                    }
                }
            }
            sumTab[m] = t;
            System.out.print(sumTab[m]+", ");
        }
    }
}
