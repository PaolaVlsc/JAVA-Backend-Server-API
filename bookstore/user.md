# Test the Login API

Open Postman and create a new request.
Set the request type to POST.
Enter the API URL: http://localhost:8080/api/login.
In the Body tab, select raw and choose JSON as the content type.
Add the following JSON payload (replace with actual user data):
json
Copy code
{
"username": "testuser01",
"password": "password123"
}

# database

mysql> use bookstore;
Database changed
mysql> show tables;
+---------------------+
| Tables_in_bookstore |
+---------------------+
| user |
+---------------------+
1 row in set (0.00 sec)

mysql> describe user;
+------------+--------------+------+-----+-------------------+-------------------+
| Field | Type | Null | Key | Default | Extra |
+------------+--------------+------+-----+-------------------+-------------------+
| id | bigint | NO | PRI | NULL | auto_increment |
| username | varchar(255) | YES | UNI | NULL | |
| password | varchar(255) | NO | | NULL | |
| created_at | timestamp | YES | | CURRENT_TIMESTAMP | DEFAULT_GENERATED |
+------------+--------------+------+-----+-------------------+-------------------+
4 rows in set (0.00 sec)

mysql> select \* from user;
+----+------------+--------------------------------------------------------------+---------------------+
| id | username | password | created_at |
+----+------------+--------------------------------------------------------------+---------------------+
| 1 | testuser | $2a$10$eBqjSMzZcODyGl5IjXvAeOgWaVfZ8O8ThAS98RAURUwSPH8UmHyPa | 2024-12-26 13:18:46 |
| 2 | user | $2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36AX3udmyo7VoM0Lk4eNx2W | 2024-12-26 13:22:31 |
| 3 | tester | $2a$10$LuaP59riygmRcyANbUQbd.g52hrAafRWkJR.Yf128u4uG8.q3/YWO | 2024-12-26 13:39:00 |
| 5 | john_doe | securepassword | 2024-12-26 13:50:24 |
| 7 | admin | admin123 | 2024-12-27 12:10:57 |
| 8 | testuser01 | password123 | 2024-12-27 12:11:26 |
+----+------------+--------------------------------------------------------------+---------------------+
6 rows in set (0.00 sec)

mysql>
