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

import cargame.entities.Track;
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
import cargame.other.PhysicsSpacePrinter;
import cargame.other.SceneGraphPrinter;
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
    
    // protected FlyByCamera flyCam;
    // protected ChaseCamera chaseCam;

    private Sun sun;
    //private Sky sky;
    private Track terrain;
    private StartingPoint startingPoint;
    //private Terrain_node terrain_node;
    //private CarPlayer player;
    private SimpleCarPlayer player;
    //private CharacterPlayer player;
    //private SimpleEnemy simpleEnemy;
    
    private AbstractAppState inputController;
    private FPSState fpsState;
    // private StartingPointState startingPointState;
    private PassThroughZoneDetectionState startingPointState;
    private PassThroughZoneDetectionState checkPointState;
    private BoxCollisionShapeControlState boxCollisionShapeControlState;
    private LapTimesState lapTimesState;
    private ThirdPersonCameraState thirdPersonCameraState;
    private CheckEndOfRaceState checkEndOfRaceState;
    private ExitMenuState exitMenuState;
    
    private PhysicsSpacePrinter physicsSpacePrinter;
    private SceneGraphPrinter sceneGraphPrinter;

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
                    // game.stop();
                    // game.loadResultsMenu(lapTimes[0], lapTimes[1], lapTimes[2], lapTimes[3]);
                    game.loadResultsMenu_return();
                }else if (name.equals("CARGAME_LoadMenu")){
                    game.loadMenu(this);
                }

            }
        }

    public void onAnalog(String name, float value, float tpf) {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        // enable depth test and back-face culling for performance
        app.getRenderer().applyRenderState(RenderState.DEFAULT);

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);
        // loadMenu();

        // game.getPhysicsSpace().enableDebug(game.getAssetManager());
        
        // Load game

        if(niftyDisplay != null)    {
            game.getGUIViewPort().addProcessor(niftyDisplay);
        }
        game.getInputManager().setCursorVisible(true);
        
        // loadText();

        if (game.getInputManager() != null){
            /*
            flyCam = new FlyByCamera(game.getCamera());
            flyCam.setMoveSpeed(5f);
            flyCam.registerWithInput(game.getInputManager());
             * Replaced with ChaseCam
             */
            //chaseCam = new ChaseCamera(game.getCamera(), player.getNode(), game.getInputManager());

            // game.getInputManager().deleteMapping("SIMPLEAPP_Exit");
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

        // Attach Sun state to StateManager here !!!
        sun = new Sun(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        game.getStateManager().attach(sun);

        // Attach Track state to StateManager here !!!
        String track = game.getTrack();
        terrain = new Track(track, game.getAssetManager(), rootNode, game.getPhysicsSpace());
        game.getStateManager().attach(terrain);

        // Attach StartingPoint state to StateManager here !!!
        // startingPointState = new StartingPointState(game);
        startingPointState = new PassThroughZoneDetectionState(game);
        game.getStateManager().attach(startingPointState);

        // Attach SimpleCarPlayer state to StateManager here !!!
        player = new SimpleCarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        game.getStateManager().attach(player);
        
        // Attach BoxCollisionShapeControlState state to StateManager here !!!
        boxCollisionShapeControlState = new BoxCollisionShapeControlState(game);
        game.getStateManager().attach(boxCollisionShapeControlState);

        // Attach LapTimes state to StateManager here !!!
        lapTimesState = new LapTimesState(game);
        game.getStateManager().attach(lapTimesState);

        // Attach third person camera to StateManager here !!!
        thirdPersonCameraState = new ThirdPersonCameraState(game);
        game.getStateManager().attach(thirdPersonCameraState);

        // Attach check end of race state to StateManager here !!!
        checkEndOfRaceState = new CheckEndOfRaceState(game);
        game.getStateManager().attach(checkEndOfRaceState);
        
        physicsSpacePrinter = new PhysicsSpacePrinter(game);

        sceneGraphPrinter = new SceneGraphPrinter();

        physicsSpacePrinter.print();

        sceneGraphPrinter.print(rootNode);
        
        //sun = new Sun(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //sky = new Sky(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //terrain = new Track(track, game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //startingPoint = new StartingPoint(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera());
        //terrain_node = new Terrain_node(game.getCamera(), game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new CarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new SimpleCarPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace());
        //player = new CharacterPlayer(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera());
        //simpleEnemy = new SimpleEnemy(player, game.getAssetManager(), rootNode, game.getPhysicsSpace());

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

        // To LapTimesState

        this.game.getLogger().log(Level.INFO, "GameState-update: begin update nodes.");

        // Place the camera behind the player.
        // Now in ThirdPersonCameraState.

            // Check the end of the race.
            // Now in CheckEndOfRaceState.
    }

    @Override
    public void render(RenderManager rm) {
    }

    @Override
    public void cleanup() {
        super.cleanup();

        game.getInputManager().setCursorVisible(false);
        if(niftyDisplay != null)    {
            game.getGUIViewPort().removeProcessor(niftyDisplay);
        }

        // Cleanup check end of race to StateManager here !!!
        checkEndOfRaceState.cleanupManual();
        game.getStateManager().detach(checkEndOfRaceState);
        
        // Cleanup ThirdPersonCameraState here !!!
        thirdPersonCameraState.cleanupManual();
        game.getStateManager().detach(thirdPersonCameraState);

        // Attach LapTimes state to StateManager here !!!
        lapTimesState.cleanupManual();
        game.getStateManager().detach(lapTimesState);

        // Cleanup BoxCollisionShapeControlState state to StateManager here !!!
        boxCollisionShapeControlState.cleanupManual();
        game.getStateManager().detach(boxCollisionShapeControlState);
        
        // Cleanup SimpleCarPlayer state to StateManager here !!!
        player.cleanupManual();
        game.getStateManager().detach(player);
        
        // Cleanup StartingPoint state to StateManager here !!!
        startingPointState.cleanupManual();
        game.getStateManager().detach(startingPointState);

        // Cleanup Track state to StateManager here !!!
        terrain.cleanupManual();
        game.getStateManager().detach(terrain);
        
        // Cleanup Sun state to StateManager here !!!
        sun.cleanupManual();
        game.getStateManager().detach(sun);
        
        // Cleanup FPSState here !!!
        game.getStateManager().detach(fpsState);
        fpsState.cleanupManual();
        
        // Cleanup InputListener here.
        if(inputController != null)   {
            CleanupManualInterface inputControllerCleanupManual = (CleanupManualInterface)inputController;
            inputControllerCleanupManual.cleanupManual();
            game.getStateManager().detach(inputController);
        }
        
        physicsSpacePrinter.print();

        sceneGraphPrinter.print(rootNode);

        // Unload game
        rootNode.detachAllChildren();
        guiNode.detachAllChildren();
        
        game.getInputManager().removeListener(this);
        // if(flyCam != null) flyCam.setEnabled(false);
        // if(chaseCam != null) chaseCam.setEnabled(false);
    	
        // game.getViewPort().detachScene(rootNode);
        // game.getGUIViewPort().detachScene(guiNode);
    }

    public SimpleCarPlayer getPlayer() {
        return player;
    }
    
    public AbstractAppState getInputController()    {
        return inputController;
    }
    
}
