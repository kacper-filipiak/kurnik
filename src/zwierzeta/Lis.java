package zwierzeta;

import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;

public class Lis extends Zwierze {
    static float wspolczynnikSzansAtaku = 0.2f;

    public static void setWspolczynnikSzansAtaku(float _wspolczynnikSzansAtaku){
        wspolczynnikSzansAtaku = _wspolczynnikSzansAtaku;
    }
    static float _zapotrzebowanieEnergetyczne = 100.f;

    public static void setZapotrzebowanieEnergetyczne(float zapotrzebowanieEnergetyczne) {
        Lis._zapotrzebowanieEnergetyczne = zapotrzebowanieEnergetyczne;
    }


    public Lis(Point _pozycja, float _wspolczynnikSzansAtaku) {
        super(_zapotrzebowanieEnergetyczne,0.f, 0.f, 0.f, _pozycja);
        wspolczynnikSzansAtaku = _wspolczynnikSzansAtaku;
    }

    @Override
    public ACTIONS decyduj() {
        glod += zapotrzebowanieEnergetyczne;
        if(wspolczynnikSzansAtaku * glod > zapotrzebowanieEnergetyczne * 10){
            if(GlobalRandom.rand.nextBoolean()) chce = ACTIONS.ATAK;
        }
        return chce == null ? ACTIONS.NIC : chce;
    }
}
