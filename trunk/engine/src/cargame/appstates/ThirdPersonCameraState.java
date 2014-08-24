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
import cargame.entities.SimpleCarPlayer;

public class ThirdPersonCameraState extends AbstractAppState    {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    private SimpleCarPlayer player;
    private CarGame game = null;
    
    public ThirdPersonCameraState(CarGame game) {
    	this.game = game;

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        // Replace flyCam with ChaseCamera (see example TestChaseCamera.jave) !!!!!!!!!!!!!!!!!!!1

        player = game.getStateManager().getState(GameState.class).getPlayer();

        /*
        Vector3f camDir = flyCam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = flyCam.getLeft().clone().multLocal(0.4f);
        flyCam.setLocation(player.getNode().getLocalTranslation());
         * Some methods don't work. Find another way for this or ask tutorial writer for correction.
         */

        this.game.getLogger().log(Level.INFO, "GameState-update: begin update nodes.");

        // Place the camera behind the player
        Vector3f direction = player.getNode().getLocalRotation().getRotationColumn(2);
        Vector3f direction2 = player.getNode().getLocalRotation().getRotationColumn(1);

        float yDirection = direction2.y;
        float xDirection = direction.x;
        float zDirection = direction.z;

        Vector3f camLocation = new Vector3f(player.getNode().getWorldTranslation().x+(xDirection*10), player.getNode().getWorldTranslation().y+4f, player.getNode().getWorldTranslation().z + (zDirection*10));

        game.getCamera().setLocation(camLocation);
        game.getCamera().lookAt(player.getNode().getWorldTranslation(), Vector3f.UNIT_Y);
    }
    
    @Override
    public void render(RenderManager rm) {
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
}