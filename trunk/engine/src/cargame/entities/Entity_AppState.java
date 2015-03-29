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

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Sunshine GameStudio
 */
public class Entity_AppState extends AbstractAppState {
    private AssetManager assetManager;
    private Node parent;
    private PhysicsSpace physicsSpace;

    public Entity_AppState(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace)    {
        setAssetManager(assetManager);
        setParent(parent);
        setPhysicsSpace(physicsSpace);
    }

    /*
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }
    */

    /*
    @Override
    public void cleanup() {
        super.cleanup();
    }
    */
    
    /**
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * @param assetManager the assetManager to set
     */
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the physicsSpace
     */
    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }

    /**
     * @param physicsSpace the physicsSpace to set
     */
    public void setPhysicsSpace(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
    }


}
