package sys.internal;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import sys.Immutable;
import sys.ImmutableList;

/**
 * This class checks if classes in the provided class folder are immutable.
 * 
 * @author ruwen
 *
 */
public class ImmutableChecker {

	private Set<CtClass> consideredImmutable = new HashSet<>();
	private Path classpath;
	private ClassPool pool;

	private ImmutableChecker(String classpathString) throws NotFoundException {
		pool = new ClassPool();
		pool.appendSystemPath();
		pool.appendClassPath(classpathString);

		classpath = Paths.get(classpathString);

		// Those classes are considered immutable even though
		// they don't implement Immutable.

		consideredImmutable.add(pool.get(String.class.getCanonicalName()));
		consideredImmutable.add(pool.get(Integer.class.getCanonicalName()));
		consideredImmutable.add(pool.get(Boolean.class.getCanonicalName()));
		consideredImmutable.add(pool.get(Object.class.getCanonicalName()));
		consideredImmutable
				.add(pool.get(ImmutableList.class.getCanonicalName()));
	}

	private boolean implementsImmutable(CtClass c) throws NotFoundException {
		return Arrays.asList(c.getInterfaces()).contains(
				pool.get(Immutable.class.getCanonicalName()));
	}

	private void check() throws Exception {
		Files.walk(classpath)
				.filter(x -> x.toFile().isFile() && x.toString().endsWith(".class"))
				.map(x -> classpath.relativize(x).toString()
						.replace(File.separatorChar, '.').replace(".class", ""))
				.filter(x -> x.startsWith("biz.")).forEach(x -> {
					try {
						CtClass clazz = pool.get(x);
						if (implementsImmutable(clazz)) {
							checkImmutable(clazz);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
		;

	}

	public static void main(String[] args) throws Exception {
		String classpathString = args[0];
		new ImmutableChecker(classpathString).check();
	}

	private void checkImmutable(CtClass clazz) throws CannotCompileException,
			ClassNotFoundException, NotFoundException {
		if (consideredImmutable.contains(clazz)) {
			return;
		}
		System.out.println("Checking " + clazz.getName());
			
		// valid superclass?
		checkImmutable(clazz.getSuperclass());

		for (CtField each : clazz.getDeclaredFields()) {
			// check for final
			if ((each.getFieldInfo().getAccessFlags() & AccessFlag.FINAL) == 0) {
				throw new RuntimeException(each.getName() + " is not final");
			}
			// immutable?
			if (!implementsImmutable(each.getType())
					&& !consideredImmutable.contains(each.getType())
					&& !each.getType().isPrimitive()) {
				throw new RuntimeException(each.getName() + " is not immutable");
			}
		}
	}
}
