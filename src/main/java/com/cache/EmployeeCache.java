package com.cache;

import com.model.Employee;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeCache {
    private static final Map<String, Long> employeeTokenMapper = new ConcurrentHashMap<>();
    private static final Map<Long, Employee> employeeCache = new ConcurrentHashMap<>();
    public static void putEmployee(String token, Employee emp) {

        if (!employeeCache.containsKey(emp.getId())) {
            employeeCache.put(emp.getId(), emp);
        }
        employeeTokenMapper.put(token, emp.getId());


    }

    public static Employee getEmployee(String token) {
        Long empID = employeeTokenMapper.get(token);
        if (empID == null) {
            return null;
        }
        return employeeCache.get(empID);
    }

    public static void removeEmployee(String token) {
        Long empID = employeeTokenMapper.get(token);
        if (empID != null) {
            employeeCache.remove(empID);
            employeeTokenMapper.remove(token);
        }
    }




}
