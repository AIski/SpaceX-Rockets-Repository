package demo;

import api.SpaceXRocketsRepository;
import internal.InMemorySpaceXRocketsRepository;

class Demo {
    public static void main(String[] args) {
        System.out.println("SpaceXRocketsRepository App started.");
        SpaceXRocketsRepository repository = new InMemorySpaceXRocketsRepository();

        repository.addRocket("Dragon 1");
        repository.addMission("Mars");
        System.out.println("Created Dragon 1 rocket and Mars mission.");
        repository.assignRocketToMission("Dragon 1", "Mars");
        System.out.println("Assigned Dragon 1 to Mars mission.");

        System.out.println(repository.getRocketSummaries());
        System.out.println(repository.getMissionSummaries());

        repository.launchMission("Mars");
        System.out.println("Mars mission launched.");
        System.out.println(repository.getRocketSummaries());
        System.out.println(repository.getMissionSummaries());

        repository.endMission("Mars");
        System.out.println("Mars mission ended.");
        System.out.println(repository.getRocketSummaries());
        System.out.println(repository.getMissionSummaries());

        repository.addMission("Venus");
        System.out.println("Venus mission added.");
        repository.assignRocketToMission("Dragon 1", "Venus");
        System.out.println("Assigned Dragon 1 to Venus mission.");
        System.out.println(repository.getRocketSummaries());
        System.out.println(repository.getMissionSummaries());

    }
}
