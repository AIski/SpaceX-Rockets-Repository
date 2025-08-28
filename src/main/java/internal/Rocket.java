package internal;

import api.RocketStatus;

import java.util.Objects;


class Rocket {
    private String name;
    private RocketStatus status = RocketStatus.ON_GROUND;
    private Mission mission;

    // Constructor
    Rocket(String name) {
        this.name = name;
    }

    // Getters & Setters
    String getName() {
        return name;
    }

    RocketStatus getStatus() {
        return status;
    }

    Mission getMission() {
        return mission;
    }

    // Methods
    void repair() {
        if (this.getStatus() != RocketStatus.IN_REPAIR) {
            throw new IllegalStateException(
                    "Rocket to be repaired must be of status IN_REPAIR. Current status: " + this.getStatus()
            );
        }
        this.status = RocketStatus.ON_GROUND;
    }

    void launch() {
        if (this.getStatus() != RocketStatus.ON_GROUND) {
            throw new IllegalStateException(
                    "Rocket to be launched must be of status ON_GROUND. Current status: " + this.getStatus()
            );
        }
        this.status = RocketStatus.IN_SPACE;
    }

    void endMission() {
        if (this.getStatus() != RocketStatus.IN_SPACE) {
            throw new IllegalStateException(
                    "Rocket to end mission must be of status IN_SPACE. Current status: " + this.getStatus()
            );
        }
        this.mission = null;
        this.status = RocketStatus.IN_REPAIR;
    }

    void assignMission(Mission mission) {
        if (Objects.nonNull(this.mission)) {
            throw new IllegalStateException(
                    "Mission cannot be assigned to a rocket if it is already assigned to another mission"
            );
        }
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rocket)) return false;
        Rocket other = (Rocket) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
