## 1. GROUP SERVICE [Port: 8081]

## 2. AUTH SERVICE [Port: 8082]

## 3. CLASSROOM SERVICE [Port: 8083]
classroom service:
1 classroom have multiple attendance record that keep track of the attendance (start open attendance - end closed attendance). 

classroom service have jobs to:
1. CRUD classroom table
2. attendance system that check student JWT validity, student group role permission (whether or not he has access to this class using bitmask operatoin) 
and attendance logic which doesnt allow student to attend before opened attendance time and after closed attendance time.

## 4. PROFILE SERVICE [Port: 8084]
classroom service:
manage user profile creation (student / teacher)
create user profile will be called when user register in **Auth Service**

## 5. DATABASE SERVICE [Port: 9090]
database service: (shared-database)
expose the H2 database to TCP so that all service can connect to this DB
