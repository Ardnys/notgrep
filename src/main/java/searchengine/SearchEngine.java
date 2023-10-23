package searchengine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchEngine {

    WordList wordList;

    public SearchEngine() {
    }

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
            wordList = scanner.scanFile();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while reading the input file");
        }
    }

    public Set<String> search(List<String> keywords) {
        if (wordList == null) {
            System.err.println("Word list has not been initialized");
            return new HashSet<>();
        }
        return wordList.search(keywords);
    }
}
