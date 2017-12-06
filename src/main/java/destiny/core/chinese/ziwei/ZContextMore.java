/**
 * Created by smallufo on 2017-04-27.
 */
package destiny.core.chinese.ziwei;

import destiny.core.Descriptive;
import destiny.core.IntAgeNote;
import destiny.core.calendar.chinese.ChineseDateIF;
import destiny.core.calendar.chinese.IFinalMonthNumber;
import destiny.core.calendar.eightwords.*;
import destiny.core.chinese.FortuneOutput;
import destiny.core.chinese.TianyiIF;

import java.util.*;

/**
 * 純粹「設定」，並不包含 生日、性別、出生地 等資訊
 *
 * 另外附加 與紫微「計算」無關的設定
 * 例如
 * 是否顯示小限
 * 真太陽時(還是手錶平均時間)
 * 八字排列方向
 */
public class ZContextMore extends ZContext {

  /** 宮干四化「自化」 顯示選項 */
  public enum SelfTransFour implements Descriptive {
    SELF_TRANS_FOUR_NONE,   /** 不顯示 */
    SELF_TRANS_FOUR_TEXT,   /** 文字顯示 */
    SELF_TRANS_FOUR_ARROW,;   /** 箭頭朝外 */

    @Override
    public String getTitle(Locale locale) {
      return ResourceBundle.getBundle(ZContextMore.class.getName(), locale).getString(name());
    }

    @Override
    public String getDescription(Locale locale) {
      return getTitle(locale);
    }
  }
  private final SelfTransFour selfTransFour;

  /** 宮干四化「化入對宮」的顯示選項 */
  public enum OppoTransFour implements Descriptive {
    OPPO_TRANS_FOUR_NONE,   /** 不顯示 */
    OPPO_TRANS_FOUR_ARROW,; /** 朝內(對宮) 箭頭 */

    @Override
    public String getTitle(Locale locale) {
      return ResourceBundle.getBundle(ZContextMore.class.getName(), locale).getString(name());
    }

    @Override
    public String getDescription(Locale locale) {
      return getTitle(locale);
    }
  }
  private final OppoTransFour oppoTransFour;

  /** 是否顯示小限 */
  private final boolean showSmallRange;

  /** 民用曆法 or 天文曆法 */
  protected final ChineseDateIF chineseDateImpl;

  /** 是否顯示八字盤 */
  protected final boolean showEightWords;

  /** 八字排盤，右至左 or 左至右 */
  protected final Direction direction;

  /** 時辰劃分 */
  protected final HourIF hourImpl;

  /** 子正判定 */
  protected final MidnightIF midnightImpl;

  /** 子初換日 (true) 或 子正換日 (false) */
  private final boolean changeDayAfterZi;

  /** 顯示雜曜 */
  private final boolean showMinors;

  /** 顯示博士12神煞 */
  private final boolean showDoctors;

  /** 顯示長生12神煞 */
  private final boolean showLongevity;

  /** 顯示 將前12星 */
  private final boolean showGeneralFront;

  /** 顯示 歲前12星 */
  private final boolean showYearFront;


