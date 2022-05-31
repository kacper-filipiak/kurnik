package urzadzenia;

public class Pasza {
    float kalorycznosc;
    float masa;

    public void Pasza(float _kalorycznosc, float _masa) {
        kalorycznosc = _kalorycznosc;
        masa = _masa;
    }
    point podziel(float podzial){
        //TODO: not implemented yet
    }
    Pasza(Pasza pasza1, Pasza pasza2){
        //TODO: not implemented yet
    }

    public float getEnergie(){
        return kalorycznosc * masa;
    }
}