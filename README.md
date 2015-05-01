Non-League Football Supporter - GDX edition
=========
This branch contains a rewrite of Non-League Football Supporter to use the multi-platform LibGDX framework.

The branch is currently **semi-functional**. Please refer to the master branch of the project for a functional native Android implementation.

Outstanding Tasks
------------------
- MatchView is currently skipped for debug purposes. Check the flag in MatchView
- Seasons end, new leagues are all generated nicely, but it needs an interstitial screen to summarise team season results.
- General beautification throughout

Bugs
----
- See Issues system on Github

Compiling
---------
Set up your environment according to the LibGDX setup: https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-%28Eclipse%2C-Intellij-IDEA%2C-NetBeans%29

Check out NLFS-GDX and import as Gradle project (there is one project per platform, at least do -desktop and -core (core is where the source lives)). Gradle *should* download all the appropriate dependencies. You should then be able to right click on the project "-desktop" and "Run As" Java Application. It will then ask which class to start with, select DesktopLauncher.

Android is a little more complex. You need to have the dependencies (e.g. Android SDK) set up according to the LibGDX guide. Then, make sure to set the path to the Android SDK in local.properties (sdk.dir=/home/blah) to compile the Android version (or disable it in the settings). We've been using Eclipse with ADT for Android, but your own gradle-supported IDE should work.

-----------
Licences:

TBC
