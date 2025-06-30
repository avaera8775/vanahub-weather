import { ref, onMounted, watch } from 'vue'

const isDark = ref(false)
const storageKey = 'vanahub-theme'

export function useTheme() {
  const initTheme = () => {
    // Load theme from localStorage or detect system preference
    const stored = localStorage.getItem(storageKey)
    
    if (stored !== null) {
      // Use stored preference
      isDark.value = stored === 'dark'
    } else {
      // Use system preference
      isDark.value = window.matchMedia && 
                   window.matchMedia('(prefers-color-scheme: dark)').matches
    }
    
    applyTheme()
  }

  const applyTheme = () => {
    const html = document.documentElement
    
    if (isDark.value) {
      html.classList.add('dark')
    } else {
      html.classList.remove('dark')
    }
    
    // Save to localStorage
    localStorage.setItem(storageKey, isDark.value ? 'dark' : 'light')
  }

  const toggleTheme = () => {
    isDark.value = !isDark.value
    applyTheme()
  }

  const setTheme = (theme: 'light' | 'dark') => {
    isDark.value = theme === 'dark'
    applyTheme()
  }

  const watchSystemTheme = () => {
    // Listen for system theme changes
    if (window.matchMedia) {
      const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)')
      
      mediaQuery.addEventListener('change', (e) => {
        // Only update if user hasn't set a manual preference
        const stored = localStorage.getItem(storageKey)
        if (stored === null) {
          isDark.value = e.matches
          applyTheme()
        }
      })
    }
  }

  // Watch for changes and apply theme
  watch(isDark, applyTheme)

  return {
    isDark,
    initTheme,
    toggleTheme,
    setTheme,
    watchSystemTheme
  }
}
