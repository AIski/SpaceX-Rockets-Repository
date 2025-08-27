package internal;

import api.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InMemorySpaceXRocketsRepository implements SpaceXRocketsRepository {
    private final Set<Mission> missions = new HashSet<>();
    private final Set<Rocket> rockets = new HashSet<>();


    @Override
    public void addRocket(String rocketName) {
        validateRocketName(rocketName);
        Rocket rocket = new Rocket(rocketName.trim());
        rockets.add(rocket);
    }

    private void validateRocketName(String name) {
        validateNameStringNotNullOrBlank(name);
        validateUniqueRocketName(name);
    }

    private void validateNameStringNotNullOrBlank(String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty or blank");
        }
    }

    private void validateUniqueRocketName(String name) {
        String normalizedName = name.trim().toLowerCase();

        boolean duplicateRocketFound = rockets.stream()
                .anyMatch(
                        rocket -> rocket.getName().toLowerCase().equals(normalizedName)
                );

        if (duplicateRocketFound) {
            throw new IllegalArgumentException("Rocket name already used: " + normalizedName);
        }
    }

    @Override
    public void addMission(String missionName) {
        validateMissionName(missionName);
        Mission mission = new Mission(missionName.trim());
        missions.add(mission);
    }

    private void validateMissionName(String name) {
        validateNameStringNotNullOrBlank(name);
        validateUniqueMissionName(name);
    }

    private void validateUniqueMissionName(String name) {
        String normalizedName = name.trim().toLowerCase();

        boolean duplicateMissionFound = missions.stream()
                .anyMatch(
                        mission -> mission.getName().toLowerCase().equals(normalizedName)
                );

        if (duplicateMissionFound) {
            throw new IllegalArgumentException("Mission name already used: " + normalizedName);
        }
    }

    @Override
    public void assignRocketToMission(String rocketName, String missionName) {

    }

    @Override
    public void changeRocketStatus(String rocketName, RocketStatus newStatus) {

    }

    @Override
    public void changeMissionStatus(String rocketName, MissionStatus newStatus) {

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
