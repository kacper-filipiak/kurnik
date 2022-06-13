package zwierzeta;

import inne.ACTIONS;
import inne.GlobalRandom;

import java.awt.*;

public class Kurczak extends Drob {
    long wiekDojrzewania = 300;
    private static float zapotrzebowanieKalorii;

    public static void setZapotrzebowanieKalorii(float zapotrzebowanie) {
        Kurczak.zapotrzebowanieKalorii = zapotrzebowanie;
    }

    private static float zapotrzebowanieWody;

    public static void setZapotrzebowanieWody(float zapotrzebowanie) {
        Kurczak.zapotrzebowanieWody = zapotrzebowanie;
    }

    public Kurczak(float _glod, float _pragnienie, Point _pozycja, long _wiek) {
        super(zapotrzebowanieKalorii, zapotrzebowanieWody, _glod, _pragnienie, _pozycja, _wiek);
    }

    public Kurczak(Point _pozycja) {
        super(zapotrzebowanieKalorii, zapotrzebowanieWody, 0, 0, _pozycja, 0);
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
        if (wiekDojrzewania < wiek) return ACTIONS.DOROSNIJ_KURCZAKA;
        else return ACTIONS.NIC;
    }
}
