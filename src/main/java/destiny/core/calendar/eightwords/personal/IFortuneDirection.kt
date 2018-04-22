/*
 * @author smallufo
 * @date 2005/5/17
 * @time 上午 07:43:25
 */
package destiny.core.calendar.eightwords.personal

import destiny.core.Gender
import destiny.core.calendar.eightwords.EightWords

/**
 * 取得大運的順逆
 */
interface IFortuneDirection {

  /** 大運是否順行  */
  fun isForward(gender : Gender, eightWords: EightWords): Boolean
}
