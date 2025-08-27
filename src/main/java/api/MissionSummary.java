package api;

import java.util.List;

public record MissionSummary(
        String name,
        MissionStatus status,
        int rocketsCount,
        List<RocketSummary> rockets
) {
}