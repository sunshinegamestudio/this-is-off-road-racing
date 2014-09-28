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
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
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
import cargame.gui.GameHUDScreenController_Analog;
import cargame.gui.GameHUDScreenController_Digital;

public class KeyboardInputController extends AbstractAppState implements ActionListener{

    private CarGame game = null;
    private GameState gamestate;
    private SimpleCarPlayer player;

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

    public KeyboardInputController(CarGame game) {
    	this.game = game;
        gamestate = game.getStateManager().getState(GameState.class);
        player=gamestate.getPlayer();

        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    public void onAction(String name, boolean value, float tpf) {
        if (!value)
            return;
        // Load other state
        // game.loadGame("Default");
        
        player=gamestate.getPlayer();
        if(player != null)  {
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

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    @Override
    public void render(RenderManager rm) {
    }
    
    @Override
    public void cleanup() {
        super.cleanup();

        game.getInputManager().removeListener(this);
    }
}