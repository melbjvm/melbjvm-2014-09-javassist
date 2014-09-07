classchecker
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
Gradle compiles the project and runs afterwards the *ImmutableChecker*. The *ImmutableChecker* will check that all classes in the *biz.* package. Every class that implements *Immutable* should also be immutable.
