package searchengine;

import java.io.File;
import java.util.Optional;

public class NotGrep {
    private enum Flag {
        BST,
        LL,
        RB,
        HELP,
        UNDEF
    }

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

            // types make it a bit clear here
            Flag flag = checkFlag(dsFlag);
            Optional<File> fileOrNot = checkFile(pathOfFile);

            fileOrNot.ifPresentOrElse(
                    NotGrep::parseCommandFile,
                    () -> {
                        System.err.println("Invalid command file path.");
                        System.exit(1);
                    });

            /* ====== Author Note ======
            Here I used both Optional<T> type and lambda expressions. I know how they work (kind of)
            and IntelliJ assists me with hints and tricks when I do something wrong.

            I can very well use null instead of Optional<T> but many languages have features for these.
            Some languages don't even have null. I used Java libraries that fully incorporated Optional<T>.

            Often it's shorter to unwrap the Optional<T> and shorter than if checks. Good lambda practice as well.
             */

        } else if (args.length == 1) {
            var flagOrFile = args[0];
            // TODO check this one too somehow
        } else {
            // REP
            // if I have time left
        }

        new SearchEngine().readInputFile("smolfilesystem.txt");
    }

    private static Flag checkFlag(String flag) {
        // TODO Strategy Pattern to change data structures.
        // TODO operate on flags instead of returning it
        return switch (flag) {
            case "-bst" -> Flag.BST;
            case "-ll" -> Flag.LL;
            case "-rb" -> Flag.RB;
            case "-h" -> Flag.HELP;
            default -> Flag.UNDEF;
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

    private static void parseCommandFile(File file) {
        // TODO parse the command text file
        System.out.println("Oops, can't parse the file rn");
    }
}
