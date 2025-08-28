package api;

import java.util.List;

/**
 * Immutable summary of a mission.
 *
 * @param name         mission name
 * @param status       mission status
 * @param dragonsCount number of rockets assigned
 * @param dragons      list of rocket summaries
 * @see MissionStatus
 */
public record MissionSummary(
        String name,
        MissionStatus status,
        int dragonsCount,
        List<RocketSummary> dragons
) {
    @Override
    public String toString() {
        return "{" +
                name + " - " + status +
                "- Dragons: " + dragonsCount +
                " " + dragons + '}';
    }
}