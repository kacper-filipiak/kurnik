package zwierzeta;

import inne.ACTIONS;
import org.jetbrains.annotations.Nullable;

public class Jajko {
    long czasWykluwania;
    boolean zaplodnione;

    public boolean uzywane = false;

    public Jajko(boolean _zaplodnione, long _czasWykluwania) {
        zaplodnione = _zaplodnione;
        czasWykluwania = _czasWykluwania;
    }

    //zmniejsza czas potrzebny do wyklucia i jesli spadnie do 0 to zwraca akcje wyklucia kurczaka
    @Nullable
    public ACTIONS zaktualizujWysiadywanie(long czas) {
        czasWykluwania -= czas;
        if (czasWykluwania < 0 && zaplodnione) return ACTIONS.WYKLUJ_KURCZAKA;
        else if (czasWykluwania < -10) {
            return ACTIONS.ZNISZCZ_JAJKO;
        } else return null;
    }
}