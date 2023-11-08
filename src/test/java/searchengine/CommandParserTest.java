package searchengine;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandParserTest {
    @Test
    void searchOneItemWithTwoResults() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search study");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("Computer Science, Physics", res);
        } catch (Exception e) {
        }
    }

    @Test
    void searchTwoItemsWithOneResult() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search study, computer");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("Computer Science", res);
        } catch (Exception e) {
        }
    }

    @Test
    void searchManyItemsWithOneResult() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search study, computer, algorithms," +
                " computation, data, secure, communication");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("Computer Science", res);
        } catch (Exception e) {
        }
    }

    @Test
    void searchWithoutLoading() {
        List<String> commandList = List.of("search computer");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        Exception thrown = assertThrows(
                Exception.class,
                () -> parser.testCommands(commands),
                "Expected search without load to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Word list has not been initialized."));
    }

    @Test
    void loadInvalidPath() {
        List<String> commandList = List.of("load deezgunz.ufufuxd", "search computer");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        IOException thrown = assertThrows(
                IOException.class,
                () -> parser.testCommands(commands),
                "Expected load to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Invalid input path."));
    }

    @Test
    void resetTheEngine() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search study, computer", "reset", "search study, computer");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("", res);
        } catch (Exception e) {
        }
    }

    @Test
    void resetWithArguments() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search computer", "reset deezfile");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        Exception thrown = assertThrows(
                Exception.class,
                () -> parser.testCommands(commands),
                "Expected reset to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Unexpected argument in RESET command.\n"));
    }

    @Test
    void removeOneDocument() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search study", "remove Computer Science", "search study");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("Physics", res);
        } catch (Exception e) {
        }
    }

    @Test
    void removeTwoDocuments() {
        List<String> commandList = List.of("load smolfilesystem.txt", "search study", "remove Computer Science, Physics", "search study");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("", res);
        } catch (Exception e) {
        }
    }

    @Test
    void removeNonexistentDocument() {
        // it doesn't do anything. maybe throw an error?
        List<String> commandList = List.of("load smolfilesystem.txt", "search study", "remove Computer Science, biology", "search study");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        try {
            var res = parser.testCommands(commands);
            assertEquals("Physics", res);
        } catch (Exception e) {
        }
    }

    @Test
    void removeWithoutArguments() {
        // it doesn't do anything. maybe throw an error?
        List<String> commandList = List.of("load smolfilesystem.txt", "search study", "remove", "search study");
        Queue<String> commands = new ArrayDeque<>(commandList);
        var parser = new CommandParser(Flag.BST);
        Exception thrown = assertThrows(
                Exception.class,
                () -> parser.testCommands(commands),
                "Expected remove to throw, but it didn't"
        );
        assertTrue(thrown.getMessage().contains("Expected document arguments in REMOVE command.\n"));
    }


}