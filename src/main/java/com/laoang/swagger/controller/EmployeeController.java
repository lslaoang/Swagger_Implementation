package com.laoang.swagger.controller;

import com.laoang.swagger.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    ConcurrentMap<String,Employee> empDirectory = new ConcurrentHashMap<>();

    @GetMapping("/")
    public List<Employee> getAllEmployee(){
        return new ArrayList<Employee>(empDirectory.values());
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id){
        return empDirectory.get(id);
    }

    @PostMapping("/")
    public Employee addEmployee(@RequestBody Employee employee){
        empDirectory.put(employee.getId(),employee);
        return employee;

    }
}
