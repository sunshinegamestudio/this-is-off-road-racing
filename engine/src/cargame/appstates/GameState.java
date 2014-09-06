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

package cargame.appstates;

import cargame.entities.Terrain;
import cargame.entities.Sky;
import cargame.entities.Sun;
import cargame.entities.SimpleCarPlayer;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.Joystick;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.JoyInput;
import com.jme3.input.controls.JoyAxisTrigger;
import com.jme3.input.controls.JoyButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.system.Timer;
import jme3tools.savegame.SaveGame;

import java.util.logging.Level;

import cargame.core.CarGame;
import cargame.entities.*;
import cargame.gui.GameHUDScreenController_Analog;
import cargame.gui.GameHUDScreenController_Digital;
import cargame.inputcontrollers.JoystickInputController;
import cargame.inputcontrollers.KeyboardInputController;
import cargame.inputcontrollers.TouchScreenInputController;
import cargame.other.Time;
import cargame.other.TrackStatistics;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;

public class GameState extends AbstractAppState implements ActionListener, AnalogListener   {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;
    
    protected BitmapText fpsText;
    protected BitmapText menuText;
    protected BitmapText currentLapText;
    protected BitmapText maxLapsText;
    protected BitmapText fastestLapTimeText;
    protected BitmapText currentLapTimeText;
    protected BitmapText currentTimeText;
    protected BitmapFont guiFont;

    private boolean newLap = false;
    private int currentLap = 0;
    private int maxLaps = 4;
    // private int maxLaps = 1;
    private long fastestLapTime = 0;
    private Time fastestLapTimeTime;
    private long currentLapTime = 0;
    private Time currentLapTimeTime;
    private long lastLapTime = 0;
    private long currentTime = 0;
    private Time currentTimeTime;
    private long lapTimes[];
    
    private boolean isOnStartingPoint = true;

    // protected FlyByCamera flyCam;
    // protected ChaseCamera chaseCam;

    private Sun sun;
    //private Sky sky;
    private Terrain terrain;
    private StartingPoint startingPoint;
    //private Terrain_node terrain_node;
    //private CarPlayer player;
    private SimpleCarPlayer player;
    //private CharacterPlayer player;
    //private SimpleEnemy simpleEnemy;
    
    private TrackStatistics trackStatistics;

    private AbstractAppState inputController;
    private ThirdPersonCameraState thirdPersonCameraState;
    private FPSState fpsState;
    private StartingPointState startingPointState;

    private NiftyJmeDisplay niftyDisplay = null;
    private Nifty nifty = null;
    
    private CarGame game = null;
    
    public GameState(CarGame game) {
    	this.game = game;

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        this.game.getLogger().log(Level.SEVERE, "GameState created.");
    }
    
    public void startNewGame()  {
        System.out.println("startNewGame");
    }


    public void onAction(String name, boolean value, float tpf) {
            System.out.println("Name: " + name);
            System.out.println("Value: " + value);

            if (CarGame.getApp().isKeyboardControlled())    {
                if (name.equals("CARGAME_Exit")){
                    game.stop();
                }else if (name.equals("CARGAME_LoadMenu")){
                    game.loadMenu(this);
                }

            }
        }

    public void onAnalog(String name, float value, float tpf) {
    }

    public void loadText(){
        /*
        currentLapText;
        fastestLapTimeText;
        currentLapTimeText;
         * Create all the BitmapText.
         */

        guiFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        menuText = new BitmapText(guiFont, false);
        currentLapText = new BitmapText(guiFont, false);
        currentLapText.setSize(guiFont.getCharSet().getRenderedSize());
        currentLapText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-100, 0);
        currentLapText.setText("Current Lap: ");
        guiNode.attachChild(currentLapText);

