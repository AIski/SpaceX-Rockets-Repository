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
        validateNameString(rocketName);
        Rocket newRocket = new Rocket(rocketName);
        rockets.add(newRocket);
    }

    private void validateNameString(String rocketName) {
        if (rocketName == null) {
            throw new NullPointerException("Rocket name cannot be null");
        }
        if (rocketName.isBlank()) {
            throw new IllegalArgumentException("Rocket name cannot be empty or blank");
        }
    }

    @Override
    public void addMission(String missionName) {

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
        return List.of();
    }

    @Override
    public List<RocketSummary> getRocketSummaries() {
        return rockets.stream()
                .sorted(Comparator.comparing(Rocket::getName))
                .map(rocket -> new RocketSummary(rocket.getName(), rocket.getStatus()))
                .toList();
    }

    // Constructors
    public InMemorySpaceXRocketsRepository() {
    }
}
