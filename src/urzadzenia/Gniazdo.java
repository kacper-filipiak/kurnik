package urzadzenia;

import org.jetbrains.annotations.Nullable;
import zwierzeta.Jajko;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Gniazdo extends Urzadzenie {
    List<Jajko> jajka = new LinkedList<>();

    public Gniazdo(Point _pozycja, int _liczbaStanowisk) {
        super(_pozycja, _liczbaStanowisk);
    }
    Jajko zwrocWolneJajko(){
        //TODO: not implemented yet
    }
    List<Jajko> zwrocWszystkieJajka(){
        //TODO: not implemented yet
    };
    void usunJajko(jajko Jajko){
        //TODO: not implemented yet
    };

    @Override
    boolean zajmijStanowisko() {
        return false;
    }

    public void dodajJajo(Jajko jajo){
        jajka.add(jajo);
    }

    public void usunJajko(Jajko jajko){
        jajka.remove(jajko);
    }

    @Nullable
    public Jajko zwrocWolneJajko(){
        return jajka.isEmpty()? null : jajka.get(0);
    }
}