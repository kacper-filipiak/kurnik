package urzadzenia;

import java.awt.*;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class Pasnik extends Urzadzenie {
    float zwracanieNaRaz;
    float pojemnosc;
    Pasza zawartosc;

    public Pasnik(Point _pozycja, int _liczbaStanowisk, float _pojemnosc, Pasza _zawartosc, float zwracanieNaRaz) {
        super(_pozycja, _liczbaStanowisk);
        pojemnosc = _pojemnosc;
        zawartosc = _zawartosc;
        this.zwracanieNaRaz = zwracanieNaRaz;
    }

    //dodaje pasze do zawartosci
    public void uzupelnijPasze(Pasza pasza) {
        zawartosc = new Pasza(pasza, zawartosc);
    }

    //zwraca pasze z swojej zawartosci
    public Pasza wydajPasze() {
        zawartosc.masa -= zwracanieNaRaz;
        if (zawartosc.masa < 0) zawartosc.masa = 0;
        return zawartosc.masa > zwracanieNaRaz ? zawartosc.podziel(zwracanieNaRaz / zawartosc.masa).get(0) : zawartosc;
    }

    @Override
    boolean zajmijStanowisko() {
        return true;
    }
}