package com.example.pingmonitor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для работы с конфигурацией приложения
 * Загружает настройки из файла app.properties
 */
public class AppConfig {
    private String hostsFilePath;
    private int pingCount;
    private int pingTimeoutSeconds;
    
    /**
     * Конструктор, загружающий конфигурацию из файла
     * @throws IOException если файл конфигурации не найден или поврежден
     */
    public AppConfig() throws IOException {
        loadConfig();
    }
    
    private void loadConfig() throws IOException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            if (input == null) {
                throw new IOException("Файл app.properties не найден в classpath");
            }
            props.load(input);
            
            this.hostsFilePath = props.getProperty("hosts.file.path", "./config/hosts.txt");
            this.pingCount = Integer.parseInt(props.getProperty("ping.count", "4"));
            this.pingTimeoutSeconds = Integer.parseInt(props.getProperty("ping.timeout.seconds", "2"));
        }
    }
    
    public String getHostsFilePath() {
        return hostsFilePath;
    }
    
    public int getPingCount() {
        return pingCount;
    }
    
    public int getPingTimeoutSeconds() {
        return pingTimeoutSeconds;
    }
}