package internal;

import api.MissionStatus;

import java.util.List;
import java.util.UUID;

public class Mission {
    private UUID id;
    private String name;
    private MissionStatus status;
    private List<Rocket> rockets;
}
