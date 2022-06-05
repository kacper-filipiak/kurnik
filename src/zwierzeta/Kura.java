package zwierzeta;


import inne.ACTIONS;
import inne.GlobalRandom;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Kura extends Drob {
    private boolean zaplodniona = false;
    private static float zapotrzebowanieKalorii;
    public static void setZapotrzebowanieKalorii(float zapotrzebowanie){
        Kura.zapotrzebowanieKalorii = zapotrzebowanie;
    }
    private static float zapotrzebowanieWody;
    public static void setZapotrzebowanieWody(float zapotrzebowanie){
        Kura.zapotrzebowanieWody = zapotrzebowanie;
    }

    public void setZaplodniona(boolean zaplodniona) {
        this.zaplodniona = zaplodniona;
    }

    public Kura(float _glod, float _pragnienie, Point _pozycja, long _wiek) {
        super(zapotrzebowanieKalorii, zapotrzebowanieWody, _glod, _pragnienie, _pozycja, _wiek);
    }

    public Kura(Kurczak kurczak) {
        super(zapotrzebowanieKalorii, zapotrzebowanieWody, kurczak.glod, kurczak.pragnienie, kurczak.pozycja, kurczak.wiek);
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
        if(super.decyduj() == ACTIONS.ZABIJ_SIE) return ACTIONS.ZABIJ_SIE;
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
