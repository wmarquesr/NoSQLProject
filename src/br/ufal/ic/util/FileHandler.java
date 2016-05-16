package br.ufal.ic.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by wmarques on 16/05/16.
 */
public class FileHandler {

    public static String readFileIntoString(String path){
        String everything = "";

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return everything;
    }

    public static Properties loadPropertiesFile(){
        Properties prop = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream("resources/paths.properties");

            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }
}
