Non-League Football Supporter - GDX edition
=========
The game is currently **semi-functional**.

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


Licences:
==========

Typefaces:
----------
- Babas Neue; http://dharmatype.com/ ; SIL Open Font License: http://scripts.sil.org/cms/scripts/page.php?site_id=nrsi&id=OFL_web
- TGL 0-1451 Engschrift; Peter Wiegel; CC-BY; http://www.peter-wiegel.de/TGL_0-1451.html- 
- MPH 2B Damase; Mark Williamson; OFL

Images:
-------
Football field; See-ming Lee, CC-BY; https://www.flickr.com/photos/seeminglee/8688505741
Supporter with Beer; Russell James Smith, CC-BY; https://www.flickr.com/photos/russelljsmith/488207808/
