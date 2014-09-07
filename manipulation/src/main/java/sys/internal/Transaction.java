package sys.internal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 * A very basic transaction class with saves all the entities into files. No
 * read support :(
 * 
 * @author ruwen
 *
 */
public class Transaction {
	// use ThreadLocals for multithreading...
	private static Transaction current;

	public static void add(IEntity o) {
		current.saveables.add(o);
	}
	
	private final String target = System.getProperty("java.io.tmpdir");
	
	public void register() {
		current = this;
	}

	private Set<IEntity> saveables = new HashSet<>();

	public void commit() {
		System.out.println("Writing to " + target);
		for (IEntity each : saveables) {
			try (BufferedWriter bw = Files.newBufferedWriter(
					Paths.get(target, each._getName()), StandardCharsets.UTF_8,
					StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
				bw.append(each._getStringToSave());
				bw.newLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		current = null;
	}

	public void rollback() {
		// make transaction unusable
		saveables = null;
		current = null;
	}
}
