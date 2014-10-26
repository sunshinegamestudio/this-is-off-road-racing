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

public class InputController extends AbstractAppState implements CleanupManualInterface {

    private CarGame game = null;
    private GameState gamestate;

    private boolean cleanedupManual = false;

    public InputController(CarGame game) {
    	this.game = game;
        gamestate = game.getStateManager().getState(GameState.class);

        this.game.getLogger().log(Level.SEVERE, "InputController created.");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

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