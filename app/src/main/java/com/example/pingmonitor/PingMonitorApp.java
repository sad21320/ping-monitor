package com.example.pingmonitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс приложения Пинг-монитор
 * Выполняет проверку доступности хостов и генерирует отчет
 */
public class PingMonitorApp {
    
    /**
     * Точка входа в приложение
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        try {
            PingMonitorApp app = new PingMonitorApp();
            app.run();
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Основной метод выполнения приложения
     * @throws IOException при ошибках чтения файлов
     */
    public void run() throws IOException {
        // Загрузка конфигурации
        AppConfig config = new AppConfig();
        
        // Чтение списка хостов
        HostListReader hostReader = new HostListReader();
        List<String> hosts = hostReader.readHosts(config.getHostsFilePath());
        
        // Выполнение ping-проверок
        PingExecutor pingExecutor = new PingExecutor(config.getPingCount(), config.getPingTimeoutSeconds());
        List<PingResult> results = new ArrayList<>();
        
        for (String host : hosts) {
            System.out.println("Проверка хоста: " + host);
            PingResult result = pingExecutor.ping(host);
            results.add(result);
        }
        
        // Генерация и вывод отчета
        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReport(results);
        
        System.out.println("\n=== ОТЧЕТ ПИНГ-МОНИТОРА ===");
        System.out.print(report);
    }
}