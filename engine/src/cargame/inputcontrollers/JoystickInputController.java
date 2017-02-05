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

package cargame.inputcontrollers;

import cargame.appstates.*;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.JoyInput;
import com.jme3.input.controls.JoyAxisTrigger;
import com.jme3.input.controls.JoyButtonTrigger;
import com.jme3.material.RenderState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.*;

import java.util.logging.Level;

import cargame.core.CarGame;
import cargame.entities.SimpleCarPlayer;
import cargame.gui.nifty.screencontrollers.GameHUDScreenController_Analog;
import cargame.gui.nifty.screencontrollers.GameHUDScreenController_Digital;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.InputListener;

public class JoystickInputController extends AbstractAppState implements AnalogListener, ActionListener, CleanupManualInterface {

    private CarGame game = null;
    private GameState gamestate;
    private SimpleCarPlayer player;

    private float acceleration_dig_v = -800;
    private float acceleration_dig_nv = 800;
    private float brake_dig_v = 800f;
    private float brake_dig_nv = -0f;
    private float steer_dig_v = .1f;
    private float steer_dig_nv = -.1f;
    
    // private float sensitivity = 0.1f;
    private float sensitivity = 0.002f;
    private float acceleration_ana_v = (-800 * sensitivity);
    private float acceleration_ana_nv = (800 * sensitivity);
    private float brake_ana_v = (800f * sensitivity);
    private float brake_ana_nv = (-0f * sensitivity);
    private float steer_ana_v = (.1f * sensitivity * 16);
    private float steer_ana_nv = (-.1f * sensitivity * 16);

    private boolean cleanedupManual = false;

    public JoystickInputController(CarGame game) {
        this.game = game;
        gamestate = game.getStateManager().getState(GameState.class);
        player=gamestate.getPlayer();

        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    public void onAnalog(String name, float intensity, float tpf) {
        player=gamestate.getPlayer();
        if(player != null)  {
         if (name.equals("RightStick Left")) {
                    if (intensity > 0) { 
                        player.setSteering(steer_ana_v * intensity * 40000);
                        // player.addSteering(steer_ana_v);
                    }   else    { 
                        // player.addSteering(steer_ana_nv);
                    }
                    player.processSteering();
            }
            else if (name.equals("RightStick Right")) {
                    if (intensity > 0) {
                        player.setSteering(-steer_ana_v * intensity * 40000);
                        // player.addSteering(-steer_ana_v);
                    }   else    {
                        // player.addSteering(-steer_ana_nv);
                    }
                    player.processSteering();
            }
            else if (name.equals("LeftStick Up")) {
                    if (intensity > 0) {
                        // player.setAcceleration(acceleration_ana_v);
                        player.addAcceleration(acceleration_ana_v);
                    }   else    {
                        // player.addAcceleration(acceleration_ana_nv);  
                    }
                    player.processAcceleration();
            }
            else if (name.equals("LeftStick Down")) {
                    if (intensity > 0) {
                        player.brake(brake_ana_v);
                    }   else    {
                        player.brake(brake_ana_nv);
                    }
            }
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("RightStick Left")) {
            if (!isPressed) { 
                player.setSteering(0);
                // player.addSteering(steer_ana_nv);
                player.processSteering();
            }
        }
        else if (name.equals("RightStick Right")) {
            if (!isPressed) {
                player.setSteering(0);
                // player.addSteering(-steer_ana_nv);
                player.processSteering();
            }
        }
        // else if (name.equals("Ups")) {
        else if (name.equals("LeftStick Up")) {
            if (!isPressed)  {
                player.setAcceleration(0);  
                // player.addAcceleration(acceleration_ana_nv);  
                player.processAcceleration();
            }
        }
        else if (name.equals("Button X")) {
            if (isPressed)  {
                if(player != null)    {
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
            }
        }
        
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        // Init input
        /*
        if (game.getInputManager() != null){
            game.getInputManager().addMapping("CARGAME_Exit1", new KeyTrigger(KeyInput.KEY_ESCAPE));
        }

        game.getInputManager().addListener(this, "CARGAME_Exit1");
        */

        // Initialise/setup input bindings here?
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

        cleanedupManual = false;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    @Override
    public void render(RenderManager rm) {
    }
    
    @Override
    public void cleanupManual() {
        // cleanup
        game.getInputManager().removeListener(this);

        cleanedupManual=true;
    }

    @Override
    public void cleanup() {
        super.cleanup();

        if(cleanedupManual == false) {
            cleanupManual();
        }
    }
}