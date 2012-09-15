rem "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed.apk"
rem "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed-aligned.apk"
copy "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-unsigned.apk" "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed.apk"
jarsigner -verbose -keystore "C:\Users\Vortex\.android\debug.keystore" "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed.apk" androiddebugkey
jarsigner -verify -verbose -certs "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed.apk"
zipalign -v 4 "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed.apk" "G:\Development\GameDev\jMP\CarGame\engine\dist\CarGame-release-signed-aligned.apk"
