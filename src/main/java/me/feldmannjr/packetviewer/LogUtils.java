package me.feldmannjr.packetviewer;


import java.io.*;
import java.text.SimpleDateFormat;

public class LogUtils {


    public static String lastServer = "null";
    public static File saida;
    private static PrintWriter writer;
    private static SimpleDateFormat format;

    public static void writeLine(String oq)
    {
        if (format == null) {
            format = new SimpleDateFormat();
        }
        oq = "[" + lastServer + "] (" + format.format(System.currentTimeMillis()) + ")";
        writeLine(saida, oq);
    }

    private static void writeLine(File f, String oq)
    {

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (writer == null) {
            try {
                writer = new PrintWriter(f, "UTF-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        writer.println(oq);


    }
}
