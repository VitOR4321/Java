import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GreedySearch {

    public static ArrayList sortArrayList(ArrayList listaDoPosortowania){
        Comparator<Przedmiot> komparator = new Comparator<Przedmiot>() {
            @Override
            public int compare(Przedmiot o1, Przedmiot o2) {
                if(o1.waznoscPrzedmiotu > o2.waznoscPrzedmiotu){
                    return 1;
                }
                else if(o1.waznoscPrzedmiotu == o2.waznoscPrzedmiotu){
                    return 0;
                }
                else {
                    return  -1;
                }
            }
        };
        Collections.sort(listaDoPosortowania, komparator);
        return listaDoPosortowania;
    }
    public static Przedmiot setFirstItem(ArrayList<Przedmiot> listaPrzedmiotow){
        int ItemID = 0;
        do {
            ItemID = new Random().nextInt(0, listaPrzedmiotow.size());
        }
        while (ItemID > listaPrzedmiotow.size());
        return listaPrzedmiotow.get(ItemID);
    }

    public static void main(String[] args) {
        int obecnaWielkoscPlecaka = 0;
        int maksymalnaIloscPrzedmiotow = 50;

        ArrayList<Przedmiot> wszystkiePrzedmioty = new ArrayList<Przedmiot>();
        ArrayList<Przedmiot> rozwiazania = new ArrayList<>();
        Plecak plecak = new Plecak();

        wszystkiePrzedmioty.add(new Przedmiot(1,3,6,0));
        wszystkiePrzedmioty.add(new Przedmiot(2,9,4,0));
        wszystkiePrzedmioty.add(new Przedmiot(3,12,7,0));
        wszystkiePrzedmioty.add(new Przedmiot(4,4,9,0));
        wszystkiePrzedmioty.add(new Przedmiot(5,6,13,0));
        wszystkiePrzedmioty.add(new Przedmiot(6,2,11,0));
        wszystkiePrzedmioty.add(new Przedmiot(7,1,5,0));
        wszystkiePrzedmioty.add(new Przedmiot(8,6,6,0));

        if(maksymalnaIloscPrzedmiotow > 8){
            for (int i = 8; i < maksymalnaIloscPrzedmiotow-8; i++) {
                wszystkiePrzedmioty.add(new Przedmiot().generujLosowyPrzedmiot(i));
            }
        }


        wszystkiePrzedmioty = sortArrayList(wszystkiePrzedmioty);
        Collections.reverse(wszystkiePrzedmioty);




        //Czas działania algorytmu
        long timeStart = System.currentTimeMillis();
        plecak.setObecnaPojemnoscPlecaka(0);
        Przedmiot pierwszyPrzedmiot = setFirstItem(wszystkiePrzedmioty);
        System.out.println("Wylosowany przedmiot: \r\n"+pierwszyPrzedmiot.toString());
        plecak.dodajElementDoPlecaka(pierwszyPrzedmiot);
        System.out.println("Obecna wielkość plecaka: "+plecak.getObecnaPojemnoscPlecaka()+"\r\n");
        //plecak.setObecnaPojemnoscPlecaka(plecak.getObecnaPojemnoscPlecaka() + pierwszyPrzedmiot.wielkoscPrzedmiotu);
        plecak.ZwiekszObecnaPojemnoscPlecaka(pierwszyPrzedmiot.wielkoscPrzedmiotu);
        System.out.println("Pozostałe przedmioty: \r\n");
        int wielkoscPlecaka = plecak.getObecnaPojemnoscPlecaka();
        for (int i = 0; i < wszystkiePrzedmioty.size(); i++) {
            if(wszystkiePrzedmioty.get(i) == pierwszyPrzedmiot){
                continue;
            }
            if(plecak.getObecnaPojemnoscPlecaka()+wszystkiePrzedmioty.get(i).wielkoscPrzedmiotu <= plecak.getMaksymalnaPojemnoscPlecaka())
            {
                plecak.dodajElementDoPlecaka(wszystkiePrzedmioty.get(i));
                System.out.println(wszystkiePrzedmioty.get(i).toString());
                System.out.println("Obecna wielkość plecaka: "+plecak.getObecnaPojemnoscPlecaka()+"\r\n");
            }
            else {
                break;
            }
        }
        long timeStop = System.currentTimeMillis();
        long minioneMilisekundy = timeStop - timeStart;

        System.out.println("Czas działania algorytmu: "+minioneMilisekundy+" ms");
        int iloscWolnegoMiejsca = plecak.pokazIloscWolnegoMiejsca();
        if(iloscWolnegoMiejsca < 0){
            System.out.println("Miejsce w plecaku przekroczone o: "+Math.abs(iloscWolnegoMiejsca));
        }
        else {
            System.out.println("Ilosć wolnego miejsca w plecaku: "+iloscWolnegoMiejsca);
        }
        if(plecak.getObecnaPojemnoscPlecaka() <= plecak.getMaksymalnaPojemnoscPlecaka()){
            double stosunekWolnegoMiejsca = (double) iloscWolnegoMiejsca / plecak.getMaksymalnaPojemnoscPlecaka();
            double roznicaWProcentach = stosunekWolnegoMiejsca * 100.0;
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println("Różnica względem przeszukiwania wyczerpującego: "+df.format(roznicaWProcentach)+"%");
        }
    }
}
