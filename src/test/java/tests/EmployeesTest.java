package tests;

import Util.Generator;
import com.example.springboot.soap.interfaces.DeleteEmployeeResponse;
import com.example.springboot.soap.interfaces.EmployeeInfo;
import com.example.springboot.soap.interfaces.GetEmployeeByIdResponse;
import com.example.springboot.soap.interfaces.UpdateEmployeeResponse;
import data.Constants;
import db.models.Employee;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.AuthSteps;
import steps.EmployeesSteps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Employee Management System")
@Feature("Employee SOAP Web Services")
public class EmployeesTest {

    private EmployeesSteps employeesSteps;
    private Employee employee;
    AuthSteps authSteps;

    @BeforeClass
    public void beforeClass() {
        employeesSteps = new EmployeesSteps();
        authSteps = new AuthSteps();
    }

    @Test(priority = 1)
    @Story("Get Employee By ID Validation")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Inserts a new employee into DB using MyBatis mapper, sends getEmployeeById SOAP request, and validates returned employee details against database record.")
    public void addEmployee() {
        employee = employeesSteps.insertEmployeeDb(Generator.GenerateEmployee());
        System.out.println(employee.getEmployeeId());
        GetEmployeeByIdResponse response = employeesSteps.getEmployeeById(employee.getEmployeeId());
        assertThat(employee.getEmployeeId(), equalTo(response.getEmployeeInfo().getEmployeeId()));
        assertThat(employee.getName(), equalTo(response.getEmployeeInfo().getName()));
        assertThat(employee.getEmail(), equalTo(response.getEmployeeInfo().getEmail()));
    }

    @Test(priority = 2)
    @Story("Update Employee via SOAP Service")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Constructs SOAP update request body with updated details, sends request, ensures SUCCESS status, and validates updated details directly in database.")
    public void UpdateEmployeeFromService(){
        EmployeeInfo updatedEmployeeInfo = Generator.createEmployeeInfo();
        UpdateEmployeeResponse updateEmployeeResponse = employeesSteps.updateEmployee(updatedEmployeeInfo,employee.getEmployeeId());
        assertThat(updateEmployeeResponse.getServiceStatus().getStatus(),equalTo(Constants.SUCCESS_STATUS));

        employee = employeesSteps.getEmployeeDb(updatedEmployeeInfo.getEmployeeId());

        assertThat(employee.getName(),equalTo(updatedEmployeeInfo.getName()));
        assertThat(employee.getEmail(),equalTo(updatedEmployeeInfo.getEmail()));
    }

    @Test(priority = 3)
    @Story("Update Employee via Database Query")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Updates employee directly in database, then calls getEmployeeById SOAP service to validate updated details are correctly returned by SOAP service.")
    public void updateEmployeeFromDb() {
        long id =employee.getEmployeeId();
        employee = employeesSteps.updateEmployeeDb(Generator.GenerateEmployee(),id);
        GetEmployeeByIdResponse response = employeesSteps.getEmployeeById(employee.getEmployeeId());

        assertThat(employee.getEmployeeId(), equalTo(response.getEmployeeInfo().getEmployeeId()));
        assertThat(employee.getName(), equalTo(response.getEmployeeInfo().getName()));
        assertThat(employee.getEmail(), equalTo(response.getEmployeeInfo().getEmail()));
    }

    @Test(priority = 4)
    @Story("Delete Employee via SOAP Service")
    @Severity(SeverityLevel.NORMAL)
    @Description("Constructs SOAP delete request with employeeId, sends request, validates response status, and verifies employee row count in database decreased by 1.")
    public void deleteEmployeeFromService() {
        int countBefore = employeesSteps.getEmployeeCount();
        DeleteEmployeeResponse deleteEmployeeResponse = employeesSteps.deleteEmployee(employee.getEmployeeId());
        assertThat(deleteEmployeeResponse.getServiceStatus().getStatus(),equalTo(Constants.SUCCESS_STATUS));
        int countAfter = employeesSteps.getEmployeeCount();
        assertThat(countBefore - 1, equalTo(countAfter));
    }
}