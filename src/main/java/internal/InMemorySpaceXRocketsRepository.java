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
        Rocket newRocket = new Rocket(rocketName);
        rockets.add(newRocket);
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
