/**
 * @author smallufo 
 * Created on 2007/12/31 at 上午 3:25:34
 */ 
package destiny.astrology.classical.rules.debilities;

import destiny.astrology.HoroscopeContext;
import destiny.astrology.Planet;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/** Under the Sunbeans (between 8.5 and 17 from Sol). */
public final class Sunbeam extends Rule
{

  public Sunbeam()
  {
  }

  @Override
  protected Optional<Pair<String, Object[]>> getResult(Planet planet, @NotNull HoroscopeContext horoscopeContext)
  {
    if (planet != Planet.SUN)
    {
      if (horoscopeContext.getHoroscope().getAngle(planet , Planet.SUN) > 8.5 &&
          horoscopeContext.getHoroscope().getAngle(planet , Planet.SUN) <= 17)
      {
        //addComment(Locale.TAIWAN , planet + " 被太陽曬傷 (Sunbeam)");
        return Optional.of(ImmutablePair.of("comment", new Object[]{planet}));
      }
    }
    return Optional.empty();
  }

}
