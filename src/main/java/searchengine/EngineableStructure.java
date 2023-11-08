package searchengine;

import java.util.List;
import java.util.Set;

public interface EngineableStructure {
    // i dub the best names
    void append(String word, String document);

    Set<String> search(List<String> keywords);

    void remove(String document);

    void reset();
    void setAllDocs(Set<String> docs);
}
