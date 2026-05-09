package org.example.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.example.domain.User;

public class FileUtil {

    private static final String FILE = "users.txt";

    public static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (User u : users) {
                pw.println(u.getUsername() + "," + u.getPassword()+ "," + u.getEmail());
            }
        } catch (IOException e) {
           System.err.println("An error occurred while processing the file.");
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE);

        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            System.err.println("An error occurred while processing the file.");
        }

        return users;
    }
    public static List<String> read(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty())
                    lines.add(line);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("File read error: " + e.getMessage());
        }

        return lines;
    }

    public static void write(String fileName, List<String> lines) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("File write error: " + e.getMessage());
        }
    }
}
