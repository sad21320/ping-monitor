package com.example.pingmonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для чтения списка хостов из файла
 */
public class HostListReader {
    
    /**
     * Читает список хостов из файла
     * @param filePath путь к файлу с хостами
     * @return список хостов
     * @throws IOException если файл не найден или недоступен
     */
    public List<String> readHosts(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("Файл с хостами не найден: " + filePath);
        }
        
        return Files.lines(path)
                .map(String::trim)
                .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                .collect(Collectors.toList());
    }
}