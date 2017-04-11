/**
 * Created by smallufo on 2017-04-11.
 */
package destiny.core.chinese.ziwei;

import destiny.core.chinese.Branch;
import destiny.core.chinese.BranchTools;
import destiny.core.chinese.Stem;

import java.util.function.BiFunction;
import java.util.function.Function;

import static destiny.core.chinese.Branch.*;

/**
 * 六兇星
 */
@SuppressWarnings("Duplicates")
public final class UnluckyStar extends ZStar {

  public final static UnluckyStar 擎羊 = new UnluckyStar("擎羊");
  public final static UnluckyStar 陀羅 = new UnluckyStar("陀羅");
  public final static UnluckyStar 火星 = new UnluckyStar("火星");
  public final static UnluckyStar 鈴星 = new UnluckyStar("鈴星");
  public final static UnluckyStar 地劫 = new UnluckyStar("地劫");
  public final static UnluckyStar 地空 = new UnluckyStar("地空");

  public final static UnluckyStar[] values = {擎羊 , 陀羅 , 火星 , 鈴星 , 地劫 , 地空};

  public UnluckyStar(String nameKey) {
    super(nameKey, ZStar.class.getName() , nameKey+"_ABBR");
  }

  /** 擎羊 : 年干 -> 地支 */
  public final static Function<Stem, Branch> fun擎羊 = year -> {
    switch (year) {
      case 甲: return 卯;
      case 乙: return 辰;
      case 丙: case 戊: return 午;
      case 丁: case 己: return 未;
      case 庚: return 酉;
      case 辛: return 戌;
      case 壬: return 子;
      case 癸: return 丑;
      default: throw new AssertionError(year);
    }
  };

  /** 陀羅 : 年干 -> 地支 */
  public final static Function<Stem , Branch> fun陀羅 = year -> {
    switch (year) {
      case 甲: return 丑;
      case 乙: return 寅;
      case 丙: case 戊: return 辰;
      case 丁: case 己: return 巳;
      case 庚: return 未;
      case 辛: return 申;
      case 壬: return 戌;
      case 癸: return 亥;
      default: throw new AssertionError(year);
    }
  };

  /** 火星 : (年支、時支) -> 地支 */
  public final static BiFunction<Branch , Branch , Branch> fun火星 = (year , hour) -> {
    switch (BranchTools.trilogy(year)) {
      case 火: return Branch.get(hour.getIndex()+1);
      case 水: return Branch.get(hour.getIndex()+2);
      case 金: return Branch.get(hour.getIndex()+3);
      case 木: return Branch.get(hour.getIndex()+9);
      default: throw new AssertionError("年支 = " + year + " , 時支 = " + hour);
    }
  };

  /** 鈴星 : (年支、時支) -> 地支 */
  public final static BiFunction<Branch , Branch , Branch> fun鈴星 = (year , hour ) -> {
    switch (BranchTools.trilogy(year)) {
      case 火: return Branch.get(hour.getIndex()+3);
      case 水:
      case 金:
      case 木:
        return Branch.get(hour.getIndex() + 10);
      default: throw new AssertionError("年支 = " + year + " , 時支 = " + hour);
    }
  };
}
