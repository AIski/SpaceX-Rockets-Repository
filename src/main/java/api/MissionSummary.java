package api;

import java.util.List;

public record MissionSummary(
        String name,
        RocketStatus status,
        int rocketsCount,
        List<RocketSummary> rockets
) {
}