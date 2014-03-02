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

    private float acceleration_dig_v = -800;
    private float acceleration_dig_nv = 800;
    private float brake_dig_v = 800f;
    private float brake_dig_nv = -0f;
    private float steer_dig_v = .1f;
    private float steer_dig_nv = -.1f;
    
    private float sensitivity = 0.1f;
    private float acceleration_ana_v = (-800 * sensitivity);
    private float acceleration_ana_nv = (800 * sensitivity);
    private float brake_ana_v = (800f * sensitivity);
    private float brake_ana_nv = (-0f * sensitivity);
    private float steer_ana_v = (.1f * sensitivity);
    private float steer_ana_nv = (-.1f * sensitivity);
            
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

    Joystick[] joysticks = null;
    
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;

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

                //player.resetControls();

                /*
                if (name.equals("Lefts")) {
                    if (value)
                        { player.left(true);}
                    else
                        { player.left(false);}
                } else if (name.equals("Rights")) {
                    if (value)
                        { player.right(true);}
                    else
                        { player.right(false);}
                } else if (name.equals("Ups")) {
                    if (value)
                        { player.up(true);}
                    else
                        { player.up(false);}
                } else if (name.equals("Downs")) {
                    if (value)
                        { player.down(true);}
                    else
                        { player.down(false);}
                } else if (name.equals("Jumps")) {
                    if (value)
                        { player.jump(true);}
                    else
                        { player.jump(false);}
                }
                 * 
                 */
                
                if (name.equals("Lefts")) {
                    if (value)
                        { player.steer(steer_dig_v);}
                    else
                        { player.steer(steer_dig_nv);}
                } else if (name.equals("Rights")) {
                    if (value)
                        { player.steer(-steer_dig_v);}
                    else
                        { player.steer(-steer_dig_nv);}
                } else if (name.equals("Ups")) {
                    if (value)
                        { player.accelerate(acceleration_dig_v);}
                    else
                        { player.accelerate(acceleration_dig_nv);}
                } else if (name.equals("Downs")) {
                    if (value)
                        { player.brake(brake_dig_v);}
                    else
                        { player.brake(brake_dig_nv);}
                } else if (name.equals("Gears")) {
                    if (value)  {
                        if(player.getGear()>0)  {
                            player.setGear(-1);
                        }
                        else if(player.getGear()<0)  {
                            player.setGear(1);
                        }
                        else    {
                            player.setGear(1);
                        }
                    }
                } else if (name.equals("Jumps")) {
                    //player.getNode().jump();
                }
            }
        }

    public void onAnalog(String name, float value, float tpf) {
        if (CarGame.getApp().isJoystickControlled())    {
            if (name.equals("RightStick Left")) {
                    if (value != 0)
                        { player.steer(steer_ana_v); }
                    else
                        { player.steer(steer_ana_nv);}
            }
            else if (name.equals("RightStick Right")) {
                    if (value != 0)
                        { player.steer(-steer_ana_v); }
                    else
                        { player.steer(-steer_ana_nv);}
            }
            else if (name.equals("LeftStick Up")) {
                    if (value != 0)
                        { player.accelerate(acceleration_ana_v); }
                    else
                        { player.accelerate(acceleration_ana_nv);}
            }
            else if (name.equals("LeftStick Down")) {
                    if (value != 0)
                        { player.brake(brake_ana_v); }
                    else
                        { player.brake(brake_ana_nv);}
            }
        }
    }

    private void setupKeys() {
        game.getInputManager().addMapping("Lefts",  new KeyTrigger(KeyInput.KEY_LEFT));
        game.getInputManager().addMapping("Rights", new KeyTrigger(KeyInput.KEY_RIGHT));
        game.getInputManager().addMapping("Ups",    new KeyTrigger(KeyInput.KEY_UP));
        game.getInputManager().addMapping("Downs",  new KeyTrigger(KeyInput.KEY_DOWN));
        game.getInputManager().addMapping("Gears",  new KeyTrigger(KeyInput.KEY_R));
        game.getInputManager().addMapping("Jumps",  new KeyTrigger(KeyInput.KEY_SPACE));
        game.getInputManager().addListener(this, "Lefts");
        game.getInputManager().addListener(this, "Rights");
        game.getInputManager().addListener(this, "Ups");
        game.getInputManager().addListener(this, "Downs");
        game.getInputManager().addListener(this, "Gears");
        game.getInputManager().addListener(this, "Jumps");
    }
    
    private void setupJoystick()    {
        joysticks = game.getInputManager().getJoysticks();
        if (joysticks == null)
            throw new IllegalStateException("Cannot find any joysticks!");
            //hudTextHeader.setText("Cannot find any joysticks!");
                         
        for (Joystick joy : joysticks){
            System.out.println(joy.toString());
            //hudTextHeader.setText(joy.getName()+" with "+joy.getButtonCount()+" buttons and "+joy.getAxisCount()+" axes");
        }
 
        //for (Joystick joy : joysticks){
        //    System.out.println(joy.getAxisCount());
        //    System.out.println(joy.getButtonCount());
        //}
         
        // DPAD
        game.getInputManager().addMapping("DPAD Left", new JoyAxisTrigger(0, JoyInput.AXIS_POV_X, true));
        game.getInputManager().addMapping("DPAD Right", new JoyAxisTrigger(0, JoyInput.AXIS_POV_X, false));
        game.getInputManager().addMapping("DPAD Down", new JoyAxisTrigger(0, JoyInput.AXIS_POV_Y, true));
        game.getInputManager().addMapping("DPAD Up", new JoyAxisTrigger(0, JoyInput.AXIS_POV_Y, false));
        // Buttons
        game.getInputManager().addMapping("Button A", new JoyButtonTrigger(0, 0));
        game.getInputManager().addMapping("Button B", new JoyButtonTrigger(0, 1));
        game.getInputManager().addMapping("Button X", new JoyButtonTrigger(0, 2));
        game.getInputManager().addMapping("Button Y", new JoyButtonTrigger(0, 3));
        game.getInputManager().addMapping("Trigger L1", new JoyButtonTrigger(0, 4));
        game.getInputManager().addMapping("Trigger R1", new JoyButtonTrigger(0, 5));
        game.getInputManager().addMapping("Select", new JoyButtonTrigger(0, 6));
        game.getInputManager().addMapping("Start", new JoyButtonTrigger(0, 7));
        game.getInputManager().addMapping("LStick Click", new JoyButtonTrigger(0, 8));
        game.getInputManager().addMapping("RStick Click", new JoyButtonTrigger(0, 9));
 
         
        game.getInputManager().addListener(this,  "DPAD Left",
                                        "DPAD Right",
                                        "DPAD Down",
                                        "DPAD Up",
                                         
                                        "Button A",
                                        "Button B",
                                        "Button X",
                                        "Button Y",
                                        "Trigger L1",
                                        "Trigger R1",
                                        "Select",
                                        "Start",
                                        "LStick Click",
                                        "RStick Click"
                                        );
        game.getInputManager().addMapping("6th axis negative", new JoyAxisTrigger(0, 5, true));
        game.getInputManager().addMapping("6th axis positive", new JoyAxisTrigger(0, 5, false));
        game.getInputManager().addMapping("Trigger R2", new JoyAxisTrigger(0, 4, true));
        game.getInputManager().addMapping("Trigger L2", new JoyAxisTrigger(0, 4, false));
        game.getInputManager().addMapping("RightStick Left", new JoyAxisTrigger(0, 3, true));
        game.getInputManager().addMapping("RightStick Right", new JoyAxisTrigger(0, 3, false));
        game.getInputManager().addMapping("RightStick Down", new JoyAxisTrigger(0, 2, false));
        game.getInputManager().addMapping("RightStick Up", new JoyAxisTrigger(0, 2, true));
        game.getInputManager().addMapping("LeftStick Left", new JoyAxisTrigger(0, 1, true));
        game.getInputManager().addMapping("LeftStick Right", new JoyAxisTrigger(0, 1, false));
        game.getInputManager().addMapping("LeftStick Down", new JoyAxisTrigger(0, 0, false));
        game.getInputManager().addMapping("LeftStick Up", new JoyAxisTrigger(0, 0, true));
        game.getInputManager().addListener(this,  "LeftStick Left",
                                        "LeftStick Right",   
                                        "LeftStick Down",
                                        "LeftStick Up",
                                        "RightStick Left",
                                        "RightStick Right",
                                        "RightStick Down",
                                        "RightStick Up",
                                        "Trigger R2",
                                        "Trigger L2",
                                        "6th axis negative",
                                        "6th axis positive"
                                        );
    }

    public void loadText(){
        guiFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        fpsText = new BitmapText(guiFont, false);
        fpsText.setSize(guiFont.getCharSet().getRenderedSize());
        fpsText.setLocalTranslation(0, fpsText.getLineHeight(), 0);
        //fpsText.setText("Frames per second");
        guiNode.attachChild(fpsText);
        
        menuText = new BitmapText(guiFont, false);
        menuText.setSize(guiFont.getCharSet().getRenderedSize());
        menuText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f), 0);
        menuText.setText("Press [M] to go back to the Menu");
        guiNode.attachChild(menuText);
        
        /*
        currentLapText;
        fastestLapTimeText;
        currentLapTimeText;
         * Create all the BitmapText.
         */
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
        if (CarGame.getApp().isTouchscreenControlled()) {
            niftyDisplay = game.getNiftyDisplay();
            nifty = niftyDisplay.getNifty();

            /*
            nifty.fromXml("General/Interface/GameHUD_Analog.xml", "GameHUD");
            GameHUDScreenController_Analog gameHUDScreenController_Analog = (GameHUDScreenController_Analog)nifty.getScreen("GameHUD").getScreenController();
            gameHUDScreenController_Analog.setGameState(this);
            */

            
            nifty.fromXml("General/Interface/GameHUD_Digital.xml", "GameHUD");
            GameHUDScreenController_Digital gameHUDScreenController_Digital = (GameHUDScreenController_Digital)nifty.getScreen("GameHUD").getScreenController();
            gameHUDScreenController_Digital.setGameState(this);
            

            // attach the nifty display to the gui view port as a processor
            // game.getGUIViewPort().addProcessor(niftyDisplay);
        }
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

        setupKeys();
        //setupJoystick();
        
        // Add a simple Box
        /*
        Box boxshape1 = new Box(new Vector3f(-3f,1.1f,0f), 1f,1f,1f);
        Geometry cube = new Geometry("My Textured Box", boxshape1);
        Material mat_stl = new Material(game.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
        //Texture tex_ml = game.getAssetManager().loadTexture("Interface/Logo/Monkey.jpg");
        //mat_stl.setTexture("m_ColorMap", tex_ml);
        cube.setMaterial(mat_stl);
        rootNode.attachChild(cube);
         *
         */

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
        
        int fps = (int) game.getTimer().getFrameRate();
        fpsText.setText("Frames per second: "+fps);
        
        // Set isOnStartingPoint to false, only if the player was already on startingPoint
        if(isOnStartingPoint == true)   {
            if(startingPoint.isOnStartinPoint() == false)  {
                isOnStartingPoint = false;
            }
        }
        
        // Check for new lap
         newLap = checkForNewLap();
        
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

        // simple update and root node
        //player.update(tpf);
        //simpleEnemy.update(tpf);

        /*
        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();
        */

        // Place the camera behind the player
        Vector3f direction = player.getNode().getLocalRotation().getRotationColumn(2);
        Vector3f direction2 = player.getNode().getLocalRotation().getRotationColumn(1);

        float yDirection = direction2.y;
        float xDirection = direction.x;
        float zDirection = direction.z;

        Vector3f camLocation = new Vector3f(player.getNode().getWorldTranslation().x+(xDirection*10), player.getNode().getWorldTranslation().y+4f, player.getNode().getWorldTranslation().z + (zDirection*10));

        game.getCamera().setLocation(camLocation);
        game.getCamera().lookAt(player.getNode().getWorldTranslation(), Vector3f.UNIT_Y);

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
        game.getInputManager().removeListener(this);
        // if(flyCam != null) flyCam.setEnabled(false);
        // if(chaseCam != null) chaseCam.setEnabled(false);
    	
        // game.getViewPort().detachScene(rootNode);
        // game.getGUIViewPort().detachScene(guiNode);
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
