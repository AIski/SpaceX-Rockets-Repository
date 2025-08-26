package api;

public enum RocketStatus {
    ON_GROUND, // initial status, where the rocket is not assigned to any mission
    IN_SPACE, // the rocket was assigned to the mission
    IN_REPAIR // the rocket is due to repair, it implies “Pending” status of the mission
}
