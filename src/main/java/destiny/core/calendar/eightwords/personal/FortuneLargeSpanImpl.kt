/**
 * Created by smallufo on 2018-04-27.
 */
package destiny.core.calendar.eightwords.personal

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import destiny.astrology.Coordinate
import destiny.astrology.IStarTransit
import destiny.astrology.Planet
import destiny.core.Gender
import destiny.core.IIntAge
import destiny.core.IntAgeNote
import destiny.core.calendar.ILocation
import destiny.core.calendar.SolarTerms
import destiny.core.calendar.TimeTools
import destiny.core.calendar.eightwords.EightWordsContext
import destiny.core.calendar.eightwords.IEightWordsContextModel
import destiny.core.chinese.StemBranch
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.time.Duration
import java.time.chrono.ChronoLocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.math.abs


/** 以「出生時刻，到『節』，的固定倍數法」 (內定 120.0倍) 求得大運 */
class FortuneLargeSpanImpl(private val eightWordsContext: EightWordsContext,
                           /** 大運的順逆，內定採用『陽男陰女順排；陰男陽女逆排』的演算法  */
                           private val fortuneDirectionImpl: IFortuneDirection,
                           /** 歲數實作  */
                           private val intAgeImpl: IIntAge,
                           /** 星體運行到某點的介面  */
                           private val starTransitImpl: IStarTransit,
                           /** 運 :「月」的 span 倍數，內定 120，即：一個月干支 擴展(乘以)120 倍，變成十年  */
                           private val fortuneMonthSpan: Double = 120.0,
                           /** 歲數註解實作  */
                           private val ageNoteImpls: List<IntAgeNote>) : IPersonFortuneLarge, Serializable {

  private val logger = LoggerFactory.getLogger(javaClass)

  private val cache: Cache<Pair<Double, Gender>, MutableMap<Int, Double>> = CacheBuilder.newBuilder()
    .maximumSize(100)
    .expireAfterAccess(1, TimeUnit.MINUTES)
    .build<Pair<Double, Gender>, MutableMap<Int, Double>>()

  private fun getAgeMap(toAge: Int,
                        gmtJulDay: Double,
                        gender: Gender,
                        location: ILocation): Map<Int, Pair<Double, Double>> {
    return intAgeImpl.getRangesMap(gender, gmtJulDay, location, 1, toAge)
  }

  /** 順推大運 , 取得該命盤的幾條大運 */
  override fun getFortuneDataList(lmt: ChronoLocalDateTime<*>,
                                  location: ILocation,
                                  gender: Gender,
                                  count: Int): List<FortuneData> {

    val eightWords = eightWordsContext.eightWordsImpl.getEightWords(lmt , location)

    val forward =  fortuneDirectionImpl.isForward(lmt , location , gender)
    val gmtJulDay = TimeTools.getGmtJulDay(lmt, location)

    val ageMap: Map<Int, Pair<Double, Double>> = getAgeMap(120, gmtJulDay, gender, location)


    //下個大運的干支
    var i = 1
    return generateSequence {
      // 距離下個「節」有幾秒
      val startFortuneSeconds = getTargetMajorSolarTermsSeconds(gmtJulDay, gender, i * if (forward) 1 else -1)
      val endFortuneSeconds = getTargetMajorSolarTermsSeconds(gmtJulDay, gender, (i + 1) * if (forward) 1 else -1)

      // 將距離秒數乘上倍數 (ex : 120) , 就可以得知 該大運的起點時刻
      val startFortuneGmtJulDay = gmtJulDay + abs(startFortuneSeconds) * fortuneMonthSpan / 86400.0
      val endFortuneGmtJulDay = gmtJulDay + abs(endFortuneSeconds) * fortuneMonthSpan / 86400.0

      /** 該大運起點 , 歲數為何 . 歲數的定義則由 [IIntAge] 處理 */
      val startFortuneAge = getAge(startFortuneGmtJulDay, ageMap) ?: 0
      val endFortuneAge = getAge(endFortuneGmtJulDay, ageMap) ?: 0

      /** 附加上 西元、民國 之類的註記 */
      val startFortuneAgeNotes: List<String> =
        ageNoteImpls.map { impl -> ageMap[startFortuneAge]?.let { impl.getAgeNote(it) } }.filter { it != null }
          .map { it!! }.toList()
      val endFortuneAgeNotes: List<String> =
        ageNoteImpls.map { impl -> ageMap[endFortuneAge]?.let { impl.getAgeNote(it) } }.filter { it != null }
          .map { it!! }.toList()

      val sb: StemBranch = eightWords.month.let { if (forward) it.next(i) else it.prev(i) }
      i++
      FortuneData(sb, startFortuneGmtJulDay, endFortuneGmtJulDay, startFortuneAge, endFortuneAge,
                  startFortuneAgeNotes, endFortuneAgeNotes)
    }.takeWhile { i <= count + 1 }
      .toList()

  }


  /**
   * 距離下 index 個「節」有幾秒 , 如果 index 為負，代表計算之前的「節」。 index 不能等於 0
   *
   * @return 如果 index 為正，則傳回正值; 如果 index 為負，則傳回負值
   */
  private fun getTargetMajorSolarTermsSeconds(gmtJulDay: Double, gender: Gender, index: Int): Double {
    require(index != 0) { "index cannot be 0 !" }

    val reverse = index < 0

    var stepGmtJulDay = gmtJulDay
    //現在的 節氣
    var currentSolarTerms = eightWordsContext.solarTermsImpl.getSolarTermsFromGMT(gmtJulDay)
    var stepMajorSolarTerms = SolarTerms.getNextMajorSolarTerms(currentSolarTerms, reverse)

    var i: Int = if (!reverse) 1 else -1

    var hashMap: MutableMap<Int, Double>? = cache.getIfPresent(Pair(gmtJulDay, gender))

    if (hashMap == null) {
      hashMap = LinkedHashMap()
      cache.put(Pair(gmtJulDay, gender), hashMap)
    }

    var targetGmtJulDay: Double? = null
    if (hashMap.containsKey(index)) {
      targetGmtJulDay = hashMap[index]
      logger.debug("from map , index = {} , targetGmtJulDay exists , value = {}", index, targetGmtJulDay)
    }

    if (targetGmtJulDay == null) {
      if (!reverse) {
        //順推
        if (hashMap[index - 1] != null)
          i = index - 1

        while (i <= index) {
          // 推算到上一個/下一個「節」的秒數：陽男陰女順推，陰男陽女逆推
          if (hashMap[i] == null) {
            logger.debug("順推 cache.get({}) miss", i)
            //沒有計算過
            targetGmtJulDay = starTransitImpl.getNextTransitGmt(Planet.SUN, stepMajorSolarTerms.zodiacDegree.toDouble(),
                                                                Coordinate.ECLIPTIC, stepGmtJulDay, true)
            //以隔天計算現在節氣
            stepGmtJulDay = targetGmtJulDay + 1

            hashMap[i] = targetGmtJulDay
            cache.put(Pair(gmtJulDay, gender), hashMap)
          } else {
            //之前計算過
            logger.debug("順推 cache.get({}) hit", i)
            targetGmtJulDay = hashMap[i]
            stepGmtJulDay = targetGmtJulDay!! + 1
          }

          currentSolarTerms = eightWordsContext.solarTermsImpl.getSolarTermsFromGMT(stepGmtJulDay)
          stepMajorSolarTerms = SolarTerms.getNextMajorSolarTerms(currentSolarTerms, false)
          i++
        } // while (i <= index)
      } //順推
      else {
        //逆推
        if (hashMap[index + 1] != null)
          i = index + 1

        while (i >= index) {
          // 推算到上一個/下一個「節」的秒數：陽男陰女順推，陰男陽女逆推

          if (hashMap[i] == null) {
            logger.debug("逆推 cache.get({}) miss", i)
            //沒有計算過

            targetGmtJulDay = starTransitImpl.getNextTransitGmt(Planet.SUN, stepMajorSolarTerms.zodiacDegree.toDouble(),
                                                                Coordinate.ECLIPTIC, stepGmtJulDay, false)
            //以前一天計算現在節氣
            stepGmtJulDay = targetGmtJulDay - 1
            hashMap[i] = targetGmtJulDay
            cache.put(Pair(gmtJulDay, gender), hashMap)
          } else {
            //之前計算過
            logger.debug("逆推 cache.get({}) hit", i)
            targetGmtJulDay = hashMap[i]
            stepGmtJulDay = targetGmtJulDay!! - 1 //LocalDateTime.from(targetGmt).minusSeconds(24 * 60 * 60);
          }

          currentSolarTerms = eightWordsContext.solarTermsImpl.getSolarTermsFromGMT(stepGmtJulDay)
          stepMajorSolarTerms = SolarTerms.getNextMajorSolarTerms(currentSolarTerms, true)
          i--
        } //while (i >= index)
      } //逆推
    }

    assert(targetGmtJulDay != null)

    // 同義於 Duration.between(gmt , targetGmtJulDay)
    val durDays = targetGmtJulDay!! - gmtJulDay
    logger.trace("durDays = {} ", durDays)
    return durDays * 86400

  } // getTargetMajorSolarTermsSeconds(int)

  /**
   * @return 在此 gmtJulDay 時刻，座落於歲數的哪一歲當中
   * 可能歲數超出範圍之後，或是根本在出生之前，就會傳回 empty
   */
  private fun getAge(gmtJulDay: Double, ageMap: Map<Int, Pair<Double, Double>>): Int? {
    return ageMap.entries.firstOrNull { (age, pair) -> gmtJulDay > pair.first && pair.second > gmtJulDay }?.key
  }

  /**
   * 逆推大運
   */
  override fun getStemBranch(lmt: ChronoLocalDateTime<*>,
                             location: ILocation,
                             gender: Gender,
                             targetGmt: ChronoLocalDateTime<*>): StemBranch {

    val gmtJulDay = TimeTools.getGmtJulDay(lmt, location)
    val gmt = TimeTools.getGmtFromLmt(lmt, location)


    require(targetGmt.isAfter(gmt)) { "targetGmt $targetGmt must be after birth's time : $gmt" }

    val ewModel: IEightWordsContextModel = eightWordsContext.getEightWordsContextModel(lmt, location, null)
    var resultStemBranch = ewModel.eightWords.month

    // 大運是否順行
    val fortuneForward = fortuneDirectionImpl.isForward(lmt , location , gender)

    val dur = Duration.between(targetGmt, gmt).abs()
    val diffSeconds = dur.seconds + dur.nano / 1_000_000_000.0

    if (fortuneForward) {
      logger.debug("大運順行")
      var index = 1
      while (getTargetMajorSolarTermsSeconds(gmtJulDay, gender, index) * fortuneMonthSpan < diffSeconds) {
        resultStemBranch = resultStemBranch.next
        index++
      }
      return resultStemBranch
    } else {
      logger.debug("大運逆行")
      var index = -1
      while (abs(getTargetMajorSolarTermsSeconds(gmtJulDay, gender, index) * fortuneMonthSpan) < diffSeconds) {
        resultStemBranch = resultStemBranch.previous
        index--
      }
      return resultStemBranch
    }
  }
}