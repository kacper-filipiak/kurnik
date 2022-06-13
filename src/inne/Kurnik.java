package inne;

import inne.*;
import urzadzenia.*;
import zwierzeta.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.*;

import static inne.Logger.log;

public class Kurnik extends Frame implements EventBus {

    final private int fieldsX;
    final private int fieldsY;

    private final ArrayList<Zwierze> zwierzeta = new ArrayList<>();

    private LinkedList<Urzadzenie> urzadzenia = new LinkedList<>();

    private final ArrayList<Gospodarz> gospodarze = new ArrayList<>();

    public Kurnik(int liczbaKur, int liczbaKogutow, int liczbaGospodarzy, int liczbaLisow, int liczbaPasnikow, int liczbaPoidel, int liczbaGniazd) {
        super("Java 2D inne.Kurnik");
        EventSubscriber.subscribe(this);
        setSize(400, 300);
        fieldsX = 40;
        fieldsY = 20;
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
        loop();
    }

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
                default -> System.out.println("No action");
            }
        }
        switch (drob.starzej()) {
            case ZABIJ_SIE -> zabij(drob);
            case DOROSNIJ_KURCZAKA -> dorosniKurczaka((Kurczak) drob);
            default -> {
            }
        }
    }


    void dorosniKurczaka(Kurczak kurczak) {
        Drob drob = GlobalRandom.rand.nextInt(2) == 0 ? new Kura(kurczak) : new Kogut(kurczak);
        zwierzeta.add(drob);
        zwierzeta.remove(kurczak);
        log("DOROSNIJ_KURCZAKA", kurczak + " -> " + drob);
    }

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

    void zabij(Zwierze zwierze) {
//        zwierze.dispose();
        zwierzeta.remove(zwierze);
        log("ZABIJ", zwierze.toString());
    }

    boolean uciekacPrzedGospodarzem(Zwierze zwierze) {
        for (Gospodarz gospodarz : gospodarze) {
            if (gospodarz.pozycja.distance(zwierze.pozycja) < 1.5f) return true;
        }
        return false;
    }

    void biegaj(Zwierze kura) {
        Point destination = new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY));
        if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            kura.poruszajSie(destination);
        kura.chce = null;
    }

    void biegaj(Gospodarz gospodarz) {
        Point destination = new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY));
        if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            gospodarz.poruszajSie(destination);
        gospodarz.chce = null;
    }

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

    void ruchLisa(Lis lis) {
        if (uciekacPrzedGospodarzem(lis)) {
            if (GlobalRandom.rand.nextBoolean()) {
                biegaj(lis);
            } else {
                zabij(lis);
                zwierzeta.add(new Lis(new Point(0, 0), 0.2f));
            }
        } else {
            if (lis.decyduj() == ACTIONS.ATAK) {
                polujNaDrob(lis);
            }
        }
    }

    void polujNaDrob(Lis lis) {
        Optional<Zwierze> zwierze = zwierzeta.stream().filter((elem) -> elem instanceof Drob).findFirst();
        zwierze.ifPresentOrElse(
                (item) -> {
                    Drob drob = (Drob) item;
                    Point destination = drob.pozycja;
                    if (destination.distance(lis.pozycja) < 0.5) {
                        zabij(drob);
                        lis.jedz(5000.f);
                        lis.chce = ACTIONS.NIC;
                        log("ATAK_LISA", lis + "->" + drob);
                    } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
                        lis.poruszajSie(destination);
                },
                () -> lis.chce = ACTIONS.NIC
        );
    }

    void loop() {
        while (getWindows().length > 0 && zwierzeta.size() > 0) {
            for (Gospodarz gospodarz : gospodarze) {
                ruchGospodarza(gospodarz);
            }
            for (int i = 0; i < zwierzeta.size(); i++) {
                Zwierze zwierze = zwierzeta.get(i);
                if (zwierze instanceof Drob) ruchDrobiu((Drob) zwierze);
                if (zwierze instanceof Lis) ruchLisa((Lis) zwierze);
            }
            this.repaint();
            System.out.println(".");
            if (zwierzeta.size() > fieldsX * fieldsY) break;
            try {
                Thread.sleep(10 * Speed.getTimeBase());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paint(Graphics g) {
        drawGrid(g);
        int liczbaKur = 0;
        int liczbaKogutow = 0;
        int liczbaKurczakow = 0;
        int liczbaLisow = 0;
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
        }
        ArrayList<String> summaryList = new ArrayList<>(Arrays.asList(Integer.toString(liczbaKur), Integer.toString(liczbaKogutow), Integer.toString(liczbaKurczakow), Integer.toString(liczbaLisow), "Ilosc Zebranych Jajek: " + Gospodarz.getZebraneJajka()));
        drawSummary(g, summaryList);
        for (Urzadzenie urzadzenie :
                urzadzenia) {
            if (urzadzenie instanceof Poidlo) drawObject(g, urzadzenie.pozycja, Color.BLUE, "Poidełko");
            if (urzadzenie instanceof Pasnik) drawObject(g, urzadzenie.pozycja, Color.GREEN, "Paśnik");
            if (urzadzenie instanceof Gniazdo) drawObject(g, urzadzenie.pozycja, Color.orange, "Gniazdo");
        }
    }

    private int scaleX(int x) {
        return (int) (getWidth() / 1920.f * x);
    }

    private int scaleY(int y) {
        return (int) (getHeight() / 1080.f * y);
    }

    private int scaleSmaller(int val) {
        return getHeight() > getWidth() ? scaleX(val) : scaleY(val);
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int rectWidth = getWidth() / fieldsX;
        int rectHeight = getHeight() / fieldsY;
        for (int x = 0; x < fieldsX; x++) {
            for (int y = 0; y < fieldsY; y++) {
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawRect(x * rectWidth, y * rectHeight, rectWidth, rectHeight);
            }
        }
    }

    private void drawObject(Graphics g, Point point, Color color, String text) {
        Graphics2D g2d = (Graphics2D) g;
        int rectWidth = getWidth() / fieldsX;
        int rectHeight = getHeight() / fieldsY;
        g2d.setColor(color);
        g2d.fillRect(point.x * rectWidth, point.y * rectHeight, rectWidth, rectHeight);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, scaleSmaller(50)));
        g2d.drawString(text, point.x * rectWidth, (point.y + 1) * rectHeight);
    }

    private void drawSummary(Graphics g, ArrayList<String> list) {
        Graphics2D g2d = (Graphics2D) g;
        int rectWidth = getWidth() / fieldsX;
        int rectHeight = getHeight() / fieldsY;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, scaleSmaller(50)));
        for (int i = 0; i < list.size(); i++) {
            g2d.drawString(list.get(i), (fieldsX - 20) * rectWidth, (fieldsY - list.size() + i) * rectHeight);
        }
    }

    @Override
    public void onEvent() {
        this.repaint();
    }
}
