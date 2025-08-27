


Assumptions:
Rocket and Mission names are unique. We will trim, and compare lowercase, so there will be no case like "Dragon 1", and "DRAGON 1", or "dragon 1".


Notes:
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