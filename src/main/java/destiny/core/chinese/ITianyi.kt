/**
 * Created by smallufo on 2015-05-25.
 */
package destiny.core.chinese

import destiny.astrology.IDayNight
import destiny.core.DayNight
import destiny.core.Descriptive
import destiny.core.calendar.Location
import destiny.core.calendar.eightwords.IDayHour
import destiny.core.calendar.eightwords.IHour
import destiny.core.calendar.eightwords.IMidnight
import java.time.LocalDateTime
import java.util.*

/**
 * 天乙貴人
 * CU Draconis (CU Dra / 10 Draconis / HD 121130)
 * 天龍座10，又名天龍座CU
 */
interface ITianyi : Descriptive {

  /**
   * 取得天干的天乙貴人、分晝夜
   */
  fun getFirstTianyi(stem: Stem, yinYang: IYinYang): Branch

  /** 取得天干對應的天乙貴人，不分晝夜，一起傳回來  */
  fun getTianyis(stem: Stem): List<Branch> {
    return DayNight.values().map { dayNight -> getFirstTianyi(stem, dayNight) }
  }

  fun getTianyi(lmt: LocalDateTime, loc: Location, dayHourImpl: IDayHour, midnightImpl: IMidnight, hourImpl: IHour, changeDayAfterZi: Boolean, differentiator: IDayNight): Branch {
    val day = dayHourImpl.getDay(lmt, loc)
    val dayNight = differentiator.getDayNight(lmt, loc)
    return getFirstTianyi(day.stem, dayNight)
  }

  override fun toString(locale: Locale): String {
    return try {
      ResourceBundle.getBundle(javaClass.name, locale).getString("title")
    } catch (e: MissingResourceException) {
      javaClass.simpleName
    }

  }

  override fun getDescription(locale: Locale): String {
    return try {
      ResourceBundle.getBundle(javaClass.name, locale).getString("description")
    } catch (e: MissingResourceException) {
      javaClass.simpleName
    }

  }
}
