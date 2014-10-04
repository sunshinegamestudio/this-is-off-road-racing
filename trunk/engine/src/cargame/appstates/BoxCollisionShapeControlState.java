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

import cargame.controls.BoxCollisionShapeControl;
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
import cargame.entities.PassThroughZoneDetection;
import cargame.entities.SimpleCarPlayer;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

public class BoxCollisionShapeControlState extends AbstractAppState    {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    private CarGame game = null;
    
    public BoxCollisionShapeControlState(CarGame game) {
    	this.game = game;

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }

    
    SceneGraphVisitor boxcol_initialize = new SceneGraphVisitor() {
        
        @Override
        public void visit(Spatial spatial) {
            String boxCollisionShape;
            
            try {
                boxCollisionShape = spatial.getUserData("boxCollisionShape");
                if(boxCollisionShape.equals("true"))   {
                    BoxCollisionShapeControl boxCollisionShapeControl=new BoxCollisionShapeControl();
                    spatial.addControl(boxCollisionShapeControl);
                }
            }
            catch (Exception e)   {
                
            }
        }
    };

    SceneGraphVisitor boxcol_cleanup = new SceneGraphVisitor() {
        @Override
        public void visit(Spatial spatial) {
            String boxCollisionShape;

            try {
                boxCollisionShape = spatial.getUserData ("boxCollisionShape");
                if(boxCollisionShape.equals("true"))   {
                    spatial.removeControl(BoxCollisionShapeControl.class);
                }
            }
            catch (Exception e)   {
                
            }
        }
    };
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        rootNode.breadthFirstTraversal(boxcol_initialize);
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

        rootNode.breadthFirstTraversal(boxcol_cleanup);
    }
}