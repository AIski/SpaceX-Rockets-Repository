# SpaceX Rockets Management Library
A Java library for managing SpaceX rockets and missions with a clean, intuitive API.
## Overview
This library provides a streamlined way to manage rockets and missions for space operations. 
It handles the complete lifecycle of rockets and missions, from creation to completion, with proper status tracking and validation.
## Features
- **Rocket Management**:
    - Create and track rockets
    - Monitor rocket status (ON_GROUND, IN_REPAIR, IN_MISSION)
    - Repair rockets after missions

- **Mission Management**:
    - Create missions
    - Assign rockets to missions
    - Launch, resume, and complete missions
    - Automatic status tracking (SCHEDULED, PENDING, IN_PROGRESS, COMPLETED)

- **Status Transitions**:
    - Automatic status management based on api operations
    - Proper validation to prevent invalid state transitions

## Installation
### Maven
Add the JitPack repository to your build file:
``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the dependency:
``` xml
<dependency>
    <groupId>com.github.AIski</groupId>
    <artifactId>SpaceX-Rockets-Repository</artifactId>
    <version>v1.0.0</version>
</dependency>

<!-- Optional: Add SLF4J for logging -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.17</version>
    <scope>compile</scope>
</dependency>
```
### Gradle
Add the JitPack repository to your build file:
``` groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:
``` groovy
dependencies {
    implementation 'com.github.AIski:SpaceX-Rockets-Repository:v1.0.0'
    implementation 'org.slf4j:slf4j-simple:2.0.17'
}
```
## Quick Start
``` java
import api.SpaceXRocketsRepository;
import internal.InMemorySpaceXRocketsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickStartExample {
    private static final Logger log = LoggerFactory.getLogger(QuickStartExample.class);

    public static void main(String[] args) {
        // Initialize the repository
        SpaceXRocketsRepository repository = new InMemorySpaceXRocketsRepository();
        
        // Create rockets
        repository.addRocket("Falcon 9");
        repository.addRocket("Falcon Heavy");
        log.info("Created rockets: Falcon 9 and Falcon Heavy");
        
        // Create a mission
        repository.addMission("Mars Exploration");
        log.info("Created mission: Mars Exploration");
        
        // Assign rockets to mission
        repository.assignRocketToMission("Falcon 9", "Mars Exploration");
        repository.assignRocketToMission("Falcon Heavy", "Mars Exploration");
        log.info("Assigned rockets to Mars Exploration mission");
        
        // Launch the mission
        repository.launchMission("Mars Exploration");
        log.info("Mars Exploration mission launched");
        
        // Complete the mission
        repository.endMission("Mars Exploration");
        log.info("Mars Exploration mission completed");
        
        // Repair rockets after mission
        repository.repairRocket("Falcon 9");
        repository.repairRocket("Falcon Heavy");
        log.info("Rockets repaired and ready for next mission");
        
        // Get summaries
        log.info("Mission summaries: {}", repository.getMissionSummaries());
        log.info("Rocket summaries: {}", repository.getRocketSummaries());
    }
}
```
## Detailed Example
Here's a more comprehensive example demonstrating various features:
``` java
import api.SpaceXRocketsRepository;
import internal.InMemorySpaceXRocketsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetailedExample {
    private static final Logger log = LoggerFactory.getLogger(DetailedExample.class);

    public static void main(String[] args) {
        log.info("Starting SpaceXRocketsRepository");
        SpaceXRocketsRepository repository = new InMemorySpaceXRocketsRepository();

        // Create missions and rockets
        repository.addMission("Mars");
        repository.addRocket("Dragon 1");
        repository.addRocket("Dragon 2");
        log.info("Created Mars mission and Dragon rockets");
        logRocketsAndMissions(repository);

        // Single rocket mission
        repository.addMission("Vertical Landing");
        repository.assignRocketToMission("Dragon 1", "Vertical Landing");
        repository.launchMission("Vertical Landing");
        log.info("Launched Vertical Landing mission with Dragon 1");
        logRocketsAndMissions(repository);

        repository.endMission("Vertical Landing");
        log.info("Completed Vertical Landing mission");
        logRocketsAndMissions(repository);

        repository.repairRocket("Dragon 1");
        log.info("Repaired Dragon 1 rocket");
        logRocketsAndMissions(repository);

        // Multi-rocket mission
        repository.addMission("Double Landing");
        repository.assignRocketToMission("Dragon 1", "Double Landing");
        repository.assignRocketToMission("Dragon 2", "Double Landing");
        repository.launchMission("Double Landing");
        log.info("Launched Double Landing mission with both Dragons");
        logRocketsAndMissions(repository);

        repository.endMission("Double Landing");
        log.info("Completed Double Landing mission");
        logRocketsAndMissions(repository);

        // Pending mission example
        repository.addMission("Luna1");
        repository.assignRocketToMission("Dragon 1", "Luna1");
        repository.assignRocketToMission("Dragon 2", "Luna1");
        repository.launchMission("Luna1");
        log.info("Luna1 mission launched with rockets needing repair - should be PENDING");
        logRocketsAndMissions(repository);

        repository.repairRocket("Dragon 1");
        repository.repairRocket("Dragon 2");
        log.info("Repaired both dragons, mission still needs to be resumed");
        logRocketsAndMissions(repository);

        // Use resumeMission() to continue a PENDING mission
        repository.resumeMission("Luna1");
        log.info("Resumed Luna1 mission - should now be IN_PROGRESS");
        logRocketsAndMissions(repository);
    }

    private static void logRocketsAndMissions(SpaceXRocketsRepository repository) {
        log.info("Mission summaries: {}", repository.getMissionSummaries());
        log.info("Rocket summaries: {}", repository.getRocketSummaries());
        log.info("---");
    }
}
```
## Core Concepts
### Rocket Status
- **ON_GROUND**: Ready for mission assignment
- **IN_REPAIR**: Under maintenance, cannot be assigned to missions
- **IN_MISSION**: Currently assigned to an active mission

### Mission Status
- **SCHEDULED**: Initial status, can accept rocket assignments
- **PENDING**: Mission has at least one rocket in repair
- **IN_PROGRESS**: Mission is actively running with all rockets ready
- **COMPLETED**: Mission has been successfully completed

## API Reference
### Rocket Operations
- `addRocket(String name)`: Create a new rocket
- `repairRocket(String name)`: Set a rocket's status to ON_GROUND (from IN_REPAIR)
- `getRocketSummary(String name)`: Get detailed information about a rocket
- `getRocketSummaries()`: Get summaries of all rockets

### Mission Operations
- `addMission(String name)`: Create a new mission
- `assignRocketToMission(String rocketName, String missionName)`: Assign a rocket to a mission
- `launchMission(String name)`: Launch a mission (changes status to IN_PROGRESS or PENDING)
- `resumeMission(String name)`: Resume a PENDING mission if all rockets are ready
- `endMission(String name)`: Complete a mission and set all rockets to IN_REPAIR
- `getMissionSummary(String name)`: Get detailed information about a mission
- `getMissionSummaries()`: Get summaries of all missions

## Design Decisions
- Rocket and mission names are unique identifiers
- Names are case-insensitive for comparison purposes
- Strict validation ensures operations maintain system integrity
- Simple API hides complexity of status management
- In-memory storage for simplicity

## Requirements
- Java 17
- SLF4J for logging (optional but recommended)

## License
[MIT License](LICENSE)



## Design Decisions and Development Journal
This section outlines the thought process, assumptions, and decisions made during the development of this library.
### Core Assumptions
- **Unique Identifiers**: Rocket and Mission names are unique and serve as identifiers
    - Names are normalized (trimmed and compared case-insensitive)
    - No duplicate names like "Dragon 1" and "DRAGON 1" are allowed

- **Rocket Status Transitions**:
    - Manual status changes limited to `repairRocket()` (IN_REPAIR → ON_GROUND)
    - Other status changes happen automatically as part of mission operations
    - After mission completion, rockets automatically enter IN_REPAIR status

- **Mission Status Logic**:
    - SCHEDULED → PENDING: When at least one assigned rocket is in repair
    - SCHEDULED → IN_PROGRESS: When all assigned rockets are ready (ON_GROUND)
    - IN_PROGRESS → COMPLETED: When mission is explicitly ended
    - PENDING → IN_PROGRESS: Manual resumption after all rockets are repaired

- **Assignment Rules**:
    - Rockets can only be assigned to one mission at a time
    - Reassignment only possible after mission completion and rocket repair

### Design Considerations
1. **Simple Identifiers vs. IDs**:
    - Chose to use names as unique identifiers for simplicity
    - Avoids additional complexity of ID management
    - Trade-off: Makes renaming entities more challenging

2. **API Design Philosophy**:
    - Focused on exposing simple, logical operations (e.g., `launchMission()`)
    - Complex status transitions are handled internally
    - Prevents invalid state transitions by design

3. **Error Handling Approach**:
    - Clear exceptions for invalid operations rather than silent failures
    - Helps identify issues early during development
    - Provides explicit feedback on operation failures

4. **Data Storage**:
    - Simple collection-based storage for in-memory implementation
    - Prioritized simplicity over performance optimizations
    - Suitable for the expected scale of operations

5. **Code Organization**:
    - Separated validation logic into dedicated components
    - Encapsulated domain logic within entities
    - Clear separation between API and implementation

### Technical Challenges Solved
- **Status Management**: Created a system where status changes follow business rules automatically
- **Association Handling**: Implemented bidirectional relationship between rockets and missions
- **Validation**: Built comprehensive validation to prevent invalid operations
- **Testing**: Developed comprehensive tests for all possible workflows

### Development Process
Several iterations improved the design:
1. Normalized comparison operations and error messages
2. Simplified testing approach to focus on API-level tests
3. Revised access modifiers for better encapsulation
4. Extracted nameValidation, mapping, lookup logic to dedicated components.
5. Implemented proper equals/hashCode for entity comparison
7. Fixed a bug where rocket-mission links weren't properly broken after mission completion

The final design prioritizes a clean API, proper encapsulation, and robustness against invalid operations.
