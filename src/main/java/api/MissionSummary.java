package api;

import java.util.List;

public record MissionSummary(
        String name,
        MissionStatus status,
        int dragonsCount,
        List<RocketSummary> dragons
) {
    @Override
    public String toString() {
        return "{" +
                 name + " - "
                + status +
                "- Dragons: " + dragonsCount +
                " " + dragons +
                '}';
    }
}