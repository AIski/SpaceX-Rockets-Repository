package api;

import java.util.List;

public interface SpaceXRocketsRepository {
    void addRocket(String rocketName);

    void assignRocketToMission(String rocketName, String missionName);

    void changeRocketStatus(String rocketName, RocketStatus newStatus);

    void addMission(String missionName);

    void changeMissionStatus(String rocketName, RocketStatus newStatus);

    List<MissionSummary> getMissionSummaries();

    List<RocketSummary> getRocketSummaries();
}
