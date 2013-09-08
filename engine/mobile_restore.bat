@echo off
echo Restore custom files in mobile directory.
copy mobile_backup\src\com\sunshinegamestudio\this_is_off_road_racing\MainActivity.java mobile\src\com\sunshinegamestudio\this_is_off_road_racing\MainActivity.java
copy mobile_backup\AndroidManifest.xml mobile\AndroidManifest.xml
copy mobile_backup\res\values\strings.xml mobile\res\values\strings.xml
copy mobile_backup\ant.properties mobile\ant.properties