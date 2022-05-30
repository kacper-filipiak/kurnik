package urzadzenia;

public class Pasza {
    float kalorycznosc;
    float masa;

    public void pasza(float _kalorycznosc, float _masa) {
        kalorycznosc = _kalorycznosc;
        masa = _masa;
    }

    public float getEnergie(){
        return kalorycznosc * masa;
    }
}