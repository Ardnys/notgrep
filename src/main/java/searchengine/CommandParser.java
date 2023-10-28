package searchengine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class CommandParser {

    Queue<String> commands = new ArrayDeque<>();
    SearchEngine engine = new SearchEngine();
    File outputFile;
    StringBuilder outputString = new StringBuilder();
    public CommandParser(File file) {
        // TODO accept the flag for DS here and send it to search engine
        System.out.println("Reading the file " + file.getAbsolutePath());
        readCommandFile(file);
    }
    private void readCommandFile(File file) {
        try(var reader = Files.newBufferedReader(file.toPath())) {
            int chr;
            var command = new StringBuilder();
            while ((chr = reader.read()) != -1) {
                if (chr == ';') {
                    // end of command
                    commands.add(command.toString().trim());
//                    System.out.println("queue command: " + command.toString().trim());
                    command.setLength(0);
                    // set length clears the StringBuilder without additional memory allocation
                } else {
                    command.append((char)chr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while reading the command file.");
        }
    }
    public void parse() {
        parseCommands();
        // a bit weird but oh well
    }
    private void parseCommands() {
        Path outputPath;
        try {
            outputPath = Paths.get(Objects.requireNonNull(commands.poll()));
        } catch (InvalidPathException | NullPointerException e) {
            System.err.println("Invalid output file path.");
            return;
        }
        outputFile = new File(outputPath.toUri());

    }
}
