package urzadzenia;

import zwierzeta.Jajko;

import java.awt.*;
import java.util.List;

public class Gniazdo extends Urzadzenie {
    List<Jajko> jajka;

    Gniazdo(Point _pozycja, int _liczbaStanowisk, List<Jajko> _jajka) {
        super(_pozycja, _liczbaStanowisk);
        jajka = _jajka;
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
}