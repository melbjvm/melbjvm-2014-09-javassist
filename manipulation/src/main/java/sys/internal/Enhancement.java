package sys.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import sys.Entity;

/**
 * This class makes all classes with the annotation {@link Entity} implement the
 * interface IEntity.
 * 
 * @author ruwen
 *
 */
public class Enhancement {

	public static void main(String[] args) throws NotFoundException,
			IOException {
		String classpathString = args[0];
		ClassPool pool = new ClassPool();
		pool.appendSystemPath();
		pool.appendClassPath(classpathString);

		Path classpath = Paths.get(classpathString);

		CtClass iface = pool.get(IEntity.class.getCanonicalName());

		Files.walk(classpath)
				.filter(x -> x.toFile().isFile())
				.map(x -> classpath.relativize(x).toString()
						.replace(File.separatorChar, '.').replace(".class", ""))
				.filter(x -> x.startsWith("biz.")).forEach(x -> {
					try {
						CtClass clazz = pool.get(x);
						if (clazz.hasAnnotation(Entity.class)) {
							addGetName(clazz);
							addGetStringToSave(clazz);
							clazz.addInterface(iface);
							extendConstructor(clazz);
							clazz.writeFile(classpathString);
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
		;

	}

	private static void extendConstructor(CtClass clazz)
			throws CannotCompileException {
		for (CtConstructor each : clazz.getConstructors()) {
			each.insertBeforeBody("sys.internal.Transaction.add(this);");
		}
	}

	private static void addGetStringToSave(CtClass clazz)
			throws CannotCompileException {
		StringBuilder sb = new StringBuilder();
		sb.append("public String _getStringToSave() {");
		sb.append("StringBuilder sb = new StringBuilder();");
		for (CtField field : clazz.getDeclaredFields()) {
			sb.append("sb.append(\"");
			sb.append(field.getName());
			sb.append("=\");");
			sb.append("sb.append(");
			sb.append(field.getName());
			sb.append(");");
			sb.append("sb.append(\",\");");
		}
		sb.append("return sb.toString();}");
		clazz.addMethod(CtNewMethod.make(sb.toString(), clazz));
	}

	private static void addGetName(CtClass clazz)
			throws CannotCompileException, ClassNotFoundException {

		Entity annotation = (Entity) clazz.getAnnotation(Entity.class);
		clazz.addMethod(CtNewMethod.make("public String _getName() { return \""
				+ annotation.value() + "\"; }", clazz));
	}
}
