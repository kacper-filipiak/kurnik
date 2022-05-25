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
        zwierzeta.add(new Kura(2, 7));

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
                int x = zwierzeta.getFirst().x + rand.nextInt() % 3 - 1;
                int y = zwierzeta.getFirst().y + rand.nextInt() % 3 - 1;
                if (x >= 0 && x < fieldsX) zwierzeta.getFirst().x = x;
                if (y >= 0 && y < fieldsX) zwierzeta.getFirst().y = y;
                this.repaint();
                System.out.println(".");
            }
        } catch (InterruptedException e) {

        }
    }

    public void paint(Graphics g) {
        drawGrid(g);
        drawObject(g, 2, 3, Color.BLUE, "poidełko");
        drawObject(g, zwierzeta.getFirst().x, zwierzeta.getFirst().y, Color.YELLOW, "kura");
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

class Kura {
    public int x;
    public int y;

    Kura(int positionX, int positionY) {
        x = positionX;
        y = positionY;
    }
}
