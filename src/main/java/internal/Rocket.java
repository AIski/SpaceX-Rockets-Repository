package internal;

import api.RocketStatus;

class Rocket {
    private String name;
    private RocketStatus status = RocketStatus.ON_GROUND;
    private Mission mission;

    // Constructor
    public Rocket(String name) {
        this.name = name;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public RocketStatus getStatus() {
        return status;
    }

    public Mission getMission() {
        return mission;
    }
}
