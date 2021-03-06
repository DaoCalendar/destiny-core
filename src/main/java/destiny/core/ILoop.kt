/**
 * Created by smallufo on 2018-06-11.
 */
package destiny.core

interface ILoop<T> {

  fun next(n: Int): T

  fun prev(n: Int): T {
    return next(0 - n)
  }

  val next: T
    get() = next(1)

  val prev: T
    get() = prev(1)

  operator fun plus(value: Int): T {
    return this.next(value)
  }

  operator fun minus(value: Int) : T {
    return this.prev(value)
  }
}
