package api;

public enum MissionStatus {
    SCHEDULED, // initial status, where no rockets are assigned
    PENDING, // at least one rocket is assigned and one or more assigned rockets are in repair
    IN_PROGRESS, //at least one rocket is assigned and none of them is in repair
    ENDED //  the final stage of the mission, at this point rockets should not be assigned anymore to a mission
}
