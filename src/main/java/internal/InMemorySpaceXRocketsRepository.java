package internal;

import api.MissionSummary;
import api.RocketSummary;
import api.SpaceXRocketsRepository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InMemorySpaceXRocketsRepository implements SpaceXRocketsRepository {
    private final Set<Mission> missions = new HashSet<>();
    private final Set<Rocket> rockets = new HashSet<>();

    @Override
    public void addRocket(String rocketName) {
        EntityNameValidator.validateRocketName(rocketName, rockets);
        Rocket rocket = new Rocket(rocketName.trim());
        rockets.add(rocket);
    }

    @Override
    public void addMission(String missionName) {
        EntityNameValidator.validateMissionName(missionName, missions);
        Mission mission = new Mission(missionName.trim());
        missions.add(mission);
    }

    @Override
    public void assignRocketToMission(String rocketName, String missionName) {

    }

    @Override
    public void repairRocket(String rocketName) {

    }

    @Override
    public void launchMission(String missionName) {

    }

    @Override
    public void endMission(String missionName) {

    }

    @Override
    public List<MissionSummary> getMissionSummaries() {
        return missions.stream()
                .sorted(
                        Comparator.comparingInt((Mission mission) -> mission.getRockets().size()).reversed()
                                .thenComparing(Mission::getName).reversed())
                .map((Mission mission) -> toMissionSummary(mission))
                .toList();
    }

    private MissionSummary toMissionSummary(Mission mission) {
        List<RocketSummary> missionRockets = mission.getRockets().stream()
                .sorted(Comparator.comparing(Rocket::getName))
                .map(this::toRocketSummary)
                .toList();
        return new MissionSummary(
                mission.getName(),
                mission.getStatus(),
                mission.getRockets().size(),
                missionRockets
        );
    }

    @Override
    public List<RocketSummary> getRocketSummaries() {
        return rockets.stream()
                .sorted(Comparator.comparing(Rocket::getName))
                .map(this::toRocketSummary)
                .toList();
    }

    private RocketSummary toRocketSummary(Rocket rocket) {
        return new RocketSummary(rocket.getName(), rocket.getStatus());
    }

    // Constructors
    public InMemorySpaceXRocketsRepository() {
    }
}
