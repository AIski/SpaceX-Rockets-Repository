package internal;

import java.util.Collection;

final class EntityNameValidator {

    static void validateRocketName(String name, Collection<Rocket> rockets) {
        validateNameStringNotNullOrBlank(name);
        validateUniqueRocketName(name, rockets);
    }

    private static void validateNameStringNotNullOrBlank(String name) {
        if (name == null) {
            throw new NullPointerException("Name cannot be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty or blank");
        }
    }
    private static void validateUniqueRocketName(String name, Collection<Rocket> rockets) {
        String normalizedName = name.trim().toLowerCase();

        boolean duplicateRocketFound = rockets.stream()
                .anyMatch(
                        rocket -> rocket.getName().equalsIgnoreCase(normalizedName)
                );

        if (duplicateRocketFound) {
            throw new IllegalArgumentException("Rocket name already used: " + normalizedName);
        }
    }

    static void validateMissionName(String name, Collection<Mission> missions) {
        validateNameStringNotNullOrBlank(name);
        validateUniqueMissionName(name, missions);
    }

    private static void validateUniqueMissionName(String name, Collection<Mission> missions) {
        String normalizedName = name.trim().toLowerCase();

        boolean duplicateMissionFound = missions.stream()
                .anyMatch(
                        mission -> mission.getName().toLowerCase().equalsIgnoreCase(normalizedName)
                );

        if (duplicateMissionFound) {
            throw new IllegalArgumentException("Mission name already used: " + normalizedName);
        }
    }

}
