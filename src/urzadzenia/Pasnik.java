package urzadzenia;

import java.awt.*;

public class Pasnik extends Urzadzenie {
    float pojemnosc;
    Pasza zawartosc;

    public Pasnik(Point _pozycja, int _liczbaStanowisk, float _pojemnosc, Pasza _zawartosc) {
        super(_pozycja, _liczbaStanowisk);
        pojemnosc = _pojemnosc;
        zawartosc = _zawartosc;
    }

    public Pasza wydajPasze(){
        zawartosc.masa -= 100.f;
        return zawartosc;
    }

    @Override
    boolean zajmijStanowisko() {
        return false;
    }
}