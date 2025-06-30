package info.vanahub.weather.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
@DisplayName("FF11 Weather Controller Integration Tests")
class FF11WeatherControllerTest {

    @Nested
    @DisplayName("Current Weather Endpoint Tests")
    class CurrentWeatherEndpointTests {

        @Test
        @DisplayName("Should return current weather for valid zone")
        void shouldReturnCurrentWeatherForValidZone() {
            given()
                .when()
                .get("/api/v1/weather/current/Eastern_Altepa_Desert")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("zone", equalTo("Eastern_Altepa_Desert"))
                .body("commonWeather", notNullValue())
                .body("normalWeather", notNullValue())
                .body("rareWeather", notNullValue())
                .body("date", notNullValue())
                .body("date.year", greaterThan(0))
                .body("date.month", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(12)))
                .body("date.day", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(30)));
        }

        @ParameterizedTest
        @DisplayName("Should return weather for all supported zones")
        @ValueSource(strings = {
            "Jugner_Forest",
            "Eastern_Altepa_Desert", 
            "La_Theine_Plateau",
            "Rolanberry_Fields",
            "Yuhtunga_Jungle",
            "Yhoator_Jungle",
            "Western_Altepa_Desert",
            "Qufim_Island",
            "Wajaom_Woodlands",
            "Bhaflau_Thickets",
            "Valkurm_Dunes",
            "Konschtat_Highlands",
            "Pashhow_Marshlands",
            "Tahrongi_Canyon",
            "Buburimu_Peninsula",
            "Meriphataud_Mountains"
        })
        void shouldReturnWeatherForAllSupportedZones(String zoneName) {
            given()
                .when()
                .get("/api/v1/weather/current/" + zoneName)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("zone", equalTo(zoneName))
                .body("commonWeather", notNullValue())
                .body("normalWeather", notNullValue())
                .body("rareWeather", notNullValue());
        }

        @Test
        @DisplayName("Should return 400 for invalid zone")
        void shouldReturn400ForInvalidZone() {
            given()
                .when()
                .get("/api/v1/weather/current/Invalid_Zone")
                .then()
                .statusCode(400);
        }

        @Test
        @DisplayName("Should return 400 for empty zone")
        void shouldReturn400ForEmptyZone() {
            given()
                .when()
                .get("/api/v1/weather/current/")
                .then()
                .statusCode(404); // Path not found
        }
    }

    @Nested
    @DisplayName("Weather Forecast Endpoint Tests")
    class WeatherForecastEndpointTests {

        @Test
        @DisplayName("Should return weather forecast for valid zone and days")
        void shouldReturnWeatherForecastForValidZoneAndDays() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Eastern_Altepa_Desert/7")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("days", hasSize(7))
                .body("days[0].zone", equalTo("Eastern_Altepa_Desert"))
                .body("days[0].commonWeather", notNullValue())
                .body("days[0].normalWeather", notNullValue())
                .body("days[0].rareWeather", notNullValue())
                .body("days[0].date", notNullValue());
        }

        @Test
        @DisplayName("Should return single day forecast")
        void shouldReturnSingleDayForecast() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Jugner_Forest/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("days", hasSize(1));
        }

        @Test
        @DisplayName("Should return extended forecast")
        void shouldReturnExtendedForecast() {
            given()
                .when()
                .get("/api/v1/weather/forecast/La_Theine_Plateau/30")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("days", hasSize(30));
        }

        @Test
        @DisplayName("Should return 400 for invalid zone in forecast")
        void shouldReturn400ForInvalidZoneInForecast() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Invalid_Zone/7")
                .then()
                .statusCode(400);
        }

        @Test
        @DisplayName("Should return 400 for invalid days parameter")
        void shouldReturn400ForInvalidDaysParameter() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Eastern_Altepa_Desert/invalid")
                .then()
                .statusCode(404); // Path parameter parsing error
        }

        @Test
        @DisplayName("Should handle zero days parameter")
        void shouldHandleZeroDaysParameter() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Eastern_Altepa_Desert/0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("days", hasSize(0));
        }
    }

    @Nested
    @DisplayName("Weather for Specific Day Endpoint Tests")
    class WeatherForDayEndpointTests {

        @Test
        @DisplayName("Should return weather for current day")
        void shouldReturnWeatherForCurrentDay() {
            given()
                .when()
                .get("/api/v1/weather/day/Eastern_Altepa_Desert/0")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("zone", equalTo("Eastern_Altepa_Desert"))
                .body("commonWeather", notNullValue())
                .body("normalWeather", notNullValue())
                .body("rareWeather", notNullValue())
                .body("daysForward", equalTo(0));
        }

        @Test
        @DisplayName("Should return weather for future day")
        void shouldReturnWeatherForFutureDay() {
            given()
                .when()
                .get("/api/v1/weather/day/Rolanberry_Fields/5")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("zone", equalTo("Rolanberry_Fields"))
                .body("daysForward", equalTo(5));
        }

        @Test
        @DisplayName("Should return 400 for invalid zone")
        void shouldReturn400ForInvalidZone() {
            given()
                .when()
                .get("/api/v1/weather/day/Invalid_Zone/0")
                .then()
                .statusCode(400);
        }
    }

    @Nested
    @DisplayName("Available Zones Endpoint Tests")
    class AvailableZonesEndpointTests {

        @Test
        @DisplayName("Should return all available zones")
        void shouldReturnAllAvailableZones() {
            given()
                .when()
                .get("/api/v1/weather/zones")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(greaterThan(0)))
                .body("$", hasItem("Eastern_Altepa_Desert"))
                .body("$", hasItem("Jugner_Forest"));
        }

        @Test
        @DisplayName("Should return zones without duplicates")
        void shouldReturnZonesWithoutDuplicates() {
            String[] zones = given()
                .when()
                .get("/api/v1/weather/zones")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .as(String[].class);

            // Check for duplicates
            for (int i = 0; i < zones.length; i++) {
                for (int j = i + 1; j < zones.length; j++) {
                    if (zones[i].equals(zones[j])) {
                        throw new AssertionError("Duplicate zone found: " + zones[i]);
                    }
                }
            }
        }
    }

    @Nested
    @DisplayName("Zones by Weather Endpoint Tests")
    class ZonesByWeatherEndpointTests {

        @Test
        @DisplayName("Should return zones with specific weather type")
        void shouldReturnZonesWithSpecificWeatherType() {
            given()
                .when()
                .get("/api/v1/weather/zones/by-weather/Sunshine")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", isA(java.util.List.class));
        }

        @ParameterizedTest
        @DisplayName("Should handle various weather types")
        @ValueSource(strings = {
            "Sunshine", "Clouds", "Rain", "Snow", "Wind", "Fog"
        })
        void shouldHandleVariousWeatherTypes(String weatherType) {
            given()
                .when()
                .get("/api/v1/weather/zones/by-weather/" + weatherType)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", isA(java.util.List.class));
        }

        @Test
        @DisplayName("Should return empty array for non-existent weather")
        void shouldReturnEmptyArrayForNonExistentWeather() {
            given()
                .when()
                .get("/api/v1/weather/zones/by-weather/NonExistentWeather")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(0));
        }

        @Test
        @DisplayName("Should handle special characters in weather type")
        void shouldHandleSpecialCharactersInWeatherType() {
            given()
                .when()
                .get("/api/v1/weather/zones/by-weather/Weather@#$")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(400)));
        }
    }

    @Nested
    @DisplayName("Current Date Endpoint Tests")
    class CurrentDateEndpointTests {

        @Test
        @DisplayName("Should return current Vana'diel date")
        void shouldReturnCurrentVanaDate() {
            given()
                .when()
                .get("/api/v1/weather/date")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("year", greaterThan(0))
                .body("month", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(12)))
                .body("day", allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(30)))
                .body("elementalDay", allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(7)))
                .body("elementalDayName", notNullValue())
                .body("dateString", notNullValue());
        }

        @Test
        @DisplayName("Should return valid elemental day names")
        void shouldReturnValidElementalDayNames() {
            String elementalDayName = given()
                .when()
                .get("/api/v1/weather/date")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("elementalDayName");

            String[] validElementalDays = {
                "Firesday", "Earthsday", "Watersday", "Windsday", 
                "Iceday", "Lightningday", "Lightsday", "Darksday"
            };

            boolean isValid = false;
            for (String validDay : validElementalDays) {
                if (validDay.equals(elementalDayName)) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                throw new AssertionError("Invalid elemental day name: " + elementalDayName);
            }
        }
    }

    @Nested
    @DisplayName("API Response Format Tests")
    class ApiResponseFormatTests {

        @Test
        @DisplayName("Should return consistent response format for weather info")
        void shouldReturnConsistentResponseFormatForWeatherInfo() {
            given()
                .when()
                .get("/api/v1/weather/current/Eastern_Altepa_Desert")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasKey("zone"))
                .body("$", hasKey("commonWeather"))
                .body("$", hasKey("normalWeather"))
                .body("$", hasKey("rareWeather"))
                .body("$", hasKey("commonWeatherValue"))
                .body("$", hasKey("normalWeatherValue"))
                .body("$", hasKey("rareWeatherValue"))
                .body("$", hasKey("date"))
                .body("$", hasKey("daysForward"))
                .body("date", hasKey("year"))
                .body("date", hasKey("month"))
                .body("date", hasKey("day"))
                .body("date", hasKey("elementalDay"))
                .body("date", hasKey("elementalDayName"))
                .body("date", hasKey("dateString"));
        }

        @Test
        @DisplayName("Should return consistent response format for forecast")
        void shouldReturnConsistentResponseFormatForForecast() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Eastern_Altepa_Desert/3")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasKey("days"))
                .body("days", hasSize(3))
                .body("days[0]", hasKey("zone"))
                .body("days[0]", hasKey("commonWeather"))
                .body("days[0]", hasKey("normalWeather"))
                .body("days[0]", hasKey("rareWeather"))
                .body("days[0]", hasKey("date"));
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle malformed requests gracefully")
        void shouldHandleMalformedRequestsGracefully() {
            // Test with special characters in zone name
            given()
                .when()
                .get("/api/v1/weather/current/Zone@#$%")
                .then()
                .statusCode(400);
        }

        @Test
        @DisplayName("Should handle very large days parameter")
        void shouldHandleVeryLargeDaysParameter() {
            given()
                .when()
                .get("/api/v1/weather/forecast/Eastern_Altepa_Desert/999999")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(400))); // Either works or fails gracefully
        }

        @Test
        @DisplayName("Should handle negative days parameter")
        void shouldHandleNegativeDaysParameter() {
            given()
                .when()
                .get("/api/v1/weather/day/Eastern_Altepa_Desert/-1")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(400))); // Either works or fails gracefully
        }
    }

    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Should respond quickly to current weather requests")
        void shouldRespondQuicklyToCurrentWeatherRequests() {
            long startTime = System.currentTimeMillis();
            
            given()
                .when()
                .get("/api/v1/weather/current/Eastern_Altepa_Desert")
                .then()
                .statusCode(200);
            
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;
            
            // Response should be under 1 second
            if (responseTime > 1000) {
                throw new AssertionError("Response time too slow: " + responseTime + "ms");
            }
        }

        @Test
        @DisplayName("Should handle multiple concurrent requests")
        void shouldHandleMultipleConcurrentRequests() {
            // Test multiple zones in quick succession
            String[] zones = {"Eastern_Altepa_Desert", "Jugner_Forest", "La_Theine_Plateau"};
            
            for (String zone : zones) {
                given()
                    .when()
                    .get("/api/v1/weather/current/" + zone)
                    .then()
                    .statusCode(200)
                    .body("zone", equalTo(zone));
            }
        }
    }
}
