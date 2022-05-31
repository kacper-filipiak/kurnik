import inne.EventBus;
import inne.EventSubscriber;
import inne.GlobalRandom;
import urzadzenia.*;
import zwierzeta.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class Kurnik extends Frame implements EventBus {

    final private int fieldsX;
    final private int fieldsY;

    private final LinkedList<Zwierze> zwierzeta = new LinkedList<>();

    private LinkedList<Urzadzenie> urzadzenia = new LinkedList<>();

    Kurnik() {
        super("Java 2D Kurnik");
        EventSubscriber.subscribe(this);
        setSize(400, 300);
        fieldsX = 40;
        fieldsY = 20;
        zwierzeta.add(new Kura(100.f, 0.f, 0.f, new Point(5, 5), 100, 1000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, 0.f, new Point(5, 5), 100, 10000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, 0.f, new Point(5, 5), 100, 50, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, 0.f, new Point(5, 5), 100, 2000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, 0.f, new Point(5, 5), 100, 10000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, 0.f, new Point(5, 5), 100, 5000, 1000.f, 200.f));

        urzadzenia.add(new Poidlo(new Point(1, 4), 4, 4.f, 4.f));
        urzadzenia.add(new Pasnik(new Point(16, 18), 4, 10.f, new Pasza()));
        urzadzenia.add(new Gniazdo(new Point(24, 5), 3));

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

    public static void main(String[] args) {
        System.out.println("Starting");
        new Kurnik();
    }

    void ruchDrobiu(Drob drob) {
        switch (drob.decyduj()) {
            case PIJ -> pij(drob);
            case JEDZ -> jedz(drob);
            case WYSIADUJ_JAJO -> wysiadujJajko((Kura) drob);
            case ZABIJ_SIE -> zabij(drob);
            case BIEGAJ -> biegaj(drob);
            default -> System.out.println("No action");
        }
    }

    void pij(Zwierze zwierze) {
        Point destination = new Point();
        Poidlo poidlo = null;
        for (Urzadzenie urzadzenie :
                urzadzenia) {
            if (urzadzenie instanceof Poidlo) {
                destination = urzadzenie.pozycja;
                poidlo = (Poidlo) urzadzenie;
            }
        }
        if (destination.x == zwierze.pozycja.x && destination.y == zwierze.pozycja.y) {
            assert poidlo != null;
            zwierze.pij(poidlo.wydajWode());
        } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            zwierze.poruszajSie(destination);
    }

    void jedz(Zwierze zwierze) {
        Point destination = new Point();
        Pasnik pasnik = null;
        for (Urzadzenie urzadzenie :
                urzadzenia) {
            if (urzadzenie instanceof Pasnik) {
                destination = urzadzenie.pozycja;
                pasnik = (Pasnik) urzadzenie;
            }
        }
        if (destination.x == zwierze.pozycja.x && destination.y == zwierze.pozycja.y) {
            assert pasnik != null;
            Pasza pasza = pasnik.wydajPasze();
            zwierze.jedz(pasza.getEnergie());
        } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            zwierze.poruszajSie(destination);
    }

    void wysiadujJajko(Kura kura) {
        Point destination = new Point();
        Gniazdo gniazdo = null;
        for (Urzadzenie urzadzenie :
                urzadzenia) {
            if (urzadzenie instanceof Gniazdo) {
                destination = urzadzenie.pozycja;
                gniazdo = (Gniazdo) urzadzenie;
            }
        }
        if (destination.x == kura.pozycja.x && destination.y == kura.pozycja.y) {
            assert gniazdo != null;
            gniazdo.dodajJajo(kura.zlozJajko());
        } else if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            kura.poruszajSie(destination);
    }

    void zabij(Zwierze zwierze) {
        zwierze.dispose();
        zwierzeta.remove(zwierze);
    }

    void biegaj(Zwierze kura) {
        Point destination = new Point(GlobalRandom.rand.nextInt(fieldsX), GlobalRandom.rand.nextInt(fieldsY));
        if (destination.x >= 0 && destination.x < fieldsX && destination.y >= 0 && destination.y < fieldsY)
            kura.poruszajSie(destination);
        kura.chce = null;
    }

    void loop() {
        while (getWindows().length > 0) {
            for (Zwierze zwierze : zwierzeta) {
                if (zwierze instanceof Drob) ruchDrobiu((Drob) zwierze);
            }
            this.repaint();
            System.out.println(".");
        }
    }

    public void paint(Graphics g) {
        drawGrid(g);
        for (Zwierze zwierze : zwierzeta) {
            if(zwierze instanceof Kura) drawObject(g, zwierze.pozycja, Color.YELLOW, "kura");
            if(zwierze instanceof Kogut) drawObject(g, zwierze.pozycja, Color.YELLOW, "kogut");
            if(zwierze instanceof Kurczak) drawObject(g, zwierze.pozycja, Color.YELLOW, "kurczak");
            if(zwierze instanceof Lis) drawObject(g, zwierze.pozycja, Color.ORANGE, "lis");

        }
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

    @Override
    public void onEvent() {
        this.repaint();
    }
}

