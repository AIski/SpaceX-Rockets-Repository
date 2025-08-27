package api;

import java.util.List;

public interface SpaceXRocketsRepository {
    void addRocket(String rocketName);

    void addMission(String missionName);

    void assignRocketToMission(String rocketName, String missionName);

    void changeRocketStatus(String rocketName, RocketStatus newStatus);

    void changeMissionStatus(String rocketName, MissionStatus newStatus);

    List<MissionSummary> getMissionSummaries();

    List<RocketSummary> getRocketSummaries();
}
