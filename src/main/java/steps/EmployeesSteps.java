package steps;

import Util.Marshall;
import Util.SoapServiceSender;
import Util.Unmarshall;
import com.example.springboot.soap.interfaces.*;
import data.Constants;
import io.restassured.path.xml.XmlPath;

/*
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:int="http://interfaces.soap.springboot.example.com">
   <soapenv:Header/>
   <soapenv:Body>
      <int:addEmployeeRequest>
         <int:employeeInfo>
            <int:employeeId>?</int:employeeId>
            <int:name>?</int:name>
            <int:department>?</int:department>
            <!--Optional:-->
            <int:phone>?</int:phone>
            <!--Optional:-->
            <int:address>?</int:address>
            <int:salary>?</int:salary>
            <int:email>?</int:email>
            <int:birthDate>?</int:birthDate>
         </int:employeeInfo>
      </int:addEmployeeRequest>
   </soapenv:Body>
</soapenv:Envelope>
* */
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
    public UpdateEmployeeResponse updateEmployee(EmployeeInfo employeeInfo){
        UpdateEmployeeRequest request = objectFactory.createUpdateEmployeeRequest();
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
}
