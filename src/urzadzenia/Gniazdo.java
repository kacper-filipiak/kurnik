package urzadzenia;

import zwierzeta.Jajko;

import java.awt.*;
import java.util.List;

public class Gniazdo extends Urzadzenie {
    List<Jajko> jajka;

    public Gniazdo(Point _pozycja, int _liczbaStanowisk, List<Jajko> _jajka) {
        super(_pozycja, _liczbaStanowisk);
        jajka = _jajka;
    }

    @Override
    boolean zajmijStanowisko() {
        return false;
    }

    public void dodajJajo(Jajko jajo){
        jajka.add(jajo);
    }
}