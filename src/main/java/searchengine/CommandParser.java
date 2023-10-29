package searchengine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


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
        try (var reader = Files.newBufferedReader(file.toPath())) {
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
                    command.append((char) chr);
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading the command file.");
        }
    }

    public void parse() throws Exception {
        Path outputPath;
        try {
            outputPath = Paths.get(Objects.requireNonNull(commands.poll()));
        } catch (InvalidPathException | NullPointerException e) {
            System.err.println("Invalid output file path.");
            return;
        }
        outputFile = new File(outputPath.toUri());
        parseCommands();
    }

    private void parseCommands() throws Exception {
        while (!commands.isEmpty()) {
            var arguments = Arrays.asList(commands.poll().split(" "));
            var commandString = arguments.get(0).trim(); // and the rest are arguments.
            Command command = Command.valueOf(commandString.toUpperCase()); // this is cool

            // then we can pattern match it
            switch (command) {
                case LOAD -> {
                    if (arguments.size() != 2) throw new Exception("Expected input path for LOAD command.\n");

                    engine.load(arguments.get(1));
                }
                case RESET -> {
                    if (arguments.size() > 1) throw new Exception("Unexpected argument in RESET command.\n");

                    engine.reset();
                }
                case REMOVE -> {
                    if (arguments.size() < 2) throw new Exception("Expected document arguments in REMOVE command.\n");

                    List<String> args = arguments.stream()
                            .map(arg -> arg.replace(',', ' '))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    args.remove(0);
                    engine.remove(args);
                }
                case SEARCH -> {
                    if (arguments.size() < 2) throw new Exception("Expected document arguments in SEARCH command.\n");

                    List<String> args = arguments.stream()
                            .map(arg -> arg.replace(',', ' '))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    args.remove(0);

                    Set<String> searchResults = engine.search(args);
                    logToOutputFile(args, searchResults);
                }
            }
        }
    }
    private void logToOutputFile(List<String> searchArgs, Set<String> searchResults) {
        if (outputFile == null) {
            System.err.println("output file is null for some reason.");
            return;
        }
        try(var writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("query: ");
            StringBuilder sb = new StringBuilder();
            for (String arg : searchArgs) {
                sb.append(arg);
                sb.append(',');
                sb.append(' ');
            }
            sb.append('\n');
            writer.write(sb.toString());
            sb.setLength(0);
            for (String res : searchResults) {
                sb.append(res);
                sb.append(',');
                sb.append(' ');
                System.out.println("result: " + res);
            }
            sb.append('\n');
            writer.write(sb.toString());
            writer.write('\n');
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            System.err.println("error while writing to output file.");
        }
    }

    private enum Command {
        LOAD,
        SEARCH,
        REMOVE,
        RESET,
    }
}
