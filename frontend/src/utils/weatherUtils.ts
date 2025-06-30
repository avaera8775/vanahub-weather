// Vana'diel time conversion utilities
// Constants from the Java backend
const VANA_EPOCH = 1009810800 // Jan 1, 2002 00:00:00 JST
const VANA_DAY_LENGTH = 2300 // 2300 seconds per Vana'diel day

export interface TimeRemaining {
  hours: number
  minutes: number
  seconds: number
  totalSeconds: number
  isActive: boolean
}

export class WeatherUtils {
  /**
   * Get current Vana'diel time (matches Java getCurrentVanaTime)
   */
  static getCurrentVanaTime(): number {
    return Math.floor(Date.now() / 1000) - VANA_EPOCH
  }

  /**
   * Calculate when a specific Vana'diel day becomes active in Earth time
   */
  static getEarthTimeForVanaDay(daysForward: number): Date {
    const currentVanaTime = this.getCurrentVanaTime()
    const currentVanaDay = Math.floor(currentVanaTime / VANA_DAY_LENGTH)
    const targetVanaDay = currentVanaDay + daysForward

    // Calculate when the target Vana'diel day starts
    const targetVanaTime = targetVanaDay * VANA_DAY_LENGTH
    const targetEarthTime = (targetVanaTime + VANA_EPOCH) * 1000 // Convert to milliseconds

    return new Date(targetEarthTime)
  }

  /**
   * Calculate time remaining until a weather change occurs
   */
  static getTimeUntilWeatherChange(daysForward: number): TimeRemaining {
    const targetTime = this.getEarthTimeForVanaDay(daysForward)
    const now = new Date()
    const diffMs = targetTime.getTime() - now.getTime()

    if (diffMs <= 0) {
      // This weather is currently active
      if (daysForward === 0) {
        return {
          hours: 0,
          minutes: 0,
          seconds: 0,
          totalSeconds: 0,
          isActive: true
        }
      }

      // Past weather - shouldn't happen in normal usage
      return {
        hours: 0,
        minutes: 0,
        seconds: 0,
        totalSeconds: 0,
        isActive: false
      }
    }

    const totalSeconds = Math.floor(diffMs / 1000)
    const hours = Math.floor(totalSeconds / 3600)
    const minutes = Math.floor((totalSeconds % 3600) / 60)
    const seconds = totalSeconds % 60

    return {
      hours,
      minutes,
      seconds,
      totalSeconds,
      isActive: false
    }
  }

  /**
   * Format time remaining as a human-readable string
   */
  static formatTimeRemaining(timeRemaining: TimeRemaining): string {
    if (timeRemaining.isActive) {
      return 'Active now'
    }

    if (timeRemaining.totalSeconds <= 0) {
      return 'Past'
    }

    const parts: string[] = []

    if (timeRemaining.hours > 0) {
      parts.push(`${timeRemaining.hours}h`)
    }

    if (timeRemaining.minutes > 0) {
      parts.push(`${timeRemaining.minutes}m`)
    }

    if (timeRemaining.hours === 0) {
      parts.push(`${timeRemaining.seconds}s`)
    }

    return parts.join(' ')
  }

  /**
   * Get a more detailed time format for longer durations
   */
  static formatDetailedTimeRemaining(timeRemaining: TimeRemaining): string {
    if (timeRemaining.isActive) {
      return 'Active now'
    }

    if (timeRemaining.totalSeconds <= 0) {
      return 'Past'
    }

    const totalHours = timeRemaining.hours
    const days = Math.floor(totalHours / 24)
    const hours = totalHours % 24

    if (days > 0) {
      return `${days}d ${hours}h ${timeRemaining.minutes}m`
    }

    if (hours > 0) {
      return `${hours}h ${timeRemaining.minutes}m ${timeRemaining.seconds}s`
    }

    return `${timeRemaining.minutes}m ${timeRemaining.seconds}s`
  }
}
