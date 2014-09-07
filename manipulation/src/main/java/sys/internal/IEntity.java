package sys.internal;

import sys.Entity;

/**
 * Classes with the annotation {@link Entity} implement this interface after
 * enhancement. Instances are registered at the current {@link Transaction}
 * 
 * @author ruwen
 *
 */
public interface IEntity {
	/**
	 * 
	 * @return the data to save
	 */
	public String _getStringToSave();

	/**
	 * 
	 * @return where to save the data? 
	 */
	public String _getName();
}
