/**
 * Created by smallufo on 2017-04-16.
 */
package destiny.core.chinese.ziwei;

import destiny.core.chinese.Branch;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static destiny.core.chinese.Branch.亥;
import static destiny.core.chinese.Branch.子;
import static destiny.core.chinese.Branch.申;
import static org.junit.Assert.assertNotNull;

public class FlowMonthYearMainHouseDepImplTest {

  private Logger logger = LoggerFactory.getLogger(getClass());

  IFlowMonth impl = new FlowMonthYearMainHouseDepImpl();

  @Test
  public void testString() {
    assertNotNull(impl.getTitle(Locale.TAIWAN));
    assertNotNull(impl.getTitle(Locale.SIMPLIFIED_CHINESE));
    logger.info("title tw = {} , cn = {}" , impl.getTitle(Locale.TAIWAN) , impl.getTitle(Locale.SIMPLIFIED_CHINESE));
  }

  /**
   * 比對 : http://imgur.com/Xz3tQkP
   */
  @Test
  public void getFlowMonth() throws Exception {
    Branch b = impl.getFlowMonth(申 , 亥, 1, 子);
    logger.info("b = {}" , b);
  }

}