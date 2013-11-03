package com.sunshinegamestudio.this_is_off_road_racing;
 
import com.jme3.app.AndroidHarness;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.jme3.system.android.AndroidConfigChooser.ConfigType;
import com.jme3.renderer.android.TextureUtil;
import cargame.core.CarGame;
 
public class MainActivity extends AndroidHarness{
 
    /*
     * Note that you can ignore the errors displayed in this file,
     * the android project will build regardless.
     * Install the 'Android' plugin under Tools->Plugins->Available Plugins
     * to get error checks and code completion for the Android project files.
     */
 
    public MainActivity(){
        // Set the application class to run
        appClass = "cargame.core.CarGame";
        // Try ConfigType.FASTEST; or ConfigType.LEGACY if you have problems
        eglConfigType = ConfigType.BEST;
        // Exit Dialog title & message
        exitDialogTitle = "Exit?";
        exitDialogMessage = "Press Yes";
        // Enable verbose logging
        eglConfigVerboseLogging = false;
        // Choose screen orientation
        screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        // Invert the MouseEvents X (default = true)
        // mouseEventsInvertX = true;
        mouseEventsInvertX = false;
        // Invert the MouseEvents Y (default = true)
        // mouseEventsInvertY = true;
        mouseEventsInvertY = false;
        // Disable texture compression to avoid black textures
        TextureUtil.ENABLE_COMPRESSION = false;
    }

    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        
        CarGame.getApp().setAndroidApiLevel_System(android.os.Build.VERSION.SDK_INT);
    }

}
