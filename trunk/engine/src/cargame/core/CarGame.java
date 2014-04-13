/*
This Is Off Road Racing, a game where you can drive off road.
Copyright (C) 2010 - 2013  Sunshine GameStudio

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package cargame.core;

import java.io.IOException;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.FlyCamAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.BulletAppState.ThreadingType;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsSpace.BroadphaseType;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Caps;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;
import com.jme3.system.Platform;
import com.jme3.system.Timer;

import java.util.Collection;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.app.state.AbstractAppState;
import cargame.appstates.MainMenuState;
import cargame.appstates.LicenseViewMenuState;
import cargame.appstates.TrackSelectorState;
import cargame.appstates.InGameMenuState;
import cargame.appstates.GameState;
import cargame.appstates.LicenseAcceptanceState;
import cargame.appstates.ResultsMenuState;
import cargame.core.statetasks.ChangeStateTask;
import cargame.core.statetasks.ChangeTrackTask;
import cargame.core.statetasks.ChangeResultsLapTimesTask;
import com.jme3.niftygui.NiftyJmeDisplay;

public class CarGame extends SimpleApplication {
        private LicenseAcceptanceState licenseAcceptanceState = null;
        private GameState gameState = null;
	private MainMenuState menuState = null;
        private LicenseViewMenuState licenseViewMenuState = null;
	private TrackSelectorState trackSelectorState = null;
	private InGameMenuState inGameMenuState = null;
	private ResultsMenuState resultsMenuState = null;
        private BulletAppState bulletAppState = null;
        private Logger logger;
        private FileHandler fh;
        private NiftyJmeDisplay niftyDisplay;
        private String platform;
        private boolean debugMode = false;
        // private boolean debugMode = true;
        private boolean keyboardControlled = false;
        private boolean joystickControlled = false;
        private boolean touchscreenControlled = false;
        private int AndroidApiLevel_System = 0;
        /*
        public enum AndroidApiLevel_Type   {
            ICE_CREAM_SANDWITCH ( 14 )
        };
        * Need a good solution for this...
        */

        static CarGame thisApp;

        private String track;
	
	public CarGame() {
		thisApp=this;
	}

        public static CarGame getApp()  {
            return thisApp;
        }

    public PhysicsSpace getPhysicsSpace() {
        //return pSpace;
        return bulletAppState.getPhysicsSpace();
    }

    public Logger getLogger()
    {
        return logger;
    }
    
    @Override
    public void start(){
        logger = Logger.getLogger(CarGame.class.getName());
        /*
        try {
            fh = new FileHandler("PlatformGame_log.xml");
        } catch (IOException ex) {
            Logger.getLogger(CarGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(CarGame.class.getName()).log(Level.SEVERE, null, ex);
        }
         * Temperary disabled filehandler for readonly filesystem on Android.
         */
        //logger.setLevel(Level.ALL);
        logger.setLevel(Level.SEVERE);

        // getLogger().addHandler(fh);
        
        super.start();
    }
	
	// @Override
    // public void initialize() {
	@Override
    public void simpleInitApp() {
                // initialize the standard environment first
		// super.initialize();

                getLogger().log(Level.SEVERE, "Start initialization");
            
                // Get renderer capabilities
                Collection<Caps> caps = renderer.getCaps();
                getLogger().log(Level.SEVERE, "Renderer capabilities {0}: " + caps.toString());
                
                // Create NiftyDisplay
                niftyDisplay = new NiftyJmeDisplay(getAssetManager(),
                getInputManager(),
                getAudioRenderer(),
                getGUIViewPort());
                
                // Detect the platform
                detectPlatform();
                
                // Init controls
                initControls();
    
		// Create the States
                bulletAppState = new BulletAppState();
                licenseAcceptanceState = new LicenseAcceptanceState(this);
                menuState = new MainMenuState(this);
                licenseViewMenuState = new LicenseViewMenuState(this);
                trackSelectorState = new TrackSelectorState(this);
		gameState = new GameState(this);
                inGameMenuState = new InGameMenuState(this);
                resultsMenuState = new ResultsMenuState(this);

                // Detach unneeded States
                setDisplayFps(false);
                setDisplayStatView(false);
                stateManager.detach(stateManager.getState(FlyCamAppState.class));
                
		// Attach MenuState
                getStateManager().attach(bulletAppState);
		// getStateManager().attach(menuState);
		getStateManager().attach(licenseAcceptanceState);
    }
	
	
    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    /**
     * @return the AndroidApiLevel_System
     */
    public int getAndroidApiLevel_System() {
        return AndroidApiLevel_System;
    }

    /**
     * @param AndroidApiLevel_System the AndroidApiLevel_System to set
     */
    public void setAndroidApiLevel_System(int AndroidApiLevel_System) {
        this.AndroidApiLevel_System = AndroidApiLevel_System;
    }

	public void loadLicenseAcceptanceState() {
                // this.enqueue(new ChangeStateTask(gameState,menuState,viewPort,stateManager));
        }
        
        public void loadLicenseAcceptanceState_return()  {
            loadMenu(licenseAcceptanceState);
        }

        
        // Add a parameter so the previous state can also be the LicenseAcceptance state.
        public void loadMenu(AbstractAppState previousState) {
                this.enqueue(new ChangeStateTask(previousState,menuState,viewPort,stateManager));
        }

        public void loadMenu_return(String returnValue) {
                if (returnValue=="loadLicenseViewMenu") {
                    loadLicenseViewMenu(menuState);
                }
        }
        
        public void loadLicenseViewMenu(AbstractAppState previousState) {
                this.enqueue(new ChangeStateTask(previousState,licenseViewMenuState,viewPort,stateManager));
        }

        public void loadLicenseViewMenuState_return() {
            loadMenu(licenseViewMenuState);
        }
        
	public void loadTrackSelector() {
                this.enqueue(new ChangeStateTask(menuState,trackSelectorState,viewPort,stateManager));
        }
	
	
	public void loadGame(String track) {
            // Only load track "AndroidTest" on Android for now (until TerraMonkey works on Android).
            /*
            Platform platform = JmeSystem.getPlatform();
            if (    platform.toString()=="Android_ARM5" ||
                    platform.toString()=="Android_ARM6" ||
                    platform.toString()=="Android_ARM7" ||
                    platform.toString()=="Android_X86" )  {
                        track="Android Test";
                }
                */
                
                this.enqueue(new ChangeTrackTask(track, this));
                this.enqueue(new ChangeStateTask(trackSelectorState,gameState,viewPort,stateManager));
	}
        
        public void loadGame_return(long lap0, long lap1, long lap2, long lap3) {
            loadResultsMenu(lap0, lap1, lap2, lap3);
        }
        
        public void loadResultsMenu(long lap0, long lap1, long lap2, long lap3) {
            this.enqueue(new ChangeResultsLapTimesTask(resultsMenuState, lap0, lap1, lap2, lap3));
            this.enqueue(new ChangeStateTask(gameState,resultsMenuState,viewPort,stateManager));
        }
        
        public void loadResultsMenu_return()    {
            loadMenu(resultsMenuState);
        }
	
	private void initControls() {
            if (getPlatform()=="Android")    {
                        // For analog controls
                        /*
                        setKeyboardControlled(false);
                        setJoystickControlled(true);
                        */
                        // For Digital controls
                        
                        setKeyboardControlled(true);
                        setJoystickControlled(false);
                        
                        
                        // For touchscreen controls
                        setTouchscreenControlled(true);
                }
            else    {
                if ( getDebugMode())   {
                    // For analog controls
                    /*
                    setKeyboardControlled(false);
                    setJoystickControlled(true);
                    */

                    // For Digital controls
                    
                    setKeyboardControlled(true);
                    setJoystickControlled(false);
                    

                    // For touchscreen controls
                    setTouchscreenControlled(true);
                }
                else    {
                    setKeyboardControlled(true);
                    setJoystickControlled(false);
                    setTouchscreenControlled(false);
                }
            }
        }
	
	public ViewPort getViewPort() {
		return viewPort;
	}
	
	public ViewPort getGUIViewPort() {
		return guiViewPort;
	}
	
	
	public Timer getTimer() {
		return timer;
	}

        public NiftyJmeDisplay getNiftyDisplay()    {
            return niftyDisplay;
        }

    /**
     * @detect the platform
     */
    public void detectPlatform() {
        Platform platform = JmeSystem.getPlatform();
        if (platform.toString().contains("Android"))    {
            this.platform="Android";
        }
        else    {
            this.platform="Unknown";
        }
    }

    /**
     * @return the platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }
        
        public boolean getDebugMode()   {
            return debugMode;
        }

    /**
     * @return the keyboardControlled
     */
    public boolean isKeyboardControlled() {
        return keyboardControlled;
    }

    /**
     * @param keyboardControlled the keyboardControlled to set
     */
    public void setKeyboardControlled(boolean keyboardControlled) {
        this.keyboardControlled = keyboardControlled;
    }

    /**
     * @return the joystickControlled
     */
    public boolean isJoystickControlled() {
        return joystickControlled;
    }

    /**
     * @param joystickControlled the joystickControlled to set
     */
    public void setJoystickControlled(boolean joystickControlled) {
        this.joystickControlled = joystickControlled;
    }

    /**
     * @return the touchscreenControlled
     */
    public boolean isTouchscreenControlled() {
        return touchscreenControlled;
    }

    /**
     * @param touchscreenControlled the touchscreenControlled to set
     */
    public void setTouchscreenControlled(boolean touchscreenControlled) {
        this.touchscreenControlled = touchscreenControlled;
    }

        public String getTrack()    {
            return track;
        }
        
    public void setTrack(String track) {
        this.track=track;
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("This Is Off-Road Racing");
        settings.setUseJoysticks(true);
        CarGame app = new CarGame();
        app.setSettings(settings);
        app.start();
    }
}