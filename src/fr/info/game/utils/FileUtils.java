package fr.info.game.utils;

import fr.info.game.assets.TextResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    /**
     * @param resource The file containing the text to extract
     * @return Return the text contained in the file
     */
    public static String loadAsString(TextResource resource) {

        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getResourceAsStream()));
            String buffer;

            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
