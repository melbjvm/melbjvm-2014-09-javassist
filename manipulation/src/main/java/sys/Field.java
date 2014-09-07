package sys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Fields with this annotation inside an {@link Entity} annotated are getting
 * persisted.
 * 
 * @author ruwen
 *
 */
@Target(ElementType.FIELD)
public @interface Field {
}
