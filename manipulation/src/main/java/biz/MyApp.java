package biz;

import sys.Application;

public class MyApp implements Application {

	@Override
	public void run() {
		Customer c = new Customer();
		c.setFirstname("ruwen");
		c.setLastname("schwedewsky");
	}

}
