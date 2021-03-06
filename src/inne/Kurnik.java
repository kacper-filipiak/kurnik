package inne;

import urzadzenia.*;
import zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.*;

import static inne.Logger.log;
import static java.lang.Math.min;
import static java.util.Map.entry;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class Kurnik extends Frame implements EventBus {

    final private int fieldsX;
    final private int fieldsY;
    private final ArrayList<Zwierze> zwierzeta = new ArrayList<>();
    private final ArrayList<Gospodarz> gospodarze = new ArrayList<>();
    SummaryPage summaryPage;
    private boolean koniecCzasu = false;
    private LinkedList<Urzadzenie> urzadzenia = new LinkedList<>();

    public Kurnik(int X, int Y, long maksymalnyCzasSymulacji, int maksymalnaLiczbaDrobiu, int liczbaKur, int liczbaKogutow, int liczbaGospodarzy, int liczbaLisow, int liczbaPasnikow, int liczbaPoidel, int liczbaGniazd) {
        super("Java 2D Kurnik");
        //pokazanie podsumowania
        new Thread(() -> summaryPage = new SummaryPage()).start();
        //subskrybowanie listenera w celu wywolywania repaint()
        EventSubscriber.subscribe(this);
        //specyfikacja rozmiaru i ilosci pol w oknie
        setSize(400, 300);
        fieldsX = X;
        fieldsY = Y;
        //Przygotowanie plikow wynikowych
        Logger.clearFile("summary");
        Logger.log(new LinkedHashMap<>(Map.ofEntries(
                entry("Licaba kur", "Licaba kur"),
                entry("Liczba kogutow", "Liczba kogutow"),
                entry("Liczba kurczakow", "Liczba kurczakow"),
                entry("Liczba lisow", "Liczba lisow"),
                entry("Liczba gospodarzy", "Liczba gospodarzy"),
                entry("Liczba zebranych jajek", "Liczba zebranych jajek"))), "summary");
        log("START", "////////////////// " + (new Timestamp(System.currentTimeMillis())) + " /////////////////////");
        for (int i = 0; i < liczbaKur; i++) {
            zwierzeta.add(new Kura(0.f, 0.f, new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY)), 100));
        }
        for (int i = 0; i < liczbaKogutow; i++) {
            zwierzeta.add(new Kogut(0.f, 0.f, new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY)), 100));
        }
        for (int i = 0; i < liczbaLisow; i++) {
            zwierzeta.add(new Lis(new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY)), 0.2f));
        }
        for (int i = 0; i < liczbaGospodarzy; i++) {
            gospodarze.add(new Gospodarz(5, new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY))));
        }

        for (int i = 0; i < liczbaPoidel; i++) {
            urzadzenia.add(new Poidlo(new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY)), 4, 4.f, 4.f, 500.f));
        }
        for (int i = 0; i < liczbaPasnikow; i++) {
            urzadzenia.add(new Pasnik(new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY)), 4, 10.f, new Pasza(50.f), 500.f));
        }
        for (int i = 0; i < liczbaGniazd; i++) {
            urzadzenia.add(new Gniazdo(new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY)), 3));
        }

        setVisible(true);
        addWindowListener(new WindowAdapter() {
                              public void windowClosing(WindowEvent e) {
                                  dispose();
                                  System.exit(0);
                              }
                          }
        );
        //Uruchomienie odliczania do konca czasu przeznaczonego na symulacje
        new Thread(() -> {
            try {
                Thread.sleep(maksymalnyCzasSymulacji);
                koniecCzasu = true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        //Start petli glownej programu
        loop(maksymalnaLiczbaDrobiu);

        //Pokazanie popupa na koncu symulacji
        JOptionPane.showMessageDialog(this,
                "Symulacja zakonczona!");
    }

    ////////////////////////////////////////////////Funkcje opowiedzialne za ruch drobiu////////////////////////////////
    void ruchDrobiu(Drob drob) {
        if (uciekacPrzedGospodarzem(drob)) {
            biegaj(drob);
        } else {
            switch (drob.decyduj()) {
                case PIJ -> pij(drob);
                case JEDZ -> jedz(drob);
                case WYSIADUJ_JAJO -> wysiadujJajko((Kura) drob);
                case ZLOZ_JAJKO -> zlozJajko((Kura) drob);
                case BIEGAJ -> biegaj(drob);
                case ZAPLODNIJ_KURE -> zaplodniKure((Kogut) drob);
                default -> {
                }
            }
        }
        switch (drob.starzej()) {
            case ZABIJ_SIE -> zabij(drob);
            case DOROSNIJ_KURCZAKA -> dorosniKurczaka((Kurczak) drob);
            default -> {
            }
        }
    }


    //Zmienia kurczaka na kure lub koguta
    void dorosniKurczaka(Kurczak kurczak) {
        Drob drob = GlobalRandom.rand.nextInt(2) == 0 ? new Kura(kurczak) : new Kogut(kurczak);
        zwierzeta.add(drob);
        zwierzeta.remove(kurczak);
        log("DOROSNIJ_KURCZAKA", kurczak + " -> " + drob);
    }

    //Znajduje najblizsza kure i gdy dojdzie do niej umozliwia zlozenie jej zaplodnionego jajka w kurniku
    void zaplodniKure(Kogut kogut) {
        Point destination = new Point();
        Optional<Zwierze> kura = zwierzeta.stream().filter(zwierze -> zwierze instanceof Kura).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(kogut.pozycja)));
        kura.ifPresentOrElse((zwierze) ->
                {
                    if (destination.distance(kogut.pozycja) < 0.5) {
                        kogut.zaplodnijKure((Kura) zwierze);
                        log("ZAPLODNIJ_KURE", kogut + " -> " + kura);
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        kogut.poruszajSie(destination);
                    else kogut.chce = null;
                },
                () ->
                        kogut.chce = null
        );
    }

    //znajduje poidlo i gdy tam dojdze to zminejsza pragnienie
    void pij(Zwierze zwierze) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Poidlo).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(zwierze.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Poidlo poidlo = (Poidlo) item;
                    Point destination = poidlo.pozycja;
                    if (destination.distance(zwierze.pozycja) < 0.5) {
                        zwierze.pij(poidlo.wydajWode());
                        log("PIJ", zwierze.toString());
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        zwierze.poruszajSie(destination);
                    else zwierze.chce = null;
                },
                () -> zwierze.chce = null
        );
    }

    //znajduje najblizszy pasnik i zmniejsza glod gdy do niego dojdzie
    void jedz(Zwierze zwierze) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Pasnik).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(zwierze.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Pasnik pasnik = (Pasnik) item;
                    Point destination = pasnik.pozycja;
                    if (destination.distance(zwierze.pozycja) < 0.5) {
                        Pasza pasza = pasnik.wydajPasze();
                        zwierze.jedz(pasza.getEnergie());
                        log("JEDZ", zwierze.toString());
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        zwierze.poruszajSie(destination);
                    else zwierze.chce = null;
                },
                () -> zwierze.chce = null);
    }

    //szuka najblizszego gniazda, prosi o zwrocenie wolnego jajka i gdy takie jest wysiaduje je, co moze owocowac w wykluciu kurczaka
    void wysiadujJajko(Kura kura) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Gniazdo).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(kura.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Gniazdo gniazdo = (Gniazdo) item;
                    Point destination = gniazdo.pozycja;
                    if (destination.distance(kura.pozycja) < 0.5) {
                        Jajko jajko = gniazdo.zwrocWolneJajko();
                        if (jajko != null) {
                            ACTIONS action = kura.wysiadujJajko(jajko);
                            log("WYSIADUJ_JAJKO", kura + " -> " + jajko);
                            if (action == ACTIONS.WYKLUJ_KURCZAKA) {
                                zwierzeta.add(new Kurczak(new Point(gniazdo.pozycja)));
                                gniazdo.usunJajko(jajko);
                                log("WYKLUJ_KURCZAKA", jajko.toString());
                            } else if (action == ACTIONS.ZNISZCZ_JAJKO) {
                                gniazdo.usunJajko(jajko);
                                log("ZNISZCZ_JAJKO", jajko.toString());
                            }
                        } else {
                            kura.chce = ACTIONS.NIC;
                        }
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        kura.poruszajSie(destination);
                    else kura.chce = null;
                },
                () -> kura.chce = null);
    }

    //szuka najblizszego gniazda i gdy tam dojdzie kura znosi jajko
    void zlozJajko(Kura kura) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Gniazdo).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(kura.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Gniazdo gniazdo = (Gniazdo) item;
                    Point destination = gniazdo.pozycja;
                    if (destination.distance(kura.pozycja) < 0.5) {
                        gniazdo.dodajJajo(kura.zlozJajko());
                        log("ZLOZ_JAJKO", kura.toString());
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        kura.poruszajSie(destination);
                    else kura.chce = null;
                },
                () -> kura.chce = null);
    }

    //zabija zwierze
    void zabij(Zwierze zwierze) {
        zwierzeta.remove(zwierze);
        log("ZABIJ", zwierze.toString());
    }

    //gdy zwierze w poblizu dwoch pol od gospodarza zwraca prawde
    boolean uciekacPrzedGospodarzem(Zwierze zwierze) {
        for (Gospodarz gospodarz : gospodarze) {
            if (gospodarz.pozycja.distance(zwierze.pozycja) < 1.5f) return true;
        }
        return false;
    }

    //realizuje ruch w losowy punkt na mapie dla zwierzat
    void biegaj(Zwierze kura) {
        Point destination = new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY));
        if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            kura.poruszajSie(destination);
        kura.chce = null;
    }

    //////////////////////////////////////////////////////////////GOSPODARZ/////////////////////////////////////////////////
    //realizuje ruch w losowy punkt na mapie dla gospodarza
    void biegaj(Gospodarz gospodarz) {
        Point destination = new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY));
        if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            gospodarz.poruszajSie(destination);
        gospodarz.chce = null;
    }

    //Wybur obecnej akcji gospodarza
    void ruchGospodarza(Gospodarz gospodarz) {
        switch (gospodarz.decyduj()) {
            case NAPELNIJ_PASNIK -> napelnijPasnik(gospodarz);
            case NAPELNIJ_POIDLO -> napelnijPoidlo(gospodarz);
            case ZBIERAJ_JAJKA -> zbierajJajka(gospodarz);
            case BIEGAJ -> biegaj(gospodarz);
            default -> {
            }

        }
    }

    //szuka najblizszego pasnika, gdy do niego dojdzie napelnia go
    void napelnijPasnik(Gospodarz gospodarz) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Pasnik).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(gospodarz.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Pasnik pasnik = (Pasnik) item;
                    Point destination = pasnik.pozycja;
                    if (destination.distance(gospodarz.pozycja) < 0.5) {
                        gospodarz.uzupelnijPasze(pasnik);
                        gospodarz.chce = ACTIONS.NIC;
                        log("NAPELNIJ_WODE", gospodarz + " -> " + pasnik);
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        gospodarz.poruszajSie(destination);
                },
                () -> gospodarz.chce = ACTIONS.NIC
        );
    }

    //szuka najblizszego poidla i napelnia je gdy do niego dojdzie
    void napelnijPoidlo(Gospodarz gospodarz) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Poidlo).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(gospodarz.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Poidlo poidlo = (Poidlo) item;
                    Point destination = poidlo.pozycja;
                    if (destination.distance(gospodarz.pozycja) < 0.5) {
                        gospodarz.uzupelnijWode(poidlo);
                        gospodarz.chce = ACTIONS.NIC;
                        log("NAPELNIJ_PASZE", gospodarz + "->" + poidlo);
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        gospodarz.poruszajSie(destination);
                },
                () -> gospodarz.chce = ACTIONS.NIC
        );
    }

    //szuka najblizszego gniazda i jak do niego dojdzie to zbiera z niego jajka
    void zbierajJajka(Gospodarz gospodarz) {
        Optional<Urzadzenie> urzadzenie = urzadzenia.stream().filter(_urzadzenie -> _urzadzenie instanceof Gniazdo).min(Comparator.comparingDouble(elem -> elem.pozycja.distance(gospodarz.pozycja)));
        urzadzenie.ifPresentOrElse(
                (item) -> {
                    Gniazdo gniazdo = (Gniazdo) item;
                    Point destination = gniazdo.pozycja;
                    if (destination.distance(gospodarz.pozycja) < 0.5) {
                        gospodarz.zbierzJajka(gniazdo);
                        gospodarz.chce = ACTIONS.NIC;
                        log("ZBIERAJ_JAJKA", gospodarz + "->" + gniazdo);
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        gospodarz.poruszajSie(destination);
                },
                () -> gospodarz.chce = ACTIONS.NIC
        );
    }

    /////////////////////////////////////////////////LIS////////////////////////////////////////////////////////////////////
    //Wybur akcji lisa
    void ruchLisa(Lis lis) {
        if (uciekacPrzedGospodarzem(lis)) {
            //Je??li lis blisko gospodarza to albo ucieka albo zabija go gospodarz
            if (GlobalRandom.rand.nextBoolean()) {
                biegaj(lis);
            } else {
                zabij(lis);
            }
        } else {
            switch (lis.decyduj()) {
                case ATAK -> polujNaDrob(lis);
                case BIEGAJ -> biegaj(lis);
                default -> {
                }
            }
        }
    }

    //Szuka najblizszego drobiu i jesli dopadnie drob to go zabija i redukuje swoj glod
    void polujNaDrob(Lis lis) {
        Optional<Zwierze> zwierze = zwierzeta.stream().filter((elem) -> elem instanceof Drob).findFirst();
        zwierze.ifPresentOrElse(
                (item) -> {
                    Drob drob = (Drob) item;
                    Point destination = drob.pozycja;
                    if (destination.distance(lis.pozycja) < 0.5) {
                        zabij(drob);
                        lis.jedz(Drob.getKalorycznoscDrobiu());
                        lis.chce = ACTIONS.NIC;
                        log("ATAK_LISA", lis + "->" + drob);
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        lis.poruszajSie(destination);
                },
                () -> lis.chce = ACTIONS.NIC
        );
    }

    ////////////////////////////////////////////////////////INNE////////////////////////////////////////////////////////
    //Glowna petla programu
    void loop(int maksymalnaLiczbaDrobiu) {
        //sprawdzanie warunkow konca symulacji
        while (getWindows().length > 0 && !koniecCzasu && zwierzeta.stream().filter((elem) -> elem instanceof Drob).toList().size() > 0 && maksymalnaLiczbaDrobiu > zwierzeta.stream().filter((elem) -> elem instanceof Drob).toList().size()) {
            for (Gospodarz gospodarz : gospodarze) {
                ruchGospodarza(gospodarz);
            }
            for (int i = 0; i < zwierzeta.size(); i++) {
                Zwierze zwierze = zwierzeta.get(i);
                if (zwierze instanceof Drob) ruchDrobiu((Drob) zwierze);
                if (zwierze instanceof Lis) ruchLisa((Lis) zwierze);
            }
            this.repaint();
            try {
                Thread.sleep(min(10 * Speed.getTimeBase(), 100));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //////////////////////////////////////WYSWIETLANIE//////////////////////////////////////////////////////////////////
    public void paint(Graphics g) {
        drawGrid(g);
        int liczbaKur = 0;
        int liczbaKogutow = 0;
        int liczbaKurczakow = 0;
        int liczbaLisow = 0;
        int liczbaGospodarzy = 0;
        for (int i = 0; i < zwierzeta.size(); i++) {
            Zwierze zwierze = zwierzeta.get(i);
            if (zwierze instanceof Kura) {
                drawObject(g, zwierze.pozycja, Color.YELLOW, "kura");
                liczbaKur++;
            }
            if (zwierze instanceof Kogut) {
                drawObject(g, zwierze.pozycja, Color.YELLOW, "kogut");
                liczbaKogutow++;
            }
            if (zwierze instanceof Kurczak) {
                drawObject(g, zwierze.pozycja, Color.YELLOW, "kurczak");
                liczbaKurczakow++;
            }
            if (zwierze instanceof Lis) {
                drawObject(g, zwierze.pozycja, Color.ORANGE, "lis");
                liczbaLisow++;
            }

        }
        for (Gospodarz gospodarz : gospodarze) {
            drawObject(g, gospodarz.pozycja, Color.PINK, "gospodarz");
            liczbaGospodarzy++;
        }
        //Przekazywanie statystyk do podsumowania
        LinkedHashMap<String, String> summaryList = new LinkedHashMap<>(Map.ofEntries(
                entry("Licaba kur", Integer.toString(liczbaKur)),
                entry("Liczba kogutow", Integer.toString(liczbaKogutow)),
                entry("Liczba kurczakow", Integer.toString(liczbaKurczakow)),
                entry("Liczba lisow", Integer.toString(liczbaLisow)),
                entry("Liczba gospodarzy", Integer.toString(liczbaGospodarzy)),
                entry("Liczba zebranych jajek", Integer.toString(Gospodarz.getZebraneJajka()))));
        summaryPage.setSummaryMap(summaryList);
        //Wpisanie statystyk do pliku summary.csv
        Logger.log(summaryList, "summary");
        for (Urzadzenie urzadzenie :
                urzadzenia) {
            if (urzadzenie instanceof Poidlo) drawObject(g, urzadzenie.pozycja, Color.BLUE, "Poide??ko");
            if (urzadzenie instanceof Pasnik) drawObject(g, urzadzenie.pozycja, Color.GREEN, "Pa??nik");
            if (urzadzenie instanceof Gniazdo) drawObject(g, urzadzenie.pozycja, Color.orange, "Gniazdo");
        }
    }

    //dynamiczne skanowanie szerokosci
    private int scaleX(int x) {
        return (int) (getWidth() / 1920.f * x);
    }

    //dynamiczne skalowanie wysokosci
    private int scaleY(int y) {
        return (int) (getHeight() / 1080.f * y);
    }

    //wybor mniejszego wymiary tak aby zawsze zmiescic obiekt
    private int scaleSmaller(int val) {
        return getHeight() > getWidth() ? scaleX(val) : scaleY(val);
    }

    //rysuje plansze o wymiarach fieldsX na fieldsY
    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int rectWidth = getWidth() / (fieldsX + 1);
        int rectHeight = getHeight() / (fieldsY + 1);
        for (int x = 0; x < fieldsX; x++) {
            for (int y = 0; y < fieldsY; y++) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect((x + 1) * rectWidth, (y + 1) * rectHeight, rectWidth, rectHeight);
            }
        }
    }

    //rysuje obiekt z tekstem w postaci kwadratu w zadanym kolorze
    private void drawObject(Graphics g, Point point, Color color, String text) {
        Graphics2D g2d = (Graphics2D) g;
        int rectWidth = getWidth() / (fieldsX + 1);
        int rectHeight = getHeight() / (fieldsY + 1);
        g2d.setColor(color);
        g2d.fillRect((point.x + 1) * rectWidth, (point.y + 1) * rectHeight, rectWidth, rectHeight);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, scaleSmaller(50)));
        g2d.drawString(text, (point.x + 1) * rectWidth, (point.y + 2) * rectHeight);
    }

    //Funkcja implementujaca obserwatora
    @Override
    public void onEvent() {
        this.repaint();
    }
}

