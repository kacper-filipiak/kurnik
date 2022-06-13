package zwierzeta;


import inne.ACTIONS;
import inne.GlobalRandom;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Kura extends Drob {
    private boolean zaplodniona = false;
    private static float zapotrzebowanieKalorii;

    public static void setZapotrzebowanieKalorii(float zapotrzebowanie) {
        Kura.zapotrzebowanieKalorii = zapotrzebowanie;
    }

    private static float zapotrzebowanieWody;

    public static void setZapotrzebowanieWody(float zapotrzebowanie) {
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
        thread = new Thread(() -> {
            lock.lock();
            jajo.uzywane = true;
            try {
                thread.wait(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            jajo.uzywane = false;
            lock.unlock();
        });

        return jajo.zaktualizujWysiadywanie(100);
    }

    @NotNull
    @Override
    public ACTIONS decyduj() {
        if (chce == ACTIONS.NIC) chce = switch (GlobalRandom.rand.nextInt(10)) {
            case 0 -> ACTIONS.BIEGAJ;
            case 1 -> ACTIONS.JEDZ;
            case 2 -> ACTIONS.PIJ;
            case 3, 4, 7, 8, 9 -> ACTIONS.WYSIADUJ_JAJO;
            case 5, 6 -> ACTIONS.ZLOZ_JAJKO;
            default -> ACTIONS.NIC;
        };
        if (chce == null) chce = ACTIONS.NIC;
        return chce;
    }
}
