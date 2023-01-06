public class Rozwiazanie {
    String zakodowaneRozwiazanie;
    int pojemnoscPlecaka;

    public Rozwiazanie(String rozwiazanie, int pojemnosc){
        this.zakodowaneRozwiazanie = rozwiazanie;
        this.pojemnoscPlecaka = pojemnosc;
    }

    public int getPojemnoscPlecaka(){
        return this.pojemnoscPlecaka;
    }

    public String getZakodowaneRozwiazanie(){
        return this.zakodowaneRozwiazanie;
    }
    @Override
    public String toString(){
        return "\r\nWielkość plecaka dla rozwiązania: "+this.zakodowaneRozwiazanie+" wynosi: "+this.pojemnoscPlecaka+
                "\r\n";
    }
}
