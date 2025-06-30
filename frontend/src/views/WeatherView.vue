<template>
  <div class="max-w-6xl mx-auto px-4 py-8">
    <!-- Page Header -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-2">Weather Forecast</h1>
      <p class="text-gray-600 dark:text-gray-300">
        Track Vana'diel weather patterns and plan your adventures
      </p>
    </div>
    
    <!-- Controls Section -->
    <div class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 mb-6 border border-gray-200 dark:border-gray-700">
      <!-- Zone Dropdown -->
      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-2">
          Zone
        </label>
        <select 
          v-model="selectedZone" 
          @change="loadWeatherData"
          class="w-full max-w-xs px-3 py-2 text-sm text-gray-700 bg-white border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600"
        >
          <option value="ALL_ZONES">All Zones</option>
          <option v-for="zone in zones" :key="zone" :value="zone">
            {{ formatZoneName(zone) }}
          </option>
        </select>
      </div>
      
      <!-- Vana'diel Time Display -->
      <div class="mb-4 p-3 bg-blue-50 rounded-lg dark:bg-blue-900/20">
        <div class="text-sm font-medium text-blue-800 dark:text-blue-200">
          Current Vana'diel Time
        </div>
        <div class="text-lg font-semibold text-blue-900 dark:text-blue-100">
          <span v-if="currentDate">
            {{ currentDate.dateString }} ({{ currentDate.elementalDayName }})
          </span>
          <span v-else class="text-gray-500">Loading...</span>
        </div>
      </div>
      
      <!-- Weather Type Filter Buttons -->
      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-2">
          Filter by Weather Type
        </label>
<div class="flex flex-wrap gap-2">
  <!-- All Weather Button -->
  <button 
    @click="setWeatherFilter(null)"
    :class="[
      'px-3 py-1 text-xs font-medium rounded-lg transition-colors duration-150',
      !selectedWeatherType
        ? 'bg-blue-500 text-white'
        : 'bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600'
    ]"
  >
    All Weather
  </button>

  <!-- Weather Type Buttons -->
  <button 
    v-for="weatherType in weatherTypes" 
    :key="weatherType"
    @click="setWeatherFilter(weatherType)"
    :class="[
      'px-3 py-1 text-xs font-medium rounded-lg transition-colors duration-150',
      selectedWeatherType === weatherType
        ? 'bg-blue-500 text-white'
        : 'bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600'
    ]"
  >
    {{ weatherType }}
  </button>
