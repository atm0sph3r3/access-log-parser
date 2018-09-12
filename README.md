# Purpose
This repository contains a Java application that parses a pipe-delimited server access log file and does two things:

* Stores the logs in a MySQL database on the first run
* Using provided command-line arguments, outputs whether a particular IP has accessed the server more than a given number of times with a given period of time.

# Docker container for testing
    docker run --rm -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password -d mysql
    
## Connecting to database via CLI
    mysql -u root -p -P 3306 —protocol=tcp

# Generate jooq classes from XML
    java --add-modules java.xml.bind -classpath jooq-3.11.4.jar:jooq-meta-3.11.4.jar:jooq-codegen-3.11.4.jar:mysql-connector-java-8.0.12.jar:. org.jooq.codegen.GenerationTool jooq.xm

# Sample access log input
    2017-01-01 00:00:11.763|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"
    2017-01-01 00:00:21.164|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"
    
# MySQL access log schema
```
+-----------+---------------+------+-----+---------+----------------+
| Field     | Type          | Null | Key | Default | Extra          |
+-----------+---------------+------+-----+---------+----------------+
| ip        | varchar(36)   | NO   |     | NULL    |                |
| request   | varchar(1024) | NO   |     | NULL    |                |
| status    | int(11)       | NO   |     | NULL    |                |
| userAgent | varchar(2024) | NO   |     | NULL    |                |
| id        | int(11)       | NO   | PRI | NULL    | auto_increment |
| date      | timestamp     | NO   |     | NULL    |                |
+-----------+---------------+------+-----+---------+----------------+
```    
   
# MySQL violations schema
```
+---------+---------------+------+-----+---------+----------------+
| Field   | Type          | Null | Key | Default | Extra          |
+---------+---------------+------+-----+---------+----------------+
| id      | int(11)       | NO   | PRI | NULL    | auto_increment |
| ip      | varchar(36)   | NO   |     | NULL    |                |
| comment | varchar(1024) | NO   |     | NULL    |                |
+---------+---------------+------+-----+---------+----------------+

```
 
# Example command line usage
    java -cp "parser.jar" com.ef.Parser --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --accesslog=/path/to/file

# Sample application output
    192.168.77.101 was observed at least 100 times between 2017-01-01 13:00:00.0 and 2017-01-01 14:00:00.0.
    192.168.228.188 was observed at least 100 times between 2017-01-01 13:00:00.0 and 2017-01-01 14:00:00.0.
    
# Sample access log in MySQL
```
 +----------------+------------------+--------+-------------------------------------------------------------------------------------------------------------------------------------------+--------+---------------------+
   | ip             | request          | status | userAgent                                                                                                                                 | id     | date                |
   +----------------+------------------+--------+-------------------------------------------------------------------------------------------------------------------------------------------+--------+---------------------+
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657505 | 2017-01-01 13:00:42 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657514 | 2017-01-01 13:00:49 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657626 | 2017-01-01 13:02:36 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657650 | 2017-01-01 13:02:49 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657667 | 2017-01-01 13:02:58 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657669 | 2017-01-01 13:02:59 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657670 | 2017-01-01 13:02:59 |
   | 192.168.77.101 | "GET / HTTP/1.1" |    200 | "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Mobile Safari/537.36" | 657756 | 2017-01-01 13:03:59 |
```
# Sample violations written to MySQL database
```
+----+-----------------+----------------------------------------------------------------------------------------------------------+
| id | ip              | comment                                                                                                  |
+----+-----------------+----------------------------------------------------------------------------------------------------------+
|  1 | 192.168.77.101  | 192.168.77.101 was observed at least 100 times between 2017-01-01 13:00:00.0 and 2017-01-01 14:00:00.0.  |
|  2 | 192.168.228.188 | 192.168.228.188 was observed at least 100 times between 2017-01-01 13:00:00.0 and 2017-01-01 14:00:00.0. |
```

# Sample SQL query for verification
    SELECT a.ip, COUNT(*) as count FROM access as a WHERE a.date >= '2017-01-01.13:00:00' and a.date <= '2017-01-01.14:00:00' GROUP BY ip HAVING count > 100;

# Sample SQL statement to query for requests by a given IP
    SELECT * FROM access as a WHERE a.date >= '2017-01-01.13:00:00' and a.date <= '2017-01-01.14:00:00' and a.ip = '192.168.77.101';
