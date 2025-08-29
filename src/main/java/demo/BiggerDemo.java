package demo;

import api.SpaceXRocketsRepository;
import internal.InMemorySpaceXRocketsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class BiggerDemo {
    private static final Logger log = LoggerFactory.getLogger(BiggerDemo.class);

    public static void main(String[] args) {
        log.info("SpaceXRocketsRepository App started - BiggerDemo.");
        SpaceXRocketsRepository repository = SpaceXRocketsRepository.inMemory();

        repository.addMission("Mars");
        log.info("Created Mars mission.");

        repository.addRocket("Dragon 1");
        repository.addRocket("Dragon 2");
        log.info("Created Dragon 1 and Dragon 2 dragons.");
        logRocketsAndMissions(repository);

        repository.addMission("Vertical Landing");
        log.info("Created Vertical Landing mission.");
        repository.assignRocketToMission("Dragon 1", "Vertical Landing");
        log.info("Assigned Dragon 1 to Vertical Landing mission.");
        logRocketsAndMissions(repository);

        repository.launchMission("Vertical Landing");
        log.info("Vertical Landing mission launched.");
        logRocketsAndMissions(repository);

        repository.endMission("Vertical Landing");
        log.info("Vertical Landing mission ended.");
        logRocketsAndMissions(repository);

        repository.repairRocket("Dragon 1");
        log.info("Dragon 1 rocket repaired.");
        logRocketsAndMissions(repository);

        repository.addMission("Double Landing");
        repository.assignRocketToMission("Dragon 1", "Double Landing");
        repository.assignRocketToMission("Dragon 2", "Double Landing");
        log.info("Created Double Landing mission. Assigned Dragon 1 and Dragon 2 to it.");
        logRocketsAndMissions(repository);

        repository.launchMission("Double Landing");
        log.info("Double Landing mission launched.");
        logRocketsAndMissions(repository);

        repository.endMission("Double Landing");
        log.info("Double Landing mission ended.");
        logRocketsAndMissions(repository);

        repository.addMission("Luna1");
        repository.assignRocketToMission("Dragon 1", "Luna1");
        repository.assignRocketToMission("Dragon 2", "Luna1");
        log.info("Created Luna1 mission. Assigned Dragon 1 and Dragon 2 to it. They need to be repaired in order for the mission to be launched properly.");
        logRocketsAndMissions(repository);

        repository.launchMission("Luna1");
        log.info("Luna1 mission launched. With dragons that need to be repaired.");
        logRocketsAndMissions(repository);

        repository.repairRocket("Dragon 1");
        repository.repairRocket("Dragon 2");
        log.info("Dragon 1 and 2 dragons repaired.");
        logRocketsAndMissions(repository);
        log.info("In order to resume Luna1 mission, resume() method must be called on the mission.");

        repository.addMission("Luna2");
        log.info("Created Luna2 mission without a rocket assigned to it.");

        log.info("//////////////////////");

        repository.addMission("Transit");
        repository.addRocket("Red Dragon");
        repository.addRocket("Dragon XL");
        repository.addRocket("Falcon Heavy");
        log.info("Created Transit mission. Added Red Dragon, Dragon XL and Falcon Heavy dragons.");

        repository.assignRocketToMission("Red Dragon", "Transit");
        repository.assignRocketToMission("Dragon XL", "Transit");
        repository.assignRocketToMission("Falcon Heavy", "Transit");
        log.info("Assigned Red Dragon, Dragon XL and Falcon Heavy to Transit mission.");
        logRocketsAndMissions(repository);

        repository.launchMission("Transit");
        log.info("Transit mission launched.");
        logRocketsAndMissions(repository);


    }

    private static void logRocketsAndMissions(SpaceXRocketsRepository repository) {
        log.info("Mission summaries: {}", repository.getMissionSummaries().toString());
        log.info("Rocket summaries: {}", repository.getRocketSummaries().toString());
    }
}
