package org.example.util;

import org.example.domain.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    @Test
    void saveAndLoadUsersShouldWork() {

        List<User> users = new ArrayList<>();
        users.add(new User("test1","123","testuser@example.com"));
        users.add(new User("test2","456","testuser@example.com"));

        FileUtil.saveUsers(users);

        List<User> loaded = FileUtil.loadUsers();

        assertNotNull(loaded);
        assertTrue(loaded.size() >= 2);
    }

    @Test
    void readShouldReturnLines() {

        List<String> lines = new ArrayList<>();
        lines.add("line1");
        lines.add("line2");

        FileUtil.write("testfile.txt", lines);

        List<String> result = FileUtil.read("testfile.txt");

        assertEquals(2, result.size());
    }

    @Test
    void readShouldCreateFileIfNotExists() {

        File file = new File("newfile.txt");
        
        if (file.exists()) {
            assertTrue(file.delete(), "Existing file should be deleted before the test starts");
        }

        List<String> lines = FileUtil.read("newfile.txt");

        assertNotNull(lines);
        assertTrue(file.exists());
    }
}
