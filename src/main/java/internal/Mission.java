package internal;

import api.MissionStatus;

import java.util.ArrayList;
import java.util.List;

class Mission {
    private String name;
    private MissionStatus status = MissionStatus.SCHEDULED;
    private List<Rocket> rockets = new ArrayList<>();

    // Constructor
    public Mission(String name) {
        this.name = name;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public List<Rocket> getRockets() {
        return rockets;
    }
}
