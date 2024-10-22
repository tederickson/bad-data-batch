# bad-data-batch
Spring Batch and PostgreSQL database processing of bad data.

Use Liquibase to handle database migrations.

The two CSV files contain duplicates and invalid data.
Try different ways to handle importing invalid information.

# Movie Data Integrity
Some issues found with movies.csv:
* Missing year - id=302152, title=Of Girls and Horses, year=NULL
* Titles with leading/trailing spaces - 271991," Novo, novo Vrijeme: Who Wants to be a President",2001
* Duplicate title and year - id=302322, title=American Ultra, year=2015
* Spaces between words
* Changes in capitalization

The database contains 182,548 rows while movies.csv contains 184,784 lines.
The duplicate_movie table contains 2161 rows.
There are 75 invalid movie entries.

# Actor And Director Data Integrity
Some issues found with actors_and_directors.csv:
* 272 different roles instead of the expected two roles: actor, director
* Missing name - 300170," ",director
* Invalid movie id - 282774,"Ty Fenton",cast

The database contains 827,126 rows while actors_and_directors.csv contains 844,338 lines.

Database modified to store duplicate movies.
The actor_and_director table now contains 836771 rows.  There are 7567 invalid entries.

There are 675,916 rows that are not actor or director roles.

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
1. Delete file-upload database 
2. Create file-upload database

actors_and_directors.csv has been updated to provide a primary key.  All imports now produce the same result.

# Run all tests
Run the following command in a terminal window:

```bash
mvn clean install
```

# Run the Application
Run the following command in a terminal window:

```bash
mvn clean spring-boot:run
```

# Documentation
Swagger provides the API documentation of the REST endpoints.  
Run the application and point a browser to http://localhost:8080/swagger-ui.html
