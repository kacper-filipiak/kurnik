package urzadzenia;

import java.util.List;

public class Pasza {
    static private float kalorycznosc;

    static public void setKalorycznosc(float kalorycznosc) {
        Pasza.kalorycznosc = kalorycznosc;
    }

    float masa;

    //dzieli pasze w stosunku podanym w parametrze (0-1)
    List<Pasza> podziel(float podzial) {
        return List.of(new Pasza(masa * podzial), new Pasza(masa * (1 - podzial)));
    }

    public Pasza(Pasza pasza1, Pasza pasza2) {
        masa = pasza1.masa + pasza2.masa;
    }

    public Pasza(float masa) {
        this.masa = masa;
    }

    public float getEnergie() {
        return kalorycznosc * masa;
    }
}