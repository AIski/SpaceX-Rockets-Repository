package internal;

import java.util.Collection;
import java.util.NoSuchElementException;

final class LookupUtils {

    static Mission getMissionByNameOrThrow(String missionName, Collection<Mission> missions) {
        EntityNameValidator.validateNameNotNullOrBlank(missionName);
        return missions.stream()
                .filter(mission -> mission.getName().equalsIgnoreCase(missionName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Mission not found: " + missionName));
    }

    static Rocket getRocketByNameOrThrow(String rocketName, Collection<Rocket> rockets) {
        EntityNameValidator.validateNameNotNullOrBlank(rocketName);
        return rockets.stream()
                .filter(rocket -> rocket.getName().equalsIgnoreCase(rocketName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Rocket not found: " + rocketName));
    }
}
