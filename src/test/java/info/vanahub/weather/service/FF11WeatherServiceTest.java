package info.vanahub.weather.service;

import info.vanahub.weather.service.FF11WeatherService.WeatherInfo;
import info.vanahub.weather.service.FF11WeatherService.WeatherForecast;
import info.vanahub.weather.service.FF11WeatherService.VanaDate;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@DisplayName("FF11 Weather Service Tests")
class FF11WeatherServiceTest {

    @Inject
    FF11WeatherService weatherService;

    @Nested
    @DisplayName("Current Weather Tests")
    class CurrentWeatherTests {

        @Test
        @DisplayName("Should return current weather for valid zone")
        void shouldReturnCurrentWeatherForValidZone() {
            // Given
            String zoneName = "Eastern_Altepa_Desert";

            // When
            WeatherInfo weather = weatherService.getCurrentWeather(zoneName);

            // Then
            assertNotNull(weather);
            assertNotNull(weather.getCommonWeather());
            assertNotNull(weather.getZone());
            assertNotNull(weather.getDate());
            assertEquals(zoneName, weather.getZone());
            assertTrue(weather.getCommonWeather().length() > 0);
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
            // When
            WeatherInfo weather = weatherService.getCurrentWeather(zoneName);

            // Then
            assertNotNull(weather);
            assertEquals(zoneName, weather.getZone());
            assertNotNull(weather.getCommonWeather());
        }

