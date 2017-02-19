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

package cargame.tracks.Common;

import cargame.ecs.systems.CleanupManualInterface;
import cargame.core.CarGame;
import cargame.ecs.entities.Entity;
import cargame.ecs.entities.Entity_AppState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
//import com.jme3.terrain.jbullet.TerrainPhysicsShapeFactory;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import jme3tools.converters.ImageToAwt;

/**
 *
 * @author Sunshine GameStudio
 */
// public class Terrain extends Entity_AppState implements CleanupManualInterface {
public class Terrain extends Entity implements CleanupManualInterface {
    private CarGame game = null;
    private AssetManager assetManager = null;
    private Node parent = null;
    private Camera camera;
    private PhysicsSpace physicsSpace;
    
    //terrain
    TerrainQuad terrain;
    Node terrainPhysicsNode;
    //Materials
    private Material mat_terrain;
    Material matRock;
    Material matWire;
    private String text_terrain;
    private String heightmap_terrain;
    
    private boolean cleanedupManual = false;

    public Terrain(Camera camera, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace, String text_terrain, String heightmap_terrain) {
        super(assetManager, parent, physicsSpace);
        
    	this.game = CarGame.getApp();
        this.assetManager = assetManager;
        this.parent = parent;
        this.physicsSpace = physicsSpace;
        this.camera = game.getCamera();
        this.text_terrain = text_terrain;
        this.heightmap_terrain = heightmap_terrain;
    }

    // @Override
    public void initialize(AppStateManager stateManager, Application app) {
        // super.initialize(stateManager, app);

        // 1. Create terrain material and load four textures into it.
        /*
        mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/Terrain.j3md");
        */
        // Use this material definition later on (Phong illumination)
        mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/TerrainLighting.j3md");

        // 1.2) Add GRASS texture into the red layer (Tex1).
        // Texture grass = assetManager.loadTexture(
            // "Tracks/Grass Hill/Textures/Terrain/simple/grass.jpg");
        Texture grass = assetManager.loadTexture(
            text_terrain);
        grass.setWrap(WrapMode.Repeat);
        // mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setTexture("DiffuseMap", grass);
        // mat_terrain.setFloat("Tex1Scale", 64f);
        mat_terrain.setFloat("DiffuseMap_0_scale", 64f);
        // mat_terrain.setFloat("DiffuseMap_0_scale", 2048f);

        // 2. Create the height map
        AbstractHeightMap heightmap = null;
        // Texture heightMapImage = assetManager.loadTexture(
                // "Tracks/Grass Hill/Textures/Terrain/splat/mountains512.png");
        // Texture heightMapImage = assetManager.loadTexture(
                // "Tracks/Grass Hill/Textures/Terrain/simple/flat.png");
        // Texture heightMapImage = assetManager.loadTexture(
                // "Tracks/Grass Hill/Textures/Terrain/simple/terrain_1_node.png");
        Texture heightMapImage = assetManager.loadTexture(
                heightmap_terrain);
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        
        int patchSize = 65;
        terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());
 
        TerrainLodControl control = new TerrainLodControl(terrain, camera);
        control.setLodCalculator( new DistanceLodCalculator(65, 2.7f) ); // patch size, and a multiplier
        terrain.addControl(control);
        terrain.setModelBound(new BoundingBox());
        terrain.updateModelBound();
        
        /** 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(mat_terrain);
        // terrain.setLocalTranslation(0, -100, 0);
        // terrain.setLocalScale(2f, 1f, 2f);
        terrain.setLocalTranslation(100, 0, 0);
        parent.attachChild(terrain);        
        
        terrain.addControl(new RigidBodyControl(new HeightfieldCollisionShape(terrain.getHeightMap(), terrain.getLocalScale()), 0));
        physicsSpace.add(terrain);
        
        cleanedupManual = false;
    }

    @Override
    public void cleanupManual() {
        // cleanup
        try {
            getPhysicsSpace().removeAll(terrain);
        } catch(NullPointerException e)    {

        }
        getParent().detachChild(terrain);

        cleanedupManual=true;
    }
    
    // @Override
    public void cleanup()   {
        // super.cleanup();
        
        if(cleanedupManual == false) {
            cleanupManual();
        }
    }
}
