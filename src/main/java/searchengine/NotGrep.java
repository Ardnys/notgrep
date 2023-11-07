package searchengine;

import java.io.File;
import java.util.Optional;

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
                String usage = "Usage: As is in the project without an output file\n\n" +
                        "$ notgrep [OPTIONAL FLAGS] <command_file.txt>\n" +
                        "Usage: - REP (Read-Evaluate-Print) normally there's an L but this doesn't have loops\n\n" +
                        "$ notgrep\n" +
                        "> load ...\n" +
                        "> search ...\n" +
                        "> remove ...\n" +
                        "> reset;\n" +
                        "> exit;\n" +
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
            System.out.println("ohoho slow down cowboy");
            // REP
            // if I have time left
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
}
