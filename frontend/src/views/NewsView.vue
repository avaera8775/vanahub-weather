<template>
  <div class="max-w-6xl mx-auto px-4 py-8">
    <!-- Page Header -->
    <div class="mb-8">
      <h1 class="text-3xl font-bold text-gray-900 dark:text-white mb-2">
        {{ contentType === 'news' ? 'Latest News' : 'Maintenance Information' }}
      </h1>
      <p class="text-gray-600 dark:text-gray-300">
        Stay updated with the latest FFXI {{ contentType === 'news' ? 'news and announcements' : 'maintenance schedules' }}
      </p>
    </div>
    
    <!-- Controls Section -->
    <div class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-6 mb-6 border border-gray-200 dark:border-gray-700">
      <!-- Content Type Toggle -->
          <h3 class="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-3">Category</h3>

      <div class="flex justify-between items-center mb-4">
        
        <div class="flex space-x-3">
          <button 
            @click="setContentType('news')"
            :class="[
              'px-3 py-1 text-xs font-medium rounded-lg transition-colors duration-150',
              contentType === 'news' 
                ? 'bg-blue-500 text-white' 
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600'
            ]"
          >
            News
          </button>
          <button 
            @click="setContentType('maintenance')"
            :class="[
              'px-3 py-1 text-xs font-medium rounded-lg transition-colors duration-150',
              contentType === 'maintenance' 
                ? 'bg-blue-500 text-white' 
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600'
            ]"
          >
            Maintenance
          </button>
        </div>
      </div>
      
      <!-- Region Filter -->
      <div class="mb-4">
        <label class="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-2">
          Region
        </label>
        <div class="flex space-x-2">
          <button 
            v-for="region in regions" 
            :key="region.value"
            @click="changeRegion(region.value)"
            :class="[
              'px-3 py-1 text-xs font-medium rounded-lg transition-colors duration-150',
              selectedRegion === region.value 
                ? 'bg-blue-500 text-white' 
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600'
            ]"
          >
            {{ region.label }}
          </button>
        </div>
      </div>
      
      <!-- Category Filter (only for news) -->
      <div v-if="contentType === 'news'" class="mb-4">
        <label class="block text-sm font-medium text-gray-600 dark:text-gray-300 mb-2">
          Filter
        </label>
        <div class="flex space-x-2 flex-wrap">
          <button 
            v-for="category in categories" 
            :key="category.value"
            @click="changeCategory(category.value)"
            :class="[
              'px-3 py-1 text-xs font-medium rounded-lg transition-colors duration-150 mb-1',
              selectedCategory === category.value 
                ? 'bg-blue-500 text-white' 
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200 dark:bg-gray-700 dark:text-gray-300 dark:hover:bg-gray-600'
            ]"
          >
            {{ category.label }}
          </button>
        </div>
      </div>
    </div>
    
    <!-- Content Area -->
    <div v-if="loading" class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-8 text-center border border-gray-200 dark:border-gray-700">
      <div class="flex justify-center items-center space-x-3">
        <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-500"></div>
        <span class="text-gray-600 dark:text-gray-300">Loading {{ contentType }}...</span>
      </div>
    </div>
    
    <div v-else-if="error" class="p-4 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-800">
      <div class="flex items-center space-x-2 mb-2">
        <svg class="w-5 h-5 text-red-800 dark:text-red-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
        </svg>
        <span class="text-red-800 dark:text-red-200 font-medium">Error</span>
      </div>
      <p class="text-red-800 dark:text-red-200">{{ error }}</p>
      <button @click="fetchContent" class="mt-3 bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg transition-colors duration-200 font-medium">
        Try Again
      </button>
    </div>
    
    <div v-else-if="newsItems.length === 0" class="bg-white dark:bg-gray-800 rounded-lg shadow-sm p-8 text-center border border-gray-200 dark:border-gray-700">
      <div class="text-gray-500 dark:text-gray-400">
        <svg class="w-12 h-12 mx-auto mb-4 opacity-50" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 20H5a2 2 0 01-2-2V6a2 2 0 012-2h10a2 2 0 012 2v1m2 13a2 2 0 01-2-2V7m2 13a2 2 0 002-2V9a2 2 0 00-2-2h-2m-4-3H9M7 16h6M7 8h6v4H7V8z"></path>
        </svg>
        <p>No {{ contentType }} items found for this region.</p>
      </div>
    </div>
    
    <div v-else class="bg-white dark:bg-gray-800 rounded-lg shadow-sm border border-gray-200 dark:border-gray-700">
      <!-- Results Header -->
      <div class="px-6 py-4 border-b border-gray-200 dark:border-gray-700">
        <h4 class="text-lg font-semibold text-gray-600 dark:text-gray-300">
          {{ contentType === 'news' ? 'News Articles' : 'Maintenance Schedule' }}
          <span v-if="selectedCategory !== 'all' && contentType === 'news'" class="text-sm font-normal text-gray-500 dark:text-gray-400">
            - {{ categories.find(c => c.value === selectedCategory)?.label }}
          </span>
        </h4>
        <p v-if="totalElements > 0" class="text-sm text-gray-500 dark:text-gray-400 mt-1">
          {{ totalElements }} items found
        </p>
      </div>
      
      <!-- News Items -->
      <div class="p-6 space-y-6">
        <div 
          v-for="item in newsItems" 
          :key="item.id" 
          class="flex flex-col md:flex-row gap-4 pb-6 border-b border-gray-200 dark:border-gray-700 last:border-b-0 last:pb-0"
        >
          <!-- Image -->
          <div class="md:w-48 flex-shrink-0">
            <div class="w-full h-24 overflow-hidden rounded-lg bg-gray-100 dark:bg-gray-700">
              <img 
                :src="getImageUrl(item)" 
                :alt="item.title" 
                class="w-full h-24 object-cover object-center rounded-lg"
                @error="handleImageError"
              >
            </div>
          </div>
          
          <!-- Content -->
          <div class="flex-1">
            <h3 class="text-lg font-semibold mb-2">
              <a 
                :href="item.link || item.guid" 
                target="_blank" 
                class="text-blue-600 hover:text-blue-800 dark:text-blue-400 dark:hover:text-blue-300 hover:underline"
              >
                {{ item.title }}
              </a>
            </h3>
            
            <p v-if="item.description" class="text-gray-600 dark:text-gray-300 mb-3 line-clamp-3">
              {{ item.description }}
            </p>
            
            <div class="flex items-center text-sm text-gray-500 dark:text-gray-400">
              <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
              </svg>
              {{ formatTimeAgo(item.createdAt || item.publicationDate) }}
              
              <span v-if="item.category" class="mx-2">•</span>
              <span v-if="item.category" class="px-2 py-1 bg-gray-100 dark:bg-gray-700 rounded text-xs">
                {{ item.category }}
              </span>
            </div>
          </div>
        </div>
      </div>
      
      <!-- Pagination -->
      <div v-if="totalPages > 1" class="flex items-center justify-between px-6 py-4 bg-gray-50 border-t border-gray-200 dark:bg-gray-700 dark:border-gray-600">
        <div class="text-sm text-gray-700 dark:text-gray-400">
          Showing {{ ((currentPage - 1) * pageSize) + 1 }} to {{ Math.min(currentPage * pageSize, totalElements) }} of {{ totalElements }} results
        </div>
        <div class="flex space-x-2">
          <button 
            @click="previousPage" 
            :disabled="currentPage === 1" 
            class="px-3 py-1 text-sm text-gray-500 bg-white border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400 dark:hover:bg-gray-700"
          >
            Previous
          </button>
          
          <button 
            v-for="page in visiblePages" 
            :key="page"
            @click="goToPage(page)" 
            :class="[
              'px-3 py-1 text-sm border rounded transition-colors duration-200',
              page === currentPage 
                ? 'bg-blue-500 text-white border-blue-500' 
                : 'text-gray-500 bg-white border-gray-300 hover:bg-gray-50 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400 dark:hover:bg-gray-700'
            ]"
          >
            {{ page }}
          </button>
          
          <button 
            @click="nextPage" 
            :disabled="currentPage === totalPages" 
            class="px-3 py-1 text-sm text-gray-500 bg-white border border-gray-300 rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400 dark:hover:bg-gray-700"
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

