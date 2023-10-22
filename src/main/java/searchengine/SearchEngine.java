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
                if (!line.equals("####")) {
                    // regex matches all non-alpha characters except whitespaces
                    line = line.replaceAll("[^a-zA-Z\\d\\s:]", "");
                }
                allFiles.append(line);
            }
            System.out.println(allFiles);
            FileScanner scanner = new FileScanner(allFiles.toString());
            WordList wordList = scanner.scanFile();
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
