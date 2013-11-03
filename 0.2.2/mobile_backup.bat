@echo off
echo Backup custom files in mobile directory.

mkdir mobile_backup\src\com\sunshinegamestudio\this_is_off_road_racing
mkdir mobile_backup\res\values

copy mobile\src\com\sunshinegamestudio\this_is_off_road_racing\MainActivity.java mobile_backup\src\com\sunshinegamestudio\this_is_off_road_racing\MainActivity.java
copy mobile\AndroidManifest.xml mobile_backup\AndroidManifest.xml
copy mobile\res\values\strings.xml mobile_backup\res\values\strings.xml
copy mobile\ant.properties mobile_backup\ant.properties