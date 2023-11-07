package searchengine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class NotGrep {


    public static void main(String[] args) {
        /* +-----------------------------+
            EXAMPLE USAGE - as it is in the project

           $ notgrep [OPTIONAL FLAGS] <command_file.txt>
             path/to/output.txt

           EXAMPLE USAGE - REP (Read-Evaluate-Print) normally there's an L but this doesn't have loops
           $ notgrep
            > load ...
            > search ...
            > remove ...
            > clear list ...

            OPTIONAL FLAGS
            -h   displays a help page
            -bst changes the data structure to binary search tree
            -ll  changes the data structure to linked list
            -rb  changes the data structure to red-black tree (default)

           +-----------------------------+   */

        if (args.length > 2) {
            System.out.println("Usage: notgrep [OPTIONAL FLAGS] [path/to/command_file.txt]");
            System.exit(64);
        } else if (args.length == 2) {
            var dsFlag = args[0];
            var pathOfFile = args[1];
            System.out.println("two flags okay");

            // types make it a bit clear here
            try {
                Flag flag = checkFlag(dsFlag);
                Optional<File> fileOrNot = checkFile(pathOfFile);

                fileOrNot.ifPresentOrElse(
                        file -> parseCommandFile(file, flag),
                        () -> {
                            System.err.println("Invalid command file path.");
                            System.exit(1);
                        });
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.println("notgrep -h for usage and available flags");
            }


            /* ====== Author Note ======
            Here I used both Optional<T> type and lambda expressions. I know how they work (kind of)
            and IntelliJ assists me with hints and tricks when I do something wrong.

            I can very well use null instead of Optional<T> but many languages have features for these.
            Some languages don't even have null. I used Java libraries that fully incorporated Optional<T>.

            Often it's shorter to unwrap the Optional<T> and shorter than if checks. Good lambda practice as well.
             */

        } else if (args.length == 1) {
            // this could be -h flag
            if (args[0].equals("-h")) {
                displayHelpPage();
            } else {
                var pathOfFile = args[0];
                Optional<File> fileOrNot = checkFile(pathOfFile);
                System.out.println("no flag okay");

                fileOrNot.ifPresentOrElse(
                        file -> parseCommandFile(file, Flag.BST),
                        () -> {
                            System.err.println("Invalid command file path.");
                            System.exit(1);
                        });
            }

        } else {
            System.out.println("easy come, easy go...");
            // REP
            // if I have time left
            // TODO perhaps test this even more
            var scanner = new Scanner(System.in);
            Queue<String> commands = new ArrayDeque<>();
            SearchEngine engine = new SearchEngine(Flag.BST);

            while (true) {
                System.out.print("> ");
                var line = scanner.nextLine().toCharArray();
                var query = new StringBuilder();
                for (char c : line) {
                    if (c == ';') {
                        commands.add(query.toString().trim());
//                        System.out.println("Command: " + query.toString().trim());
                        query.setLength(0);
                    } else {
                        query.append(c);
                    }
                }
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

                    try {

                        Command command = Command.valueOf(commandString.toUpperCase()); // this is cool

                        boolean yes = false;
                        switch (command) {
                            case LOAD -> {
                                if (arguments.size() != 1) System.err.println("Expected input path for LOAD command.\n");

                                try {
                                    engine.load(arguments.get(0).trim());
                                    System.out.printf("Loaded %s...%n", arguments.get(0).trim());
                                } catch (IOException e) {
                                    System.err.println(e.getMessage());
                                    yes = true;
                                }
                            }
                            case RESET -> {
                                if (!arguments.isEmpty()) System.err.println("Unexpected argument in RESET command.\n");

                                engine.reset();
                                System.out.println("Engine is reset");
                            }
                            case REMOVE -> {
                                if (arguments.isEmpty()) System.err.println("Expected document arguments in REMOVE command.\n");

                                List<String> params = arguments.stream()
                                        .map(arg -> arg.replace(',', ' '))
                                        .map(String::trim)
                                        .collect(Collectors.toList());

                                engine.remove(params);
                                System.out.printf("Successfully removed %s.%n", String.join(", ", params));
                            }
                            case SEARCH -> {
                                if (arguments.isEmpty()) System.err.println("Expected document arguments in SEARCH command.\n");

                                List<String> params = arguments.stream()
                                        .map(arg -> arg.replace(',', ' '))
                                        .map(String::trim)
                                        .collect(Collectors.toList());

                                System.out.printf("Searching %s...%n", String.join(", ", params));

                                try {
                                    Set<String> searchResults = engine.search(params);
                                    System.out.printf("Documents found: %s%n", String.join(",\n", searchResults));
                                } catch (Exception e) {
                                    System.err.println(e.getMessage());
                                    yes = true;
                                }
                            }
                            case EXIT -> {
                                if (!arguments.isEmpty()) System.err.println("Unexpected argument in EXIT command.");

                                System.out.println("Exiting...");
                                System.out.println("See you space cowboy");
                                System.exit(35);
                            }
                            case HELP -> {
                                if (!arguments.isEmpty()) System.err.println("Unexpected argument in HELP command.");

                                displayHelpPage();
                            }
                        }
                        if (yes) {
                            commands.clear();
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.err.println("Undefined behaviour. Command flushed.");
                        commands.clear();
                        break;
                    }


                }
            }


        }

    }

    private static Flag checkFlag(String flag) throws Exception {
        return switch (flag) {
            case "-bst" -> Flag.BST;
            case "-ll" -> Flag.LL;
            default -> throw new Exception("Unknown flag");
        };

    }

    private static Optional<File> checkFile(String path) {
        var file = new File(path);
        if (file.exists() && file.isFile()) {
            return Optional.of(file);
        } else {
            return Optional.empty();
        }
    }

    private static void parseCommandFile(File file, Flag flag) {
        var parser = new CommandParser(file, flag);
        try {
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
    private static void displayHelpPage() {
        String usage = "Usage: As is in the project without an output file\n\n" +
                "$ notgrep [OPTIONAL FLAGS] <command_file.txt>\n" +
                "Usage: - REP (Read-Evaluate-Print) normally there's an L but this doesn't have loops\n\n" +
                "$ notgrep\n" +
                "> load ...;\n" +
                "> search ...;\n" +
                "> remove ...;\n" +
                "> reset;\n" +
                "> exit;\n" +
                "> help;\n" +
                "OPTIONAL FLAGS\n" +
                "-h   displays a help page\n" +
                "-bst changes the data structure to binary search tree (default) \n" +
                "-ll  changes the data structure to linked list\n";
        String commands = "notgrep supports the following commands which are also supposed to be written in a text with very specific rules. \n" +
                "|             Command           |                              Description                                            |\n" +
                "| ----------------------------- | ----------------------------------------------------------------------------------- |\n" +
                "| load <path/to/input/file.txt> | loads the input file to system. querying will be done on this file                  |\n" +
                "| search <query-string>         | searches the given arguments in the loaded file                                     |\n" +
                "| remove <document-name>        | removes a document from the system. the document IS NOT removed from the input file |\n" +
                "| reset                         | clears the entries and restarts the system                                          |\n" +
                "| help                          | displays this message                                                               |\n" +
                "| exit                          | exits. (cool, right?)                                                               |\n" +
                "\n" +
                "The command file shall follow the following rule:\n" +
                "path/to/output/txt;\n" +
                "command1;\n" +
                "command2;\n" +
                "...\n";
        String usageTitle = "  _    _  _____         _____ ______ \n" +
                " | |  | |/ ____|  /\\   / ____|  ____|\n" +
                " | |  | | (___   /  \\ | |  __| |__   \n" +
                " | |  | |\\___ \\ / /\\ \\| | |_ |  __|  \n" +
                " | |__| |____) / ____ \\ |__| | |____ \n" +
                "  \\____/|_____/_/    \\_\\_____|______|\n" +
                "                                     \n" +
                "                                     \n";
        String commandsTitle = "   _____ ____  __  __ __  __          _   _ _____   _____ \n" +
                "  / ____/ __ \\|  \\/  |  \\/  |   /\\   | \\ | |  __ \\ / ____|\n" +
                " | |   | |  | | \\  / | \\  / |  /  \\  |  \\| | |  | | (___  \n" +
                " | |   | |  | | |\\/| | |\\/| | / /\\ \\ | . ` | |  | |\\___ \\ \n" +
                " | |___| |__| | |  | | |  | |/ ____ \\| |\\  | |__| |____) |\n" +
                "  \\_____\\____/|_|  |_|_|  |_/_/    \\_\\_| \\_|_____/|_____/ \n" +
                "                                                          \n" +
                "                                                          \n";
        System.out.printf("%s%s%n%s%s%n",  commandsTitle, commands, usageTitle, usage);
    }
}
