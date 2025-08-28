package demo;

import api.SpaceXRocketsRepository;
import internal.InMemorySpaceXRocketsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class Demo {
    private static final Logger log = LoggerFactory.getLogger(Demo.class);

    public static void main(String[] args) {
        log.info("SpaceXRocketsRepository App started.");
        SpaceXRocketsRepository repository = new InMemorySpaceXRocketsRepository();

        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        log.info("Created Dragon 1 rocket and Mars mission.");
        repository.assignRocketToMission("Dragon 1", "Mars");
        log.info("Assigned Dragon 1 to Mars mission.");

        logRocketsAndMissions(repository);;

        repository.launchMission("Mars");
        log.info("Mars mission launched.");
        logRocketsAndMissions(repository);

        repository.endMission("Mars");
        log.info("Mars mission ended.");
        logRocketsAndMissions(repository);

        repository.addMission("Venus");
        log.info("Venus mission added.");
        repository.assignRocketToMission("Dragon 1", "Venus");
        log.info("Assigned Dragon 1 to Venus mission.");
        logRocketsAndMissions(repository);

        repository.addMission("Luna 2");
        log.info("Luna 2 mission added.");
        logRocketsAndMissions(repository);

    }

    private static void logRocketsAndMissions(SpaceXRocketsRepository repository) {
        log.info("Mission summaries: {}", repository.getMissionSummaries().toString());
        log.info("Rocket summaries: {}", repository.getRocketSummaries().toString());
    }
}
