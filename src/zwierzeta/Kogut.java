package zwierzeta;

import inne.ACTIONS;

import java.awt.*;

public class Kogut extends Drob {
    public Kogut(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pragnienie, _pozycja, _wiek, _wiekSmierci, _smiertelnyDeficytKalorii, _smiertelnyDeficytWody);
    }

    @Override
    public ACTIONS decyduj() {
        return null;
    }
}
