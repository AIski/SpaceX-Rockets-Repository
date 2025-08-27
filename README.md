


Assumptions:
Rocket and Mission names are unique.


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
