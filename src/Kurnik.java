import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.Random;

public class Kurnik extends Frame {

    final private int fieldsX;
    final private int fieldsY;

    private final LinkedList<Kura> zwierzeta;

    public static void main(String[] args) {
        System.out.println("Starting");
        new Kurnik();
    }

    Kurnik() {
        super("Java 2D Kurnik");
        setSize(400, 300);
        fieldsX = 20;
        fieldsY = 10;
        zwierzeta = new LinkedList<Kura>();
        zwierzeta.add(new Kura(100.f, 0.f, new Point(5, 5), 100, 1000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, new Point(5, 5), 100, 10000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, new Point(5, 5), 100, 50, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, new Point(5, 5), 100, 2000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, new Point(5, 5), 100, 10000, 1000.f, 200.f));
        zwierzeta.add(new Kura(100.f, 0.f, new Point(5, 5), 100, 5000, 1000.f, 200.f));

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

    void loop() {
        Random rand = new Random();
        try {
            while (getWindows().length > 0) {
                Thread.sleep(1000);
                for( Kura kura : zwierzeta) {
                    int x = kura.pozycja.x + rand.nextInt() % 3 - 1;
                    int y = kura.pozycja.y + rand.nextInt() % 3 - 1;
                    if (x >= 0 && x < fieldsX) kura.pozycja.x = x;
                    if (y >= 0 && y < fieldsX) kura.pozycja.y = y;
                }
                zwierzeta.removeIf(k -> (k.starzej() == ACTIONS.ZABIJ_SIE));
                this.repaint();
                System.out.println(".");
            }
        } catch (InterruptedException e) {

        }
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawObject(g, 2, 3, Color.BLUE, "poidełko");
        for(Kura kura : zwierzeta) {
            drawObject(g, kura.pozycja.x, kura.pozycja.y, Color.YELLOW, "kura");
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

    private void drawObject(Graphics g, int x, int y, Color color, String text) {
        Graphics2D g2d = (Graphics2D) g;
        int rectWidth = getWidth() / fieldsX;
        int rectHeight = getHeight() / fieldsY;
        g2d.setColor(color);
        g2d.fillRect(x * rectWidth, y * rectHeight, rectWidth, rectHeight);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, scaleSmaller(50)));
        g2d.drawString(text, x * rectWidth, (y + 1) * rectHeight);
    }
}

