package searchengine;

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
            Flag flag = checkFlag(dsFlag);
            // TODO check flag
            var cmdFile = args[1];
            // TODO can I check if the file exists?
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
        // flag could be valid or invalid. return an optional to handle both of the cases
        return switch (flag) {
            case "-bst" -> Flag.BST;
            case "-ll" -> Flag.LL;
            case "-rb" -> Flag.RB;
            case "-h" -> Flag.HELP;
            default -> Flag.UNDEF;
        };

    }
}
