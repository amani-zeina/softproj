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
                pw.println(u.getUsername() + "," + u.getPassword() + "," + u.getEmail());
            }
        } catch (IOException e) {
e.printStackTrace();       }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE);

        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
e.printStackTrace();   }

        return users;
    }

    public static List<String> read(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);

        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created) {
                    System.err.println("Notice: File already exists or could not be created.");
                }
            }

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().isEmpty())
                        lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }

        return lines;
    }

    public static void write(String fileName, List<String> lines) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }
}
