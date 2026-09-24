package tests;

import data.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.path.xml.XmlPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.ContinentsSteps;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Country Info SOAP API")
@Feature("Continents")
public class ContinentsTest {
    private  ContinentsSteps continentsSteps;
    @BeforeClass
    public void beforeClass() {
        continentsSteps = new ContinentsSteps();
    }


    @Test(description = "Validate continents returned by ListOfContinentsByName")
    @Story("Validate continents")
    @Description("Validate continent names, codes, uniqueness and naming rules")
    public void validateContinents() {
        XmlPath continentsResponse = continentsSteps.getContinentsResponse();

        List<String> names =
                continentsResponse.getList("tContinent.sName");

        List<String> codes =
                continentsResponse.getList("tContinent.sCode");

        assertThat(
                names,
                hasSize(Constants.CONTINENTS_COUNT)
        );

        assertThat(
                names,
                containsInAnyOrder(
                        Constants.AFRICA,
                        Constants.ANTARCTICA,
                        Constants.ASIA,
                        Constants.EUROPE,
                        Constants.OCENANIA,
                        Constants.THE_AMERICAS
                )
        );

        assertThat(
                names.get(names.size() - 1),
                equalTo(Constants.THE_AMERICAS)
        );

        assertThat(
                names,
                hasItems(
                        Constants.AFRICA,
                        Constants.ANTARCTICA,
                        Constants.ASIA,
                        Constants.EUROPE,
                        Constants.OCENANIA,
                        Constants.THE_AMERICAS
                )
        );

        assertThat(
                names,
                everyItem(not(matchesPattern(".*\\d.*")))
        );

        assertThat(
                new HashSet<>(names),
                hasSize(Constants.CONTINENTS_COUNT)
        );

        String startsWithO = names.stream()
                .filter(name -> name.startsWith("O"))
                .findFirst()
                .orElse(null);
        assertThat(startsWithO,
                equalTo(Constants.OCENANIA)
        );

        List<String> startsWithAandEndsWithCa =
                names.stream()
                        .filter(name -> name.startsWith("A") && name.endsWith("ca"))
                        .collect(Collectors.toList());

        assertThat(
                startsWithAandEndsWithCa,
                hasItems(
                        Constants.AFRICA,
                        Constants.ANTARCTICA
                )
        );

        assertThat(
                codes,
                everyItem(matchesPattern("[A-Z]{2}"))
        );

    }
}
