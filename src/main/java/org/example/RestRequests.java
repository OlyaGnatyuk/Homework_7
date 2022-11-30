package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestRequests {
    public static String getToken(String baseUri, String body, String path) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth")
                .then()
                .log().body(true)
                .statusCode(200)
                .extract()
                .response()
                .path(path).toString();
    }

    public static String createBooking(String baseUri, String body, int expectedStatusCode, String path) {
        Response response = RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/booking")
                .then()
                .log().body(true)
                .statusCode(expectedStatusCode)
                .extract()
                .response();

        if (path != null) {
            return response.path(path).toString();
        }

        return null;
    }

    public static String getBooking(String baseUri, String id, int expectedStatusCode) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .when()
                .get("/booking/" + id)
                .then()
                .log().body(true)
                .statusCode(expectedStatusCode)
                .extract()
                .response().asString();
    }

    public static String updateBooking(String baseUri, String id, String token, String body) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .accept(ContentType.ANY)
                .cookie("token", token)
                .body(body)
                .when()
                .put("/booking/" + id)
                .then()
                .log().body(true)
                .statusCode(200)
                .extract()
                .response().asString();
    }

    public static String deleteBooking(String baseUri, String id, String token, int expectedStatusCode) {
        return RestAssured.given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .delete("/booking/" + id)
                .then()
                .log().body(true)
                .statusCode(expectedStatusCode)
                .extract()
                .response().asString();
    }
}
