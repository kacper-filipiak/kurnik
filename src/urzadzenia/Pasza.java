package urzadzenia;

import java.awt.*;

public class Pasza {
    float kalorycznosc;
    float masa;

    public void Pasza(float _kalorycznosc, float _masa) {
        kalorycznosc = _kalorycznosc;
        masa = _masa;
    }
    Point podziel(float podzial){
        //TODO: not implemented yet
        return null;
    }
    public Pasza(Pasza pasza1, Pasza pasza2){
        //TODO: not implemented yet
    }

    public Pasza(){

    }

    public float getEnergie(){
        return kalorycznosc * masa;
    }
}