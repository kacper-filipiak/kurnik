package urzadzenia;

import java.awt.*;

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
        return false;
    }

    public void uzupelnijWode(){
        zawartosc = pojemnosc;
    }

    public float wydajWode(){
        return zwracanieNaRaz;
    }
}