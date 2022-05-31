package zwierzeta;


import inne.ACTIONS;
import inne.GlobalRandom;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class Kura extends Drob {
    private boolean zaplodniona = false;

    public void setZaplodniona(boolean zaplodniona) {
        this.zaplodniona = zaplodniona;
    }

    public Kura(float _zapotrzebowanie, float _glod, float _pragnienie, Point _pozycja, long _wiek, long _wiekSmierci, float _smiertelnyDeficytKalorii, float _smiertelnyDeficytWody) {
        super(_zapotrzebowanie, _glod, _pragnienie, _pozycja, _wiek, _wiekSmierci, _smiertelnyDeficytKalorii, _smiertelnyDeficytWody);
    }

    public Kura(Kurczak kurczak) {
        super(kurczak.zapotrzebowanieEnergetyczne, kurczak.glod, kurczak.pragnienie, kurczak.pozycja, kurczak.wiek, kurczak.wiekSmierci, kurczak.smiertelnyDeficytKalorii, kurczak.smiertelnyDeficytWody);
    }

    public Jajko zlozJajko() {
        Jajko jajo = new Jajko(zaplodniona, GlobalRandom.rand.nextLong(1000));
        zaplodniona = false;
        chce = ACTIONS.NIC;
        return jajo;
    }

    public ACTIONS wysiadujJajko(Jajko jajo) {
        chce = ACTIONS.NIC;
        return jajo.zaktualizujWysiadywanie(100);
    }

    @NotNull
    @Override
    public ACTIONS decyduj() {
        chce = super.decyduj();
        if (chce == ACTIONS.NIC) chce = switch (GlobalRandom.rand.nextInt(7)) {
            case 0 -> ACTIONS.BIEGAJ;
            case 1 -> ACTIONS.JEDZ;
            case 2 -> ACTIONS.PIJ;
            case 3, 4 -> ACTIONS.WYSIADUJ_JAJO;
            case 5, 6 -> ACTIONS.ZLOZ_JAJKO;
            default -> ACTIONS.NIC;
        };
        if (chce == null) chce = ACTIONS.NIC;
        return chce;
    }
}
