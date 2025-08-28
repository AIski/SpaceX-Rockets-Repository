package api;

import internal.InMemorySpaceXRocketsRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class SpaceXRocketsRepositoryTest {

    private SpaceXRocketsRepository createRepository() {
        return new InMemorySpaceXRocketsRepository();
    }

    @Test
    void addRocket_addsRocketSuccessfully() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");

        assertEquals(1, repository.getRocketSummaries().size());
        assertEquals("Dragon 1", repository.getRocketSummaries().get(0).name());
    }

    @Test
    void addRocket_nullName_throwsNullPointerException() {
        var repository = createRepository();
        assertThrows(NullPointerException.class, () -> repository.addRocket(null));
    }

    @Test
    void addRocket_blankName_throwsIllegalArgumentException() {
        var repository = createRepository();
        assertThrows(IllegalArgumentException.class, () -> repository.addRocket(""));
        assertThrows(IllegalArgumentException.class, () -> repository.addRocket("   "));
    }

    @Test
    void getRocketSummaries_whenNoRockets_returnsEmptyList() {
        var repository = createRepository();
        List<RocketSummary> summaries = repository.getRocketSummaries();
        assertNotNull(summaries);
        assertTrue(summaries.isEmpty());
    }

    @Test
    void getRocketSummaries_mapsNameAndStatus() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");

        var s = repository.getRocketSummaries();
        assertEquals(1, s.size());
        assertEquals("Dragon 1", s.get(0).name());
        assertEquals(RocketStatus.ON_GROUND, s.get(0).status());
    }

    @Test
    void getRocketSummaries_isSortedByNameAscending() {
        var repository = createRepository();
        repository.addRocket("Dragon 3");
        repository.addRocket("Dragon 2");
        repository.addRocket("Dragon 1");

        var names = repository.getRocketSummaries().stream()
                .map(RocketSummary::name)
                .toList();

        assertEquals(List.of("Dragon 1", "Dragon 2", "Dragon 3"), names);
    }

    @Test
    void getMissionSummaries_whenNoMissions_returnsEmptyList() {
        var repository = createRepository();
        var summaries = repository.getMissionSummaries();
        assertNotNull(summaries);
        assertTrue(summaries.isEmpty());
    }


    @Test
    void addMission_addsMissionSuccessfully() {
        var repository = createRepository();
        repository.addMission("Mars");

        var summaries = repository.getMissionSummaries();
        assertEquals(1, summaries.size());
        assertEquals("Mars", summaries.get(0).name());
    }

    @Test
    void addMission_missionRocketsNotNull() {
        var repository = createRepository();
        repository.addMission("Mars");

        var summaries = repository.getMissionSummaries();
        assertNotNull(summaries.get(0).rockets());
    }

    @Test
    void getMissionSummaries_mapsNameAndStatusCorrectly() {
        var repository = createRepository();
        repository.addMission("Mars");

        var marsMission = repository.getMissionSummaries().get(0);
        assertEquals(MissionStatus.SCHEDULED, marsMission.status());
        assertEquals(0, marsMission.rocketsCount());
        assertEquals("Mars", marsMission.name());
        assertTrue(marsMission.rockets().isEmpty());
    }

    @Test
    void addMission_nullName_throwsNullPointerException() {
        var repository = createRepository();
        assertThrows(NullPointerException.class, () -> repository.addMission(null));
    }

    @Test
    void addMission_blankName_throwsIllegalArgumentException() {
        var repository = createRepository();
        assertThrows(IllegalArgumentException.class, () -> repository.addMission(""));
        assertThrows(IllegalArgumentException.class, () -> repository.addMission("  "));
    }

    @Test
    void getMissionSummaries_whenAllCountsEqual_ordersByNameDesc() {
        var repository = createRepository();

        repository.addMission("Alpha");
        repository.addMission("Zeta");
        repository.addMission("Mars");

        repository.addRocket("Dragon 1");
        repository.addRocket("Dragon 2");
        repository.addRocket("Dragon 3");


        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.assignRocketToMission("Dragon 2", "Zeta");
        repository.assignRocketToMission("Dragon 3", "Alpha");

        var names = repository.getMissionSummaries().stream()
                .map(MissionSummary::name)
                .toList();

        assertEquals(List.of("Zeta", "Mars", "Alpha"), names);
    }

    @Test
    void getMissionSummaries_ordersByCountDesc_thenNameDescOnTie() {
        var repository = createRepository();

        repository.addMission("Alpha");
        repository.addMission("Zeta");
        repository.addMission("Mars");

        repository.addRocket("Dragon 1");
        repository.addRocket("Dragon 2");
        repository.addRocket("Dragon 3");
        repository.addRocket("Dragon 4");

        repository.assignRocketToMission("Dragon 1", "Zeta");
        repository.assignRocketToMission("Dragon 2", "Zeta");
        repository.assignRocketToMission("Dragon 3", "Alpha");
        repository.assignRocketToMission("Dragon 4", "Mars");

        List<MissionSummary> summaries = repository.getMissionSummaries();

        assertEquals(List.of("Zeta", "Mars", "Alpha"),
                summaries.stream().map(MissionSummary::name).toList());

        assertEquals(List.of(2, 1, 1),
                summaries.stream().map(MissionSummary::rocketsCount).toList());
    }

    @Test
    void addRocket_nameAlreadyUsed_throwsIllegalArgumentException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");

        assertThrows(IllegalArgumentException.class,
                () -> repository.addRocket("Dragon 1"));
    }

    @Test
    void addRocket_similarNameAlreadyUsed_throwsIllegalArgumentException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");

        assertThrows(IllegalArgumentException.class,
                () -> repository.addRocket("DRAGON 1   "));
    }

    @Test
    void addMission_nameAlreadyUsed_throwsIllegalArgumentException() {
        var repository = createRepository();
        repository.addMission("Mars");

        assertThrows(IllegalArgumentException.class,
                () -> repository.addMission("Mars"));
    }

    @Test
    void addMission_similarNameAlreadyUsed_throwsIllegalArgumentException() {
        var repository = createRepository();
        repository.addMission("Mars");

        assertThrows(IllegalArgumentException.class,
                () -> repository.addMission("MARS   "));
    }

    @Test
    void assignRocketToMission_validInput_assignsSuccessfully() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");

        repository.assignRocketToMission("Dragon 1", "Mars");

        assertEquals(1, repository.getMissionSummaries().get(0).rocketsCount());
        assertEquals("Dragon 1", repository.getMissionSummaries().get(0).rockets().get(0).name());
    }

    @Test
    void assignRocketToMission_rocketNotFound_throwsNoSuchElementException() {
        var repository = createRepository();
        repository.addMission("Mars");
        assertThrows(
                NoSuchElementException.class,
                () -> repository.assignRocketToMission("Dragon 1", "Mars")
        );
    }

    @Test
    void assignRocketToMission_missionNotFound_throwsNoSuchElementException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");

        assertThrows(
                NoSuchElementException.class,
                () -> repository.assignRocketToMission("Dragon 1", "Mars")
        );
    }

    @Test
    void assignRocketToMission_rocketAlreadyAssignedToMission_throwsIllegalStateException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.addMission("Venus");

        assertThrows(
                IllegalStateException.class,
                () -> repository.assignRocketToMission("Dragon 1", "Venus")
        );
    }

    @Test
    void assignRocketToMission_invalidMissionStatus_throwsIllegalStateException() {
        // mission launched with all repaired rockets, then ended
        // then we try to assign rocket to mission.
    }

    // case after mission 1 is finished, rockets are repaired and reused to 2nd mission.


    @Test
    void repairRocket_validInput_backFromPreviousMission_repairsRocketSuccessfully() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");
        repository.endMission("Mars");

        repository.repairRocket("Dragon 1");
        assertEquals(
                RocketStatus.ON_GROUND,
                repository.getRocketSummaries().get(0).status()
        );
    }

    @Test
    void repairRocket_invalidRocketStatusOnGround_throwsIllegalStateException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");

        assertThrows(
                IllegalStateException.class,
                () -> repository.repairRocket("Dragon 1")
        );
    }

    @Test
    void repairRocket_invalidRocketStatusInSpace_throwsIllegalStateException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");

        assertThrows(
                IllegalStateException.class,
                () -> repository.repairRocket("Dragon 1")
        );
    }

    @Test
    void repairRocket_nameNotFound_throwsNoSuchElementException() {
        var repository = createRepository();
        assertThrows(
                NoSuchElementException.class,
                () -> repository.repairRocket("Dragon 1")
        );
    }

    @Test
    void launchMission_validInput_launchesSuccessfully_changesStatusesCorrectly() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");

        assertEquals(MissionStatus.IN_PROGRESS, repository.getMissionSummaries().get(0).status());
        assertEquals(RocketStatus.IN_SPACE, repository.getRocketSummaries().get(0).status());
    }

    @Test
    void launchMission_validInputMultipleRockets_launchesSuccessfullyToSpace_changesStatusesCorrectly() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addRocket("Dragon 2");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.assignRocketToMission("Dragon 2", "Mars");
        repository.launchMission("Mars");

        assertEquals(MissionStatus.IN_PROGRESS, repository.getMissionSummaries().get(0).status());
        assertEquals(RocketStatus.IN_SPACE, repository.getRocketSummaries().get(0).status());
        assertEquals(RocketStatus.IN_SPACE, repository.getRocketSummaries().get(1).status());
    }

    @Test
    void launchMission_noRocketAssignedToMission_throwsIllegalStateException() {
        var repository = createRepository();
        repository.addMission("Mars");

        assertThrows(
                IllegalStateException.class,
                () -> repository.launchMission("Mars")
        );
    }

    @Test
    void launchMission_rocketInRepair_changesMissionStatusToPending() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");
        repository.endMission("Mars");
        //Dragon 1 is still in repair from previous mission and is assigned to second mission before repair.
        // Second mission is launched before repair

        repository.addMission("Venus");
        repository.assignRocketToMission("Dragon 1", "Venus");
        repository.launchMission("Venus");

        assertEquals(MissionStatus.PENDING, repository.getMissionSummaries().get(0).status());
        assertEquals(RocketStatus.IN_REPAIR, repository.getRocketSummaries().get(0).status());
    }

    @Test
    void launchMission_invalidStatusInSpace_throwsIllegalStateException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");

        assertThrows(
                IllegalStateException.class,
                () -> repository.launchMission("Mars")
        );
    }

    //We should probably test remaining status cases - ENDED, PENDING, but we will skip it for now.

    @Test
    void launchMission_nameNotFound_throwsNoSuchElementException() {
        var repository = createRepository();
        assertThrows(
                NoSuchElementException.class,
                () -> repository.launchMission("Mars")
        );
    }

    @Test
    void resumeMission_validInput_resumesSuccessfully_changesStatusesCorrectly() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");
        repository.endMission("Mars");

        //Dragon 1 is still in repair from previous mission and is assigned to second mission before repair.
        //We launch mission 2, before repairing rocket.
        repository.addMission("Venus");
        repository.assignRocketToMission("Dragon 1", "Venus");
        repository.launchMission("Venus");

        // Dragon 1 is repaired, mission resumed.
        repository.repairRocket("Dragon 1");
        repository.resumeMission("Venus");

        assertEquals(MissionStatus.IN_PROGRESS, repository.getMissionSummaries().get(0).status());
        assertEquals(RocketStatus.IN_SPACE, repository.getRocketSummaries().get(0).status());

    }

    @Test
    void resumeMission_nameNotFound_throwsNoSuchElementException() {
        var repository = createRepository();
        assertThrows(
                NoSuchElementException.class,
                () -> repository.resumeMission("Mars")
        );
    }

    @Test
    void resumeMission_invalidMissionStatus_throwsIllegalStateException() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");

        //Mission is not launched yet.
        assertThrows(
                IllegalStateException.class,
                () -> repository.resumeMission("Mars")
        );

        //Mission alreadyStarted and moved to PENDING.
        repository.launchMission("Mars");
        assertThrows(
                IllegalStateException.class,
                () -> repository.resumeMission("Mars")
        );

        //Mission ended
        repository.endMission("Mars");
        assertThrows(
                IllegalStateException.class,
                () -> repository.resumeMission("Mars")
        );
    }

    @Test
    void endMission_nameNotFound_throwsNoSuchElementException() {
        var repository = createRepository();
        assertThrows(
                NoSuchElementException.class,
                () -> repository.resumeMission("Mars")
        );
    }

    @Test
    void endMission_validInput_endsSuccessfully_changesStatusesCorrectly() {
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");
        repository.launchMission("Mars");
        repository.endMission("Mars");
        assertEquals(MissionStatus.ENDED, repository.getMissionSummaries().get(0).status());
        assertEquals(RocketStatus.IN_REPAIR, repository.getRocketSummaries().get(0).status());

    }

    @Test
    void endMission_invalidStatus_throwsIllegalStateException(){
        var repository = createRepository();
        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        repository.assignRocketToMission("Dragon 1", "Mars");

        //Mission is not launched yet.
        assertThrows(
                IllegalStateException.class,
                () -> repository.endMission("Mars")
        );

        //Mission PENDING.
        repository.launchMission("Mars");
        repository.endMission("Mars");
        assertThrows(
                IllegalStateException.class,
                () -> repository.endMission("Mars")
        );
    }

    // endMission extra case: We should also check if the back reference from rocket to mission is removed.
    // This will be tough now, since RocketSummary has no such a field.

}