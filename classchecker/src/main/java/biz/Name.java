package biz;

import java.util.Arrays;

import sys.Immutable;
import sys.ImmutableList;

/**
 * A sample implementation of a name.
 * 
 * @author ruwen
 *
 */
public class Name implements Immutable {

	private final String firstname;
	private final String lastname;
	private final ImmutableList<String> middlenames;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Name(String firstname, String lastname, String... middlenames) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.middlenames=ImmutableList.ofStrings(Arrays.asList(middlenames));
	}
	
	public ImmutableList<String> getMiddlenames() {
		return middlenames;
	}
}
