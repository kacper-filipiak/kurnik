package zwierzeta;

import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;

public class Kogut extends Drob {
    public Kogut(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pragnienie, _pozycja, _wiek, _wiekSmierci, _smiertelnyDeficytKalorii, _smiertelnyDeficytWody);
    }

    public void zaplodnijKure(Kura kura) {
        kura.setZaplodniona(true);
    }

    @Override
    public ACTIONS decyduj() {
        if (chce == ACTIONS.NIC) chce = switch (GlobalRandom.rand.nextInt(6)) {
            case 0 -> ACTIONS.BIEGAJ;
            case 1 -> ACTIONS.JEDZ;
            case 2 -> ACTIONS.PIJ;
            case 3,4,5 -> ACTIONS.ZAPLODNIJ_KURE;
            default -> ACTIONS.NIC;
        };
        if (chce == null) chce = ACTIONS.NIC;
        return chce;
    }
}
