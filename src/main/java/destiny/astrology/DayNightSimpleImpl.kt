/**
 * Created by smallufo on 2017-12-24.
 */
package destiny.astrology

import destiny.core.DayNight
import destiny.core.calendar.ILocation
import destiny.core.calendar.JulDayResolver1582CutoverImpl
import destiny.core.calendar.TimeTools
import destiny.tools.Domain
import destiny.tools.Impl
import destiny.tools.converters.Domains.KEY_DAY_NIGHT
import java.io.Serializable
import java.time.temporal.ChronoField
import java.util.*

@Impl([Domain(KEY_DAY_NIGHT, DayNightSimpleImpl.VALUE)])
class DayNightSimpleImpl : IDayNight, Serializable {

  override fun getDayNight(gmtJulDay: Double, location: ILocation): DayNight {
    val lmt = TimeTools.getLmtFromGmt(revJulDayFunc.invoke(gmtJulDay), location)
    val hour = lmt.get(ChronoField.HOUR_OF_DAY)
    return if (hour in 6..17)
      DayNight.DAY
    else
      DayNight.NIGHT
  }

  override fun toString(locale: Locale): String {
    return "最基本的區分法"
  }

  override fun getDescription(locale: Locale): String {
    return "六至十八為白天，其餘為晚上。僅能作為 Test 使用，勿用於 Production 環境"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is DayNightSimpleImpl) return false
    return true
  }

  override fun hashCode(): Int {
    return javaClass.hashCode()
  }


  companion object {
    const val VALUE: String = "simple"
    private val revJulDayFunc = { value: Double -> JulDayResolver1582CutoverImpl.getLocalDateTimeStatic(value) }
  }


}
