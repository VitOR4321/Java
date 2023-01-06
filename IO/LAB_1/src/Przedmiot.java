import java.util.Random;

public class Przedmiot {
    int nrPrzedmiotu;
    int wielkoscPrzedmiotu;
    int waznoscPrzedmiotu;

    int czyPrzedmiotWPlecaku;

    public Przedmiot(int numerPrzedmiotu, int wielkoscPrzedmiotu, int waznoscPrzedmiotu, int czyItemWPlecaku){
        this.nrPrzedmiotu = numerPrzedmiotu;
        this.wielkoscPrzedmiotu = wielkoscPrzedmiotu;
        this.waznoscPrzedmiotu = waznoscPrzedmiotu;
        this.czyPrzedmiotWPlecaku = czyItemWPlecaku;
    }

    public Przedmiot(){
        this.nrPrzedmiotu = 0;
        this.wielkoscPrzedmiotu = 0;
        this.waznoscPrzedmiotu = 0;
        this.czyPrzedmiotWPlecaku = 0;
    }

    public int getWielkoscPrzedmiotu(){
        return  this.wielkoscPrzedmiotu;
    }

    public Przedmiot generujLosowyPrzedmiot(int numerPrzedmiotu){
        int losowaWielkosc = new Random().nextInt(0, 20);
        int losowaWaznosc = new Random().nextInt(0, 20);
        int czyLosowyItemWPlecaku = 0;
        Przedmiot nowyPrzedmiot = new Przedmiot(numerPrzedmiotu,losowaWielkosc,losowaWaznosc,czyLosowyItemWPlecaku);
        return nowyPrzedmiot;

    }

    @Override
    public String toString() {
        return "Numer przedmiotu=" + nrPrzedmiotu +
                "\r\nWielkość przedmiotu=" + wielkoscPrzedmiotu +
                "\r\nWaga przedmiotu=" + waznoscPrzedmiotu+"\r\n";
    }
}
