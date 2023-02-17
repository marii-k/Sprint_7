package Courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class LoginCourierTest {
    TestFixture testFixture = new TestFixture();
    Response createdCourier;
    String login = "lCourier";
    String password = "4321";
    String firstName = "lCourie";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown() {
        if (createdCourier.statusCode() == 201) {
            testFixture.deleteCourier(testFixture.getCourierId(testFixture.loginCourier(login, password)));
        }
    }

    @Test
    @DisplayName("Try to login new courier with valid data")
    @Description("Test /api/v1/courier/login. Return 200 and courierId")
    public void loginCourierWithValidDataExpectedStatus200() {
        CourierSerializer courierJsonData = new CourierSerializer(login, password, firstName);
        CourierSerializer courierLoginData = new CourierSerializer(login, password);

        createdCourier = testFixture.createNewCourier(courierJsonData);
        Response loginCourierRes = testFixture.loginCourier(courierLoginData.getLogin(), courierLoginData.getPassword());

        loginCourierRes.then().assertThat().statusCode(200);
        MatcherAssert.assertThat(loginCourierRes.as(CourierLoginDeserializer.class).getId(), is(notNullValue()));
    }

    @Test
    @DisplayName("Try to login new courier with invalid data")
    @Description("Test /api/v1/courier/login with invalid login. Return 404 and message: Учетная запись не найдена")
    public void loginCourierWithInvalidLoginExpectedStatus404() {
        CourierSerializer courierJsonData = new CourierSerializer(login, password, firstName);
        CourierSerializer courierLoginData = new CourierSerializer("invalidCourierLogin", password);

        createdCourier = testFixture.createNewCourier(courierJsonData);
        Response response = testFixture.loginCourier(courierLoginData.getLogin(), courierLoginData.getPassword());

        response.then().assertThat().statusCode(404);
        MatcherAssert.assertThat(response.as(CourierLoginDeserializer.class).getMessage(), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Try to login new courier with invalid password")
    @Description("Test /api/v1/courier/login with invalid password. Return 404 and message: Учетная запись не найдена")
    public void loginCourierWithInvalidPasswordExpectedStatus404() {
        CourierSerializer courierJsonData = new CourierSerializer(login, password, firstName);
        CourierSerializer courierLoginData = new CourierSerializer(login, "invalidPassword");

        createdCourier = testFixture.createNewCourier(courierJsonData);
        Response response = testFixture.loginCourier(courierLoginData.getLogin(), courierLoginData.getPassword());

        response.then().assertThat().statusCode(404);
        MatcherAssert.assertThat(response.as(CourierLoginDeserializer.class).getMessage(), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Try to login new courier without login")
    @Description("Test /api/v1/courier/login without login. Return 404 and message: Учетная запись не найдена")
    public void loginCourierWithoutLoginExpectedStatus404() {
        CourierSerializer courierJsonData = new CourierSerializer(login, password, firstName);
        CourierSerializer courierLoginData = new CourierSerializer(null, password);

        createdCourier = testFixture.createNewCourier(courierJsonData);
        Response response = testFixture.loginCourier(courierLoginData.getLogin(), courierLoginData.getPassword());

        response.then().assertThat().statusCode(404);
        MatcherAssert.assertThat(response.as(CourierLoginDeserializer.class).getMessage(), equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Try to login new courier without password")
    @Description("Test /api/v1/courier/login without password. Return 404 and message: Учетная запись не найдена")
    public void loginCourierWithoutPasswordExpectedStatus404() {
        CourierSerializer courierJsonData = new CourierSerializer(login, password, firstName);
        CourierSerializer courierLoginData = new CourierSerializer(login, null);

        createdCourier = testFixture.createNewCourier(courierJsonData);
        Response response = testFixture.loginCourier(courierLoginData.getLogin(), courierLoginData.getPassword());

        response.then().assertThat().statusCode(404);
        MatcherAssert.assertThat(response.as(CourierLoginDeserializer.class).getMessage(), equalTo("Учетная запись не найдена"));
    }
}