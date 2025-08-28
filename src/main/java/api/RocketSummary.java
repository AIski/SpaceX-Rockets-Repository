package api;

public record RocketSummary(
        String name,
        RocketStatus status
) {
    @Override
    public String toString() {
        return "{" +
                name +
                " - " + status +
                '}';
    }
}
