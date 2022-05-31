package urzadzenia;

import java.awt.*;

public class Poidlo extends Urzadzenie {
    float pojemnosc;
    float zawartosc;

    public Poidlo(Point _pozycja, int _liczbaStanowisk, float _pojemnosc, float _zawartosc) {
        super(_pozycja, _liczbaStanowisk);
        pojemnosc = _pojemnosc;
        zawartosc = _zawartosc;
    }
    float wydajWode(){
        //TODO: not implemented yet
    }
    Poidlo(float _pojemnosc){
        //TODO: not implemented yet
    }

    @Override
    public boolean zajmijStanowisko() {
        return false;
    }

    public float wydajWode(){
        return 0.1f;
    }
}