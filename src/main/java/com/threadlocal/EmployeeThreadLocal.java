package com.threadlocal;

import com.model.Employee;

public class EmployeeThreadLocal {
    private static final ThreadLocal<Employee> employeeThreadLocal = new ThreadLocal<>();

    public  static void setEmployeeThreadLocal(Employee employee) {
        employeeThreadLocal.set(employee);
    }
    public static Employee getEmployeeThreadLocal() {
        return employeeThreadLocal.get();
    }

    public static void clear() {

        employeeThreadLocal.remove();
    }
}

