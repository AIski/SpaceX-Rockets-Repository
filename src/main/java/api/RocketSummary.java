package api;

public record RocketSummary(
        String name,
        RocketStatus status
) {
    @Override
    public String toString() {
        return "RocketSummary{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
