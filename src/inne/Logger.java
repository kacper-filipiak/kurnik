package inne;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    static public void log(String TAG, String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", true));
            writer.append('\n');
            writer.append(TAG);
            writer.append(" - ");
            writer.append(message);
            writer.close();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }

    }
}
