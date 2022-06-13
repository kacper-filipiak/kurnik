package zwierzeta;

import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class Lis extends Zwierze {
    //Wartosc z zakresu (0-1)
    static float wspolczynnikSzansAtaku = 0.2f;

    public static void setWspolczynnikSzansAtaku(float _wspolczynnikSzansAtaku) {
        wspolczynnikSzansAtaku = _wspolczynnikSzansAtaku;
    }

    static float _zapotrzebowanieEnergetyczne = 100.f;

    public static void setZapotrzebowanieEnergetyczne(float zapotrzebowanieEnergetyczne) {
        Lis._zapotrzebowanieEnergetyczne = zapotrzebowanieEnergetyczne;
    }


    public Lis(Point _pozycja, float _wspolczynnikSzansAtaku) {
        super(_zapotrzebowanieEnergetyczne, 0.f, 0.f, 0.f, _pozycja);
        wspolczynnikSzansAtaku = _wspolczynnikSzansAtaku;
    }

    //Jesli dosc glodny zwraca akcje ataku, a jesli nie to losuje czy biega, czy stoi
    @Override
    public ACTIONS decyduj() {
        glod += zapotrzebowanieEnergetyczne;
        if (wspolczynnikSzansAtaku * glod > zapotrzebowanieEnergetyczne * 10) {
            if (GlobalRandom.rand.nextBoolean()) chce = ACTIONS.ATAK;
        }
        if(GlobalRandom.rand.nextInt(10) == 1) chce = ACTIONS.BIEGAJ;
        return chce == null ? ACTIONS.NIC : chce;
    }
}
