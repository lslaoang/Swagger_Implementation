package com.laoang.swagger.controller;

import com.laoang.swagger.model.Employee;
import org.apache.hadoop.yarn.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@RestController
@RequestMapping("api")
public class EmployeeController {

    /*
    *CocurrentMap to accommodate different Threads
     */
    ConcurrentMap<Long,Employee> empDirectory = new ConcurrentHashMap<>();

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

    @PutMapping("/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id)
            throws ResourceNotFoundException {

        //Validation if ID is null
        if(empDirectory.get(id)!=null){
            Employee emp = empDirectory.get(id);
            emp.setId(employee.getId()).setName(employee.getName()).setDepartment(employee.getDepartment());
            empDirectory.put(id,emp);
            return empDirectory.get(id);
        }else{
            throw  new ResourceNotFoundException("Employee "+id + " not found");
        }
    }

    @DeleteMapping("{id}")
    public Employee deleteEmployee(@PathVariable(value = "id") Long id){
         return empDirectory.remove(id);
    }
}
