package zwierzeta;

import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;

public class Kurczak extends Drob {
    long wiekDojrzewania = 1000;
    public Kurczak(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pragnienie, _pozycja, _wiek, _wiekSmierci, _smiertelnyDeficytKalorii, _smiertelnyDeficytWody);
    }

    @Override
    public ACTIONS decyduj() {
        if (chce == ACTIONS.NIC) chce = switch (GlobalRandom.rand.nextInt(7)) {
            case 0 -> ACTIONS.BIEGAJ;
            case 1 -> ACTIONS.JEDZ;
            case 2 -> ACTIONS.PIJ;
            default -> ACTIONS.NIC;
        };
        if (chce == null) chce = ACTIONS.NIC;
        return chce;
    }

    @Override
    public ACTIONS starzej() {
        super.starzej();
        if(wiekDojrzewania < wiek) return ACTIONS.DOROSNIJ_KURCZAKA;
        else return ACTIONS.NIC;
    }
}
