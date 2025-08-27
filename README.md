


**Assumptions:**
Rocket and Mission names are unique. We will trim, and compare lowercase, so there will be no case like "Dragon 1", and "DRAGON 1", or "dragon 1".

Statuses logic is not crystal-clear, these are assumptions we are going to stick to:
- Rocket binding to mission: requirement - rocket has to be of IN_REPAIR or ON_GROUND status, mission status of SCHEDULED or PENDING. 
  Adding rocket to pending mission is a specific case, it is not basic, but lets add it. 
  Its when mission was already given a green light, but one of rockets is still IN_REPAIR, so its on hold anyways. 
  Lets say, someone might want to add a rocket to that mission, since its not truly launched yet, we are going to implement this case too.

- Rocket status manual change can only be done on IN_REPAIR status and it can be changed only to ON_GROUND (ready to launch). 
  To simplify the logic, lets expose only one method, repairRocket(). 
  This way we avoid all different cases that are logically invalid, making code cleaner, user flow as simple as it can be.

- Mission status change I. From SCHEDULED to PENDING- given at least one rocket is assigned to it and at least one is in repair.
  If it's more than one rocket, and none of them are in repair, we move straight to IN_PROGRESS.


- Mission status change II. From IN_PROGRESS to COMPLETED- all rockets are returned Their statuses are set to IN_REPAIR- 
 The <"IN_REPAIR" after mission end> part was not mentioned in requirements, but it makes sense, maintenance has to be done to any ship before re-launch.
 Sounds like we could simplify the process of launching, and expose simple launchMission() method. 
 This way we simplify the logic, reduce the complexity of all weird statuses, make it easier for user to operate the library.

 Same store with exposing finishMission() method, we would hide all the statuses complexity, weird validations, checks, make things as simple possible.
Manual changing of mission statuses backwards, from ENDED to SCHEDULED, or stuff like this sound like a joke, we are not going to expose such a broad methods.

- No re-assigning rockets to missions. 
  Once mission is assigned to rocket, it can't be re-assigned. Only once the mission is ended, and rocket is back from IN_REPAIR.


**Notes:**
1. Should I implement IDs?
   Will come in handy if we develop rename feature or want to implement database storage.  
   For this library it sounds like overkill, will add extra complexity. Lets keep it simple for now, no Ids.

   Since names are unique, they will serve as identifiers.

2. What about get a summary of rockets? Its not in requirements, but it might be really useful.
   List rockets, being able see all the names, and its statuses. It sounds like a an extra method we should add to the library logic.

3. Return type from contract? DTO should be a necessity, lets create Summaries (records).
   
4. How to store data? Simple collection or Map? 
   Let's keep performance out of the scope for now, go with simplicity. Collection will be enough.

5. What should happen when user is trying to create duplicate?
   Throw exception? Silently ignore? 
   Idempotent adds would be nice and clean, but it would hide bugs. 
   Lets go with clear feedback and throw exceptions. Adds little complexity but cases are clearly separated and user knows what's going on.

6. Cleaning up the InMemorySpaceXRocketsRepository
   There might be some serious lines of code that could be extracted to another component. 
   For starters, we could extract validation - null/bland names, and duplicates.
   This might significantly increase readability and maintainability.

7. What about re-assigning rockets to missions?
   Its not in requirements, but it might be useful in specific case, where mission has not yet started. It adds some complexity, lets skip it for now.
   So if rocket is already assigned to mission, we can't assign it to another one.

8. How to test invalid status scenarios? Since statuses are now internal, and not set manualy. We cannot fake invalid state this way. 
   Should we expose method to setStatus just for testing? Test class is in different package, we would have to move it to same package.
   Mock it in our own library? Not sure if it's a good idea, we would also need custom protected constructor just for mocking.
   Lets keep it simple, test logic achievable by api methods.

9. How to handle status changes? Domain class state change method? Alternative is protected setter, does not sound good.
   Let's encapsulate the logic, move it to domain.

10. What if rocket was on repair, mission was launched -> moved to PENDING. Upon repair, should it be automaticaly moved to IN_PROGRESS, 
    or should the mission status upgrade be manual?
    Lets go with manual, so the user gets to decide.
    Meaning we implement resumeMission() method, that will move PENDING Mission to IN_PROGRESS, if all rockets are ON_GROUND.

Todo: normalize equals, preferably no =='s. Normalize error messages.