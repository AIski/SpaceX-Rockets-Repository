package api;

import java.util.List;

public record MissionSummary(
        String name,
        MissionStatus status,
        int rocketsCount,
        List<RocketSummary> rockets
) {
    @Override
    public String toString() {
        return "MissionSummary{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", rocketsCount=" + rocketsCount +
                ", rockets=" + rockets +
                '}';
    }
}