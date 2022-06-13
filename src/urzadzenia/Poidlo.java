package urzadzenia;

import java.awt.*;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class Poidlo extends Urzadzenie {
    float zwracanieNaRaz;
    float pojemnosc;
    float zawartosc;

    public Poidlo(Point _pozycja, int _liczbaStanowisk, float _pojemnosc, float _zawartosc, float zwracanieNaRaz) {
        super(_pozycja, _liczbaStanowisk);
        pojemnosc = _pojemnosc;
        zawartosc = _zawartosc;
        this.zwracanieNaRaz = zwracanieNaRaz;
    }

    @Override
    public boolean zajmijStanowisko() {
        return true;
    }

    public void uzupelnijWode() {
        zawartosc = pojemnosc;
    }
    //Wydaje wode jesli moze

    public float wydajWode() {
        zawartosc -= zwracanieNaRaz;
        if (zawartosc < 0) zawartosc = 0;
        return zwracanieNaRaz;
    }
}