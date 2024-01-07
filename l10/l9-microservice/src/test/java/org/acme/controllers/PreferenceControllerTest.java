package org.acme.controllers;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.acme.models.Preference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class PreferenceControllerTest {
    @Test
    public void testGetPreferencesFallbackTimeoutRetry() {
        // fallback is called
        List<Preference> fallbackResponse = RestAssured.given()
                .pathParam("timeInMs", 1000)
                .when().get("/preferences/timeout/{timeInMs}")
                .as(new TypeRef<>() {});

        assertNotNull(fallbackResponse);

        // the given thread sleep is smaller than the maximum allowed timeout
        List<Preference> retryResponse = RestAssured.given()
                .pathParam("timeInMs", 400)
                .when().get("/preferences/timeout/{timeInMs}")
                .as(new TypeRef<>() {});

        assertNotNull(retryResponse);
    }

    @Test
    public void testGetPreferencesCircuitBreaker() {
        // Trigger 2 successes
        given()
                .when().get("/preferences/circuit-breaker")
                .then()
                .statusCode(200);

        given()
                .when().get("/preferences/circuit-breaker")
                .then()
                .statusCode(200);

        // trigger 2 failures to open the circuit
        given()
                .when().get("/preferences/circuit-breaker")
                .then()
                .statusCode(500);

        given()
                .when().get("/preferences/circuit-breaker")
                .then()
                .statusCode(500)
                .body(not(empty()));

        // wait 3000 ms to be in the half-open state
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        given()
                .when().get("/preferences/circuit-breaker")
                .then()
                .statusCode(200)
                .body(not(empty()));
    }
}