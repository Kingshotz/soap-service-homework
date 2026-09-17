package tests;

import Util.Util;
import com.example.springboot.soap.interfaces.*;
import data.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.path.xml.XmlPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.EmployeesSteps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;

@Epic("Employee Management System")
@Feature("Employee SOAP Web Services")
public class EmployeesTest {

    private EmployeesSteps employeesSteps;
    private EmployeeInfo employeeInfo;

    @BeforeClass
    public void beforeClass() {
        employeesSteps = new EmployeesSteps();
        employeeInfo = Util.createEmployeeInfo();
    }

    @Test(priority = 1)
    @Story("Add Employee")
    @Description("Verify that a new employee can be successfully created via SOAP request.")
    public void addEmployee() {
        AddEmployeeResponse response = employeesSteps.addEmployee(employeeInfo);

        assertThat(response.getServiceStatus().getMessage(), equalTo(Constants.SUECESS_ADD_EMPLOYEE_MESSAGE));
        assertThat(response.getServiceStatus().getStatus(), equalTo(Constants.SUCCESS_STATUS));
    }

    @Test(priority = 2)
    @Story("Get Employee By ID")
    @Description("Verify that employee details retrieved by ID match the created employee payload.")
    public void getEmployeeById() {
        GetEmployeeByIdResponse response = employeesSteps.getEmployeeById(employeeInfo.getEmployeeId());

        assertThat(response.getEmployeeInfo().getEmployeeId(), equalTo(employeeInfo.getEmployeeId()));
        assertThat(response.getEmployeeInfo().getName(), equalTo(employeeInfo.getName()));
        assertThat(response.getEmployeeInfo().getDepartment(), equalTo(employeeInfo.getDepartment()));
        assertThat(response.getEmployeeInfo().getPhone(), equalTo(employeeInfo.getPhone()));
        assertThat(response.getEmployeeInfo().getAddress(), equalTo(employeeInfo.getAddress()));
        assertThat(response.getEmployeeInfo().getSalary(), comparesEqualTo(employeeInfo.getSalary()));
        assertThat(response.getEmployeeInfo().getEmail(), equalTo(employeeInfo.getEmail()));
        assertThat(response.getEmployeeInfo().getBirthDate(), equalTo(employeeInfo.getBirthDate()));
    }

    @Test(priority = 3)
    @Story("Update Employee")
    @Description("Verify that an existing employee record can be updated with new details.")
    public void updateEmployee() {
        long id = employeeInfo.getEmployeeId();
        employeeInfo = Util.createEmployeeInfo();
        employeeInfo.setEmployeeId(id);

        UpdateEmployeeResponse response = employeesSteps.updateEmployee(employeeInfo);

        assertThat(response.getServiceStatus().getMessage(), equalTo(Constants.SUECESS_UPDATE_EMPLOYEE_MESSAGE));
        assertThat(response.getServiceStatus().getStatus(), equalTo(Constants.SUCCESS_STATUS));
    }

    @Test(priority = 4)
    @Story("Update Employee")
    @Description("Verify that previously updated employee details persist correctly in the system.")
    public void checkUpdate() {
        getEmployeeById();
    }

    @Test(priority = 5)
    @Story("Delete Employee")
    @Description("Verify that an employee can be soft/hard deleted from the system by ID.")
    public void deleteEmployee() {
        DeleteEmployeeResponse response = employeesSteps.deleteEmployee(employeeInfo.getEmployeeId());

        assertThat(response.getServiceStatus().getMessage(), equalTo(Constants.SUECESS_DELETE_MESSAGE));
        assertThat(response.getServiceStatus().getStatus(), equalTo(Constants.SUCCESS_STATUS));
    }

    @Test(priority = 6)
    @Story("Delete Employee")
    @Description("Verify that fetching a deleted employee returns a proper SOAP Fault response.")
    public void checkDelete() {
        XmlPath response = employeesSteps.getInvalidEmployee(employeeInfo.getEmployeeId());

        assertThat(
                response.getString("Envelope.Body.Fault.faultcode"),
                equalTo(Constants.BODY_FAULT_CODE)
        );

        assertThat(
                response.getString("Envelope.Body.Fault.faultstring"),
                equalTo(Constants.BODY_FAULT_STRING)
        );
    }
}