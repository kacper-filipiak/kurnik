package zwierzeta;

import inne.ACTIONS;

import java.awt.*;

public class Lis extends Zwierze {
    float wspolczynnikSzansAtaku;

    public Lis(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja, float _wspolczynnikSzansAtaku) {
        super(_zapotrzebowanie, _zapotrzebowanie, _glod, _pragnienie, _pozycja);
        wspolczynnikSzansAtaku = _wspolczynnikSzansAtaku;
    }

    @Override
    public ACTIONS decyduj() {
        return null;
    }
}
