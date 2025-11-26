package com.example.pingmonitor;

/**
 * Модель результата ping-проверки
 */
public class PingResult {
    private final String host;
    private final HostStatus status;
    private final double averageRtt;
    private final int packetLoss;
    
    /**
     * Конструктор
     * @param host хост
     * @param status статус доступности
     * @param averageRtt среднее время отклика
     * @param packetLoss процент потерь пакетов
     */
    public PingResult(String host, HostStatus status, double averageRtt, int packetLoss) {
        this.host = host;
        this.status = status;
        this.averageRtt = averageRtt;
        this.packetLoss = packetLoss;
    }
    
    public String getHost() {
        return host;
    }
    
    public HostStatus getStatus() {
        return status;
    }
    
    public double getAverageRtt() {
        return averageRtt;
    }
    
    public int getPacketLoss() {
        return packetLoss;
    }
}