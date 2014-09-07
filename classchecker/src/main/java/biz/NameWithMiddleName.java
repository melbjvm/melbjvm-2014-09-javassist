package biz;

import sys.Immutable;

/**
 * A sample implementation of a name.
 * 
 * @author ruwen
 *
 */
public class NameWithMiddleName implements Immutable {

	private final String firstname;
	private final String lastname;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public NameWithMiddleName(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
}
