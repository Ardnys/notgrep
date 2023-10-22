package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SearchEngine {
    public SearchEngine() {}
    public void readInputFile(String path) {
        var file = new File(path);
        try (var bf = Files.newBufferedReader(Paths.get(path))) {
            String line;
            var allFiles = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                if (!line.equals("####")) {
                    // regex matches all non-alpha characters except whitespaces
                    line = line.replaceAll("[^a-zA-Z\\d\\s:]", "");
                }
                allFiles.append(line);
            }
            var scanner = new FileScanner(allFiles.toString());
            var wordList = scanner.scanFile();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while reading the input file");
        }
    }
}
