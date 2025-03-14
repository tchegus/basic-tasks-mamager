package com.roadmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GlobalCounter {

    static {
        try {
            initFileCreation();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create the counter file");
        }
    }

    private static final String counter_file_name = "counter.txt";

    public static synchronized void increment() throws IOException {
        Path counterFilePath = Paths.get(counter_file_name);
        String counter = Files.readString(counterFilePath);
        if (counter.isBlank()) {
            counter = "0";
        }
        Files.write(counterFilePath, String.valueOf(Integer.parseInt(counter) + 1).getBytes());
    }

    public static synchronized int getCounter() throws IOException {
        return Integer.parseInt(Files.readString(Paths.get(counter_file_name)));
    }

    public static synchronized void resetCounter() throws IOException {
        Files.write(Paths.get(counter_file_name), "0".getBytes());
    }

    public static void updateGlobalCounterSequence(int currentCounterValue) throws IOException {
        Files.write(Paths.get(counter_file_name), String.valueOf(currentCounterValue).getBytes());
    }

    private static void initFileCreation() throws IOException {
        Path jsonFilepath = Paths.get(counter_file_name);
        if (!Files.exists(jsonFilepath)) {
            Files.createFile(jsonFilepath);
        }
    }
}