# bad-data-batch
Spring Batch and PostgreSQL processing bad data.

The two CSV files contain duplicates and invalid data.
Try different ways to handle importing invalid information.

# Movie Data Integrity
Some issues found with movies.csv:
* Missing year - id=302152, title=Of Girls and Horses, year=NULL
* Titles with leading/trailing spaces - 271991," Novo, novo Vrijeme: Who Wants to be a President",2001
* Duplicate title and year - id=302322, title=American Ultra, year=2015

The database contains 182,634 rows while movies.csv contains 184,784 lines.

# Actor And Director Data Integrity
Some issues found with actors_and_directors.csv:
* Missing name - 300170," ",director

The database contains 827,505 rows while actors_and_directors.csv contains 844,338 lines.

The majority of the discrepancy is due to removal of duplicate movies.
