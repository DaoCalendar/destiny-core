/**
 * @author smallufo
 * Created on 2007/12/31 at 上午 3:44:04
 */
package destiny.astrology.classical.rules.debilities;

import destiny.astrology.*;
import org.jetbrains.annotations.NotNull;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.Optional;

/** Within 5 deg of Caput Algol at 26 deg 10' Taurus in January 2000. */
public final class Conj_Algol extends Rule {

  public Conj_Algol() {
  }

  @Override
  protected Optional<Tuple2<String, Object[]>> getResult(Planet planet, @NotNull HoroscopeContext horoscopeContext) {
    double planetDegree = horoscopeContext.getPosition(planet).getLng();
    double algolDeg = horoscopeContext.getPosition(FixedStar.ALGOL).getLng();

    if (AspectEffectiveModern.isEffective(planetDegree, algolDeg, Aspect.CONJUNCTION, 5)) {
      //addComment(Locale.TAIWAN , planet + " 與 " + FixedStar.ALGOL + " 形成 " + Aspect.CONJUNCTION);
      return Optional.of(Tuple.tuple("comment", new Object[]{planet, FixedStar.ALGOL, Aspect.CONJUNCTION}));
    }
    return Optional.empty();
  }

}
