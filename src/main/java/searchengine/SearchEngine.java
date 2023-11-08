package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class SearchEngine {

    EngineableStructure maybeTree;
    Flag flag;

    public SearchEngine(Flag flag) {
        this.flag = flag;
    }

    public void load(String path) throws IOException {
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
            var scanner = new FileScanner(allFiles.toString(), flag);
            maybeTree = scanner.scanFile();
        } catch (IOException e) {
            throw new IOException("Invalid input path.");
        }
    }

    public Set<String> search(List<String> keywords) throws Exception {
        if (maybeTree == null) {
            throw new Exception("Word list has not been initialized.");
        }
        return maybeTree.search(keywords);
    }

    public void remove(List<String> documents) {
        for (String doc : documents) {
            maybeTree.remove(doc);
        }
    }

    public void reset() {
        if (maybeTree == null) {
            return;
        }
        maybeTree.reset();
    }
}
