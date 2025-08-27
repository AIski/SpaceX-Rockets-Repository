package api;

import internal.InMemorySpaceXRocketsRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}