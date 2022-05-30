package urzadzenia;

import zwierzeta.Jajko;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Gniazdo extends Urzadzenie {
    List<Jajko> jajka = new LinkedList<>();

    public Gniazdo(Point _pozycja, int _liczbaStanowisk) {
        super(_pozycja, _liczbaStanowisk);
    }

    @Override
    boolean zajmijStanowisko() {
        return false;
    }

    public void dodajJajo(Jajko jajo){
        jajka.add(jajo);
    }
}