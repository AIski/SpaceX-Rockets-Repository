package internal;

import api.MissionSummary;
import api.RocketSummary;
import api.SpaceXRocketsRepository;

import java.util.*;

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
        Rocket rocket = getRocketByName(rocketName);
        Mission mission = getMissionByName(missionName);
        mission.assign(rocket);
    }

    @Override
    public void repairRocket(String rocketName) {
        Rocket rocket = getRocketByName(rocketName);
        rocket.repair();
    }

    private Rocket getRocketByName(String rocketName) {
        EntityNameValidator.validateNameNotNullOrBlank(rocketName);
        return rockets.stream()
                .filter(rocket -> rocket.getName().equalsIgnoreCase( rocketName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Rocket not found: " + rocketName));
    }

    @Override
    public void launchMission(String missionName) {
        Mission mission = getMissionByName(missionName);
        mission.launch();
    }

    @Override
    public void resumeMission(String missionName) {
        Mission mission = getMissionByName(missionName);
        mission.resume();
    }

    @Override
    public void endMission(String missionName) {
        Mission mission = getMissionByName(missionName);
        mission.end();
    }

    private Mission getMissionByName(String missionName) {
        EntityNameValidator.validateNameNotNullOrBlank(missionName);
        return missions.stream()
                .filter(mission -> mission.getName().equalsIgnoreCase(missionName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Mission not found: " + missionName));
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
