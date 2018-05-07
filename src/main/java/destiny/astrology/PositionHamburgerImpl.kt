/**
 * Created by smallufo on 2017-07-08.
 */
package destiny.astrology

import destiny.core.calendar.ILocation

open class PositionHamburgerImpl internal constructor(hamburger: Hamburger) :
  AbstractPositionImpl<Hamburger>(hamburger) {

  override fun getPosition(gmtJulDay: Double,
                           loc: ILocation,
                           centric: Centric,
                           coordinate: Coordinate,
                           starPositionImpl: IStarPosition<*>): IPos {
    return starPositionImpl.getPosition(point , gmtJulDay , centric, coordinate)
  }

}