  public ZContextMore(IMainBodyHouse mainBodyHouseImpl, IPurpleStarBranch purpleBranchImpl,
                      IFinalMonthNumber.MonthAlgo mainStarsMonthAlgo, IFinalMonthNumber.MonthAlgo monthStarsMonthAlgo,
                      YearType yearType, IHouseSeq houseSeqImpl, TianyiIF tianyiImpl, FireBell fireBell,
                      HurtAngel hurtAngel, ITransFour transFourImpl, IStrength strengthImpl,
                      IFlowYear flowYearImpl, IFlowMonth flowMonthImpl, IFlowDay flowDayImpl, IFlowHour flowHourImpl,
                      List<IntAgeNote> ageNoteImpls, IBigRange bigRangeImpl, RedBeauty redBeauty,
                      SelfTransFour selfTransFour, OppoTransFour oppoTransFour, boolean showSmallRange,
                      ChineseDateIF chineseDateImpl, boolean showEightWords, Direction direction, HourIF hourImpl,
                      MidnightIF midnightImpl, YearMonthIF yearMonthImpl, DayIF dayImpl, boolean changeDayAfterZi,
                      boolean showMinors, boolean showDoctors, boolean showLongevity, boolean showGeneralFront, boolean showYearFront) {
    super(mainBodyHouseImpl, purpleBranchImpl, mainStarsMonthAlgo, monthStarsMonthAlgo, yearType, houseSeqImpl, tianyiImpl, fireBell, hurtAngel,
      transFourImpl, strengthImpl , flowYearImpl, flowMonthImpl, flowDayImpl, flowHourImpl, FortuneOutput.虛歲, ageNoteImpls, bigRangeImpl, redBeauty);
    this.selfTransFour = selfTransFour;
    this.oppoTransFour = oppoTransFour;
    this.showSmallRange = showSmallRange;
    this.chineseDateImpl = chineseDateImpl;
    this.showEightWords = showEightWords;
    this.direction = direction;
    this.hourImpl = hourImpl;
    this.midnightImpl = midnightImpl;
    this.changeDayAfterZi = changeDayAfterZi;
    this.showMinors = showMinors;
    this.showDoctors = showDoctors;
    this.showLongevity = showLongevity;
    this.showGeneralFront = showGeneralFront;
    this.showYearFront = showYearFront;
  }

  public SelfTransFour getSelfTransFour() {
    return selfTransFour;
  }

  public OppoTransFour getOppoTransFour() {
    return oppoTransFour;
  }

  public boolean isShowSmallRange() {
    return showSmallRange;
  }

  public boolean isShowEightWords() {
    return showEightWords;
  }

  public Direction getDirection() {
    return direction;
  }

  public HourIF getHourImpl() {
    return hourImpl;
  }

  public ChineseDateIF getChineseDateImpl() {
    return chineseDateImpl;
  }

  public MidnightIF getMidnightImpl() {
    return midnightImpl;
  }

  public boolean isChangeDayAfterZi() {
    return changeDayAfterZi;
  }

  public boolean isShowMinors() {
    return showMinors;
  }

  public boolean isShowDoctors() {
    return showDoctors;
  }

  public boolean isShowLongevity() {
    return showLongevity;
  }

  public boolean isShowGeneralFront() {
    return showGeneralFront;
  }

  public boolean isShowYearFront() {
    return showYearFront;
  }

  public List<ZStar> getStars() {
    List<ZStar> starList = new ArrayList<>();
    starList.addAll(Arrays.asList(StarMain.Companion.getValues()));
    starList.addAll(Arrays.asList(StarLucky.Companion.getValues()));
    starList.addAll(Arrays.asList(StarUnlucky.Companion.getValues()));
    if (showMinors)
      starList.addAll(Arrays.asList(StarMinor.Companion.getValues()));

    if (showDoctors)
      starList.addAll(Arrays.asList(StarDoctor.Companion.getValues()));

    if (showLongevity)
      starList.addAll(Arrays.asList(StarLongevity.Companion.getValues()));

    if (showGeneralFront)
      starList.addAll(Arrays.asList(StarGeneralFront.Companion.getValues()));

    if (showYearFront)
      starList.addAll(Arrays.asList(StarYearFront.Companion.getValues()));
    return starList;
  }


  @Override
  public String toString() {
    return "[ZContextMore " + "purpleBranchImpl=" + purpleBranchImpl + ", selfTransFour=" + selfTransFour + ", oppoTransFour=" + oppoTransFour + ", showSmallRange=" + showSmallRange + ", direction=" + direction + ", houseSeqImpl=" + houseSeqImpl + ", hourImpl=" + hourImpl + ", midnightImpl=" + midnightImpl + ", tianyiImpl=" + tianyiImpl + ", changeDayAfterZi=" + changeDayAfterZi + ", showMinors=" + showMinors + ", showDoctors=" + showDoctors + ", showLongevity=" + showLongevity + ", transFourImpl=" + transFourImpl + ", strengthImpl=" + strengthImpl + ", flowYearImpl=" + flowYearImpl + ", flowMonthImpl=" + flowMonthImpl + ", flowDayImpl=" + flowDayImpl + ", flowHourImpl=" + flowHourImpl + ", bigRangeImpl=" + bigRangeImpl + ']';
  }
}
