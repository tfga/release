# release
A faster, simpler alternative to [maven-release-plugin](http://maven.apache.org/maven-release/maven-release-plugin/).

## Why?

`maven-release-plugin` has some problems:

* It's inefficient (hence, slow).

  It builds the project multiple times. For projects with long build times, this is a deal-breaker.

* It's cumbersome to use.

  It has a two-step process, with 3 different commands involved (`release:prepare`, `release:perform` and `release:rollback`).

* Its error messages are less than great.

As a result, where I work people simply didn't use it. `maven-release-plugin` took so long that they preferred to do the release procedure manually -- which is _way_ error prone. Often tags were not created; apps ended up in production with SNAPSHOT dependencies.

`release` does the same thing as `maven-release-plugin`, but:

1. It only builds the project _once_.
2. You execute _one_ command.
3. It has good error messages.

If anything bad happens, it rolls back the stuff it has to in the same way. Tags created get deleted; modifications made to `pom.xml` are reverted.

