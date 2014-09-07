package sys;

import sys.internal.Transaction;

/**
 * An app server which runs instances of {@link Application} with transactional support.
 * 
 * @author ruwen
 *
 */
public class AppServer {
	public void runApp(Application app) {
		Transaction tx = new Transaction();
		tx.register();

		try {
			app.run();
			tx.commit();
		} catch (Throwable t) {
			tx.rollback();
			t.printStackTrace();
		}
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		new AppServer().runApp((Application) Class.forName(args[0])
				.newInstance());
	}
}
