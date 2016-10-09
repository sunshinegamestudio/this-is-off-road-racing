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

package cargame.entities;

import cargame.tracks.BeachResort.*;
import cargame.entities.*;
import cargame.appstates.CleanupManualInterface;
import cargame.controls.BoxCollisionShapeControl;
import cargame.core.CarGame;
import cargame.tracks.GrassHill.GrassHill;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.JmeSystem;
import com.jme3.system.Platform;
import com.jme3.texture.Texture;

/**
 *
 * @author Sunshine GameStudio
 */
public class SimpleAssetEntity extends Entity_AppState implements CleanupManualInterface {
    
    private CarGame game;
    private AppStateManager stateManager;
    
    private String assetName;
    private Node assetNode;
    private BoxCollisionShapeControl boxCollisionShapeControl;
    private Vector3f initialTranslation;

    private boolean cleanedupManual = false;

    public SimpleAssetEntity(String assetName, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace, Vector3f initialTranslation) {
        super(assetManager, parent, physicsSpace);

        this.game = CarGame.getApp();
        this.stateManager = game.getStateManager();
        this.assetName = assetName;
        this.initialTranslation = initialTranslation;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        // Load selected assetNode
        assetNode = (Node) getAssetManager().loadModel(assetName);
        
        assetNode.setLocalTranslation(initialTranslation);

        boxCollisionShapeControl = new BoxCollisionShapeControl();
        assetNode.addControl(boxCollisionShapeControl);

        getParent().attachChild(assetNode);
        getPhysicsSpace().addAll(assetNode);

        cleanedupManual = false;
    }

    @Override
    public void cleanupManual() {
        // cleanup
        try {
            getPhysicsSpace().removeAll(assetNode);
        } catch(NullPointerException e)    {

        }
        getParent().detachChild(assetNode);

        cleanedupManual=true;
    }
    
    @Override
    public void cleanup()   {
        super.cleanup();
        
        if(cleanedupManual == false) {
            cleanupManual();
        }
    }
}
