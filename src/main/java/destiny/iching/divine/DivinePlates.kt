/**
 * Created by smallufo on 2018-02-02.
 */
package destiny.iching.divine

import destiny.core.Gender
import destiny.core.calendar.Location
import destiny.core.calendar.eightwords.EightWords
import destiny.core.calendar.eightwords.EightWordsNullable
import destiny.core.calendar.eightwords.IEightWordsNullable
import destiny.core.chinese.Branch
import destiny.core.chinese.SixAnimal
import destiny.core.chinese.StemBranch
import destiny.iching.*
import java.io.Serializable
import java.util.*

interface ICombined {
  val src: IHexagram
  val dst: IHexagram
}

interface IMeta {
  val 納甲系統: String
  val 伏神系統: String
}

data class Meta(override val 納甲系統: String,
                override val 伏神系統: String) : IMeta, Serializable


/**
 * 卜一個卦的最基本資料結構：本卦、變卦
 */
data class Combined(override val src: Hexagram,
                    override val dst: Hexagram) : ICombined, Serializable {
  constructor(src: IHexagram, dst: IHexagram) : this(Hexagram.getHexagram(src), Hexagram.getHexagram(dst))
}


/** 單一卦象，卦名、世爻應爻、六親等資訊 */
interface ISinglePlate : IHexagram {
  val hexagram: IHexagram
  /** 本宮 , 此卦 是八卦哪一宮 */
  val symbol: Symbol
  val 宮序: Int
  /** 1~6 */
  val 世爻: Int
  /** 1~6 */
  val 應爻: Int
  val 納甲: List<StemBranch>
  val 六親: List<Relative>
  val 伏神納甲: List<StemBranch?>
  val 伏神六親: List<Relative?>
}


data class SinglePlate(override val hexagram: Hexagram,
                       /** 本宮 , 此卦 是八卦哪一宮 */
                       override val symbol: Symbol,
                       override val 宮序: Int,
                       /** 1~6 */
                       override val 世爻: Int,
                       /** 1~6 */
                       override val 應爻: Int,
                       override val 納甲: List<StemBranch>,
                       override val 六親: List<Relative>,
                       override val 伏神納甲: List<StemBranch?>,
                       override val 伏神六親: List<Relative?>) : ISinglePlate, IHexagram by hexagram, Serializable


interface ICombinedWithMeta : ICombined , IMeta {
  val srcPlate: ISinglePlate
  val dstPlate: ISinglePlate
  val 變卦對於本卦的六親: List<Relative>
  //val meta: Meta
}

data class CombinedWithMeta(override val srcPlate: ISinglePlate,
                            override val dstPlate: ISinglePlate,
                            override val 變卦對於本卦的六親: List<Relative>,
                            val meta: Meta) : ICombinedWithMeta,
  ICombined by Combined(srcPlate.hexagram, dstPlate.hexagram),
  IMeta by meta,
  Serializable


interface ISinglePlateWithName : ISinglePlate, IHexagramName

data class SinglePlateWithName(private val singlePlate: SinglePlate,
                               private val hexagramName: HexagramName) :
  ISinglePlateWithName,
  ISinglePlate by singlePlate,
  IHexagramName by hexagramName,
  Serializable


/** 合併卦象，只有卦名，沒有其他卦辭、爻辭等文字，也沒有日期時間等資料 (for 經文易排盤後對照) */
interface ICombinedWithMetaName : ICombinedWithMeta {
  override val srcPlate: ISinglePlateWithName
  override val dstPlate: ISinglePlateWithName
}

/** [Combined] + [Meta] + [HexagramName] */
data class CombinedWithMetaName(override val srcPlate: SinglePlateWithName,
                                override val dstPlate: SinglePlateWithName,
                                override val 變卦對於本卦的六親: List<Relative>,
                                val meta: Meta) : ICombinedWithMetaName,
  ICombinedWithMeta by CombinedWithMeta(srcPlate, dstPlate, 變卦對於本卦的六親, meta), Serializable


/** 具備「日干支」「月令」 , 可以排出六獸 [SixAnimal] 以及神煞 , 但不具備完整時間，也沒有起卦方法 ( [DivineApproach] ) */
interface ICombinedWithMetaNameDayMonth : ICombinedWithMetaName, IEightWordsNullable {
  val day: StemBranch
  val monthBranch: Branch

  val 空亡: Set<Branch>
  val 驛馬: Branch
  val 桃花: Branch
  val 貴人: Set<Branch>
  val 羊刃: Branch
  val 六獸: List<SixAnimal>
}

/** 具備「日干支」「月令」 , 可以排出六獸 [SixAnimal] 以及神煞 , 但不具備完整時間，也沒有起卦方法 ( [DivineApproach] ) , 八字一定要包含 日干支 以及 月支  */
data class CombinedWithMetaNameDayMonth(private val combinedWithMetaName: CombinedWithMetaName,
                                        override val eightWordsNullable: EightWordsNullable,
                                        override val 空亡: Set<Branch>,
                                        override val 驛馬: Branch,
                                        override val 桃花: Branch,
                                        override val 貴人: Set<Branch>,
                                        override val 羊刃: Branch,
                                        override val 六獸: List<SixAnimal>) :
  ICombinedWithMetaNameDayMonth,
  ICombinedWithMetaName by combinedWithMetaName, Serializable {

  override val day = eightWordsNullable.day.let { StemBranch[it.stem!!, it.branch!!] }
  override val monthBranch = eightWordsNullable.month.branch!!

  init {
    if (eightWordsNullable.day.stem == null || eightWordsNullable.day.branch == null && eightWordsNullable.month.branch == null) {
      throw RuntimeException("八字 必須包含 日干支 以及 月支 ")
    }
  }
}

interface ICombinedWithMetaNameTexts : ICombinedWithMetaName {
  val pairTexts: Pair<HexagramText, HexagramText>
}


/**
 * 額外的卦象資訊，對整體卦象輸出沒有影響
 */
interface IDivineMeta : IMeta {
  val gender: Gender?
  val question: String?
  val approach: DivineApproach?
  val gmtJulDay: Double?
  val loc: Location?
  val place: String?
  /** 已經 format 的時間 */
  val decoratedTime: String?
}


data class DivineMeta(override val gender: Gender?,
                      override val question: String?,
                      override val approach: DivineApproach?,
                      override val gmtJulDay: Double? = null,
                      override val loc: Location? = Location.of(Locale.TAIWAN),
                      override val place: String?,
                      /** 已經 format 的時間 */
                      override val decoratedTime: String?,
                      val meta: Meta,
                      val link: String?) : IMeta by meta, IDivineMeta, Serializable


/** 完整卜卦盤 , 包含所有資料 */
interface ICombinedFull : ICombinedWithMetaNameDayMonth, ICombinedWithMetaNameTexts, IDivineMeta

/** 完整卜卦盤 , 具備完整八字 */
data class CombinedFull(private val combinedWithMetaNameDayMonth: CombinedWithMetaNameDayMonth,
                        val eightWords: EightWords,
                        private val divineMeta: DivineMeta,
                        override val pairTexts: Pair<HexagramText, HexagramText>) :
  ICombinedFull,
  ICombinedWithMetaNameDayMonth by combinedWithMetaNameDayMonth,
  IDivineMeta by divineMeta,
  IEightWordsNullable by eightWords,
  ICombinedWithMetaNameTexts,
  Serializable {

  override val 納甲系統: String
    get() = divineMeta.納甲系統
  override val 伏神系統: String
    get() = divineMeta.伏神系統
  override val eightWordsNullable = eightWords
}