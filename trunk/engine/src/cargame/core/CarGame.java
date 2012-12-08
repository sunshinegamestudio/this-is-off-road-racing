/*
CarGame, a game where you can drive with a car.
Copyright (C) 2010  Vortex GameStudio

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

import cargame.appstates.MainMenuState;
import cargame.appstates.TrackSelectorState;
import cargame.appstates.InGameMenuState;
import cargame.appstates.GameState;
import cargame.core.statetasks.ChangeStateTask;
import cargame.core.statetasks.ChangeTrackTask;
import com.jme3.niftygui.NiftyJmeDisplay;

public class CarGame extends Application {

    protected ThreadingType threadingType = ThreadingType.SEQUENTIAL;
    public BroadphaseType broadphaseType = BroadphaseType.DBVT;
    public Vector3f worldMin = new Vector3f(-10000f, -10000f, -10000f);
    public Vector3f worldMax = new Vector3f(10000f, 10000f, 10000f);


	private GameState gameState = null;
	private MainMenuState menuState = null;
	private TrackSelectorState trackSelectorState = null;
	private InGameMenuState inGameMenuState = null;
        private BulletAppState bulletAppState = null;
        private Logger logger;
        private FileHandler fh;
        private NiftyJmeDisplay niftyDisplay;
        private boolean debugMode = false;
        private boolean keyboardControlled = false;
        private boolean joystickControlled = false;
        private boolean touchscreenControlled = false;

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
        
        // set some default settings in-case
        // settings dialog is not shown
        if (settings == null)
            setSettings(new AppSettings(true));

        // show settings dialog
        if (!JmeSystem.showSettingsDialog(settings, false)) {
            getLogger().log(Level.SEVERE, "jME system initialisation error");
            return;
        }

        super.start();
    }
	
	@Override
    public void initialize() {
                getLogger().log(Level.SEVERE, "Start initialization");
            
                // initialize the standard environment first
		super.initialize();

                // Get renderer capabilities
                Collection<Caps> caps = renderer.getCaps();
                getLogger().log(Level.SEVERE, "Renderer capabilities {0}: " + caps.toString());
                
                // Create NiftyDisplay
                niftyDisplay = new NiftyJmeDisplay(getAssetManager(),
                getInputManager(),
                getAudioRenderer(),
                getGUIViewPort());
                
                // Switch debug mode on
                // debugMode = true;
                debugMode = false;
                
                // Init controls
                initControls();
    
		// Create the States
                bulletAppState = new BulletAppState();
                menuState = new MainMenuState(this);
                trackSelectorState = new TrackSelectorState(this);
		gameState = new GameState(this);
                inGameMenuState = new InGameMenuState(this);
		
		// Attach MenuState
                getStateManager().attach(bulletAppState);
		getStateManager().attach(menuState);
    }
	
	
	@Override
    public void update() {
        if (speed == 0 || paused) {
            return;
        }

        super.update();
        float tpf = timer.getTimePerFrame() * speed;

        // update states
        stateManager.update(tpf);

        // render states
        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        simpleRender(renderManager);
    }

    public void simpleRender(RenderManager rm) {
    }

    /**
     * @return the threadingType
     */
    public ThreadingType getThreadingType() {
        return threadingType;
    }

    /**
     * @param threadingType the threadingType to set
     */
    public void setThreadingType(ThreadingType threadingType) {
        this.threadingType = threadingType;
    }

    public void setBroadphaseType(BroadphaseType broadphaseType) {
        this.broadphaseType = broadphaseType;
    }

    public void setWorldMin(Vector3f worldMin) {
        this.worldMin = worldMin;
    }

    public void setWorldMax(Vector3f worldMax) {
        this.worldMax = worldMax;
    }

    public enum ThreadingType {

        SEQUENTIAL,
        PARALLEL,
        DETACHED
    }

	public void loadMenu() {
                this.enqueue(new ChangeStateTask(gameState,menuState,viewPort,stateManager));
        }

        
	public void loadTrackSelector() {
                this.enqueue(new ChangeStateTask(menuState,trackSelectorState,viewPort,stateManager));
        }
	
	
	public void loadGame(String track) {
                this.enqueue(new ChangeTrackTask(track, this));
                this.enqueue(new ChangeStateTask(trackSelectorState,gameState,viewPort,stateManager));
	}
	
	private void initControls() {
            Platform platform = JmeSystem.getPlatform();
            if (    platform.toString()=="Android_ARM5" ||
                    platform.toString()=="Android_ARM6" ||
                    platform.toString()=="Android_ARM7" ||
                    platform.toString()=="Android_X86" )  {
                        setKeyboardControlled(false);
                        setJoystickControlled(true);
                        setTouchscreenControlled(true);
                }
            else    {
                if ( getDebugMode())   {
                    setKeyboardControlled(true);
                    setJoystickControlled(true);
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

	public static void main(String... args) {
		new CarGame().start();
	}
}