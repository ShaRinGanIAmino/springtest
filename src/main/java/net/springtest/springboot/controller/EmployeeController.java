package net.springtest.springboot.controller;


import net.springtest.springboot.exception.ResourceNotFoundException;
import net.springtest.springboot.model.Employee;
import net.springtest.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    // Build create employee REST API
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        if (employee.getSupervisorId() != null) {
            Employee supervisor = employeeRepository.findById(employee.getSupervisorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor with id : "+employee.getSupervisorId()+"is not found !"));
        }
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeRepository.save(employee);
    }

    // Build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee>  getEmployeeByID(@PathVariable long id){
       Employee employee = employeeRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Employee with id : "+id+"is not found !"));
       return ResponseEntity.ok(employee);
    }

    //Build update employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id ,@RequestBody Employee employeeUpdates){
        Employee updatedEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id : "+id+"is not found !"));

        if (employeeUpdates.getSupervisorId() != null && employeeUpdates.getSupervisorId() == id) {
            return ResponseEntity.badRequest().body(updatedEmployee);
        }

        Employee supervisor = employeeUpdates.getSupervisorId() != null ?
                employeeRepository.findById(employeeUpdates.getSupervisorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Supervisor with id : " + employeeUpdates.getSupervisorId() + " is not found !")) :
                null;

        if (supervisor != null && supervisor.getSupervisorId() != null && supervisor.getSupervisorId() == id) {
            return ResponseEntity.badRequest().body(updatedEmployee);
        }

        updatedEmployee.setFirstName(employeeUpdates.getFirstName());
        updatedEmployee.setLastName(employeeUpdates.getLastName());
        updatedEmployee.setEmailId(employeeUpdates.getEmailId());
        updatedEmployee.setSupervisorId(employeeUpdates.getSupervisorId());

        employeeRepository.save(updatedEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id ){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id : "+id+"is not found !"));

        List<Employee> employeesWithDeletedEmployeeAsSupervisor = employeeRepository.findBySupervisorId(id);

        for (Employee e : employeesWithDeletedEmployeeAsSupervisor) {
            e.setSupervisorId(null);
        }

        employeeRepository.saveAll(employeesWithDeletedEmployeeAsSupervisor);

        employeeRepository.delete(employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
