// API service utilities
export class ApiService {
  private newsBaseUrl = import.meta.env.VITE_WEATHER_API_ENDPOINT || '';
  private weatherBaseUrl = import.meta.env.VITE_WEATHER_API_ENDPOINT || 'https://vanahub-weather.onrender.com/api/v1/weather';
  private apiKey = import.meta.env.VITE_NEWS_API_KEY || '';

  // Generic fetch wrapper with error handling
  private async fetchWithErrorHandling(url: string, options: RequestInit = {}): Promise<any> {
    try {
      const response = await fetch(url, {
        ...options,
        headers: {
          'Content-Type': 'application/json',
          ...options.headers
        }
      })

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }

      return await response.json()
    } catch (error) {
      //console.error('API request failed:', error)
      throw error
    }
  }

  // News API methods
  async getNews(region = 'eu', category = '', page = 1, limit = 10) {
    try {
      let url: string

      if (category && category !== 'all') {
        // Category endpoint
        url = `${this.newsBaseUrl}/news/category/${category}?limit=${limit}&page=${page}&region=${region}`
      } else {

        url = `${this.newsBaseUrl}/news/latest?region=${region}`
      }

      const data = await this.fetchWithErrorHandling(url, {
        headers: {
          'x-api-key': this.apiKey
        }
      })

      // Process the response based on endpoint type
      if (category && category !== 'all') {
        // Category endpoint returns paginated response
        const sortedContent = (data.content || []).sort((a: any, b: any) => {
          return new Date(b.publicationDate).getTime() - new Date(a.publicationDate).getTime()
        })

        return {
          content: this.decodeHtmlEntities(sortedContent),
          totalPages: data.totalPages || 1,
          totalElements: data.totalElements || 0
        }
      } else {
        
        const sortedContent = (data || []).sort((a: any, b: any) => {
          return new Date(b.publicationDate).getTime() - new Date(a.publicationDate).getTime()
        })

        const totalItems = sortedContent.length
        const totalPages = Math.ceil(totalItems / limit)
        const startIndex = (page - 1) * limit
        const endIndex = Math.min(startIndex + limit, totalItems)
        const paginatedContent = sortedContent.slice(startIndex, endIndex)

        return {
          content: this.decodeHtmlEntities(paginatedContent),
          totalPages: totalPages,
          totalElements: totalItems
        }
      }
      
    } catch (error) {
      //console.error('Error fetching news:', error)
      return { content: [], totalPages: 0, totalElements: 0 }
    }
  }

  async getMaintenanceInfo(region = 'eu') {
    try {
      const url = `${this.newsBaseUrl}/news/information/latest?region=${region}`
      const data = await this.fetchWithErrorHandling(url)
      return this.decodeHtmlEntities(data || [])
    } catch (error) {
      //console.error('Error fetching maintenance info:', error)
      return []
    }
  }

  // Weather API methods
  async getWeatherZones(): Promise<string[]> {
    try {
      const data = await this.fetchWithErrorHandling(`${this.weatherBaseUrl}/zones`)
      return data || []
    } catch (error) {
      //console.error('Error fetching weather zones:', error)
      // Fallback zones
      return [
        'Jugner_Forest', 'La_Theine_Plateau', 'Rolanberry_Fields',
        'Yuhtunga_Jungle', 'Yhoator_Jungle', 'Western_Altepa_Desert',
        'Qufim_Island', 'Wajaom_Woodlands', 'Bhaflau_Thickets',
        'Valkurm_Dunes', 'Konschtat_Highlands', 'Pashhow_Marshlands',
        'Eastern_Altepa_Desert', 'Tahrongi_Canyon', 'Buburimu_Peninsula',
        'Meriphataud_Mountains'
      ]
    }
  }

  async getWeatherForecast(zoneName: string, days = 30) {
    try {
      const url = `${this.weatherBaseUrl}/forecast/${zoneName}/${days}`
      const data = await this.fetchWithErrorHandling(url)
      return data || { days: [] }
    } catch (error) {
      //console.error('Error fetching weather forecast:', error)
      return { days: [] }
    }
  }

  async getCurrentVanaDate() {
    try {
      const data = await this.fetchWithErrorHandling(`${this.weatherBaseUrl}/date`)
      return data
    } catch (error) {
      //console.error('Error fetching current Vana date:', error)
      return null
    }
  }

  // Image service methods
  getNewsImageUrl(imageFileName: string): string {
    if (!imageFileName) return this.getPlaceholderImageUrl()

    // Clean filename
    const cleanFileName = imageFileName.startsWith('/news/images/')
      ? imageFileName.substring('/news/images/'.length)
      : imageFileName

    return `${this.newsBaseUrl}/news/images/${cleanFileName}`
  }

  getPlaceholderImageUrl(): string {
    return `${this.newsBaseUrl}/news/images/placeholder.jpg`
  }

  // Utility methods
  private decodeHtmlEntities(items: any): any {
    if (!Array.isArray(items)) return items

    return items.map(item => {
      if (item.title) {
        item.title = item.title
          .replace(/&#8211;/g, '-')
          .replace(/&#8212;/g, '--')
          .replace(/&#8216;/g, "'")
          .replace(/&#8217;/g, "'")
          .replace(/&#8220;/g, '"')
          .replace(/&#8221;/g, '"')
          .replace(/&#039;/g, "'")
      }
      return item
    })
  }

  formatTimeAgo(dateString: string): string {
    const date = new Date(dateString)
    const now = new Date()
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000)

    let interval = Math.floor(seconds / 31536000)
    if (interval >= 1) {
      return interval === 1 ? '1 year ago' : `${interval} years ago`
    }

    interval = Math.floor(seconds / 2592000)
    if (interval >= 1) {
      return interval === 1 ? '1 month ago' : `${interval} months ago`
    }

    interval = Math.floor(seconds / 86400)
    if (interval >= 1) {
      return interval === 1 ? '1 day ago' : `${interval} days ago`
    }

    interval = Math.floor(seconds / 3600)
    if (interval >= 1) {
      return interval === 1 ? '1 hour ago' : `${interval} hours ago`
    }

    interval = Math.floor(seconds / 60)
    if (interval >= 1) {
      return interval === 1 ? '1 minute ago' : `${interval} minutes ago`
    }

    return seconds < 10 ? 'just now' : `${Math.floor(seconds)} seconds ago`
  }

  formatZoneName(zoneName: string): string {
    return zoneName.replace(/_/g, ' ')
  }
}

// Create instance
export const apiService = new ApiService()