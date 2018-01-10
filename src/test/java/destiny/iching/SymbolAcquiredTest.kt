/**
 * Created by smallufo on 2015-05-12.
 */
package destiny.iching

import destiny.iching.Symbol.*
import org.slf4j.LoggerFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SymbolAcquiredTest {

  private val logger = LoggerFactory.getLogger(javaClass)

  @Test
  fun testGetIndex() {
    assertEquals(4, SymbolAcquired.getIndex(巽).toLong())
    assertEquals(3, SymbolAcquired.getIndex(震).toLong())
    assertEquals(8, SymbolAcquired.getIndex(艮).toLong())
    assertEquals(9, SymbolAcquired.getIndex(離).toLong())

    assertEquals(1, SymbolAcquired.getIndex(坎).toLong())
    assertEquals(2, SymbolAcquired.getIndex(坤).toLong())
    assertEquals(7, SymbolAcquired.getIndex(兌).toLong())
    assertEquals(6, SymbolAcquired.getIndex(乾).toLong())
  }

  @Test
  fun testGetSymbol() {
    for (i in 0..19) {
      logger.info("index = {} , 後天卦 = {}", i, SymbolAcquired.getSymbol(i))
    }
  }

  @Test
  fun testGetSymbolNullable() {
    assertEquals(坎, SymbolAcquired.getSymbolNullable(1))
    assertEquals(坤, SymbolAcquired.getSymbolNullable(2))
    assertEquals(震, SymbolAcquired.getSymbolNullable(3))
    assertEquals(巽, SymbolAcquired.getSymbolNullable(4))

    assertNull(SymbolAcquired.getSymbolNullable(5))

    assertEquals(乾, SymbolAcquired.getSymbolNullable(6))
    assertEquals(兌, SymbolAcquired.getSymbolNullable(7))
    assertEquals(艮, SymbolAcquired.getSymbolNullable(8))
    assertEquals(離, SymbolAcquired.getSymbolNullable(9))

  }
}