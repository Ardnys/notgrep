package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SearchEngine {
    private void readInputFile(String path) {
        File file = new File(path);
        try (BufferedReader bf = Files.newBufferedReader(Paths.get(path))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while reading the input file");
        }

    }
}
