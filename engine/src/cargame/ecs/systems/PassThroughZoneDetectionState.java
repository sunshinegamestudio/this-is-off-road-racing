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

package cargame.ecs.systems;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.*;

import java.util.logging.Level;

import cargame.core.CarGame;
import cargame.ecs.entities.PassThroughZoneDetection;
import cargame.ecs.entities.SimpleCarPlayer;

public class PassThroughZoneDetectionState extends AbstractAppState implements CleanupManualInterface   {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    private CarGame game = null;

    private PassThroughZoneDetection passThroughZoneDetection;
    private SimpleCarPlayer player;
    
    private boolean isOnPassThroughDetectionZone = true;
    
    private boolean cleanedupManual = false;

    public PassThroughZoneDetectionState(CarGame game) {
    	this.game = game;

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        passThroughZoneDetection = new PassThroughZoneDetection(game.getAssetManager(), rootNode, game.getPhysicsSpace(), game.getCamera(), "startingpoint_1-ogremesh");
        game.getStateManager().attach(passThroughZoneDetection);

        cleanedupManual = false;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        if(passThroughZoneDetection.isInitialized())    {
            // Set isOnPassThroughDetectionZone to false, only if the player was already on startingPoint
            if(isOnPassThroughDetectionZone == true)   {
                if(passThroughZoneDetection.isOnPassThroughDetectionZone() == false)  {
                    isOnPassThroughDetectionZone = false;
                }
            }
        }
        else    {
            isOnPassThroughDetectionZone = true;
        }
    }
    
    @Override
    public void render(RenderManager rm) {
    }
    
    @Override
    public void cleanupManual() {
        // cleanup
        passThroughZoneDetection.cleanupManual();
        game.getStateManager().detach(passThroughZoneDetection);

        cleanedupManual=true;
    }
    
    @Override
    public void cleanup() {
        super.cleanup();

        if(cleanedupManual == false) {
            cleanupManual();
        }
    }

    public boolean checkForNewLap()   {
        if((isOnPassThroughDetectionZone == false) && (passThroughZoneDetection.isOnPassThroughDetectionZone() == true))    {
            isOnPassThroughDetectionZone = true;
            return true;
        }   else    {
            return false;
        }
    }
}