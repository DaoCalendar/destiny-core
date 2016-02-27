/**
 * @author smallufo 
 * Created on 2007/12/31 at 上午 3:22:25
 */ 
package destiny.astrology.classical.rules.debilities;

import destiny.astrology.HoroscopeContext;
import destiny.astrology.Planet;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/** Combust the Sun (between 17' and 8.5 from Sol). */
public final class Combustion extends Rule
{
  public Combustion()
  {
  }

  @Override
  protected Optional<Pair<String, Object[]>> getResult(Planet planet, @NotNull HoroscopeContext horoscopeContext)
  {
    if (planet != Planet.SUN)
    {
      if (horoscopeContext.getHoroscope().getAngle(planet , Planet.SUN) > 17.0/60 &&
          horoscopeContext.getHoroscope().getAngle(planet , Planet.SUN) <= 8.5)
      {
        //addComment(Locale.TAIWAN , planet + " 被太陽焦傷 (Combustion)");
        return Optional.of(ImmutablePair.of("comment", new Object[]{planet}));
      }
    }
    return Optional.empty();
  }

}
