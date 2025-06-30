<template>
  <div class="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors duration-300">
    <!-- Navigation -->
    <nav
      class="bg-white dark:bg-gray-800 shadow-sm border-b border-gray-200 dark:border-gray-700 sticky top-0 z-50"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <!-- Left side - Navigation Links -->
          <div class="flex space-x-8 items-center">
            <RouterLink
              to="/"
              class="px-3 py-2 rounded-md text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white hover:bg-gray-100 dark:hover:bg-gray-700 transition-all duration-200"
              :class="{
                'text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20':
                  $route.path === '/',
              }"
            >
              Home
            </RouterLink>
         
            <RouterLink
              to="/weather"
              class="px-3 py-2 rounded-md text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white hover:bg-gray-100 dark:hover:bg-gray-700 transition-all duration-200"
              :class="{
                'text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20':
                  $route.path === '/weather',
              }"
            >
              Weather
            </RouterLink>
            <RouterLink
              to="/api"
              class="px-3 py-2 rounded-md text-sm font-medium text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white hover:bg-gray-100 dark:hover:bg-gray-700 transition-all duration-200"
              :class="{
                'text-blue-600 dark:text-blue-400 bg-blue-50 dark:bg-blue-900/20':
                  $route.path === '/api',
              }"
            >
              API
            </RouterLink>
          </div>

          <!-- Right side: group clock + theme toggle button -->
          <div class="flex items-center space-x-4">
            <!-- VanaTime clock info with margin-right -->
            <div
              class="flex items-center space-x-3 text-md font-medium text-gray-700 dark:text-gray-300 select-none mr-4"
            >
              <span class="font-mono text-gray-900 dark:text-gray-100">{{ time }}</span>
              <span class="font-mono text-md text-gray-500 dark:text-gray-400">
                ({{ nextDaySeconds }} until next gameday)
              </span>
              <span class="font-mono text-md text-gray-600 dark:text-gray-400 me-3"> {{ moon }}, {{ date }} </span>
            </div>

            <!-- Theme Toggle Button -->
            <button
              @click="toggleTheme"
              class="p-2 rounded-lg bg-gray-100 dark:bg-gray-700 hover:bg-gray-200 dark:hover:bg-gray-600 transition-colors duration-200"
            >
              <!-- Light mode icon (shown in dark mode) -->
              <svg
                v-if="isDark"
                class="w-5 h-5 text-gray-600 dark:text-gray-300"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"
                />
              </svg>
              <!-- Dark mode icon (shown in light mode) -->
              <svg
                v-else
                class="w-5 h-5 text-gray-600 dark:text-gray-300"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <main class="min-h-screen bg-gray-50 dark:bg-gray-900">
      <RouterView />
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useTheme } from './composables/useTheme'
import { VanaTimeCalculator, padZero, secondsUntilNextDay } from 'vanatime'

// Theme handling
const { isDark, initTheme, toggleTheme, watchSystemTheme } = useTheme()

// Vana'diel time state
const time = ref('')
const date = ref('')
const moon = ref('')
const nextDaySeconds = ref('')
let intervalId: number | undefined

function updateTime() {
  const vanaTime = VanaTimeCalculator.getTime()
  const vanaDate = VanaTimeCalculator.getDate()
  const currentVanaSeconds = VanaTimeCalculator.getVanaSeconds()

  time.value = vanaTime.formatted
  date.value = `${vanaDate.weekdayName}`
  moon.value = `${vanaDate.moonPhaseName} (${vanaDate.moonPercent}%)`

  // Calculate seconds until next Vana'diel day
  const remainingVanaSeconds = secondsUntilNextDay(currentVanaSeconds)
  const remainingEarthSeconds = remainingVanaSeconds * ((57.6 * 60) / 86400)

  const minutes = Math.floor((remainingEarthSeconds % 3600) / 60)
  const seconds = Math.floor(remainingEarthSeconds % 60)

  nextDaySeconds.value = `${padZero(minutes)} min ${padZero(seconds)} sec`
}

onMounted(() => {
  initTheme()
  watchSystemTheme()
  updateTime()
  intervalId = window.setInterval(updateTime, 1000)
})
</script>

