package tests;

import Util.Generator;
import auth.api.model.*;
import com.example.springboot.soap.interfaces.GetEmployeeByIdResponse;
import data.Constants;
import db.models.Employee;
import db.models.User;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.AuthSteps;
import steps.EmployeesSteps;
import steps.UsersSteps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("SOAP and REST Integration Flow")
@Feature("User Lifecycle & Authentication Management")
public class IntegrationTest {
    private AuthSteps authSteps;
    private EmployeesSteps employeesSteps;
    private Employee employee;
    private AuthenticationResponse authenticationResponse;
    private RegisterUserRequest registerUserRequest;
    private UsersSteps usersSteps;

    @BeforeClass
    public void beforeClass() {
        authSteps = new AuthSteps();
        employeesSteps = new EmployeesSteps();
        usersSteps = new UsersSteps();
    }

    @Test(priority = 1)
    @Story("Negative Registration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Call POST /api/v2/auth/register with a random email (not existing in SOAP) and validate that the REST Service returns SOAP error.")
    public void invalidRegister() {
        Response response = authSteps.register(Generator.generateRegisterUserRequest());
        assertThat(response.getStatusCode(), equalTo(502));

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.getError(), equalTo(Constants.SOAP_SERVICE_ERROR));
    }

    @Test(priority = 2)
    @Story("Valid Registration")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Call POST /api/v2/auth/register using the same email used in the addEmployee request. Extract and store the returned JWT token.")
    public void ValidRegister() {
        employee = employeesSteps.insertEmployeeDb(Generator.GenerateEmployee());

        registerUserRequest = Generator.generateRegisterUserRequest();
        registerUserRequest.setEmail(employee.getEmail());
        Response response = authSteps.register(registerUserRequest);
        assertThat(response.getStatusCode(), equalTo(200));

        authenticationResponse = response.as(AuthenticationResponse.class);
    }

    @Test(priority = 3)
    @Story("Protected Resource Access")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Call GET /api/v1/admin/resource using the JWT token and validate the response.")
    public void getAdminResource() {
        Response response = authSteps.getAdminResource(authenticationResponse.getAccessToken());
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.asString(), containsString(Constants.HELLO_MESSAGE));
    }

    @Test(priority = 4)
    @Story("Change Password")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Call POST /api/v1/user/change-password and validate the result.")
    public void changePassword() {
        String newPass = Generator.generatePassword();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest()
                .oldPassword(registerUserRequest.getPassword())
                .newPassword(newPass);
        registerUserRequest.setPassword(newPass);

        Response response = authSteps.changePassword(authenticationResponse.getAccessToken(), changePasswordRequest);
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.asString(), containsString(Constants.SUECESS_CHANGE_MESSAGE));
    }

    @Test(priority = 5)
    @Story("Logout")
    @Severity(SeverityLevel.NORMAL)
    @Description("Call POST /api/v2/auth/logout.")
    public void logOut() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest()
                .refreshToken(authenticationResponse.getRefreshToken());
        Response response = authSteps.logOut(refreshTokenRequest);
        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test(priority = 6)
    @Story("Re-authentication")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Call POST /api/v2/auth/authenticate, validate the response, and store the new JWT token.")
    public void authenticate() {
        LoginRequest loginRequest = new LoginRequest()
                .email(registerUserRequest.getEmail())
                .password(registerUserRequest.getPassword());

        Response response = authSteps.authenticate(loginRequest);
        assertThat(response.getStatusCode(), equalTo(200));

        authenticationResponse = response.as(AuthenticationResponse.class);
    }

    @Test(priority = 7)
    @Story("Change Email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Call POST /api/v1/user/change-email to change the user's email.")
    public void changeEmail() {
        ChangeEmailRequest changeEmailRequest = new ChangeEmailRequest()
                .newEmail(Generator.generateEmail());
        Response response = authSteps.changeEmail(authenticationResponse.getAccessToken(), changeEmailRequest);

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.asString(), containsString(Constants.SUECESS_CHANGE_MESSAGE));

        registerUserRequest.setEmail(changeEmailRequest.getNewEmail());
    }

    @Test(priority = 8)
    @Story("Post-change Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Call POST /api/v2/auth/authenticate again with updated credentials and validate the response.")
    public void reAuthenticate() {
        authenticate();
    }

    @Test(priority = 9)
    @Story("SOAP Email Update Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Use the SOAP service to verify that the user’s email has been updated.")
    public void validateEmailChangeWithSoap() {
        GetEmployeeByIdResponse employeeByIdResponse = employeesSteps.getEmployeeById(employee.getEmployeeId());
        assertThat(employeeByIdResponse.getEmployeeInfo().getEmail(), equalTo(registerUserRequest.getEmail()));
    }

    @Test(priority = 10)
    @Story("Database Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify in the users table and employee table that the email has been updated.")
    public void validateChangedDb() {
        User user = usersSteps.getUserDb(registerUserRequest.getEmail());
        assertThat(user, notNullValue());
        assertThat(user.getFirstname(), equalTo(registerUserRequest.getFirstname()));
        assertThat(user.getLastname(), equalTo(registerUserRequest.getLastname()));

        Employee employeeById = employeesSteps.getEmployeeDb(employee.getEmployeeId());
        assertThat(employeeById.getEmail(), equalTo(registerUserRequest.getEmail()));
    }
}
