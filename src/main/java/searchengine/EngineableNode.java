package searchengine;

import java.util.HashSet;
import java.util.Set;

public interface EngineableNode {
    // is this fine ?
    String word = null;
    Set<String> documents = new HashSet<>();
}
