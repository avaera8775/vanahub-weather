<template>
  <div class="max-w-6xl mx-auto px-4 py-8 space-y-12">
    <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4">
      Vanadiel Weather API
    </h1>
    <p class="text-gray-600 dark:text-gray-300 mb-8">
      RESTful API for Weather in Vana'diel.
    </p>

    <!-- Endpoint #1 -->
    <section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200">
        GET /api/v1/weather/current/{zoneName}
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-4">
        Returns the current weather information for the specified zone.
      </p>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Path Parameters</h3>
      <table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4">
        <thead class="bg-gray-100 dark:bg-gray-700">
          <tr>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Parameter</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Type</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Required</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Description</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="border border-gray-300 dark:border-gray-600 p-2">zoneName</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">string</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Yes</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Zone name (use underscores for spaces)</td>
          </tr>
        </tbody>
      </table>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Request</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4"><code>curl -X GET "/api/v1/weather/current/Eastern_Altepa_Desert" -H "accept: application/json"</code></pre>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Response</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto"><code>{
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
}</code></pre>
    </section>

    <!-- Endpoint #2 -->
    <section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200">
        GET /api/v1/weather/date
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-4">
        Returns the current date and time in the Final Fantasy XI (Vana'diel) calendar system.
      </p>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Request</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4"><code>curl -X GET "/api/v1/weather/date" -H "accept: application/json"</code></pre>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Response</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto"><code>{
  "year": 1480,
  "month": 9,
  "day": 14,
  "elementalDay": 5,
  "elementalDayName": "Lightningday",
  "dateString": "1480/9/14"
}</code></pre>
    </section>

    <!-- Endpoint #3 -->
    <section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200">
        GET /api/v1/weather/day/{zoneName}/{daysForward}
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-4">
        Returns weather information for a zone on a specific day in the future.
      </p>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Path Parameters</h3>
      <table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4">
        <thead class="bg-gray-100 dark:bg-gray-700">
          <tr>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Parameter</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Type</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Required</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Description</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="border border-gray-300 dark:border-gray-600 p-2">zoneName</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">string</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Yes</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Zone name (use underscores for spaces)</td>
          </tr>
          <tr>
            <td class="border border-gray-300 dark:border-gray-600 p-2">daysForward</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">integer</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Yes</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Number of days forward (0 = today, 1 = tomorrow, etc., max 30)</td>
          </tr>
        </tbody>
      </table>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Request</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4"><code>curl -X GET "/api/v1/weather/day/Eastern_Altepa_Desert/5" -H "accept: application/json"</code></pre>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Response</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto"><code>{
  "zone": "Eastern_Altepa_Desert",
  "date": {
    "year": 1480,
    "month": 9,
    "day": 19,
    "elementalDay": 2,
    "elementalDayName": "Windday",
    "dateString": "1480/9/19"
  },
  "commonWeather": "Clear Skies",
  "normalWeather": "Clear Skies",
  "rareWeather": "Thunderstorm",
  "commonWeatherValue": 7,
  "normalWeatherValue": 7,
  "rareWeatherValue": 4,
  "daysForward": 5
}</code></pre>
    </section>

    <!-- Endpoint #4 -->
    <section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200">
        GET /api/v1/weather/forecast/{zoneName}/{days}
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-4">
        Returns weather forecast for a zone covering multiple consecutive days.
      </p>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Path Parameters</h3>
      <table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4">
        <thead class="bg-gray-100 dark:bg-gray-700">
          <tr>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Parameter</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Type</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Required</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Description</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="border border-gray-300 dark:border-gray-600 p-2">zoneName</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">string</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Yes</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Zone name (use underscores for spaces)</td>
          </tr>
          <tr>
            <td class="border border-gray-300 dark:border-gray-600 p-2">days</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">integer</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Yes</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Number of days to forecast (1-30)</td>
          </tr>
        </tbody>
      </table>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Request</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4"><code>curl -X GET "/api/v1/weather/forecast/Eastern_Altepa_Desert/7" -H "accept: application/json"</code></pre>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Response</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto"><code>{
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
    /* Additional days up to requested count */
  ]
}</code></pre>
    </section>

    <!-- Endpoint #5 -->
    <section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200">
        GET /api/v1/weather/zones
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-4">
        Returns a list of all Final Fantasy XI zones supported by the weather service.
      </p>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Request</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4"><code>curl -X GET "/api/v1/weather/zones" -H "accept: application/json"</code></pre>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Response</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto"><code>[
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
]</code></pre>
    </section>

    <!-- Endpoint #6 -->
    <section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700">
      <h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200">
        GET /api/v1/weather/zones/by-weather/{weatherType}
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-4">
        Returns all zones that have the specified weather type within the given number of days (default: 7 days).
      </p>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Path Parameters</h3>
      <table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4">
        <thead class="bg-gray-100 dark:bg-gray-700">
          <tr>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Parameter</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Type</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Required</th>
            <th class="border border-gray-300 dark:border-gray-600 p-2 text-left">Description</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="border border-gray-300 dark:border-gray-600 p-2">weatherType</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">string</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Yes</td>
            <td class="border border-gray-300 dark:border-gray-600 p-2">Weather name (e.g., "Sand Storm")</td>
          </tr>
        </tbody>
      </table>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Request</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4"><code>curl -X GET "/api/v1/weather/zones/by-weather/Sand_Storm" -H "accept: application/json"</code></pre>
      <h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200">Example Response</h3>
      <pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto"><code>[
  "Eastern_Altepa_Desert",
  "Western_Altepa_Desert",
  "Jugner_Forest"
]</code></pre>
    </section>
  </div>
</template>

<script setup lang="ts">
// Static documentation, no logic needed.
</script>

<style scoped>
pre {
  font-family: 'Fira Code', monospace, monospace;
  white-space: pre-wrap;
}
table th, table td {
  vertical-align: top;
}
</style>