</div>

      </div>
    </div>
    
    <!-- Content Area -->
    <div v-if="loading" class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-8 text-center border border-gray-200 dark:border-gray-700">
      <div class="flex justify-center items-center space-x-3">
        <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-500"></div>
        <span class="text-gray-600 dark:text-gray-300">Loading weather data...</span>
      </div>
    </div>
    
    <div v-else-if="error" class="error-card">
      <div class="flex items-center space-x-2 mb-2">
        <svg class="w-5 h-5 error-text" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
        </svg>
        <span class="error-text font-medium">Error loading weather data</span>
      </div>
      <p class="error-text">{{ error }}</p>
      <button @click="loadWeatherData" class="mt-3 btn-primary">
        Try Again
      </button>
    </div>
    
    <div v-else-if="paginatedWeather.length === 0" class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-8 text-center border border-gray-200 dark:border-gray-700">
      <div class="text-gray-500 dark:text-gray-400">
        <svg class="w-12 h-12 mx-auto mb-4 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4.001 4.001 0 003 15z"></path>
        </svg>
        <p>
          <span v-if="selectedWeatherType && selectedZone === 'ALL_ZONES'">
            No weather data found for "{{ selectedWeatherType }}" in any zone
          </span>
          <span v-else-if="selectedWeatherType">
            No weather data found for "{{ selectedWeatherType }}" in {{ formatZoneName(selectedZone) }}
          </span>
          <span v-else>
            No weather data available for {{ formatZoneName(selectedZone) }}
          </span>
        </p>
      </div>
    </div>
    
    <div v-else class="bg-white dark:bg-gray-800 rounded-lg shadow-sm border border-gray-200 dark:border-gray-700">
      <!-- Results Header -->
      <div class="px-6 py-4 border-b border-gray-200 dark:border-gray-700">
        <h4 class="text-lg font-semibold text-gray-600 dark:text-gray-300">
          Weather Forecast - {{ getDisplayTitle() }}
          <span v-if="selectedWeatherType" class="text-sm font-normal text-gray-500 dark:text-gray-400">
            (Filtered by {{ selectedWeatherType }})
          </span>
        </h4>
        <p class="text-sm text-gray-500 dark:text-gray-400 mt-1">
          {{ filteredWeather.length }} forecast{{ filteredWeather.length !== 1 ? 's' : '' }} found
        </p>
      </div>
      
      <!-- Weather Cards -->
      <div class="p-6 space-y-4">
        <div 
          v-for="(weather, index) in paginatedWeather" 
          :key="index"
          class="p-4 bg-gray-50 rounded-lg dark:border-l-4 dark:border-gray-700 dark:bg-gray-700"
        >
          <!-- Header Row -->
          <div class="flex items-center justify-between mb-3">
            <div class="flex items-center space-x-4">
              <div class="text-sm font-medium text-gray-600 dark:text-gray-300">
                <span v-if="selectedZone === 'ALL_ZONES'" class="font-semibold text-blue-600 dark:text-blue-400">
                  {{ formatZoneName(weather.zone) }} - 
                </span>
                {{ weather.date.dateString }} ({{ weather.date.elementalDayName }})
              </div>
              <!-- Conditional display for days forward and countdown -->
              <div v-if="weather.daysForward === 0" class="text-xs">
                <WeatherCountdown :daysForward="weather.daysForward" />
              </div>
              <div v-else class="flex items-center space-x-3 text-xs text-gray-500 dark:text-gray-400 ">
                <span>in {{ weather.daysForward }} day{{ weather.daysForward !== 1 ? 's' : '' }}</span>
                <span class="text-gray-400">|</span>
                <WeatherCountdown :daysForward="weather.daysForward" />
              </div>
            </div>
          </div>
          
          <!-- Weather Types Row -->
          <div class="flex items-center space-x-6">
            <div class="text-left">
              <div class="text-xs text-gray-500 dark:text-gray-400">Common</div>
              <div class="text-sm font-medium text-gray-700 dark:text-gray-200">
                {{ weather.commonWeather }}
              </div>
            </div>
            <div class="text-left">
              <div class="text-xs text-gray-500 dark:text-gray-400">Normal</div>
              <div class="text-sm font-medium text-gray-700 dark:text-gray-200">
                {{ weather.normalWeather }}
              </div>
            </div>
            <div class="text-left">
              <div class="text-xs text-gray-500 dark:text-gray-400">Rare</div>
              <div class="text-sm font-medium text-gray-700 dark:text-gray-200">
                {{ weather.rareWeather }}
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Pagination -->
      <div v-if="totalPages > 1" class="flex items-center justify-between px-6 py-4">
        <div class="text-sm text-gray-500 dark:text-gray-400">
          Showing {{ startIndex + 1 }} to {{ endIndex }} of {{ filteredWeather.length }} results
        </div>
        <div class="flex space-x-2">
          <button 
            @click="previousPage"
            :disabled="currentPage === 1"
            class="pagination-btn"
          >
            Previous
          </button>
          
          <button 
            v-for="page in visiblePages" 
            :key="page"
            @click="goToPage(page)" 
            :class="['pagination-btn', { 'bg-blue-500 text-white border-blue-500': page === currentPage }]"
          >
            {{ page }}
          </button>
          
          <button 
            @click="nextPage"
            :disabled="currentPage === totalPages"
            class="pagination-btn"
          >
            Next
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { apiService } from '@/services/api'
import WeatherCountdown from '@/components/WeatherCountdown.vue'

