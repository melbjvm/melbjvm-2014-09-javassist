manipulation
============
**A javassist appetiser presented by Ruwen Schwedewsky at September 2014's MelbJVM.**

Requirements
------------
- Gradle 2 or later
- Java 8

How to run it?
--------------
Simply go to the project directory and run gradle.

What happens?
-------------
- The project is being build.
- The class *Enhancement* changes the Java byte code of all classes which are annotated with *@Entity*. All those classes implement afterwards the interface *IEntity*. 
- The *AppServer* runs the the *Application* *MyApp*. All created entities are persisted after the commit to a file in *java.io.tmpdir*.