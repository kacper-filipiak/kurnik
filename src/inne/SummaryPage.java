package inne;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
public class SummaryPage extends Frame {

    LinkedHashMap<String, String> summaryMap = new LinkedHashMap<>();

    SummaryPage() {
        super("Podsumowanie");
        setSize(400, 300);
        setVisible(true);
    }

    public void setSummaryMap(LinkedHashMap<String, String> summaryMap) {
        this.summaryMap = summaryMap;
        repaint();
    }

    public void paint(Graphics g) {
        drawSummary(g, summaryMap);
    }

    private void drawSummary(Graphics g, LinkedHashMap<String, String> list) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, scaleSmaller(50)));
        AtomicInteger i = new AtomicInteger();
        list.forEach((key, value) -> g2d.drawString(key + " " + value, 0, (i.getAndIncrement() + 2) * scaleY(100)));
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
}
