package api;

import internal.InMemorySpaceXRocketsRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void addRocket_emptyOrBlankName_throwsIllegalArgumentException() {
        var repository = createRepository();
        assertThrows(IllegalArgumentException.class, () -> repository.addRocket(""));
        assertThrows(IllegalArgumentException.class, () -> repository.addRocket("   "));
    }

}