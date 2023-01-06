import java.util.ArrayList;

public class ExhaustiveSearch {

    public static String toBinary(int x, int len)
    {
        if (len > 0)
        {
            return String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0");
        }
        return null;
    }

    public static void main(String[] args) {

        int maksymalnaIloscPrzedmiotow = 8;

        ArrayList<Przedmiot> wszystkiePrzedmioty = new ArrayList<Przedmiot>();
        ArrayList<Rozwiazanie> poprawneRozwiazania = new ArrayList<>();
        Plecak plecak = new Plecak();

        wszystkiePrzedmioty.add(new Przedmiot(1,3,6,0));
        wszystkiePrzedmioty.add(new Przedmiot(2,9,4,0));
        wszystkiePrzedmioty.add(new Przedmiot(3,12,7,0));
        wszystkiePrzedmioty.add(new Przedmiot(4,4,9,0));
        wszystkiePrzedmioty.add(new Przedmiot(5,6,13,0));
        wszystkiePrzedmioty.add(new Przedmiot(6,2,11,0));
        wszystkiePrzedmioty.add(new Przedmiot(7,1,5,0));
        wszystkiePrzedmioty.add(new Przedmiot(8,6,6,0));

            String[] binaryValues = new String[(int) Math.pow(2,maksymalnaIloscPrzedmiotow)];
            //Przeszukiwanie dokładne
            long timeStart = System.currentTimeMillis();
            for(int i=0; i<binaryValues.length;i++)
            {
                binaryValues[i] = toBinary(i,maksymalnaIloscPrzedmiotow);
                char[] cyfry = binaryValues[i].toCharArray();
                 for (int j=0;j<cyfry.length;j++){
                    if(cyfry[j] == 49){ //1 w zapisie ASCII
                        plecak.ZwiekszObecnaPojemnoscPlecaka(wszystkiePrzedmioty.get(j).getWielkoscPrzedmiotu());
                    }
                }
                if(plecak.getObecnaPojemnoscPlecaka() > 0 && plecak.getObecnaPojemnoscPlecaka() <= plecak.getMaksymalnaPojemnoscPlecaka()){
                    poprawneRozwiazania.add(new Rozwiazanie(binaryValues[i],plecak.getObecnaPojemnoscPlecaka()));
                }
                plecak.setObecnaPojemnoscPlecaka(0);
            }
        long timeStop = System.currentTimeMillis();
        for (int i = 0; i < poprawneRozwiazania.size(); i++) {
            Rozwiazanie poprawneRozwiazanie = poprawneRozwiazania.get(i);
            if(poprawneRozwiazanie.pojemnoscPlecaka == plecak.getMaksymalnaPojemnoscPlecaka()){
                System.out.println("\r\nRozwiązanie nr: "+(i+1)+"\r\n"+poprawneRozwiazanie.toString());
                String ZakodowaneRozwiazanie = poprawneRozwiazanie.zakodowaneRozwiazanie;
                char[] elementy = ZakodowaneRozwiazanie.toCharArray();
                for (int j = 0; j < elementy.length; j++) {
                    if(elementy[j] == 49){
                        System.out.println(wszystkiePrzedmioty.get(j).toString());
                    }
                }
            }
        }
        //System.out.println("\r\nIlość optymalnych rozwiązań: "+poprawneRozwiazania.size());

        //Czas działania algorytmu
        long minioneMilisekundy = timeStop - timeStart;
        System.out.println("Czas działania algorytmu: "+minioneMilisekundy+" ms");
    }
}