        maxLapsText = new BitmapText(guiFont, false);
        maxLapsText.setSize(guiFont.getCharSet().getRenderedSize());
        maxLapsText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-80, 0);
        maxLapsText.setText("Max Laps: ");
        guiNode.attachChild(maxLapsText);

        fastestLapTimeText = new BitmapText(guiFont, false);
        fastestLapTimeText.setSize(guiFont.getCharSet().getRenderedSize());
        fastestLapTimeText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-60, 0);
        fastestLapTimeText.setText("Fastest Lap: ");
        guiNode.attachChild(fastestLapTimeText);
        
        currentLapTimeText = new BitmapText(guiFont, false);
        currentLapTimeText.setSize(guiFont.getCharSet().getRenderedSize());
        currentLapTimeText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-40, 0);
        currentLapTimeText.setText("Current Lap: ");
        guiNode.attachChild(currentLapTimeText);

        currentTimeText = new BitmapText(guiFont, false);
        currentTimeText.setSize(guiFont.getCharSet().getRenderedSize());
        currentTimeText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-20, 0);
        currentTimeText.setText("Current Time: ");
        guiNode.attachChild(currentTimeText);
    }

    private void loadMenu() {
}

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        // enable depth test and back-face culling for performance
        app.getRenderer().applyRenderState(RenderState.DEFAULT);

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);
        loadMenu();

        // game.getPhysicsSpace().enableDebug(game.getAssetManager());
        
        // Load game

        if(niftyDisplay != null)    {
            game.getGUIViewPort().addProcessor(niftyDisplay);
        }
        game.getInputManager().setCursorVisible(true);
        
        loadText();

        if (game.getInputManager() != null){
            /*
            flyCam = new FlyByCamera(game.getCamera());
            flyCam.setMoveSpeed(5f);
            flyCam.registerWithInput(game.getInputManager());
             * Replaced with ChaseCam
             */
            //chaseCam = new ChaseCamera(game.getCamera(), player.getNode(), game.getInputManager());

            game.getInputManager().addMapping("CARGAME_Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
            game.getInputManager().addMapping("CARGAME_LoadMenu", new KeyTrigger(KeyInput.KEY_M));
        }
        
        // Initialize InputController here
        if("Joystick/Gamepad".equals(game.getInputController()))   {
            inputController = new JoystickInputController(game);
        }
        else if("Keyboard".equals(game.getInputController()))   {
            inputController = new KeyboardInputController(game);
        }
        else if("Touch Screen".equals(game.getInputController()))   {
            inputController = new TouchScreenInputController(game);
        }
        if(inputController != null)   {
            game.getStateManager().attach(inputController);
        }
        
        // Attach FPS state to StateManager here !!!
        fpsState = new FPSState(game);
        game.getStateManager().attach(fpsState);

        // Attach StartingPoint state to StateManager here !!!
        startingPointState = new StartingPointState(game);
        game.getStateManager().attach(startingPointState);

        // Attach third person camera to StateManager here !!!
        thirdPersonCameraState = new ThirdPersonCameraState(game);
        game.getStateManager().attach(thirdPersonCameraState);
        
        String track = game.getTrack();
        
        sun = new Sun(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //sky = new Sky(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        terrain = new Terrain(track, game.getAssetManager(), rootNode, game.getPhysicsSpace());
        startingPoint = new StartingPoint(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera());
        //terrain_node = new Terrain_node(game.getCamera(), game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new CarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        player = new SimpleCarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new CharacterPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera());
        //simpleEnemy = new SimpleEnemy(player, game.getAssetManager(), rootNode, game.getPhysicsSpace());
        
        currentLap = 0;
        lapTimes = new long[10];
        for(int i=0; i<maxLaps; i++)    {
            lapTimes[i] = 0;
        }
        fastestLapTimeTime = new Time();
        currentLapTimeTime = new Time();
        currentTimeTime = new Time();

        // Load the fastest laptime (still to implement !!!)
        /*
        try {
        trackStatistics = (TrackStatistics) SaveGame.loadGame("com/sunshinegamestudio/cargame", "Default_Track");
        } catch (NullPointerException nullPointerException) {
            trackStatistics = new TrackStatistics();
            trackStatistics.setFastestLapTime(fastestLapTime);
            SaveGame.saveGame("com/sunshinegamestudio/cargame", "Default_Track", trackStatistics);
        }
        trackStatistics = (TrackStatistics) SaveGame.loadGame("com/sunshinegamestudio/cargame", "Default_Track");
        fastestLapTime = trackStatistics.getFastestLapTime();
         * Temperairy disabled until fixed
         */

        /*
        if (game.getInputManager() != null){
            chaseCam = new ChaseCamera(game.getCamera(), player.getNode(), game.getInputManager());
        }
         *
         */

        game.getInputManager().addListener(this, "CARGAME_Exit",
                "CARGAME_LoadMenu");
        
        // if(flyCam != null) flyCam.setEnabled(true);
        // if(chaseCam != null) chaseCam.setEnabled(true);
    	
        // game.getViewPort().attachScene(rootNode);
        // game.getGUIViewPort().attachScene(guiNode);
        
        //lapTime.reset();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        this.game.getLogger().log(Level.INFO, "GameState-update: super updated.");
        
        // To StartingPointState
        
        // Check for new lap
         newLap = startingPointState.checkForNewLap();
        
        if (newLap == true)
        {
            // Calculate the current laptime
            currentLapTime = game.getTimer().getTime() - lastLapTime;
            
            // Set the current currentLapTime in the lapTimes array
            lapTimes[currentLap]=currentLapTime;
            
            // Check if there is a new fastest laptime
            if ((fastestLapTime > currentLapTime) || (currentLap == 0))
            {
                // Make the current laptime the fastest laptime
                fastestLapTime = currentLapTime;
                // Save the fastest laptime (still to implement !!!)
                /*
                trackStatistics.setFastestLapTime(fastestLapTime);
                SaveGame.saveGame("com/sunshinegamestudio/cargame", "Default_Track", trackStatistics);
                 * Temperairy disabled until fixed
                 */
            }

            // Set the last laptime and increment the current lap
            lastLapTime = currentLapTime;
            currentLap = currentLap + 1;
        }
        
        // Update current time
        currentTime = game.getTimer().getTime() - currentLapTime;
        
        // Update HUD
        currentLapText.setText("Current Lap: " + currentLap);
        maxLapsText.setText("Max Laps: " + maxLaps);
        fastestLapTimeTime.setTime(fastestLapTime / game.getTimer().getResolution());
        // fastestLapTimeText.setText("Fastest LapTime: " + fastestLapTime);
        fastestLapTimeText.setText("Fastest LapTime: " + fastestLapTimeTime.getHour() + ":" + fastestLapTimeTime.getMinute() + ":" + fastestLapTimeTime.getSecond() + ":" + fastestLapTimeTime.getMilisecond());
        currentLapTimeTime.setTime(currentLapTime / game.getTimer().getResolution());
        // currentLapTimeText.setText("Current LapTime: " + currentLapTime);
        currentLapTimeText.setText("Current LapTime: " + currentLapTimeTime.getHour() + ":" + currentLapTimeTime.getMinute() + ":" + currentLapTimeTime.getSecond() + ":" + currentLapTimeTime.getMilisecond());
        currentTimeTime.setTime(currentTime / game.getTimer().getResolution());
        // currentTimeText.setText("Current Time: " + currentTime);
        currentTimeText.setText("Current Time: " + currentTimeTime.getHour() + ":" + currentTimeTime.getMinute() + ":" + currentTimeTime.getSecond() + ":" + currentTimeTime.getMilisecond());

        // Replace flyCam with ChaseCamera (see example TestChaseCamera.jave) !!!!!!!!!!!!!!!!!!!1

        /*
        Vector3f camDir = flyCam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = flyCam.getLeft().clone().multLocal(0.4f);
        flyCam.setLocation(player.getNode().getLocalTranslation());
         * Some methods don't work. Find another way for this or ask tutorial writer for correction.
         */

        this.game.getLogger().log(Level.INFO, "GameState-update: begin update nodes.");

        // Place the camera behind the player.
        // Now in ThirdPersonCameraState.

        if(currentLap>=maxLaps) {
            game.loadResultsMenu(lapTimes[0], lapTimes[1], lapTimes[2], lapTimes[3]);
        }
    }

    @Override
    public void render(RenderManager rm) {
    }

    @Override
    public void cleanup() {
        super.cleanup();

        // Unload game
        rootNode.detachAllChildren();
        guiNode.detachAllChildren();

        game.getInputManager().setCursorVisible(false);
        if(niftyDisplay != null)    {
            game.getGUIViewPort().removeProcessor(niftyDisplay);
        }

        // Cleanup ThirdPersonCameraState here !!!
        game.getStateManager().detach(thirdPersonCameraState);
        
        // Cleanup StartingPoint state to StateManager here !!!
        game.getStateManager().detach(startingPointState);
        
        // Cleanup FPSState here !!!
        game.getStateManager().detach(fpsState);
        
        // Cleanup InputListener here.
        if(inputController != null)   {
            game.getStateManager().detach(inputController);
        }
        
        game.getInputManager().removeListener(this);
        // if(flyCam != null) flyCam.setEnabled(false);
        // if(chaseCam != null) chaseCam.setEnabled(false);
    	
        // game.getViewPort().detachScene(rootNode);
        // game.getGUIViewPort().detachScene(guiNode);
    }

    public SimpleCarPlayer getPlayer() {
        return player;
    }
    
    private boolean checkForNewLap()   {
        if((isOnStartingPoint == false) && (startingPoint.isOnStartinPoint() == true))    {
            isOnStartingPoint = true;
            return true;
        }   else    {
            return false;
        }
    }
}