// Reactive data
const zones = ref<string[]>([])
const selectedZone = ref('ALL_ZONES')
const selectedWeatherType = ref<string | null>(null)
const weatherData = ref<any[]>([])
const currentDate = ref<any>(null)
const loading = ref(false)
const error = ref('')
const currentPage = ref(1)
const itemsPerPage = 10

const weatherTypes = [
  'Sunshine', 'Clouds', 'Fog', 'Hot Spell', 'Heat Wave',
  'Rain', 'Squall', 'Dust Storm', 'Sand Storm', 'Wind', 
  'Gales', 'Snow', 'Blizzard', 'Thunder', 'Thunderstorms',
  'Auroras', 'Stellar Glare', 'Gloom', 'Darkness'
]

// Computed properties
const filteredWeather = computed(() => {
  if (!selectedWeatherType.value) {
    return weatherData.value
  }
  
  return weatherData.value.filter(weather => 
    weather.commonWeather === selectedWeatherType.value ||
    weather.normalWeather === selectedWeatherType.value ||
    weather.rareWeather === selectedWeatherType.value
  )
})

const totalPages = computed(() => {
  return Math.ceil(filteredWeather.value.length / itemsPerPage)
})

const startIndex = computed(() => {
  return (currentPage.value - 1) * itemsPerPage
})

const endIndex = computed(() => {
  return Math.min(startIndex.value + itemsPerPage, filteredWeather.value.length)
})

const paginatedWeather = computed(() => {
  return filteredWeather.value.slice(startIndex.value, endIndex.value)
})

const visiblePages = computed(() => {
  const startPage = Math.max(1, currentPage.value - 2)
  const endPage = Math.min(totalPages.value, currentPage.value + 2)
  const pages = []
  
  for (let i = startPage; i <= endPage; i++) {
    pages.push(i)
  }
  
  return pages
})

// Methods
const formatZoneName = (zoneName: string) => {
  return apiService.formatZoneName(zoneName)
}

const getDisplayTitle = () => {
  if (selectedZone.value === 'ALL_ZONES') {
    return 'All Zones'
  }
  return formatZoneName(selectedZone.value)
}

const loadZones = async () => {
  try {
    zones.value = await apiService.getWeatherZones()
  } catch (err) {
    console.error('Error loading zones:', err)
    error.value = 'Failed to load zones'
  }
}

const loadCurrentDate = async () => {
  try {
    currentDate.value = await apiService.getCurrentVanaDate()
  } catch (err) {
    console.error('Error loading current date:', err)
  }
}

const loadWeatherData = async () => {
  if (!selectedZone.value) return
  
  loading.value = true
  error.value = ''
  
  try {
    if (selectedZone.value === 'ALL_ZONES') {
      // Load data from all zones
      const allWeatherData: any[] = []
      
      for (const zone of zones.value) {
        try {
          const forecast = await apiService.getWeatherForecast(zone, 30)
          allWeatherData.push(...forecast.days)
        } catch (zoneError) {
          console.warn(`Failed to load weather for zone ${zone}:`, zoneError)
        }
      }
      
      // Sort by days forward, then by zone name for consistent ordering
      allWeatherData.sort((a, b) => {
        if (a.daysForward !== b.daysForward) {
          return a.daysForward - b.daysForward
        }
        return a.zone.localeCompare(b.zone)
      })
      
      weatherData.value = allWeatherData
    } else {
      // Load data for single zone
      const forecast = await apiService.getWeatherForecast(selectedZone.value, 30)
      weatherData.value = forecast.days
    }
    
    currentPage.value = 1 // Reset to first page
  } catch (err) {
    console.error('Error loading weather data:', err)
    error.value = 'Failed to load weather data'
    weatherData.value = []
  } finally {
    loading.value = false
  }
}

const setWeatherFilter = (weatherType: string | null) => {
  selectedWeatherType.value = weatherType
  currentPage.value = 1 // Reset to first page when filter changes
}

const goToPage = (page: number) => {
  currentPage.value = page
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

// Lifecycle
onMounted(async () => {
  await loadZones()
  await loadCurrentDate()
  await loadWeatherData()
})
</script>