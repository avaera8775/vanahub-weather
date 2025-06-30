import{d as t,a as e,k as r,h as o,_ as d}from"./index-CMyNAFn8.js";const b={class:"max-w-6xl mx-auto px-4 py-8 space-y-12"},s=t({__name:"apiDocumentation",setup(g){return(u,a)=>(o(),e("div",b,a[0]||(a[0]=[r(`<h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-4" data-v-188e74b5> Vanadiel Weather API </h1><p class="text-gray-600 dark:text-gray-300 mb-8" data-v-188e74b5> RESTful API for Weather in Vana&#39;diel. </p><section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700" data-v-188e74b5><h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200" data-v-188e74b5> GET /api/v1/weather/current/{zoneName} </h2><p class="text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5> Returns the current weather information for the specified zone. </p><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Path Parameters</h3><table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5><thead class="bg-gray-100 dark:bg-gray-700" data-v-188e74b5><tr data-v-188e74b5><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Parameter</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Type</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Required</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Description</th></tr></thead><tbody data-v-188e74b5><tr data-v-188e74b5><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>zoneName</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>string</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Yes</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Zone name (use underscores for spaces)</td></tr></tbody></table><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Request</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4" data-v-188e74b5><code data-v-188e74b5>curl -X GET &quot;/api/v1/weather/current/Eastern_Altepa_Desert&quot; -H &quot;accept: application/json&quot;</code></pre><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Response</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto" data-v-188e74b5><code data-v-188e74b5>{
  &quot;zone&quot;: &quot;Eastern_Altepa_Desert&quot;,
  &quot;date&quot;: {
    &quot;year&quot;: 1480,
    &quot;month&quot;: 9,
    &quot;day&quot;: 14,
    &quot;elementalDay&quot;: 5,
    &quot;elementalDayName&quot;: &quot;Lightningday&quot;,
    &quot;dateString&quot;: &quot;1480/9/14&quot;
  },
  &quot;commonWeather&quot;: &quot;Sand Storm&quot;,
  &quot;normalWeather&quot;: &quot;Sand Storm&quot;,
  &quot;rareWeather&quot;: &quot;Dust Storm&quot;,
  &quot;commonWeatherValue&quot;: 9,
  &quot;normalWeatherValue&quot;: 9,
  &quot;rareWeatherValue&quot;: 8,
  &quot;daysForward&quot;: 0
}</code></pre></section><section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700" data-v-188e74b5><h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200" data-v-188e74b5> GET /api/v1/weather/date </h2><p class="text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5> Returns the current date and time in the Final Fantasy XI (Vana&#39;diel) calendar system. </p><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Request</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4" data-v-188e74b5><code data-v-188e74b5>curl -X GET &quot;/api/v1/weather/date&quot; -H &quot;accept: application/json&quot;</code></pre><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Response</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto" data-v-188e74b5><code data-v-188e74b5>{
  &quot;year&quot;: 1480,
  &quot;month&quot;: 9,
  &quot;day&quot;: 14,
  &quot;elementalDay&quot;: 5,
  &quot;elementalDayName&quot;: &quot;Lightningday&quot;,
  &quot;dateString&quot;: &quot;1480/9/14&quot;
}</code></pre></section><section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700" data-v-188e74b5><h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200" data-v-188e74b5> GET /api/v1/weather/day/{zoneName}/{daysForward} </h2><p class="text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5> Returns weather information for a zone on a specific day in the future. </p><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Path Parameters</h3><table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5><thead class="bg-gray-100 dark:bg-gray-700" data-v-188e74b5><tr data-v-188e74b5><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Parameter</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Type</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Required</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Description</th></tr></thead><tbody data-v-188e74b5><tr data-v-188e74b5><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>zoneName</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>string</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Yes</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Zone name (use underscores for spaces)</td></tr><tr data-v-188e74b5><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>daysForward</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>integer</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Yes</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Number of days forward (0 = today, 1 = tomorrow, etc., max 30)</td></tr></tbody></table><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Request</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4" data-v-188e74b5><code data-v-188e74b5>curl -X GET &quot;/api/v1/weather/day/Eastern_Altepa_Desert/5&quot; -H &quot;accept: application/json&quot;</code></pre><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Response</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto" data-v-188e74b5><code data-v-188e74b5>{
  &quot;zone&quot;: &quot;Eastern_Altepa_Desert&quot;,
  &quot;date&quot;: {
    &quot;year&quot;: 1480,
    &quot;month&quot;: 9,
    &quot;day&quot;: 19,
    &quot;elementalDay&quot;: 2,
    &quot;elementalDayName&quot;: &quot;Windday&quot;,
    &quot;dateString&quot;: &quot;1480/9/19&quot;
  },
  &quot;commonWeather&quot;: &quot;Clear Skies&quot;,
  &quot;normalWeather&quot;: &quot;Clear Skies&quot;,
  &quot;rareWeather&quot;: &quot;Thunderstorm&quot;,
  &quot;commonWeatherValue&quot;: 7,
  &quot;normalWeatherValue&quot;: 7,
  &quot;rareWeatherValue&quot;: 4,
  &quot;daysForward&quot;: 5
}</code></pre></section><section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700" data-v-188e74b5><h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200" data-v-188e74b5> GET /api/v1/weather/forecast/{zoneName}/{days} </h2><p class="text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5> Returns weather forecast for a zone covering multiple consecutive days. </p><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Path Parameters</h3><table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5><thead class="bg-gray-100 dark:bg-gray-700" data-v-188e74b5><tr data-v-188e74b5><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Parameter</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Type</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Required</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Description</th></tr></thead><tbody data-v-188e74b5><tr data-v-188e74b5><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>zoneName</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>string</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Yes</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Zone name (use underscores for spaces)</td></tr><tr data-v-188e74b5><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>days</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>integer</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Yes</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Number of days to forecast (1-30)</td></tr></tbody></table><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Request</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4" data-v-188e74b5><code data-v-188e74b5>curl -X GET &quot;/api/v1/weather/forecast/Eastern_Altepa_Desert/7&quot; -H &quot;accept: application/json&quot;</code></pre><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Response</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto" data-v-188e74b5><code data-v-188e74b5>{
  &quot;days&quot;: [
    {
      &quot;zone&quot;: &quot;Eastern_Altepa_Desert&quot;,
      &quot;date&quot;: {
        &quot;year&quot;: 1480,
        &quot;month&quot;: 9,
        &quot;day&quot;: 14,
        &quot;elementalDay&quot;: 5,
        &quot;elementalDayName&quot;: &quot;Lightningday&quot;,
        &quot;dateString&quot;: &quot;1480/9/14&quot;
      },
      &quot;commonWeather&quot;: &quot;Sand Storm&quot;,
      &quot;normalWeather&quot;: &quot;Sand Storm&quot;,
      &quot;rareWeather&quot;: &quot;Dust Storm&quot;,
      &quot;daysForward&quot;: 0
    }
    /* Additional days up to requested count */
  ]
}</code></pre></section><section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700" data-v-188e74b5><h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200" data-v-188e74b5> GET /api/v1/weather/zones </h2><p class="text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5> Returns a list of all Final Fantasy XI zones supported by the weather service. </p><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Request</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4" data-v-188e74b5><code data-v-188e74b5>curl -X GET &quot;/api/v1/weather/zones&quot; -H &quot;accept: application/json&quot;</code></pre><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Response</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto" data-v-188e74b5><code data-v-188e74b5>[
  &quot;Jugner_Forest&quot;,
  &quot;Eastern_Altepa_Desert&quot;,
  &quot;La_Theine_Plateau&quot;,
  &quot;Rolanberry_Fields&quot;,
  &quot;Yuhtunga_Jungle&quot;,
  &quot;Western_Altepa_Desert&quot;,
  &quot;Qufim_Island&quot;,
  &quot;Wajaom_Woodlands&quot;,
  &quot;Valkurm_Dunes&quot;,
  &quot;Meriphataud_Mountains&quot;
]</code></pre></section><section class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 border border-gray-200 dark:border-gray-700" data-v-188e74b5><h2 class="text-2xl font-semibold mb-4 text-gray-800 dark:text-gray-200" data-v-188e74b5> GET /api/v1/weather/zones/by-weather/{weatherType} </h2><p class="text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5> Returns all zones that have the specified weather type within the given number of days (default: 7 days). </p><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Path Parameters</h3><table class="w-full border-collapse border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 mb-4" data-v-188e74b5><thead class="bg-gray-100 dark:bg-gray-700" data-v-188e74b5><tr data-v-188e74b5><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Parameter</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Type</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Required</th><th class="border border-gray-300 dark:border-gray-600 p-2 text-left" data-v-188e74b5>Description</th></tr></thead><tbody data-v-188e74b5><tr data-v-188e74b5><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>weatherType</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>string</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Yes</td><td class="border border-gray-300 dark:border-gray-600 p-2" data-v-188e74b5>Weather name (e.g., &quot;Sand Storm&quot;)</td></tr></tbody></table><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Request</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto mb-4" data-v-188e74b5><code data-v-188e74b5>curl -X GET &quot;/api/v1/weather/zones/by-weather/Sand_Storm&quot; -H &quot;accept: application/json&quot;</code></pre><h3 class="font-semibold mb-2 text-gray-800 dark:text-gray-200" data-v-188e74b5>Example Response</h3><pre class="bg-gray-100 dark:bg-gray-700 p-4 rounded text-sm text-gray-700 dark:text-gray-300 overflow-x-auto" data-v-188e74b5><code data-v-188e74b5>[
  &quot;Eastern_Altepa_Desert&quot;,
  &quot;Western_Altepa_Desert&quot;,
  &quot;Jugner_Forest&quot;
]</code></pre></section>`,8)])))}}),y=d(s,[["__scopeId","data-v-188e74b5"]]);export{y as default};
