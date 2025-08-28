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
        Rocket rocket = getRocketByNameOrThrow(rocketName);
        Mission mission = getMissionByNameOrThrow(missionName);
        mission.assign(rocket);
    }

    private Rocket getRocketByNameOrThrow(String rocketName) {
        return LookupUtils.getRocketByNameOrThrow(rocketName, rockets);
    }

    @Override
    public void repairRocket(String rocketName) {
        Rocket rocket = getRocketByNameOrThrow(rocketName);
        rocket.repair();
    }

    @Override
    public void launchMission(String missionName) {
        Mission mission = getMissionByNameOrThrow(missionName);
        mission.launch();
    }

    @Override
    public void resumeMission(String missionName) {
        Mission mission = getMissionByNameOrThrow(missionName);
        mission.resume();
    }

    @Override
    public void endMission(String missionName) {
        Mission mission = getMissionByNameOrThrow(missionName);
        mission.end();
    }

    private Mission getMissionByNameOrThrow(String missionName) {
       return LookupUtils.getMissionByNameOrThrow(missionName, missions);
    }

    @Override
    public List<MissionSummary> getMissionSummaries() {
        return SummariesMapper.toMissionSummaries(this.missions);
    }

    @Override
    public List<RocketSummary> getRocketSummaries() {
        return SummariesMapper.toRocketSummaries(this.rockets);
    }

}
