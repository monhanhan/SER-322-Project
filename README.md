# SER-322-Project

## Database Setup
1. Run the `create_tables.sql` script found in the `database` folder at the top level of the project
2. Copy all the sample data files from `/database/sample_data/` and paste them in your computer's mysql directory
3. Run the `load_data.sql` script

**DEBUGGING INSTRUCTIONS**

When running this script, you may get an error similar to this one:

    Can't get stat of '/usr/local/var/mysql/music/albums.txt' (OS errno 2 - No such file or directory)

This means you need to copy the *.txt files to the directory specified in the error.

In this example, that might look something like this:

`$> cp ./*.txt /usr/local/var/mysql/music/`


## Running the Program
This program is designed to run in the same way as our last JDBC assignment. To run it from the command line, first compile all the java files using javac. Then run the program using the command line argument:

`java -cp lib/mysql-connector-java-8.0.23.jar:src ser322.Main "jdbc:mysql://localhost/music?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&&serverTimezone=America/New_York" root root com.mysql.cj.jdbc.Driver`

Unlike the JDBC assignment the user does not need to declare their query as part of their command line argument. Once inside the program one only has to follow the menu options. This program is designed assuming it will receive good input and only implements limited error handling. Please be kind. 
