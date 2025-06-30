<template>
  <div class="min-h-screen flex items-center justify-center px-4 py-16">
    <div class="max-w-4xl mx-12 text-center">
      <!-- Hero Section -->
      <div class="mb-12">
        <h1 class="text-5xl md:text-6xl font-bold text-gray-900 dark:text-white mb-4">
          Welcome to <span class="text-blue-600 dark:text-blue-400">VanaHub</span>
        </h1>
        <p class="text-xl md:text-2xl text-gray-600 dark:text-gray-300 mb-4 max-w-3xl mx-auto">
          Your gateway to the latest FFXI news and weather forecasts. Stay updated with Vana'diel's
          ever-changing world.
        </p>
      </div>

      <!-- Navigation Cards -->
      <div class="grid md:grid-cols-1 gap-8 mb-4">
        <!-- News Card -->
         <!-- Disabled
        <RouterLink
          to="/news"
          class="card-hover bg-white dark:bg-gray-800 rounded-xl shadow-lg p-8 border border-gray-200 dark:border-gray-700 block"
        >
          <div class="flex flex-col items-center">
            News Icon
            <div
              class="w-16 h-16 bg-blue-100 dark:bg-blue-900/30 rounded-full flex items-center justify-center mb-6"
            >
              <svg
                class="w-8 h-8 text-blue-600 dark:text-blue-400"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z"
                ></path>
              </svg>
            </div>

            <h3 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">Latest News</h3>
            <p class="text-gray-600 dark:text-gray-300 mb-6 text-center">
              Stay informed with the latest updates, announcements, and events from the world of
              FFXI.
            </p>

            <div class="flex items-center text-blue-600 dark:text-blue-400 font-medium">
              <span>View News</span>
              <svg class="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 5l7 7-7 7"
                ></path>
              </svg>
            </div>
          </div>
        </RouterLink>
        -->

        <!-- Weather Card -->
        <RouterLink
          to="/weather"
          class="card-hover bg-white dark:bg-gray-800 rounded-xl shadow-lg p-8 border border-gray-200 dark:border-gray-700 block"
        >
          <div class="flex flex-col items-center">
            <!-- Weather Icon -->
            <div
              class="w-16 h-16 bg-green-100 dark:bg-green-900/30 rounded-full flex items-center justify-center mb-6"
            >
              <svg
                class="w-8 h-8 text-green-600 dark:text-green-400"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4.001 4.001 0 003 15z"
                ></path>
              </svg>
            </div>

            <h3 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">Weather Forecast</h3>
            <p class="text-gray-600 dark:text-gray-300 mb-6 text-center">
              Track Vana'diel weather patterns and plan your adventures with accurate forecasts.
            </p>

            <div class="flex items-center text-green-600 dark:text-green-400 font-medium">
              <span>View Weather</span>
              <svg class="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M9 5l7 7-7 7"
                ></path>
              </svg>
            </div>
          </div>
        </RouterLink>
      </div>

    
      <!-- Footer -->
      <div class="mt-12 text-center">
        <p class="text-gray-500 dark:text-gray-400">Built for the FFXI community with ❤️</p>
        <p class="text-gray-500 dark:text-gray-400">
          &copy; 2024 - {{ currentYear }} - VanaHub - All rights reserved.
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed } from 'vue'

export default defineComponent({
  setup() {
    const currentYear = computed(() => new Date().getFullYear())

    return {
      currentYear,
    }
  },
})
</script>
