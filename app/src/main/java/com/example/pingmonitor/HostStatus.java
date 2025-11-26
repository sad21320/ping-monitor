package com.example.pingmonitor;

/**
 * Перечисление статусов доступности хоста
 */
public enum HostStatus {
    UP("UP"),
    FLAKY("FLAKY"),
    UNREACHABLE("UNREACHABLE");
    
    private final String displayName;
    
    HostStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Получает приоритет для сортировки
     * @return числовой приоритет (меньше = выше в списке)
     */
    public int getSortPriority() {
        switch (this) {
            case UP: return 1;
            case FLAKY: return 2;
            case UNREACHABLE: return 3;
            default: return 4;
        }
    }
}