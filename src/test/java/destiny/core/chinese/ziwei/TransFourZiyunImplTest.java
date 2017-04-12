/**
 * Created by smallufo on 2017-04-12.
 */
package destiny.core.chinese.ziwei;

import org.junit.Test;

import static destiny.core.chinese.Stem.*;
import static destiny.core.chinese.ziwei.MainStar.太陽;
import static destiny.core.chinese.ziwei.TransFour.Type.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class TransFourZiyunImplTest {

  TransFour impl = new TransFourZiyunImpl();

  @Test
  public void getStarOf() {
    assertSame(太陽, impl.getStarOf(癸, 科));
  }

  @Test
  public void getTypeOf() {
    assertSame(忌 , impl.getTypeOf(太陽 , 甲).orElse(null));
    assertNull(impl.getTypeOf(太陽 , 乙).orElse(null));
    assertNull(impl.getTypeOf(太陽 , 丙).orElse(null));
    assertNull(impl.getTypeOf(太陽 , 丁).orElse(null));
    assertNull(impl.getTypeOf(太陽 , 戊).orElse(null));
    assertNull(impl.getTypeOf(太陽 , 己).orElse(null));
    assertSame(祿 , impl.getTypeOf(太陽 , 庚).orElse(null));
    assertSame(權 , impl.getTypeOf(太陽 , 辛).orElse(null));
    assertNull(impl.getTypeOf(太陽 , 壬).orElse(null));
    assertSame(科 , impl.getTypeOf(太陽 , 癸).orElse(null));
  }
}