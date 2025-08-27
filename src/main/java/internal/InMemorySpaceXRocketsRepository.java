package internal;

import api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InMemorySpaceXRocketsRepository implements SpaceXRocketsRepository {

    private final Set<Mission> missions = new HashSet<>();
    private final Set<Rocket> rockets = new HashSet<>();

    @Override
    public void addRocket(String rocketName) {

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
        return List.of();
    }

    // Constructors
    public InMemorySpaceXRocketsRepository() {
    }
}
