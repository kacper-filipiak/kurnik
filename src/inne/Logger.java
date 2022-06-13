package inne;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;


public class Logger {
    //czy tworzyc nowy plik
    static boolean clear = true;

    //zapisanie wiadomosci do pliku z raport.txt
    static public void log(String TAG, String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("report.txt", !clear));
            clear = false;
            writer.append('\n')
                    .append(String.valueOf(System.currentTimeMillis()))
                    .append(", ")
                    .append((new Timestamp(System.currentTimeMillis())).toString())
                    .append(", ")
                    .append(TAG)
                    .append(", ")
                    .append(message)
                    .close();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    //zapisanie do pliku csv wartosci z mapy
    static public void log(LinkedHashMap<String, String> message, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".csv", true));
            writer.append('\n')
                    .append(String.valueOf(System.currentTimeMillis()))
                    .append(", ")
                    .append((new Timestamp(System.currentTimeMillis())).toString())
                    .append(", ");
            message.forEach((key, value) ->
                    {
                        try {
                            writer.append(value).append(", ");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

            writer.close();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    //wyczyszczenie zawartosci pliku w formacie csv o zadanej nazwie
    static public void clearFile(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".csv", false));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
