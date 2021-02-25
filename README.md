# SER-322-Project

This program is designed to run in the same way as our last JDBC assignment. To run it from the command line, first compile all the java files using javac. Then run the program using the command line argument:

java -cp lib/mysql-connector-java-8.0.23.jar:src ser322.Main "jdbc:mysql://localhost/music?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&&serverTimezone=America/New_York" root root com.mysql.cj.jdbc.Driver

Unlike the JDBC assignment the user does not need to declare their query as part of their command line argument. Once inside the program one only has to follow the menu options. This program is designed assuming it will receive good input and only implements limited error handling. Please be kind. 
