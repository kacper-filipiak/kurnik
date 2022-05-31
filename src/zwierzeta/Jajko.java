package zwierzeta;

import inne.ACTIONS;
import org.jetbrains.annotations.Nullable;

public class Jajko {
    long czasWykluwania;
    boolean zaplodnione;

    public Jajko(boolean _zaplodnione, long _czasWykluwania) {
        zaplodnione = _zaplodnione;
        czasWykluwania = _czasWykluwania;
    }

    @Nullable
    public ACTIONS zaktualizujWysiadywanie(long czas) {
        czasWykluwania -= czas;
        if (czasWykluwania < 0 && zaplodnione) return ACTIONS.WYKLUJ_KURCZAKA;
        else if (czasWykluwania < -10) {
            return ACTIONS.ZNISZCZ_JAJKO;
        } else return null;
    }
    kurczak zaktualizujWysiadywanie(long czas){
        //TODO: not implemented yet
    }
    Jajko(boolean _zaplodnione, long czasWykluwania){
        //TODO: not implemented yet
    }
}