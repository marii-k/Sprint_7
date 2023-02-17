package Order;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestFixture {
    public Response deleteOrder(String orderTrack) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .put("/api/v1/orders/cancel?track="+orderTrack);
    }
}