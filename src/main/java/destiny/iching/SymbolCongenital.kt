/**
 * 先天八卦
 * @author smallufo
 * Created on 2002/8/13 at 下午 01:13:22
 */
package destiny.iching

import destiny.iching.Symbol.*
import java.util.*

/**
 * <pre>
 * 先天八卦
 *
 * 兌2 | 乾1 | 巽5
 * ----+-----+----
 * 離3 |     | 坎6
 * ----+-----+----
 * 震4 | 坤8 | 艮7
</pre> *
 */
class SymbolCongenital internal constructor() : Comparator<Symbol> {

  override fun compare(s1: Symbol, s2: Symbol): Int {
    return 先天八卦.indexOf(s1) - 先天八卦.indexOf(s2)
  }

  companion object {
    private val 先天八卦 = arrayOf(乾, 兌, 離, 震, 巽, 坎, 艮, 坤)

    /**
     * 取得先天八卦的卦序
     * 乾1 , 兌2 , ... , 坤 8
     */
    fun getIndex(s: Symbol): Int {
      return 先天八卦.indexOf(s) + 1
    }

    /**
     * 由先天八卦卦序取得 Symbol
     * 0->坤 , 1->乾 , 2->兌 , ... , 8->坤 , 繼續循環 9->乾...
     */
    fun getSymbol(index: Int): Symbol {
      if (index <= 0)
        return getSymbol(index + 8)
      if (index > 8)
        return getSymbol(index - 8)

      var remainder = index % 8
      if (remainder == 0)
        remainder = 8
      return 先天八卦[remainder-1]
    }

    /**
     * 以順時針方向取得一卦
     */
    fun getClockwiseSymbol(s: Symbol): Symbol {
      return when (s) {
        乾 -> 巽
        兌 -> 乾
        離 -> 兌
        震 -> 離
        巽 -> 坎
        坎 -> 艮
        艮 -> 坤
        坤 -> 震
      }
    }

    /**
     * 對沖的卦
     */
    fun getOppositeSymbol(s: Symbol): Symbol {
      return when (s) {
        乾 -> 坤
        兌 -> 艮
        離 -> 坎
        震 -> 巽
        巽 -> 震
        坎 -> 離
        艮 -> 兌
        坤 -> 乾
      }
    }
  }

}