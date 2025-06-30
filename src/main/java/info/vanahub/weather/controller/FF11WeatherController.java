package info.vanahub.weather.controller;

import info.vanahub.weather.service.FF11WeatherService;
import info.vanahub.weather.service.FF11WeatherService.WeatherInfo;
import info.vanahub.weather.service.FF11WeatherService.WeatherForecast;
import info.vanahub.weather.service.FF11WeatherService.VanaDate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/weather")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Weather API", description = "Final Fantasy XI Weather Forecasting Service")

public class FF11WeatherController {
    
    @Inject
    FF11WeatherService weatherService;
    
    @GET
    @Path("/current/{zoneName}")
    @Operation(
        summary = "Get current weather for a zone",
        description = "Returns the current weather information for the specified zone"
    )
    @APIResponse(
        responseCode = "200",
        description = "Current weather information",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = WeatherInfo.class),
            examples = @ExampleObject(
                name = "Current Weather Example",
                value = """
                {
                  "zone": "Eastern_Altepa_Desert",
                  "date": {
                    "year": 1480,
                    "month": 9,
                    "day": 14,
                    "elementalDay": 5,
                    "elementalDayName": "Lightningday",
                    "dateString": "1480/9/14"
                  },
                  "commonWeather": "Sand Storm",
                  "normalWeather": "Sand Storm",
                  "rareWeather": "Dust Storm",
                  "commonWeatherValue": 9,
                  "normalWeatherValue": 9,
                  "rareWeatherValue": 8,
                  "daysForward": 0
                }
                """
            )
        )
    )
    @APIResponse(responseCode = "400", description = "Invalid zone name")
    public Response getCurrentWeather(
        @Parameter(
            description = "Zone name (use underscores for spaces)",
            example = "Eastern_Altepa_Desert",
            required = true
        )
        @PathParam("zoneName") String zoneName) {
        try {
            WeatherInfo weather = weatherService.getCurrentWeather(zoneName);
            return Response.ok(weather).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("/day/{zoneName}/{daysForward}")
    @Operation(
        summary = "Get weather for a specific future day",
        description = "Returns weather information for a zone on a specific day in the future"
    )
    @APIResponse(
        responseCode = "200",
        description = "Weather information for the specified day",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = WeatherInfo.class)
        )
    )
    @APIResponse(responseCode = "400", description = "Invalid zone name or days forward value (must be 0-30)")
    public Response getWeatherForDay(
        @Parameter(
            description = "Zone name (use underscores for spaces)",
            example = "Eastern_Altepa_Desert",
            required = true
        )
        @PathParam("zoneName") String zoneName,
        @Parameter(
            description = "Number of days forward (0 = today, 1 = tomorrow, etc.)",
            example = "5",
            required = true
        )
        @PathParam("daysForward") int daysForward) {
        try {
            if (daysForward < 0 || daysForward > 365) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            WeatherInfo weather = weatherService.getWeatherForDay(zoneName, daysForward);
            return Response.ok(weather).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("/forecast/{zoneName}/{days}")
    @Operation(
        summary = "Get weather forecast for multiple days",
        description = "Returns weather forecast for a zone covering multiple consecutive days"
    )
    @APIResponse(
        responseCode = "200",
        description = "Weather forecast for the specified number of days",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = WeatherForecast.class),
            examples = @ExampleObject(
                name = "Weather Forecast Example",
                value = """
                {
                  "days": [
                    {
                      "zone": "Eastern_Altepa_Desert",
                      "date": {
                        "year": 1480,
                        "month": 9,
                        "day": 14,
                        "elementalDay": 5,
                        "elementalDayName": "Lightningday",
                        "dateString": "1480/9/14"
                      },
                      "commonWeather": "Sand Storm",
                      "normalWeather": "Sand Storm",
                      "rareWeather": "Dust Storm",
                      "daysForward": 0
                    }
                  ]
                }
                """
            )
        )
    )
    @APIResponse(responseCode = "400", description = "Invalid zone name or days value (must be 1-30)")
    public Response getWeatherForecast(
        @Parameter(
            description = "Zone name (use underscores for spaces)",
            example = "Eastern_Altepa_Desert",
            required = true
        )
        @PathParam("zoneName") String zoneName,
        @Parameter(
            description = "Number of days to forecast (1-30)",
            example = "7",
            required = true
        )
        @PathParam("days") int days) {
        try {
            if (days < 0 || days > 30) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            WeatherForecast forecast = weatherService.getWeatherForecast(zoneName, days);
            return Response.ok(forecast).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("/date")
    @Operation(
        summary = "Get current Vana'diel date",
        description = "Returns the current date and time in the Final Fantasy XI (Vana'diel) calendar system"
    )
    @APIResponse(
        responseCode = "200",
        description = "Current Vana'diel date information",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = VanaDate.class),
            examples = @ExampleObject(
                name = "Vana'diel Date Example",
                value = """
                {
                  "year": 1480,
                  "month": 9,
                  "day": 14,
                  "elementalDay": 5,
                  "elementalDayName": "Lightningday",
                  "dateString": "1480/9/14"
                }
                """
            )
        )
    )
    public Response getCurrentDate() {
        VanaDate date = weatherService.getCurrentDate();
        return Response.ok(date).build();
    }
    
    @GET
    @Path("/zones")
    @Operation(
        summary = "Get all available zones",
        description = "Returns a list of all Final Fantasy XI zones supported by the weather service"
    )
    @APIResponse(
        responseCode = "200",
        description = "List of available zone names",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            examples = @ExampleObject(
                name = "Available Zones Example",
                value = """
                [
                  "Jugner_Forest",
                  "Eastern_Altepa_Desert",
                  "La_Theine_Plateau",
                  "Rolanberry_Fields",
                  "Yuhtunga_Jungle",
                  "Western_Altepa_Desert",
                  "Qufim_Island",
                  "Wajaom_Woodlands",
                  "Valkurm_Dunes",
                  "Meriphataud_Mountains"
                ]
                """
            )
        )
    )
    public Response getAvailableZones() {
        String[] zones = weatherService.getAvailableZones();
        return Response.ok(zones).build();
    }

    @GET
    @Path("/zones/by-weather/{weatherType}")
    @Operation(
        summary = "Get zones by weather type",
        description = "Returns all zones that have the specified weather type within the given number of days (default: 7 days)"
    )
    @APIResponse(
        responseCode = "200",
        description = "List of zones with the specified weather type",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            examples = @ExampleObject(
                name = "Zones by Weather Example",
                value = """
                [
                  "Western_Altepa_Desert",
                  "Eastern_Altepa_Desert"
                ]
                """
            )
        )
    )
    @APIResponse(responseCode = "400", description = "Invalid weather type or days value (must be 1-30)")
    public Response getZonesByWeather(
        @Parameter(
            description = "Weather type to search for (URL encode spaces: 'Sand%20Storm', 'Heat%20Wave')",
            example = "Sand Storm",
            required = true
        )
        @PathParam("weatherType") String weatherType,
        @Parameter(
            description = "Number of days to search ahead (1-30, default: 7)",
            example = "7"
        )
        @QueryParam("days") @DefaultValue("7") int days) {
        try {
            if (days < 1 || days > 30) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            String[] matchingZones = weatherService.getZonesByWeather(weatherType, days);
            return Response.ok(matchingZones).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