// Reactive data
const newsItems = ref<any[]>([])
const loading = ref(false)
const error = ref('')
const currentPage = ref(1)
const totalPages = ref(0)
const totalElements = ref(0)
const pageSize = ref(10)

// Filters
const selectedRegion = ref(localStorage.getItem('vanahub-news-region') || 'eu')
const selectedCategory = ref(localStorage.getItem('vanahub-news-category') || 'all')
const contentType = ref(localStorage.getItem('vanahub-content-type') || 'news')

const regions = [
  { label: 'EU', value: 'eu' },
  { label: 'US', value: 'us' },
  { label: 'JP', value: 'jp' }
]

const categories = [
  { label: 'All', value: 'all' },
  { label: 'Login Campaign', value: 'login_campaign' },
  { label: 'Version Update', value: 'version_update' }
]

// Computed properties
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
const fetchContent = async () => {
  loading.value = true
  error.value = ''
  
  try {
    if (contentType.value === 'news') {
      const response = await apiService.getNews(
        selectedRegion.value,
        selectedCategory.value,
        currentPage.value,
        pageSize.value
      )
      
      newsItems.value = response.content || []
      totalPages.value = response.totalPages || 0
      totalElements.value = response.totalElements || 0
    } else {
      const maintenanceData = await apiService.getMaintenanceInfo(selectedRegion.value)
      newsItems.value = maintenanceData || []
      totalPages.value = 1
      totalElements.value = newsItems.value.length
    }
  } catch (err) {
    console.error('Error fetching content:', err)
    error.value = `Failed to load ${contentType.value}. Please try again.`
    newsItems.value = []
  } finally {
    loading.value = false
  }
}

const setContentType = async (type: string) => {
  contentType.value = type
  localStorage.setItem('vanahub-content-type', type)
  currentPage.value = 1
  await fetchContent()
}

const changeRegion = async (region: string) => {
  selectedRegion.value = region
  localStorage.setItem('vanahub-news-region', region)
  currentPage.value = 1
  await fetchContent()
}

const changeCategory = async (category: string) => {
  selectedCategory.value = category
  localStorage.setItem('vanahub-news-category', category)
  currentPage.value = 1
  await fetchContent()
}

const goToPage = async (page: number) => {
  currentPage.value = page
  await fetchContent()
}

const previousPage = async () => {
  if (currentPage.value > 1) {
    currentPage.value--
    await fetchContent()
  }
}

const nextPage = async () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
    await fetchContent()
  }
}

const getImageUrl = (item: any) => {
  return item.imageUrl && item.imageUrl.trim() ? 
    apiService.getNewsImageUrl(item.imageUrl) : 
    apiService.getPlaceholderImageUrl()
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  target.src = apiService.getPlaceholderImageUrl()
}

const formatTimeAgo = (dateString: string) => {
  return apiService.formatTimeAgo(dateString)
}

// Lifecycle
onMounted(() => {
  fetchContent()
})
</script>

<style scoped>
.line-clamp-3 {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
