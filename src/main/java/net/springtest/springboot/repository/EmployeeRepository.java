package net.springtest.springboot.repository;

import net.springtest.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findBySupervisorId(Long supervisorId);
}
