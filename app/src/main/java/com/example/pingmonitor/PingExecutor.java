package com.example.pingmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingExecutor {
    private final int pingCount;
    private final int timeoutSeconds;
    
    public PingExecutor(int pingCount, int timeoutSeconds) {
        this.pingCount = pingCount;
        this.timeoutSeconds = timeoutSeconds;
    }
    
    public PingResult ping(String host) {
        List<String> command = buildPingCommand(host);
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            
            List<String> outputLines = readProcessOutput(process);
            int exitCode = process.waitFor();
            
            System.out.println("=== DEBUG for " + host + " ===");
            for (String line : outputLines) {
                System.out.println(line);
            }
            System.out.println("Exit code: " + exitCode);
            
            PingResult result = parsePingOutput(host, outputLines, exitCode);
            System.out.println("Result: " + result.getStatus() + ", RTT: " + result.getAverageRtt() + ", Loss: " + result.getPacketLoss() + "%");
            System.out.println("==================");
            
            return result;
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during ping: " + e.getMessage());
            return new PingResult(host, HostStatus.UNREACHABLE, 0.0, 100);
        }
    }
    
    private List<String> buildPingCommand(String host) {
        List<String> command = new ArrayList<>();
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            command.add("ping");
            command.add("-n");
            command.add(String.valueOf(pingCount));
            command.add("-w");
            command.add(String.valueOf(timeoutSeconds * 1000));
            command.add(host);
        } else {
            command.add("ping");
            command.add("-c");
            command.add(String.valueOf(pingCount));
            command.add("-W");
            command.add(String.valueOf(timeoutSeconds));
            command.add(host);
        }
        
        return command;
    }
    
    private List<String> readProcessOutput(Process process) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "CP866"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
    
    private PingResult parsePingOutput(String host, List<String> outputLines, int exitCode) {
        if (exitCode != 0) {
            return new PingResult(host, HostStatus.UNREACHABLE, 0.0, 100);
        }
        
        int packetLoss = parsePacketLoss(outputLines);
        double avgRtt = parseAverageRtt(outputLines);
        HostStatus status = determineStatus(packetLoss);
        
        return new PingResult(host, status, avgRtt, packetLoss);
    }
    
    private double parseAverageRtt(List<String> outputLines) {
        for (String line : outputLines) {
            if (line.contains("Среднее") && line.contains("мсек")) {
                Pattern pattern = Pattern.compile("Среднее\\s*=\\s*(\\d+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    try {
                        return Double.parseDouble(matcher.group(1));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        
        return 0.0;
    }
    
    private int parsePacketLoss(List<String> outputLines) {
        for (String line : outputLines) {
            if (line.contains("потерь") && line.contains("%")) {
                Pattern pattern = Pattern.compile("(\\d+)%");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    try {
                        return Integer.parseInt(matcher.group(1));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        
        return 100;
    }
    
    private HostStatus determineStatus(int packetLoss) {
        if (packetLoss == 0) {
            return HostStatus.UP;
        } else if (packetLoss == 100) {
            return HostStatus.UNREACHABLE;
        } else {
            return HostStatus.FLAKY;
        }
    }
}