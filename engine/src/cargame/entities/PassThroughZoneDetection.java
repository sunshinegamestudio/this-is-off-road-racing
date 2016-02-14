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

/*
 * This code is based on MonkeyZone -> svn/  trunk/ src/ com/ jme3/ monkeyzone/ controls/ ManualCharacterControl.java
 */

package cargame.entities;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.BulletAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
//import com.jme3.effect.EmitterSphereShape;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;
import java.util.ArrayList;
import java.util.List;
import jme3tools.converters.ImageToAwt;

import cargame.appstates.CleanupManualInterface;
import cargame.controls.PassThroughZoneDetectionControl;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.control.GhostControl;

/**
 *
 * @author Sunshine GameStudio
 */
public class PassThroughZoneDetection extends Entity_AppState   implements CleanupManualInterface   {
static final Quaternion ROTATE_LEFT = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y);
    //camera
    private Camera cam;

    //private Spatial passThroughZone_geo;
    private Node passThroughZone_geo;
    private String geo_name;

    private RigidBodyControl rigidBodyControl;

    private GhostControl ghostControl;
    private PassThroughZoneDetectionControl passThroughZoneDetectionControl;

    private boolean cleanedupManual = false;
    
    public PassThroughZoneDetection(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace, Camera cam, String geo_name) {
        super(assetManager, parent, physicsSpace);

        this.geo_name = geo_name;
    }
       
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.cam = cam;

        //startingpoint_geo = getParent().getChild("startingpoint_1-ogremesh");
        // passThroughZone_geo = (Node)getParent().getChild("startingpoint_1-ogremesh");
        passThroughZone_geo = (Node)getParent().getChild(geo_name);
        if(passThroughZone_geo != null)    {
            // RigidBodyControl for collision with startingpoint
            /*
            HullCollisionShape hullCollisionShape = new HullCollisionShape(passThroughZone_geo.getMesh());
            rigidBodyControl = new RigidBodyControl(hullCollisionShape);
            rigidBodyControl.setMass(0);
             * Temperary disabled
             */
            
            // GhostControl for new lap detection
            BoxCollisionShape boxCollisionShape = new BoxCollisionShape(new Vector3f(5.0f, 5.0f, 5.0f));
            // BoxCollisionShape boxCollisionShape = new BoxCollisionShape(passThroughZone_geo.getLocalScale());
            //BoxCollisionShape boxCollisionShape = new BoxCollisionShape(new Vector3f(passThroughZone_geo.getWorldBound().getVolume(), passThroughZone_geo.getWorldBound().getVolume(), passThroughZone_geo.getWorldBound().getVolume()));
            ghostControl = new GhostControl(boxCollisionShape);

            ghostControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_01);
            ghostControl.addCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
            ghostControl.removeCollideWithGroup(PhysicsCollisionObject.COLLISION_GROUP_01);

            getPhysicsSpace().add(ghostControl);
            passThroughZone_geo.addControl(ghostControl);
        
            passThroughZoneDetectionControl = new PassThroughZoneDetectionControl(getPhysicsSpace());
            //startingpoint_geo.addControl(passThroughZoneDetectionControl);

            getPhysicsSpace().addTickListener(passThroughZoneDetectionControl);
            getPhysicsSpace().addCollisionListener(passThroughZoneDetectionControl);

            cleanedupManual = false;
        }
    }

    @Override
    public void cleanupManual() {
        // cleanup
        getPhysicsSpace().removeTickListener(passThroughZoneDetectionControl);
        getPhysicsSpace().removeCollisionListener(passThroughZoneDetectionControl);

        try {
            getPhysicsSpace().remove(ghostControl);
        }
        catch(Exception e)  {
        }
        try {
            passThroughZone_geo.removeControl(ghostControl);
        }
        catch(Exception e)  {
        }
        
        cleanedupManual=true;
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        
        if(cleanedupManual == false) {
            cleanupManual();
        }
    }
    
    public boolean isOnPassThroughDetectionZone()    {
        return passThroughZoneDetectionControl.isOnPassThroughDetectionZone();

        /*
        if(passThroughZoneDetectionControl != null) {
            return passThroughZoneDetectionControl.isOnPassThroughDetectionZone();
        }   else  {
            return false;
            // return true;
        }
        */
    }
        /*
    public Node getNode()   {
        return spatial;
    }

    public CharacterControl getControl()   {
        return characterControl;
    }

    public void update(float tpf)    {
        
    }
         * 
         */
}
