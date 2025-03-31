# Zoho Employee Management System

## Goal
- **Admin (HR):** Can view all employees in the company.
- **Manager:** Can view all employees under their management.
- **Employee:** Can view only their own details.

## Database Schema
### Employee Table
```
(empid, accountno, name, email, password, place, dob, gender, roles, phoneno, managerID)
```

### Role-based Access Control
- **Admin**: Read, delete, update, and add employees.
- **Manager**: Read and update employees under their `managerID`.
- **Employee**: Update only their own data.

---
## Project Structure

### **Resource Folder** (Contains endpoints for employee data and login)
- **EmployeeResource**: Handles employee-related endpoints.
- **AuthResource**: Handles employee login.

### **Listener Folder** (Loads metadata when the server starts)
- **ServerListener**: Contains listener code.
- **ListenerConf**: Reads configuration files and sets up the database schema if it does not exist.

### **Validation Folder** (Validates employee data and performs checks)

### **Model Folder** (Stores employee data as class objects)

### **Filter Folder** (Filters endpoints based on role authorization)
- **AuthFilter**: Validates endpoint requests by checking authentication tokens.
- **AdminFilter**: Authorizes admin role access.
- **AdminAndManagerFilter**: Authorizes both admin and manager roles.

### **Exception Folder** (Handles custom exceptions)

### **DAO Folder** (Performs database operations)
- **EmployeeDAO**: Performs CRUD operations on the employee table.
- **DbSetupDAO**: Handles database creation, invoked by listeners.

### **DbConnection Folder** (Manages database connection)

### **Cache Folder** (Stores employee data in cache)
- **EmployeeCache**: Maps tokens to employee IDs and corresponding employee data models.

### **ThreadLocal Folder** (Assigns employee data to the request thread)

### **DBParser Folder** (Parses JSON files and converts data into `CREATE TABLE` queries)

### **Annotation Folder** (Contains custom annotations)
- These annotations are used to map endpoints to appropriate filters.

### **Utils Folder** (Performs utility functions)
- Converts string date data to long values.

- ### **Logger Folder** (used to Log Operation performed by the Application)
- **ApplicationLogger**:used to log DAO Operation
- **AccessLogger**:used to log Request recieved by the Application
  


