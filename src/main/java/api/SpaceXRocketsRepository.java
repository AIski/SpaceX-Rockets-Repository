package api;

import internal.InMemorySpaceXRocketsRepository;

import java.util.List;

/**
 * Repository interface for managing missions and rockets.
 * <p>
 * Implementations enforce the domain rules for mission and rocket state transitions.
 * Defines the core operations available to clients of the library.
 * Implementations enforce all business rules regarding mission and rocket state transitions.
 */
public interface SpaceXRocketsRepository {

    /**
     * Creates an in-memory implementation of the {@link SpaceXRocketsRepository}.
     *
     * @return an instance of {@link InMemorySpaceXRocketsRepository}, providing functionality for managing missions and rockets in memory.
     */
    static SpaceXRocketsRepository inMemory() {
        return new InMemorySpaceXRocketsRepository();
    }

    /**
     * Creates a new rocket with the given name.
     *
     * @param rocketName non-null, non-blank rocket name
     * @throws NullPointerException     if {@code rocketName} is null
     * @throws IllegalArgumentException if {@code rocketName} is blank or already exists
     */
    void addRocket(String rocketName);

    /**
     * Creates a new mission with the given name.
     *
     * @param missionName non-null, non-blank mission name
     * @throws NullPointerException     if {@code missionName} is null
     * @throws IllegalArgumentException if {@code missionName} is blank or already exists
     */
    void addMission(String missionName);

    /**
     * Assigns a rocket to a mission.
     * <p>
     * Preconditions:
     * <ul>
     *   <li>Mission status is {@link MissionStatus#SCHEDULED} or {@link MissionStatus#PENDING}.</li>
     *   <li>Rocket status is {@link RocketStatus#ON_GROUND} or {@link RocketStatus#IN_REPAIR}.</li>
     *   <li>Rocket is not already assigned to a mission.</li>
     * </ul>
     *
     * @param rocketName  name of the rocket to assign
     * @param missionName name of the mission to assign the rocket to
     * @throws java.util.NoSuchElementException if rocket or mission not found
     * @throws NullPointerException             if {@code rocketName} or {@code missionName} is null
     * @throws IllegalArgumentException         if {@code rocketName} or {@code missionName} is blank
     * @throws IllegalStateException            if preconditions are not met
     */
    void assignRocketToMission(String rocketName, String missionName);

    /**
     * Repairs a rocket and sets its status to ON_GROUND.
     * <p>
     * Preconditions:
     * <ul>
     *     <li>The rocket must currently be in {@link RocketStatus#IN_REPAIR}.</li>
     * </ul>
     * After this call, the rocket will transition to {@link RocketStatus#ON_GROUND}.
     *
     * @param rocketName non-null, non-blank name of an existing rocket
     * @throws NullPointerException             if {@code rocketName} is null
     * @throws IllegalArgumentException         if {@code rocketName} is blank
     * @throws java.util.NoSuchElementException if rocket not found
     * @throws IllegalStateException            if preconditions are not met
     */
    void repairRocket(String rocketName);

    /**
     * Launches a mission if all requirements are met.
     * <p>
     * Preconditions:
     * <ul>
     *   <li>Mission must be {@link MissionStatus#SCHEDULED}.</li>
     *   <li>At least one rocket must be assigned to the mission.</li>
     * </ul>
     * Behavior:
     * <ul>
     *   <li>If any rocket is {@link RocketStatus#IN_REPAIR}, mission becomes {@link MissionStatus#PENDING}.</li>
     *   <li>Otherwise, mission becomes {@link MissionStatus#IN_PROGRESS}, and all rockets are launched. Rocket statuses become {@link RocketStatus#IN_SPACE}. </li>
     * </ul>
     *
     * @param missionName non-null, non-blank name of an existing mission
     * @throws NullPointerException             if {@code missionName} is null
     * @throws IllegalArgumentException         if {@code missionName} is blank
     * @throws java.util.NoSuchElementException if the mission not found
     * @throws IllegalStateException            if preconditions are not met
     */
    void launchMission(String missionName);

    /**
     * Resumes a PENDING mission if all rockets are ready.
     * Preconditions:
     * <ul>
     *   <li>Mission must be {@link MissionStatus#PENDING}.</li>
     *   <li>All assigned rockets must be in {@link RocketStatus#ON_GROUND}.</li>
     * </ul>
     * After this call, the mission becomes {@link MissionStatus#IN_PROGRESS} and rockets become {@link RocketStatus#IN_SPACE}.
     *
     * @param missionName non-null, non-blank name of an existing mission
     * @throws NullPointerException             if {@code missionName} is null
     * @throws IllegalArgumentException         if {@code missionName} is blank
     * @throws java.util.NoSuchElementException if the mission not found
     * @throws IllegalStateException            if preconditions are not met
     */
    void resumeMission(String missionName);

    /**
     * Ends an active mission and sets all rockets to IN_REPAIR status.
     * <p>
     * Preconditions:
     * <ul>
     *   <li>Mission must be {@link MissionStatus#IN_PROGRESS}.</li>
     * </ul>
     * After this call, the mission becomes {@link MissionStatus#ENDED}.
     * All rockets will transition to {@link RocketStatus#IN_REPAIR} and will be unassigned from the mission.
     *
     * @param missionName non-null, non-blank name of an existing mission
     * @throws NullPointerException             if {@code missionName} is null
     * @throws IllegalArgumentException         if {@code missionName} is blank
     * @throws java.util.NoSuchElementException if the mission not found
     * @throws IllegalStateException            if preconditions are not met
     */
    void endMission(String missionName);

    /**
     * Returns summaries of all missions in the repository.
     * <p>
     * The list is sorted by:
     * <ul>
     *   <li>Number of rockets (descending).</li>
     *   <li>Name (descending, if counts are equal).</li>
     * </ul>
     *
     * @return list of mission summaries, never null
     */
    List<MissionSummary> getMissionSummaries();

    /**
     * Returns summaries of all rockets in the system.
     * <p>
     * The list is sorted alphabetically by rocket name.
     *
     * @return list of rocket summaries, never null
     */
    List<RocketSummary> getRocketSummaries();
}
