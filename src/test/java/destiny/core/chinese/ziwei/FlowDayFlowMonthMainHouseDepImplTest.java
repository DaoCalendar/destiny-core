/**
 * Created by smallufo on 2017-04-17.
 */
package destiny.core.chinese.ziwei;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static destiny.core.chinese.Branch.丑;
import static destiny.core.chinese.Branch.子;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class FlowDayFlowMonthMainHouseDepImplTest {

  private Logger logger = LoggerFactory.getLogger(getClass());

  IFlowDay impl = new FlowDayFlowMonthMainHouseDepImpl();

  @Test
  public void testString() {
    assertNotNull(impl.getTitle(Locale.TAIWAN));
    assertNotNull(impl.getTitle(Locale.SIMPLIFIED_CHINESE));
    logger.info("title tw = {} , cn = {}", impl.getTitle(Locale.TAIWAN), impl.getTitle(Locale.SIMPLIFIED_CHINESE));
  }

  /**
   * 根據此頁面範例 https://goo.gl/zwWsmO
   * 農曆：(民國)56年11月×日辰時
   * <p>
   * 一個在2002年新暦2月25日・中六合彩的命例:
   * 男:壬午年壬寅月甲子日36歳,大運乙巳
   * 天府天馬同宮,雙祿在辰午二宮夾輔,壬年祿存在亥照會,祿馬同鄕主横財,「斗君子」. // ==> 流年午年 , 斗君在子
   * <p>
   * 農暦正月十四日命宮在丑,                                            // ==> 流月 寅月 , 流日 命宮在丑
   * 甲子日廉貞在卯福徳宮化祿,
   * 天盤財帛宮在大運流年雙化禄,
   * 此日買中六合彩中齊六個字,發了一筆橫財。
   */
  @Test
  public void testFlowDay() {


    assertSame(丑, impl.getFlowDay(子, 14, 子));
  }
}