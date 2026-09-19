package db.mapper;

import db.models.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface EmployeeMapper {
    @Insert("INSERT INTO employee (name, email, phone, address, department, salary, birth_date) VALUES (#{name}, #{email}, #{phone}, #{address}, #{department}, #{salary}, #{birthDate})")
    @Options(useGeneratedKeys = true, keyProperty = "employeeId")
    void insertEmployee(Employee employee);

    @Select("SELECT * FROM employee WHERE employee_id = #{employeeId}")
    Employee getEmployeeById(Long employeeId);

    @Update("UPDATE employee SET name = #{name}, email = #{email}, phone = #{phone}, address = #{address}, department = #{department}, salary = #{salary}, birth_date = #{birthDate} WHERE employee_id = #{employeeId}")
    void UpdateEmployee(Employee employee);
    @Select("SELECT COUNT(*) FROM employee")
    int count();

}
