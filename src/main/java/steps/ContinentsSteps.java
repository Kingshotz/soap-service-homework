package steps;

import data.Constants;
import data.models.ContinentsResponse;
import io.restassured.path.xml.XmlPath;

import static io.restassured.RestAssured.given;

public class ContinentsSteps {
    public XmlPath getContinentsResponse() {
          return  given()
                        .when()
                        .get(Constants.CONTINENTS_URL)
                        .then()
                        .statusCode(200)
                        .extract()
                        .xmlPath()
                  .setRootPath(Constants.CONTINENTS_ROOT_PATH);
    }

}
