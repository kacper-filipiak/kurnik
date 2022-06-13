package urzadzenia;

import org.jetbrains.annotations.Nullable;
import zwierzeta.Jajko;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class Gniazdo extends Urzadzenie {
    List<Jajko> jajka = new LinkedList<>();

    public Gniazdo(Point _pozycja, int _liczbaStanowisk) {
        super(_pozycja, _liczbaStanowisk);
    }

    @Override
    boolean zajmijStanowisko() {
        return false;
    }

    public void dodajJajo(Jajko jajo) {
        jajka.add(jajo);
    }

    public void usunJajko(Jajko jajko) {
        jajka.remove(jajko);
    }

    //Zwraca jajko ktore nie jest uzywane w danym momencie lub jesli nie ma takiego to null
    @Nullable
    public Jajko zwrocWolneJajko() {
        return jajka.stream().filter((Jajko elem) -> !elem.uzywane).findFirst().orElse(null);
    }
}