        @Test
        @DisplayName("Should throw exception for invalid zone")
        void shouldThrowExceptionForInvalidZone() {
            // Given
            String invalidZone = "Invalid_Zone";

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                weatherService.getCurrentWeather(invalidZone);
            });
        }

        @Test
        @DisplayName("Should throw exception for null zone")
        void shouldThrowExceptionForNullZone() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                weatherService.getCurrentWeather(null);
            });
        }

        @Test
        @DisplayName("Should throw exception for empty zone")
        void shouldThrowExceptionForEmptyZone() {
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                weatherService.getCurrentWeather("");
            });
        }
    }

    @Nested
    @DisplayName("Weather Forecast Tests")
    class WeatherForecastTests {

        @Test
        @DisplayName("Should return weather forecast for valid parameters")
        void shouldReturnWeatherForecastForValidParameters() {
            // Given
            String zoneName = "Eastern_Altepa_Desert";
            int days = 7;

            // When
            WeatherForecast forecast = weatherService.getWeatherForecast(zoneName, days);

            // Then
            assertNotNull(forecast);
            assertNotNull(forecast.getDays());
            assertEquals(days, forecast.getDays().size());
            
            // Verify each forecast entry
            for (WeatherInfo weather : forecast.getDays()) {
                assertNotNull(weather);
                assertNotNull(weather.getCommonWeather());
                assertNotNull(weather.getDate());
                assertEquals(zoneName, weather.getZone());
            }
        }

        @Test
        @DisplayName("Should return single day forecast")
        void shouldReturnSingleDayForecast() {
            // Given
            String zoneName = "Jugner_Forest";
            int days = 1;

            // When
            WeatherForecast forecast = weatherService.getWeatherForecast(zoneName, days);

            // Then
            assertNotNull(forecast);
            assertEquals(1, forecast.getDays().size());
        }

        @Test
        @DisplayName("Should return extended forecast")
        void shouldReturnExtendedForecast() {
            // Given
            String zoneName = "La_Theine_Plateau";
            int days = 30;

            // When
            WeatherForecast forecast = weatherService.getWeatherForecast(zoneName, days);

            // Then
            assertNotNull(forecast);
            assertEquals(30, forecast.getDays().size());
        }

        @Test
        @DisplayName("Should throw exception for invalid zone in forecast")
        void shouldThrowExceptionForInvalidZoneInForecast() {
            // Given
            String invalidZone = "Invalid_Zone";
            int days = 7;

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                weatherService.getWeatherForecast(invalidZone, days);
            });
        }
    }

    @Nested
    @DisplayName("Weather for Specific Day Tests")
    class WeatherForDayTests {

        @Test
        @DisplayName("Should return weather for current day (0 days forward)")
        void shouldReturnWeatherForCurrentDay() {
            // Given
            String zoneName = "Eastern_Altepa_Desert";
            int daysForward = 0;

            // When
            WeatherInfo weather = weatherService.getWeatherForDay(zoneName, daysForward);

            // Then
            assertNotNull(weather);
            assertEquals(zoneName, weather.getZone());
            assertNotNull(weather.getCommonWeather());
            assertNotNull(weather.getDate());
        }

        @Test
        @DisplayName("Should return weather for future day")
        void shouldReturnWeatherForFutureDay() {
            // Given
            String zoneName = "Rolanberry_Fields";
            int daysForward = 5;

            // When
            WeatherInfo weather = weatherService.getWeatherForDay(zoneName, daysForward);

            // Then
            assertNotNull(weather);
            assertEquals(zoneName, weather.getZone());
            assertNotNull(weather.getCommonWeather());
            assertNotNull(weather.getDate());
        }

        @Test
        @DisplayName("Should return consistent weather for same day")
        void shouldReturnConsistentWeatherForSameDay() {
            // Given
            String zoneName = "Yuhtunga_Jungle";
            int daysForward = 3;

            // When
            WeatherInfo weather1 = weatherService.getWeatherForDay(zoneName, daysForward);
            WeatherInfo weather2 = weatherService.getWeatherForDay(zoneName, daysForward);

            // Then
            assertEquals(weather1.getCommonWeather(), weather2.getCommonWeather());
            assertEquals(weather1.getZone(), weather2.getZone());
        }
    }

    @Nested
    @DisplayName("Vana'diel Date Tests")
    class VanaDateTests {

        @Test
        @DisplayName("Should return current Vana'diel date")
        void shouldReturnCurrentVanaDate() {
            // When
            VanaDate date = weatherService.getCurrentDate();

            // Then
            assertNotNull(date);
            assertTrue(date.getYear() > 0);
            assertTrue(date.getMonth() >= 1 && date.getMonth() <= 12);
            assertTrue(date.getDay() >= 1 && date.getDay() <= 30);
            assertTrue(date.getElementalDay() >= 0 && date.getElementalDay() <= 7);
            assertNotNull(date.getElementalDayName());
        }

        @Test
        @DisplayName("Should have valid elemental day names")
        void shouldHaveValidElementalDayNames() {
            // When
            VanaDate date = weatherService.getCurrentDate();

            // Then
            String[] validElementalDays = {
                "Firesday", "Earthsday", "Watersday", "Windsday", 
                "Iceday", "Lightningday", "Lightsday", "Darksday"
            };
            
            boolean isValidElementalDay = false;
            for (String validElementalDay : validElementalDays) {
                if (validElementalDay.equals(date.getElementalDayName())) {
                    isValidElementalDay = true;
                    break;
                }
            }
            assertTrue(isValidElementalDay, "Elemental day should be one of the valid Vana'diel elemental days");
        }

        @Test
        @DisplayName("Should return valid date string")
        void shouldReturnValidDateString() {
            // When
            VanaDate date = weatherService.getCurrentDate();

            // Then
            String dateString = date.getDateString();
            assertNotNull(dateString);
            assertTrue(dateString.contains("/"));
            assertTrue(dateString.length() > 0);
        }
    }

    @Nested
    @DisplayName("Available Zones Tests")
    class AvailableZonesTests {

        @Test
        @DisplayName("Should return all available zones")
        void shouldReturnAllAvailableZones() {
            // When
            String[] zones = weatherService.getAvailableZones();

            // Then
            assertNotNull(zones);
            assertTrue(zones.length > 0);
            
            // Verify some expected zones are present
            boolean hasEasternAltepa = false;
            boolean hasJugnerForest = false;
            
            for (String zone : zones) {
                if ("Eastern_Altepa_Desert".equals(zone)) {
                    hasEasternAltepa = true;
                }
                if ("Jugner_Forest".equals(zone)) {
                    hasJugnerForest = true;
                }
            }
            
            assertTrue(hasEasternAltepa, "Should contain Eastern_Altepa_Desert");
            assertTrue(hasJugnerForest, "Should contain Jugner_Forest");
        }

        @Test
        @DisplayName("Should return zones without duplicates")
        void shouldReturnZonesWithoutDuplicates() {
            // When
            String[] zones = weatherService.getAvailableZones();

            // Then
            for (int i = 0; i < zones.length; i++) {
                for (int j = i + 1; j < zones.length; j++) {
                    assertNotEquals(zones[i], zones[j], "Zones should not contain duplicates");
                }
            }
        }

        @Test
        @DisplayName("Should return expected number of zones")
        void shouldReturnExpectedNumberOfZones() {
            // When
            String[] zones = weatherService.getAvailableZones();

            // Then
            // Based on the zones we know are supported
            assertTrue(zones.length >= 16, "Should have at least 16 zones");
        }
    }

    @Nested
    @DisplayName("Weather Pattern Consistency Tests")
    class WeatherPatternConsistencyTests {

        @Test
        @DisplayName("Should return consistent weather patterns")
        void shouldReturnConsistentWeatherPatterns() {
            // Given
            String zoneName = "Eastern_Altepa_Desert";

            // When - Get weather multiple times for the same moment
            WeatherInfo weather1 = weatherService.getCurrentWeather(zoneName);
            WeatherInfo weather2 = weatherService.getCurrentWeather(zoneName);

            // Then - Should be identical
            assertEquals(weather1.getCommonWeather(), weather2.getCommonWeather());
            assertEquals(weather1.getZone(), weather2.getZone());
        }

        @Test
        @DisplayName("Should have valid weather types")
        void shouldHaveValidWeatherTypes() {
            // Given
            String[] zones = weatherService.getAvailableZones();

            // When & Then
            for (String zone : zones) {
                WeatherInfo weather = weatherService.getCurrentWeather(zone);
                assertNotNull(weather.getCommonWeather());
                assertFalse(weather.getCommonWeather().trim().isEmpty());
                
                // Weather should be a reasonable string (not just numbers or special chars)
                assertTrue(weather.getCommonWeather().matches("[A-Za-z\\s]+"), 
                    "Weather should contain only letters and spaces: " + weather.getCommonWeather());
            }
        }

        @Test
        @DisplayName("Should have different weather types available")
        void shouldHaveDifferentWeatherTypesAvailable() {
            // Given
            String zoneName = "Eastern_Altepa_Desert";

            // When
            WeatherInfo weather = weatherService.getCurrentWeather(zoneName);

            // Then
            assertNotNull(weather.getCommonWeather());
            assertNotNull(weather.getNormalWeather());
            assertNotNull(weather.getRareWeather());
            
            // Verify weather values are within expected range
            assertTrue(weather.getCommonWeatherValue() >= 0);
            assertTrue(weather.getNormalWeatherValue() >= 0);
            assertTrue(weather.getRareWeatherValue() >= 0);
        }
    }
}
