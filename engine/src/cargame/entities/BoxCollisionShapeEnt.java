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

import cargame.core.CarGame;
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
 * @author Vortex
 */
public class BoxCollisionShapeEnt extends Entity {
    
    private Node boxNode;

    public BoxCollisionShapeEnt(String track, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

        for(int i=0; i<256; i++) {
            boxNode = (Node)getParent().getChild(i);
            if(boxNode != null)    {
                String boxCollisionShapeEnt = boxNode.getUserData("boxCollisionShapeEnt");
                if(boxCollisionShapeEnt=="true")    {
                    RigidBodyControl rigidBody = new RigidBodyControl();

                    BoxCollisionShape boxShape = new BoxCollisionShape(new Vector3f(100,0.1f,100));
                    rigidBody.setCollisionShape(boxShape);
                    rigidBody.setMass(0);
                    boxNode.addControl(rigidBody);

                    getPhysicsSpace().addAll(boxNode);
                }
            }
        }
    }
}
