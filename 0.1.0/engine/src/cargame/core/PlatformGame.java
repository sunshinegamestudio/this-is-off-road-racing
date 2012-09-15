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
import cargame.core.statetasks.ChangeStateTask;
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
import com.jme3.system.Timer;

import java.util.Collection;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import cargame.appstates.MainMenuState;
import cargame.appstates.InGameMenuState;
import cargame.appstates.GameState;

public class PlatformGame extends Application {

    protected ThreadingType threadingType = ThreadingType.SEQUENTIAL;
    public BroadphaseType broadphaseType = BroadphaseType.DBVT;
    public Vector3f worldMin = new Vector3f(-10000f, -10000f, -10000f);
    public Vector3f worldMax = new Vector3f(10000f, 10000f, 10000f);


	private GameState te = null;
	private MainMenuState ms = null;
	private InGameMenuState is = null;
        private BulletAppState bulletAppState = null;
        private Logger logger;
        private FileHandler fh;

        static PlatformGame thisApp;
	
	public PlatformGame() {
		thisApp=this;
	}

        public static PlatformGame getApp()  {
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
        logger = Logger.getLogger(PlatformGame.class.getName());
        /*
        try {
            fh = new FileHandler("PlatformGame_log.xml");
        } catch (IOException ex) {
            Logger.getLogger(PlatformGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PlatformGame.class.getName()).log(Level.SEVERE, null, ex);
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

		// Create the States
                bulletAppState = new BulletAppState();
                ms = new MainMenuState(this);
		te = new GameState(this);
                is = new InGameMenuState(this);
		
		// Attach MenuState
                getStateManager().attach(bulletAppState);
		getStateManager().attach(ms);
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
                this.enqueue(new ChangeStateTask(te,ms,viewPort,stateManager));
        }
	
	
	public void loadGame() {
                this.enqueue(new ChangeStateTask(ms,te,viewPort,stateManager));
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
	
	
	public static void main(String... args) {
		new PlatformGame().start();
	}
}