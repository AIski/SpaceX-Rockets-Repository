package internal;

import api.MissionStatus;
import api.RocketStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Mission {
    private String name;
    private MissionStatus status = MissionStatus.SCHEDULED;
    private List<Rocket> rockets = new ArrayList<>();

    // Constructor
    Mission(String name) {
        this.name = name;
    }

    // Getters & Setters
    String getName() {
        return name;
    }

    MissionStatus getStatus() {
        return status;
    }

    List<Rocket> getRockets() {
        return rockets;
    }

    // Methods
    protected void launch() {
        if (this.getStatus() != MissionStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Mission to be launched must be of status SCHEDULED. Current status: " + this.getStatus()
            );
        }
        if (this.getRockets().isEmpty()) {
            throw new IllegalStateException("Mission must have at least one rocket assigned to it");
        }
        if (hasRocketInRepair()) {
            this.status = MissionStatus.PENDING;
        } else {
            // Should check here if all dragons can be launched?
            this.getRockets().forEach(Rocket::launch);
            this.status = MissionStatus.IN_PROGRESS;
        }
    }

    private boolean hasRocketInRepair() {
        return rockets.stream()
                .anyMatch(r -> r.getStatus() == RocketStatus.IN_REPAIR);
    }

    protected void resume() {
        if (this.getStatus() != MissionStatus.PENDING) {
            throw new IllegalStateException(
                    "Mission to be resumed must be of status PENDING. Current status: " + this.getStatus()
            );
        }
        if (hasRocketInRepair()) {
            throw new IllegalStateException("Mission cannot be resumed if any of its dragons are in repair");
        } else {
            // Should check here if all dragons can be launched?
            this.getRockets().forEach(Rocket::launch);
            this.status = MissionStatus.IN_PROGRESS;
        }
    }

    protected void end() {
        if (this.getStatus() != MissionStatus.IN_PROGRESS) {
            throw new IllegalStateException(
                    "Mission to be ended must be of status IN_PROGRESS. Current status: " + this.getStatus()
            );
        } else {

            // Should check here if all dragons can be launched?
            this.getRockets().forEach(Rocket::endMission);
            this.getRockets().clear();
            this.status = MissionStatus.ENDED;
        }
    }

    protected void assign(Rocket rocket) {
        if (this.getStatus() != MissionStatus.SCHEDULED && this.getStatus() != MissionStatus.PENDING) {
            throw new IllegalStateException(
                    "Mission to be assigned a rocket, must be of status SCHEDULED or PENDING. Current status: " + this.getStatus()
            );
        }
        if (rocket.getStatus() == RocketStatus.IN_SPACE) {
            throw new IllegalStateException("Rocket cannot be assigned to a mission if it is already in space");
        }
        if (this.rockets.contains(rocket)) {
            throw new IllegalStateException("Rocket cannot be assigned to a mission more than once");
        }
        if (Objects.nonNull(rocket.getMission())) {
            throw new IllegalStateException("Rocket cannot be assigned to a mission if it is already assigned to another mission");
        }

        rocket.assignMission(this);
        this.rockets.add(rocket);
    }
}
