/**
 * Created by smallufo on 2017-11-18.
 */
package destiny.astrology.eclipse

import destiny.astrology.Azimuth

/**
 * 月偏食
 * [ILunarEclipsePartialObservation]
 * */
open class LunarEclipsePartialObservation(gmtJulDay: Double, lng: Double, lat: Double, alt: Double, azimuth: Azimuth,
                                          lunarType: ILunarEclipse.LunarType,
                                          magUmbra: Double,
                                          magPenumbra: Double,
                                          maxVisible: Boolean,
                                          penumbraBeginVisible: Boolean,
                                          penumbraEndVisible: Boolean,
                                          val partialBeginVisible: Boolean,
                                          val partialEndVisible: Boolean) :
  LunarEclipsePenumbraObservation(gmtJulDay, lng, lat, alt, azimuth, lunarType, magUmbra, magPenumbra, maxVisible, penumbraBeginVisible, penumbraEndVisible)
