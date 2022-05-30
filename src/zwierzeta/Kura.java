package zwierzeta;


import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;
import java.util.Random;

public class Kura extends Drob {
    boolean zaplodniona = false;

    public void setZaplodniona(boolean zaplodniona) {
        this.zaplodniona = zaplodniona;
    }

    public Kura(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pragnienie, _pozycja, _wiek, _wiekSmierci, _smiertelnyDeficytKalorii, _smiertelnyDeficytWody);
    }

    public Jajko zlozJajko() {
        Jajko jajo = new Jajko(zaplodniona, GlobalRandom.rand.nextLong(1000));
        zaplodniona = false;
        chce = null;
        return jajo;
    }

    public ACTIONS wysiadujJajko(Jajko jajo){
        chce = null;
        return jajo.zaktualizujWysiadywanie(100);
    }

    @Override
    public ACTIONS decyduj() {
        if(chce == null) chce = switch (GlobalRandom.rand.nextInt(4)) {
            case 0 -> ACTIONS.BIEGAJ;
            case 1 -> ACTIONS.JEDZ;
            case 2 -> ACTIONS.PIJ;
            case 3 -> ACTIONS.WYSIADUJ_JAJO;
            default -> ACTIONS.NIC;
        };
        return chce;
    }
}
