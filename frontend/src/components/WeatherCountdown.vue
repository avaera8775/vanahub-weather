<template>
  <div class="flex items-center space-x-2">
    <svg 
      class="w-4 h-4 text-gray-500 dark:text-gray-400" 
      fill="none" 
      stroke="currentColor" 
      viewBox="0 0 24 24"
    >
      <path 
        stroke-linecap="round" 
        stroke-linejoin="round" 
        stroke-width="2" 
        d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
      />
    </svg>
    <span 
      :class="[
        'text-sm font-medium',
        timeRemaining.isActive 
          ? 'text-green-600 dark:text-green-400' 
          : 'text-gray-600 dark:text-gray-300'
      ]"
    >
      {{ formattedTime }}
    </span>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { WeatherUtils, type TimeRemaining } from '@/utils/weatherUtils'

interface Props {
  daysForward: number
}

const props = defineProps<Props>()

const timeRemaining = ref<TimeRemaining>({
  hours: 0,
  minutes: 0,
  seconds: 0,
  totalSeconds: 0,
  isActive: false
})

let intervalId: number | null = null

const updateCountdown = () => {
  timeRemaining.value = WeatherUtils.getTimeUntilWeatherChange(props.daysForward)
}

const formattedTime = computed(() => {
  return WeatherUtils.formatDetailedTimeRemaining(timeRemaining.value)
})

onMounted(() => {
  // Initial update
  updateCountdown()
  
  // Update every second
  intervalId = window.setInterval(updateCountdown, 1000)
})

onUnmounted(() => {
  if (intervalId !== null) {
    clearInterval(intervalId)
    intervalId = null
  }
})
</script>

<style scoped>
/* Component-specific styles if needed */
</style>
