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


}