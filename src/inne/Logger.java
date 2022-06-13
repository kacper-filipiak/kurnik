package inne;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.Date;


public class Logger {
    static boolean clear = true;
    static public void log(String TAG, String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", !clear));
            clear = false;
            writer.append('\n')
                    .append(String.valueOf(System.currentTimeMillis()))
                    .append(" ")
                    .append((new Timestamp(System.currentTimeMillis())).toString())
                    .append(" : ")
                    .append(TAG)
                    .append(" - ")
                    .append(message)
                    .close();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }
}
