/*
 * @author smallufo
 * @date 2004/10/23
 * @time 上午 03:36:11
 */
package destiny.core.calendar;

import destiny.astrology.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 節氣實作
 */
public class SolarTermsImpl implements SolarTermsIF, Serializable {

  private StarTransitIF starTransitImpl;

  private StarPositionIF starPositionImpl;

  protected SolarTermsImpl() {
  }

  public SolarTermsImpl(StarTransitIF StarTransitImpl, StarPositionIF starPositionImpl) {
    this.starTransitImpl = StarTransitImpl;
    this.starPositionImpl = starPositionImpl;
  }

  /**
   * 計算某時刻當下的節氣
   * 步驟：
   * 1. 計算太陽在黃道面的度數
   * 2. 比對此度數 , 將此度數除以 15 取整數
   * 3. 將以上的值代入 SolarTermsArray[int] 即是答案
   */
  public SolarTerms getSolarTermsFromGMT(double gmtJulDay) {
    // Step 1: Calculate the Longitude of SUN
    Position sp = starPositionImpl.getPosition(Planet.SUN, gmtJulDay, Centric.GEO, Coordinate.ECLIPTIC);
    // Step 2
    int index = (int) (sp.getLongitude() / 15) + 3;
    if (index >= 24)
      index = index - 24;
    return SolarTerms.get(index);
  }

  /**
   * 計算從某時(fromGmtTime) 到某時(toGmtTime) 之間的節氣 , in GMT
   *
   * @return List <SolarTermsTime>
   */
  @NotNull
  @Override
  public List<SolarTermsTime> getPeriodSolarTerms(double fromGmt, double toGmt) {
    SolarTerms nowST = getSolarTermsFromGMT(fromGmt);

    int nextZodiacDegree = (int) destiny.astrology.Utils.getNormalizeDegree(nowST.getZodiacDegree() + 15);

    List<SolarTermsTime> resultList = new ArrayList<>();

    while (fromGmt < toGmt) {
      SolarTermsTime solarTermsTime;

      LocalDateTime fromGmtTime = starTransitImpl.getNextTransitLocalDateTime(Planet.SUN, nextZodiacDegree, Coordinate.ECLIPTIC, fromGmt, true);
      fromGmt = Time.getGmtJulDay(fromGmtTime);

      if (fromGmt > toGmt)
        break;
      nowST = nowST.next();
      solarTermsTime = new SolarTermsTime(nowST, fromGmtTime);
      resultList.add(solarTermsTime);
      nextZodiacDegree = (int) destiny.astrology.Utils.getNormalizeDegree(nextZodiacDegree + 15);
    }

    return resultList;
  }


}
