package searchengine;

import java.util.List;
import java.util.Set;

public interface EngineableStructure {
    // i dub the best names
    // TODO I'll see if other structures have similar nodes and extract a record or something
    // I mean there's add document and get document... Why not?
    void append(EngineableNode node);

    Set<String> search(List<String> keywords);

    void remove(String document);

    void reset();
}
