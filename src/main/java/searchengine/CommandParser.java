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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


public class CommandParser {
    Queue<String> commands = new ArrayDeque<>();
    SearchEngine engine;
    File outputFile;

    public CommandParser(File file, Flag flag) {
        engine = new SearchEngine(flag);
        readCommandFile(file);
    }

    /*
    +---------------------------------------------------------------------------------------+
    |                                                                                       |
    |                ! ! ! !  T E S T   M E T H O D ! ! !                                   |
    |                                       B E   C A R E F U L                             |
    |                               N O T    I N T E N D E D   F O R   U S E                |
    |                                                                                       |
    +----------------------------------------------------------------------------------------
     */
    // FOR TEST ONLY
    public CommandParser(Flag flag) {
        engine = new SearchEngine(flag);
    }

    private void readCommandFile(File file) {
        try (var reader = Files.newBufferedReader(file.toPath())) {
            int chr;
            var command = new StringBuilder();
            while ((chr = reader.read()) != -1) {
                if (chr == ';') {
                    // end of command
                    commands.add(command.toString().trim());
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
            var commandQuery = commands.poll();
            int commandArgSeparator = commandQuery.indexOf(' ');
            String commandString;
            List<String> arguments = new ArrayList<>();
            if (commandArgSeparator == -1) {
                // something like reset has no arguments hence no separator
                commandString = commandQuery;
            } else {
                commandString = commandQuery.substring(0, commandArgSeparator);
                arguments = Arrays.asList(commandQuery.substring(commandArgSeparator).split(","));
            }
            Command command = Command.valueOf(commandString.toUpperCase()); // this is cool

            // then we can fancy switch it
            switch (command) {
                case LOAD -> {
                    if (arguments.size() != 1) throw new Exception("Expected input path for LOAD command.\n");

                    engine.load(arguments.get(0).trim());
                }
                case RESET -> {
                    if (!arguments.isEmpty()) throw new Exception("Unexpected argument in RESET command.\n");

                    engine.reset();
                }
                case REMOVE -> {
                    if (arguments.isEmpty()) throw new Exception("Expected document arguments in REMOVE command.\n");

                    List<String> args = arguments.stream()
                            .map(arg -> arg.replace(',', ' '))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    engine.remove(args);
                }
                case SEARCH -> {
                    if (arguments.isEmpty()) throw new Exception("Expected document arguments in SEARCH command.\n");

                    List<String> args = arguments.stream()
                            .map(arg -> arg.replace(',', ' '))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    Set<String> searchResults = engine.search(args);
                    logToOutputFile(args, searchResults);
                    System.out.println("Output file in: " + outputFile.getAbsolutePath());
                }
            }
        }
    }

    private void logToOutputFile(List<String> searchArgs, Set<String> searchResults) {
        if (outputFile == null) {
            System.err.println("output file is null for some reason.");
            return;
        }
        try (var writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("query: ");
            String args = String.join(", ", searchArgs);

            writer.write(args);
            writer.write('\n');
            String result = String.join(", ", searchResults);

            writer.write(result);
            writer.write('\n');
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            System.err.println("error while writing to output file.");
        }
    }

    public String testCommands(Queue<String> commands) throws Exception {
        Set<String> searchResults = new HashSet<>();
        while (!commands.isEmpty()) {
            var commandQuery = commands.poll();
            int commandArgSeparator = commandQuery.indexOf(' ');
            String commandString;
            List<String> arguments = new ArrayList<>();
            if (commandArgSeparator == -1) {
                // something like reset has no arguments hence no separator
                commandString = commandQuery;
            } else {
                commandString = commandQuery.substring(0, commandArgSeparator);
                arguments = Arrays.asList(commandQuery.substring(commandArgSeparator).split(","));
            }
            Command command = Command.valueOf(commandString.toUpperCase()); // this is cool

            // then we can pattern match it
            switch (command) {
                case LOAD -> {
                    if (arguments.size() != 1) throw new Exception("Expected input path for LOAD command.\n");

                    engine.load(arguments.get(0).trim());
                }
                case RESET -> {
                    if (!arguments.isEmpty()) throw new Exception("Unexpected argument in RESET command.\n");

                    engine.reset();
                }
                case REMOVE -> {
                    if (arguments.isEmpty()) throw new Exception("Expected document arguments in REMOVE command.\n");

                    List<String> args = arguments.stream()
                            .map(arg -> arg.replace(',', ' '))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    engine.remove(args);
                }
                case SEARCH -> {
                    if (arguments.isEmpty()) throw new Exception("Expected document arguments in SEARCH command.\n");

                    List<String> args = arguments.stream()
                            .map(arg -> arg.replace(',', ' '))
                            .map(String::trim)
                            .collect(Collectors.toList());

                    searchResults = engine.search(args);
                }
            }
        }

        return String.join(", ", searchResults);
    }

}
