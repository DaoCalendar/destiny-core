/** 2009/10/21 上午2:44:14 by smallufo */
package destiny.tools.location;

import java.util.Optional;
import java.util.TimeZone;

/**
 * 從經緯度求 TimeZone
 */
public interface TimeZoneIF
{
  /** 從經緯度查詢 timezone */
  Optional<TimeZone> getTimeZone(double lng, double lat);
}
