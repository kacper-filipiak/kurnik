package inne;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SummaryPage extends Frame{

    LinkedHashMap<String, String> summaryMap = new LinkedHashMap<>();
    public void setSummaryMap(LinkedHashMap<String, String> summaryMap){
        this.summaryMap = summaryMap;
        repaint();
    }
    SummaryPage() {
        super("Podsumowanie");
        setSize(400, 300);
        setVisible(true);
    }
    public void paint(Graphics g) {
        drawSummary(g, summaryMap);
    }

    private void drawSummary(Graphics g, LinkedHashMap<String, String> list) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Microsoft YaHei", Font.PLAIN, scaleSmaller(50)));
        AtomicInteger i = new AtomicInteger();
        list.forEach((key, value) -> g2d.drawString(key + " " + value, 0, (i.getAndIncrement()+1) * scaleY(100)));
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
