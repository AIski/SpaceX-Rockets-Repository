package api;

import internal.InMemorySpaceXRocketsRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class SpaceXRocketsRepositoryTest {

    private SpaceXRocketsRepository createRepository(){
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

}