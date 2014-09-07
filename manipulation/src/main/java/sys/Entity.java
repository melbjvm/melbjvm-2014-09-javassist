package sys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Classes with this annotation are treated as entities.
 * 
 * @author ruwen
 *
 */
@Target(ElementType.TYPE)
public @interface Entity {
	/**
	 * 
	 * @return name of the entity
	 */
	String value();
}
