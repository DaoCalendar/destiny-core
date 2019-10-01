/**
 * Created by smallufo on 2017-12-18.
 */
package destiny.astrology.classical.rules

import destiny.astrology.Planet
import destiny.astrology.ZodiacSign
import destiny.core.DayNight

/**
 * 行星的 25種狀態
 * https://site.douban.com/183595/widget/notes/192509582/note/600376742/
 */

sealed class EssentialDignity(override val name: String,
                              override val planet: Planet,
                              override val notes: String? = null) : IPlanetPattern {

  override val type: RuleType = RuleType.ESSENTIAL

  data class Ruler(override val planet: Planet, val sign: ZodiacSign) : EssentialDignity(Ruler::class.java.simpleName, planet)
  data class Exaltation(override val planet: Planet, val sign: ZodiacSign) : EssentialDignity(Exaltation::class.java.simpleName, planet)
  data class Triplicity(override val planet: Planet, val sign: ZodiacSign, val dayNight: DayNight) : EssentialDignity(Triplicity::class.java.simpleName, planet)
  data class Term(override val planet: Planet, val lngDeg: Double) : EssentialDignity(Term::class.java.simpleName, planet)
  data class Face(override val planet: Planet, val lngDeg: Double) : EssentialDignity(Face::class.java.simpleName, planet)
}
//
///** p1 以 dig1 的能量招待 (接納) p2 , p2 以 dig2 的能量招待 (接納) p1 */
//sealed class Mutual(private val p1: Planet, private val dig1: Dignity,
//                    private val p2: Planet, private val dig2: Dignity,
//                    override val notes: String? = null) : IMutualPattern, IMutualData by MutualData(p1, dig1, p2, dig2) {
//
//  override val name: String
//    get() = javaClass.simpleName
//
//  /** 好的能量，互相接待 */
//  sealed class Reception(p1: Planet, dig1: Dignity, p2: Planet, dig2: Dignity) : Mutual(p1, dig1, p2, dig2) {
//    class Equal(p1: Planet, sign1: ZodiacSign,
//                p2: Planet, sign2: ZodiacSign, dignity: Dignity) : Reception(p1, dignity, p2, dignity), IMutualData by MutualDataWithSign(p1, sign1, dignity, p2, sign2, dignity)
//
//    class Mixed(p1: Planet, sign1: ZodiacSign, dig1: Dignity,
//                p2: Planet, sign2: ZodiacSign, dig2: Dignity) : Reception(p1, dig1, p2, dig2), IMutualData by MutualDataWithSign(p1, sign1, dig1, p2, sign2, dig2)
//  }
//
//  /** 互相踩對方痛腳 */
//  sealed class Deception(p1: Planet, dig1: Dignity, p2: Planet, dig2: Dignity) : Mutual(p1, dig1, p2, dig2) {
//    class Equal(p1: Planet, sign1: ZodiacSign,
//                p2: Planet, sign2: ZodiacSign, dignity: Dignity) : Deception(p1, dignity, p2, dignity)
//
//    class Mixed(p1: Planet, sign1: ZodiacSign, dig1: Dignity,
//                p2: Planet, sign2: ZodiacSign, dig2: Dignity) : Deception(p1, dig1, p2, dig2)
//  }
//}
//
///**
// * p1 與 p2 透過 相同的 [dignity] 互相接納
// * 僅適用於 [Dignity.RULER] , [Dignity.EXALTATION] , [Dignity.TRIPLICITY] , [Dignity.FALL] , [Dignity.DETRIMENT]
// * 剩下的 [Dignity.TERM] 以及 [Dignity.FACE] 需要「度數」，因此不適用
// * */
//@Deprecated("可能用不到")
//sealed class MutualReception(val p1: Planet, val sign1: ZodiacSign,
//                             val p2: Planet, val sign2: ZodiacSign,
//                             val dignity: Dignity, override val notes: String? = null) : IMutualPattern, IMutualData by MutualData(p1, dignity, p2, dignity) {
//
//  override val name: String = javaClass.simpleName
//
//  /** p1 飛至 sign1 , sign1 的主人是 p2 , p2 飛至 sign2 , sign2 的主人是 p1 .  則 , p1 , p2 透過 [Dignity.RULER] 互容 */
//  class ByRuler(p1: Planet, sign1: ZodiacSign, p2: Planet, sign2: ZodiacSign) : MutualReception(p1, sign1, p2, sign2, Dignity.RULER)
//
//  class ByExalt(p1: Planet, sign1: ZodiacSign, p2: Planet, sign2: ZodiacSign) : MutualReception(p1, sign1, p2, sign2, Dignity.EXALTATION)
//
//  class ByTriplicity(p1: Planet, sign1: ZodiacSign, p2: Planet, sign2: ZodiacSign) : MutualReception(p1, sign1, p2, sign2, Dignity.TRIPLICITY)
//
//  /** deg1 , deg2 指的是「黃道帶」上的度數 , 並非是「該星座」的度數 */
//  class ByTerm(p1: Planet, sign1: ZodiacSign, deg1: Double, p2: Planet, sign2: ZodiacSign, deg2: Double) : MutualReception(p1, sign1, p2, sign2, Dignity.TERM)
//
//  /** deg1 , deg2 指的是「黃道帶」上的度數 , 並非是「該星座」的度數 */
//  class ByFace(p1: Planet, sign1: ZodiacSign, deg1: Double, p2: Planet, sign2: ZodiacSign, deg2: Double) : MutualReception(p1, sign1, p2, sign2, Dignity.FACE)
//}
