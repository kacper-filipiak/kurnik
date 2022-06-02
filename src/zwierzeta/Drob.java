package zwierzeta;

import inne.ACTIONS;

import java.awt.*;

public abstract class Drob extends Zwierze {
    long wiek;
    long wiekSmierci;
    float smiertelnyDeficytKalorii;
    float smiertelnyDeficytWody;

    public Drob(float _zapotrzebowanie, float _glod, float _pragnieie, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pragnieie, _pozycja);
        wiek = _wiek;
        wiekSmierci = _wiekSmierci;
        smiertelnyDeficytKalorii = _smiertelnyDeficytKalorii;
        smiertelnyDeficytWody = _smiertelnyDeficytWody;
    }

    public ACTIONS starzej() {
        wiek += 5;
        glod += 10.F;
        pragnienie += 5.F;
        if (wiekSmierci < wiek)
            return ACTIONS.ZABIJ_SIE;
        else return ACTIONS.NIC;
    }

    @Override
    public ACTIONS decyduj() {
        if (glod > smiertelnyDeficytKalorii || pragnienie > smiertelnyDeficytWody || wiek > wiekSmierci) return ACTIONS.ZABIJ_SIE;
        else return ACTIONS.NIC;
    }
}

