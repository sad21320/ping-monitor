package com.example.pingmonitor;

import java.util.Comparator;
import java.util.List;

/**
 * Класс для генерации и сортировки отчетов
 */
public class ReportGenerator {
    
    /**
     * Сортирует результаты проверки согласно требованиям
     * @param results список результатов
     * @return отсортированный список
     */
    public List<PingResult> sortResults(List<PingResult> results) {
        results.sort(Comparator
                .comparing((PingResult result) -> result.getStatus().getSortPriority())
                .thenComparingDouble(PingResult::getAverageRtt));
        return results;
    }
    
    /**
     * Генерирует текстовый отчет
     * @param results список результатов
     * @return строковое представление отчета
     */
    public String generateReport(List<PingResult> results) {
        StringBuilder report = new StringBuilder();
        
        for (PingResult result : sortResults(results)) {
            report.append(formatResult(result)).append("\n");
        }
        
        return report.toString();
    }
    
    private String formatResult(PingResult result) {
        switch (result.getStatus()) {
            case UP:
                return String.format("HOST: %s, STATUS: %s, RTT: %.2f ms", 
                    result.getHost(), result.getStatus().getDisplayName(), result.getAverageRtt());
            case FLAKY:
                return String.format("HOST: %s, STATUS: %s, RTT: %.2f ms (%d%% loss)", 
                    result.getHost(), result.getStatus().getDisplayName(), 
                    result.getAverageRtt(), result.getPacketLoss());
            case UNREACHABLE:
                return String.format("HOST: %s, STATUS: %s", 
                    result.getHost(), result.getStatus().getDisplayName());
            default:
                return String.format("HOST: %s, STATUS: UNKNOWN", result.getHost());
        }
    }
}