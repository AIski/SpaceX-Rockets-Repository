package api;

import java.util.List;

public interface SpaceXRocketsRepository {
    void addRocket(String rocketName);

    void addMission(String missionName);

    void assignRocketToMission(String rocketName, String missionName);

    void repairRocket(String rocketName);

    void launchMission(String missionName);

    void resumeMission(String missionName);

    void endMission(String missionName);

    List<MissionSummary> getMissionSummaries();

    List<RocketSummary> getRocketSummaries();
}
