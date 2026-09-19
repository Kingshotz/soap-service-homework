package steps;

import Util.Marshall;
import Util.SoapServiceSender;
import Util.Unmarshall;
import com.example.springboot.soap.interfaces.*;
import data.Constants;
import db.models.Employee;
import io.restassured.path.xml.XmlPath;

import static config.DataBaseConfig.dbMapper;

public class EmployeesSteps {
    ObjectFactory  objectFactory;

    public EmployeesSteps() {
        objectFactory = new ObjectFactory();
    }
    public AddEmployeeResponse addEmployee(EmployeeInfo employeeInfo) {
        AddEmployeeRequest addEmployeeRequest = objectFactory.createAddEmployeeRequest();
        addEmployeeRequest.setEmployeeInfo(employeeInfo);
        String body = Marshall.marshallSoapRequest(addEmployeeRequest);
        String response = SoapServiceSender.send(Constants.EMPLOYEES_URL,
                        Constants.ADD_EMPLOYEE_ACTION,
                        body)
                        .body()
                        .asString();

        return Unmarshall.unmarshallResponse(response,AddEmployeeResponse.class);
    }
    public GetEmployeeByIdResponse getEmployeeById(long id){
        GetEmployeeByIdRequest request = objectFactory.createGetEmployeeByIdRequest();
        request.setEmployeeId(id);
        String body = Marshall.marshallSoapRequest(request);
        String response = SoapServiceSender.send(
                Constants.EMPLOYEES_URL,
                Constants.GET_EMPLOYEE_BY_ID_ACTION,
                body)
                .body()
                .asString();
        return Unmarshall.unmarshallResponse(response,GetEmployeeByIdResponse.class);
    }
    public UpdateEmployeeResponse updateEmployee(EmployeeInfo employeeInfo, long id){
        UpdateEmployeeRequest request = objectFactory.createUpdateEmployeeRequest();
        employeeInfo.setEmployeeId(id);
        request.setEmployeeInfo(employeeInfo);
        String body = Marshall.marshallSoapRequest(request);
        String response = SoapServiceSender.send(
                Constants.EMPLOYEES_URL,
                Constants.UPDATE_EMPLOYEE_ACTION,
                body)
                .body()
                .asString();

        return Unmarshall.unmarshallResponse(response,UpdateEmployeeResponse.class);
    }
    public DeleteEmployeeResponse deleteEmployee(long id){
        DeleteEmployeeRequest request = objectFactory.createDeleteEmployeeRequest();
        request.setEmployeeId(id);
        String body = Marshall.marshallSoapRequest(request);
        String response = SoapServiceSender.send(
                Constants.EMPLOYEES_URL,
                Constants.DELETE_EMPLOYEE_ACTION,
                body)
                .body()
                .asString();

        return Unmarshall.unmarshallResponse(response,DeleteEmployeeResponse.class);
    }
    public XmlPath getInvalidEmployee(long id){
        GetEmployeeByIdRequest request = objectFactory.createGetEmployeeByIdRequest();
        request.setEmployeeId(id);
        String body = Marshall.marshallSoapRequest(request);
        String response = SoapServiceSender.send(
                        Constants.EMPLOYEES_URL,
                        Constants.GET_EMPLOYEE_BY_ID_ACTION,
                        body)
                .body()
                .asString();
        return new XmlPath(response);
    }
    public Employee insertEmployeeDb(Employee employee){
        dbMapper().insertEmployee(employee);
        return employee;
    }
    public Employee getEmployeeDb(long id){
        Employee  employee = dbMapper().getEmployeeById(id);
        employee.setEmployeeId(id);
        return employee;
    }
    public Employee updateEmployeeDb(Employee employee,long id){
        employee.setEmployeeId(id);
        dbMapper().UpdateEmployee(employee);
        return employee;
    }
    public int getEmployeeCount(){
        return dbMapper().count();
    }
    

}
