package Courier;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestFixture {
    public Response loginCourier(String login, String password){
        String courierData = "{\"login\":\""+login+"\",\"password\":\""+password+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(courierData)
                .when()
                .post("/api/v1/courier/login");
    }
    public String getCourierId(Response response) {
        if (response.statusCode()==200) {
            CourierLoginDeserializer courierId = response.body().as(CourierLoginDeserializer.class);
            return courierId.getId();
        } else {
            return null;
        }
    }
    public void deleteCourier(String courierId){
        given()
                .delete("/api/v1/courier/"+courierId)
                .then()
                .statusCode(200);
    }
    public Response createNewCourier(CourierSerializer courierJsonData) {
        return given()
                .header("Content-type", "application/json")
                .body(courierJsonData)
                .when()
                .post("/api/v1/courier");
    }
}