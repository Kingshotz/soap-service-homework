package steps;

import auth.api.model.*;
import io.restassured.response.Response;

import static config.BaseConfiguration.authApi;
import static config.BaseConfiguration.authApiWithToken;

public class AuthSteps {
    public Response register(RegisterUserRequest registerRequest) {
        return authApi()
                .authenticationV2()
                .register()
                .body(registerRequest)
                .execute(response->response);
    }
    public Response getAdminResource(String token){
        return authApiWithToken(token)
                .authorization()
                .sayHelloWithRoleAdminAndReadAuthority()
                .execute(response->response);
    }
    public Response changePassword(String token, ChangePasswordRequest changePasswordRequest){
        return authApiWithToken(token)
                .userManagement()
                .changePassword()
                .body(changePasswordRequest)
                .execute(response->response);
    }
    public Response logOut(RefreshTokenRequest refreshTokenRequest){
        return authApi()
                .authenticationV2()
                .revokeToken()
                .body(refreshTokenRequest)
                .execute(response->response);
    }
    public Response authenticate(LoginRequest loginRequest){
        return  authApi()
                .authenticationV2()
                .authenticate()
                .body(loginRequest)
                .execute(response->response);
    }
    public Response changeEmail(String token ,ChangeEmailRequest  changeEmailRequest){
        return authApiWithToken(token)
                .userManagement()
                .changeEmail()
                .body(changeEmailRequest)
                .execute(response->response);
    }
}
