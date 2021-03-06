/**
 * Created by smallufo on 2019-09-27.
 */
package destiny.astrology.classical

import destiny.astrology.Planet
import destiny.astrology.ZodiacSign
import kotlin.test.Test
import kotlin.test.assertEquals

class MutualDataWithSignTest {

  @Test
  fun testEqual() {
    val m1 = MutualDataWithSign(Planet.VENUS, ZodiacSign.GEMINI, Dignity.RULER, Planet.MERCURY, ZodiacSign.TAURUS, Dignity.RULER)
    val m2 = MutualDataWithSign(Planet.MERCURY, ZodiacSign.TAURUS, Dignity.RULER, Planet.VENUS, ZodiacSign.GEMINI, Dignity.RULER)
    assertEquals(m1 , m2)
  }
}
