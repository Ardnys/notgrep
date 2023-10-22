package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SearchEngine {
    public static void readInputFile(String path) {
        File file = new File(path);
        try (BufferedReader bf = Files.newBufferedReader(Paths.get(path))) {
            String line;
            StringBuilder allFiles = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                allFiles.append(line);
            }
            System.out.println(allFiles);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while reading the input file");
        }

    }

    public static void main(String[] args) {
        SearchEngine.readInputFile("smolfilesystem.txt");
    }
}
