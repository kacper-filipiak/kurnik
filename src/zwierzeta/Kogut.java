package zwierzeta;

import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;

public class Kogut extends Drob {
    private static float zapotrzebowanieKalorii;

    public static void setZapotrzebowanieKalorii(float zapotrzebowanie) {
        Kogut.zapotrzebowanieKalorii = zapotrzebowanie;
    }

    private static float zapotrzebowanieWody;

    public static void setZapotrzebowanieWody(float zapotrzebowanie) {
        Kogut.zapotrzebowanieWody = zapotrzebowanie;
    }

    public Kogut(float _glod, float _pragnienie, Point _pozycja, long _wiek) {
        super(zapotrzebowanieKalorii, zapotrzebowanieWody, _glod, _pragnienie, _pozycja, _wiek);
    }

    public Kogut(Kurczak kurczak) {
        super(zapotrzebowanieKalorii, zapotrzebowanieWody, kurczak.glod, kurczak.pragnienie, kurczak.pozycja, kurczak.wiek);
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
            case 3, 4, 5 -> ACTIONS.ZAPLODNIJ_KURE;
            default -> ACTIONS.NIC;
        };
        if (chce == null) chce = ACTIONS.NIC;
        return chce;
    }
}
