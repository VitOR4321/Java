import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

// notatki co mam zrobić
// jest 100 pracowników (wiersze) i 100 zadań (kolumny) wartości w tablicy dwuwymiarowej
// to wartość czasu z jaką dany pracownik wykonuje zadanie
// dane wejsciowe to zadne na skos
// następnie losowo zamieniamy numery zadania, czyli
// pracownik 1 miał zadanie nr 1 o wartości 52, zamieniamy zadanie z pracownikiem 2,
// który miał zadanie 2 o wartości 17
// następnie sumujemy wszystkie zadania i sprawdzamy czy początkowa suma jest gorsza od wylosowanej
// potem wykonujemy to 10 razy
// następniewykonujemy schładzanie, czyli bierzemy wartość T,
// która jest wartością maksymalnej wartości zadania, i mnożymy ją przez lambde czyli schładzanie
// następnie robimy to 300 razy

// należy zaproponować własny algorytm sąsiedztwa


public class SA {
    static int M;//liczba pracowników
    static int N;//liczba zadań
    static int[][] workTimes;// koszty przypisania pracowników do zadań
    static int MAX_ITER = 300;// ilość prób pętli zewnętrznej (jak localsearch2)
    static int MIN_ITER = 10;// ilość prób pętli wewnętrznej (jak localsearch1)
    static int t=0; // suma godzin prac
    static double T = 100; // startowa "temperatura"
    static double lambda = 0.95;// częstotlwość schładzania
    static Person[] table = new Person[500]; // tablica na na której pracownicy będą mieć przydzielone zadania

    public static void LoadData(){

        try {
            File f = new File("assign500.txt");
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
        Integer[] arr = new Integer[500];
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
                    // losowanie 2 zadań do zmiany
                    for (; ; ) {
                        a = r.nextInt(0, 499);
                        b = r.nextInt(0, 499);
                        if (a != b) {
                            break;
                        }
                    }

                    /*
                    // algorytm sąsiedztwa nr 1
                    // zrobić zamiane i wyznaczenie czasu zmienionych zadań
                    // zamiana miejscami
                    object = table[a];
                    table[a].idWork = table[b].idWork;
                    table[a].work = workTimes[a][table[b].idWork];
                    table[b].idWork = object.idWork;
                    table[b].work = workTimes[b][object.idWork];

                     */


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



                    for(int n=0;n<table.length;n++){
                        temp = temp + table[n].work;
                    }

                    if(t==0 || temp<t ){
                        t=temp;
                    }
                    else if(new Random().nextDouble(0,1)>Math.exp((temp-t)/T)){
                        t=temp;
                    }
                }
                T = T * lambda;

            }
            sumTab[m] = t;
            System.out.print(sumTab[m]+", ");
        }
    }


}
