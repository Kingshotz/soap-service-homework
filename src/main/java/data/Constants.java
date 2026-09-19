package data;

public class Constants {
    public static final String CONTINENTS_URL ="http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/ListOfContinentsByName",
    AFRICA = "Africa",
    ANTARCTICA = "Antarctica",
    ASIA = "Asia",
    EUROPE = "Europe",
    OCENANIA = "Ocenania",
    THE_AMERICAS = "The Americas",
    OCEANIA_CODE = "OC",
    EMPLOYEES_URL = "http://localhost:8087/ws",
    ADD_EMPLOYEE_ACTION = "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest",
    SUCCESS_STATUS = "SUCCESS",
    SUECESS_ADD_EMPLOYEE_MESSAGE = "Content Added Successfully",
    GET_EMPLOYEE_BY_ID_ACTION = "interfaces.soap.springboot.example.com/exampleSoapHttp/getEmployeeByIdRequest",
    UPDATE_EMPLOYEE_ACTION = "interfaces.soap.springboot.example.com/exampleSoapHttp/updateEmployeeRequest",
    SUECESS_UPDATE_EMPLOYEE_MESSAGE =  "Content Updated Successfully",
            DELETE_EMPLOYEE_ACTION = "interfaces.soap.springboot.example.com/exampleSoapHttp/deleteEmployeeRequest",
    SUECESS_DELETE_MESSAGE = "Content Deleted Successfully",
    BODY_FAULT_CODE = "SOAP-ENV:Server",
    BODY_FAULT_STRING = "Source must not be null",
    CONTINENTS_ROOT_PATH = "ArrayOftContinent",
    CONNECTION_STRING = "jdbc:h2:tcp://localhost:9093/./companyDB;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE;MODE=MSSQLServer;DATABASE_TO_UPPER=FALSE;CASE_INSENSITIVE_IDENTIFIERS=TRUE",
    DRIVER = "org.h2.Driver",
    AUTH_BASE_URL = "http://localhost:8086",
            SOAP_SERVICE_ERROR = "SOAP Service Error"                    ,
    HELLO_MESSAGE = "Hello",
    SUECESS_CHANGE_MESSAGE = "changed successfully";


    public static final int CONTINENTS_COUNT = 6;
}
