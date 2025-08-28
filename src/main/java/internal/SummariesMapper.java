package internal;

import api.MissionSummary;
import api.RocketSummary;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

final class SummariesMapper {

    static List<RocketSummary> toRocketSummaries(Set<Rocket> rockets) {
        return rockets.stream()
                .sorted(Comparator.comparing(Rocket::getName))
                .map(SummariesMapper::toRocketSummary)
                .toList();
    }

    private static RocketSummary toRocketSummary(Rocket rocket) {
        return new RocketSummary(rocket.getName(), rocket.getStatus());
    }

    static List<MissionSummary> toMissionSummaries(Set<Mission> missions) {
        return missions.stream()
                .sorted(
                        Comparator.comparingInt((Mission mission) -> mission.getRockets().size()).reversed()
                                .thenComparing(Mission::getName, Comparator.reverseOrder()))
                .map(SummariesMapper::toMissionSummary)
                .toList();
    }

    private static MissionSummary toMissionSummary(Mission mission) {
        List<RocketSummary> missionRockets = mission.getRockets().stream()
                .sorted(Comparator.comparing(Rocket::getName))
                .map(SummariesMapper::toRocketSummary)
                .toList();
        return new MissionSummary(
                mission.getName(),
                mission.getStatus(),
                mission.getRockets().size(),
                missionRockets
        );
    }

}
