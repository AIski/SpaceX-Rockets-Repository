package api;

/**
 * Status of a rocket during its lifecycle.
 */
public enum RocketStatus {
    /**
     * Initial status, not yet assigned to any mission.
     */
    ON_GROUND,
    /**
     * The rocket is currently in space, assigned to a mission.
     */
    IN_SPACE,
    /**
     * The rocket requires repair; blocks mission progress.
     */
    IN_REPAIR
}
