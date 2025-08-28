package api;

/**
 * Status of a mission during its lifecycle.
 */
public enum MissionStatus {
    /**
     * Initial status, no rockets are assigned.
     */
    SCHEDULED,
    /**
     * At least one rocket assigned, but some rockets are in repair.
     */
    PENDING,
    /**
     * All assigned rockets are operational and launched.
     */
    IN_PROGRESS,
    /**
     * Mission has ended, no further rocket assignments allowed.
     */
    ENDED
}