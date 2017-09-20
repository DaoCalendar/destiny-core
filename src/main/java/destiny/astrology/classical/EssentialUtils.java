/**
 * @author smallufo 
 * Created on 2008/1/2 at 下午 4:08:25
 */ 
package destiny.astrology.classical;

import destiny.astrology.*;
import org.jetbrains.annotations.NotNull;

/** EssentialIF 的工具箱 */
public class EssentialUtils
{
  private EssentialIF essentialImpl = new EssentialDefaultImpl();
  
  /** 內定採用 Swiss Ephemeris 的 DayNightDifferentiatorImpl */
  private DayNightDifferentiator dayNightDifferentiatorImpl;// = new DayNightDifferentiatorImpl(new RiseTransImpl());
  
  public EssentialUtils(DayNightDifferentiator dayNightDifferentiatorImpl)
  {
    this.dayNightDifferentiatorImpl = dayNightDifferentiatorImpl;
  }
  
  /** 
   * receiver 是否 接納 receivee by Essential Dignities (Ruler/Exaltation/Triplicity/Term/Face) <br/>
   * 老闆是 receiver , 客人是 receivee , 如果客人進入了老闆的地盤 ( 旺 / 廟 / 三分 / Terms / Faces ) , 則「老闆接納外人」 
   * */
  public boolean isReceivingFromDignities(Point receiver , Point receivee , @NotNull Horoscope h)
  {
    ZodiacSign receiveeSign = h.getZodiacSign(receivee);
    //比對 Ruler
    if (essentialImpl.getPoint(receiveeSign, Dignity.RULER) == receiver)
    {
      //System.out.println(receiver + " 透過 " + Dignity.RULER + " 接納 " + receivee);
      return true;
    }
    //比對 Exaltation
    if (essentialImpl.getPoint(receiveeSign, Dignity.EXALTATION) == receiver)
    {
      //System.out.println(receiver + " 透過 " + Dignity.EXALTATION + " 接納 " + receivee);
      return true;
    }
    //比對 Triplicity
    if (essentialImpl.getTriplicityPoint(receiveeSign, dayNightDifferentiatorImpl.getDayNight(h.getLmt(), h.getLocation())) == receiver)
    {
      //System.out.println(receiver + " 透過 TRIPLICITY 接納 " + receivee);
      return true;
    }
    //比對 Terms
    if (essentialImpl.getTermsPoint(h.getPosition(receivee).getLng()) == receiver)
    {
      //System.out.println(receiver + " 透過 TERMS 接納 " + receivee);
      return true;
    }
    //比對 Face
    return essentialImpl.getFacePoint(h.getPosition(receivee).getLng()) == receiver;
  }
  
  /** receiver 是否 接納 receivee by Essential Debilities (Detriment/Fall) */
  public boolean isReceivingFromDebilities(Point receiver , Point receivee , @NotNull Horoscope h)
  {
    ZodiacSign receiveeSign = h.getZodiacSign(receivee);
    if (essentialImpl.getPoint(receiveeSign , Dignity.DETRIMENT) == receiver)
      return true;
    return essentialImpl.getPoint(receiveeSign, Dignity.FALL) == receiver;
  }
  
  /** Ruler 互訪 , 還沒確認是 優質互容 */
  public boolean isBothRulerVisit(Point planet , @NotNull Horoscope h)
  {
    //取得此 Planet 在什麼星座
    ZodiacSign sign = h.getZodiacSign(planet);
    
    Point signRuler = essentialImpl.getPoint(sign, Dignity.RULER);
    ZodiacSign sign2 = h.getZodiacSign(signRuler);
    Point planet2 = essentialImpl.getPoint(sign2, Dignity.RULER);
    return planet == planet2;
  }
  
  /** 如果其中一顆星處於 Dignity.DETRIMENT 或是 Dignity.FALL , 則為 true */
  public boolean isOneInBadSituation(Point p1 , ZodiacSign sign1 , Point p2 , ZodiacSign sign2)
  {
    return (p1 == essentialImpl.getPoint(sign1, Dignity.DETRIMENT) || p1 == essentialImpl.getPoint(sign1, Dignity.FALL)) || (p2 == essentialImpl.getPoint(sign2, Dignity.DETRIMENT) || p2 == essentialImpl.getPoint(sign2, Dignity.FALL));
  }
  
  /** 如果 兩顆星都處於 Dignity.DETRIMENT 或是 Dignity.FALL , 則為 true */
  public boolean isBothInBadSituation(Point p1 , ZodiacSign sign1 , Point p2 , ZodiacSign sign2)
  {
    return (p1 == essentialImpl.getPoint(sign1, Dignity.DETRIMENT) || p1 == essentialImpl.getPoint(sign1, Dignity.FALL)) && (p2 == essentialImpl.getPoint(sign2, Dignity.DETRIMENT) || p2 == essentialImpl.getPoint(sign2, Dignity.FALL));
  }

  public void setEssentialImpl(EssentialIF essentialImpl)
  {
    this.essentialImpl = essentialImpl;
  }

  public void setDayNightDifferentiatorImpl(DayNightDifferentiator dayNightDifferentiatorImpl)
  {
    this.dayNightDifferentiatorImpl = dayNightDifferentiatorImpl;
  }
}
