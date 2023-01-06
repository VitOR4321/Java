import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class    Plecak {
    private final int maksymalnaPojemnoscPlecaka = 21;
    private int obecnaPojemnoscPlecaka = 0;

    private ArrayList<Przedmiot> elementyWPlecaku = new ArrayList<Przedmiot>();

    public int getMaksymalnaPojemnoscPlecaka(){
        return this.maksymalnaPojemnoscPlecaka;
    }

    public int pokazIloscWolnegoMiejsca(){
        return maksymalnaPojemnoscPlecaka - this.obecnaPojemnoscPlecaka;
    }

    public void ZwiekszObecnaPojemnoscPlecaka(int obecnaPojemnoscPlecaka) {
        this.obecnaPojemnoscPlecaka += obecnaPojemnoscPlecaka;
    }

    public int getObecnaPojemnoscPlecaka() {
        return obecnaPojemnoscPlecaka;
    }

    public void setObecnaPojemnoscPlecaka(int obecnaPojemnoscPlecaka) {
        this.obecnaPojemnoscPlecaka = obecnaPojemnoscPlecaka;
    }

    public void dodajElementDoPlecaka(Przedmiot przedmiot){
        this.elementyWPlecaku.add(przedmiot);
        this.obecnaPojemnoscPlecaka += przedmiot.wielkoscPrzedmiotu;
    }



    public ArrayList<Przedmiot> getElementyWPlecaku() {
        return elementyWPlecaku;
    }
}
