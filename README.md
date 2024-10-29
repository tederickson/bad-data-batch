# bad-data-batch
Spring Batch and PostgreSQL database processing of bad data.

Use Liquibase to handle database migrations.

The two CSV files contain duplicates and invalid data.
Try different ways to handle importing invalid information.

# Movie Data Integrity
All movie ids are unique.  Movie ids are not sequential.

Some issues found with movies.csv with columns "id", "title", "year":
* Missing year - id=302152, title=Of Girls and Horses, year=NULL
* Titles with leading/trailing spaces - 271991," Novo, novo Vrijeme: Who Wants to be a President",2001
* Duplicate title and year:
  * 290669,"American Ultra",2015
  * 302322,"American Ultra",2015
* Spaces between title words
  * 13882,Airspeed,1998
  * 12875,"Air Speed",1998
* Changes in title capitalization 
  * 17033,"Hold back the dawn",1941
  * 11553,"Hold Back the Dawn",1941

The database contains 182,548 rows while movies.csv contains 184,784 lines.

The duplicate_movie table contains 2161 rows:

* 1,839 matching titles (multiple duplicates for the same title)
* 322 differing titles

There are 75 invalid movie entries.


```sql
SELECT movie_id, Count(*)
FROM   PUBLIC.duplicate_movie
GROUP  BY movie_id
HAVING Count(*) > 4
ORDER  BY Count(*) DESC, movie_id 
```

| movie id | count |
|----------|-------|
| 274080   | 8     |
| 18876    | 5     |
| 273324   | 5     |
| 273694   | 5     |
| 273702   | 5     |


# Actor And Director Data Integrity
Some issues found with actors_and_directors.csv with columns "movieId", "name", "role":
* Missing name - 300170," ",director
* Invalid movie id (movie id is not in MOVIE table) - 282774,"Ty Fenton",cast
* Changes in role capitalization:
  * 283055,"Andy Bausch",Cast
  * 143769,"Robbie Sublett",cast
* 12642 movies do not have a matching actor_and_director row.
```sql
SELECT Count(m.*)
FROM   PUBLIC.movie m
       LEFT JOIN PUBLIC.actor_and_director a
              ON a.movie_id = m.id
WHERE  a.movie_id IS NULL; 
```

The database contains 827,126 rows while actors_and_directors.csv contains 844,338 lines.

An ETL was performed on actors_and_directors.csv to add a unique id to each row.
The original file is found in actors_and_directors.original.csv.  
The change ensures that multiple imports produce the same results.

Database was modified to store duplicate movies in duplicate_movie.

The actor_and_director table now contains 836,771 rows.  There are 7,567 invalid entries.
The majority of the invalid entries do not contain a movie id found in the MOVIE table.

There are 20,399 rows that are not actor or director roles.

| Role  | Count  |
|---|---|
| cast  | 655134  |
| director  |  161238 |
| writer  |  6423 |
| screenwriter  |  2459 |
| producer  |  2264 |
| cinematographer  |  1328 |

# Code Structure
* client - calls the REST API methods.  Useful for other microservices and integration tests.
* config - Spring Batch config and processors plus Open API config
* controller - REST controllers that handle queries and the advice that handles Exceptions
* domain - REST DTO exposed to public domain
* dto - DTO for CSV data models
* exception - Business Exceptions that are caught and handled by the ControllerAdvice
* mapper - classes that convert a model type to another model type
* model - internal business models
* repository - interact with PostgreSQL database
* service - business logic 
* util - DRY principal: these classes consolidate methods used by multiple classes

# First Steps
Create file-upload database in local PostgreSQL.

actors_and_directors.csv has been updated to provide a primary key.  
All imports now produce the same result in the ACTOR_AND_DIRECTOR table.

# Run all tests
Run the following command in a terminal window:

```bash
mvn clean install
```

The default maven phases:
1. validate - validate the project is correct and all necessary information is available
2. compile - compile the source code of the project
3. test - test the compiled source code using a suitable unit testing framework. These tests should not require the code
   be packaged or deployed
4. package - take the compiled code and package it in its distributable format, such as a JAR.
5. verify - run any checks on results of integration tests to ensure quality criteria are met
6. **install** - install the package into the local repository, for use as a dependency in other projects locally
7. deploy - done in the build environment, copies the final package to the remote repository for sharing with other
   developers and projects.

# Run the Application
Run the following command in a terminal window:

```bash
mvn clean spring-boot:run
```

# Documentation
Swagger provides the API documentation of the REST endpoints.  
Run the application and point a browser to http://localhost:8080/swagger-ui.html
