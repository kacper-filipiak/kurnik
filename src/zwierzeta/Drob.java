package zwierzeta;

import inne.ACTIONS;

import java.awt.*;

import static inne.Logger.log;

public abstract class Drob extends Zwierze {
    long wiek;

    private static float kalorycznoscDrobiu = 1000.f;

    public static void setKalorycznoscDrobiu(float kalorycznoscDrobiu) {
        Drob.kalorycznoscDrobiu = kalorycznoscDrobiu;
    }

    public static float getKalorycznoscDrobiu() {
        return kalorycznoscDrobiu;
    }

    private static long wiekSmierci;

    public static void setWiekSmierci(long wiekSmierci) {
        Drob.wiekSmierci = wiekSmierci;
    }

    private static float smiertelnyDeficytKalorii;

    public static void setSmiertelnyDeficytKalorii(float smiertelnyDeficytKalorii) {
        Drob.smiertelnyDeficytKalorii = smiertelnyDeficytKalorii;
    }

    private static float smiertelnyDeficytWody;

    public static void setSmiertelnyDeficytWody(float smiertelnyDeficytWody) {
        Drob.smiertelnyDeficytWody = smiertelnyDeficytWody;
    }

    public Drob(float _zapotrzebowanieKalorii, float _zapotrzebowanieWody, float _glod, float _pragnieie, Point _pozycja, long _wiek) {
        super(_zapotrzebowanieKalorii, _zapotrzebowanieWody, _glod, _pragnieie, _pozycja);
        wiek = _wiek;
    }

    //symuluje starzenie sie drobiu i zwraca Zabij sie jesli warunki smierci spelnione
    public ACTIONS starzej() {
        wiek += 5;
        glod += 10.F;
        pragnienie += 5.F;
        if (glod > smiertelnyDeficytKalorii) {
            log("SMIERC_POWOD", "GLOD");
            return ACTIONS.ZABIJ_SIE;
        }
        if (pragnienie > smiertelnyDeficytWody) {
            log("SMIERC_POWOD", "PRAGNIENIE");
            return ACTIONS.ZABIJ_SIE;
        }
        if (wiek > wiekSmierci) {
            log("SMIERC_POWOD", "WIEK");
            return ACTIONS.ZABIJ_SIE;
        } else return ACTIONS.NIC;
    }
}

