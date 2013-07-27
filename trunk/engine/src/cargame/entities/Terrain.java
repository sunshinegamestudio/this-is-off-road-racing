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
public class Terrain extends Entity {
    
    private Node terrain_geo;

    public Terrain(String track, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

        //com.jme3.terrain
        //BufferGeomap

        /** Create a temperairy ground for Android Test track */
        //if (track=="Android Test")  {
        /*
            Node boxNode = new Node();
            Box box = new Box( Vector3f.ZERO, 100,1,100);
            RigidBodyControl rigidBody = new RigidBodyControl();
            
            Geometry red = new Geometry("Box", box);
            Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat2.setColor("Color", ColorRGBA.Red);
            red.setMaterial(mat2);
            red.move(0,-2,0);
            boxNode.attachChild(red);
            //boxNode.move(new Vector3f(0, -15, 0));
            
            BoxCollisionShape boxShape = new BoxCollisionShape(new Vector3f(100,0.1f,100));
            rigidBody.setCollisionShape(boxShape);
            rigidBody.setMass(0);
            boxNode.addControl(rigidBody);
            
            getParent().attachChild(boxNode);
            getPhysicsSpace().addAll(boxNode);
        */
        //}
        
        // Load selected track
        Node terrain = (Node) assetManager.loadModel("Tracks/" + track + "/Scenes/terrain_1.j3o");
      
        terrain.setLocalTranslation(new Vector3f(0,-1,10));
        terrain.updateGeometricState();

        //Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/WireColor.j3md");
        //Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat3.setColor("m_Color", ColorRGBA.Red);
        //terrain.attachDebugShape(mat3);

        getParent().attachChild(terrain);
        getPhysicsSpace().addAll(terrain);

        if (CarGame.getApp().getPlatform()=="Android")  {
            terrain_geo = (Node)getParent().getChild("terrain-terrain_1_node");
            if(terrain_geo != null)    {
                Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
                // mat.setColor("Color", ColorRGBA.Red);
                String texture_1 = terrain_geo.getUserData("texture_1");
                // Texture default_text = assetManager.loadTexture("Tracks/Grass Hill/Textures/Terrain/simple/grass.jpg");
                Texture default_text = assetManager.loadTexture(texture_1);
                mat.setTexture("ColorMap", default_text);
                terrain_geo.setMaterial(mat);
            }
        }
    }

}
