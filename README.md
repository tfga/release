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

## Usage

This is not a Maven plug-in: it's an application.

    release <maven args>

`release` has no arguments of its own. Everything you pass to it will be forwarded to Maven.

## Requirements

`svn` (CLI) and `mvn` must be on `$PATH`.

## Installation

1. Create `install.properties`:

       cp install.properties.example install.properties

   Change the value of `install.dir` to the directory where you want `release` to be installed (on Windows: backslashes (`\`) must be escaped (`\\`).

   `install.dir` must exist. If it doesn't, please create it or the install script will fail.

2. Run

       ./install    # Unix-like

   or

       install      # Windows

3. Add `${install.dir}/bin` to `$PATH`.
