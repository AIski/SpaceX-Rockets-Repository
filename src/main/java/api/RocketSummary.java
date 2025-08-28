package api;

/**
 * Immutable summary of a rocket.
 *
 * @param name   rocket name
 * @param status current status of the rocket
 * @see RocketStatus
 */
public record RocketSummary(
        String name,
        RocketStatus status
) {
    @Override
    public String toString() {
        return "{" + name + " - " + status + '}';
    }
}
