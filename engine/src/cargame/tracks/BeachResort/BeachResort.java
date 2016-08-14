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

package cargame.tracks.BeachResort;

import cargame.tracks.BeachResort.*;
import cargame.appstates.*;
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
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.terrain.Terrain;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;

public class BeachResort extends AbstractAppState implements CleanupManualInterface    {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    private CarGame game = null;
    private AssetManager assetManager = null;
    private Node parent = null;
    private PhysicsSpace physicsSpace;
    private Camera camera;
    
    private Material mat_terrain;

    private boolean cleanedupManual = false;
    private TerrainQuad terrain;
    
    public BeachResort(CarGame game, AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
    	this.game = game;
        this.assetManager = assetManager;
        this.parent = parent;
        this.physicsSpace = physicsSpace;
        this.camera = game.getCamera();

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        createSkybox();
        createTerrain();
        createStartingPoint();
        createLight();
        
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
    
    private void createSkybox()  {
        Texture west = assetManager.loadTexture("Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_w.jpg");
        Texture east = assetManager.loadTexture("Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_e.jpg");
        Texture north = assetManager.loadTexture("Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_n.jpg");
        Texture south = assetManager.loadTexture("Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_s.jpg");
        Texture top = assetManager.loadTexture("Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_t.jpg");
        Texture bottom = assetManager.loadTexture("Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_b.jpg");
        
        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, top, bottom);
        parent.attachChild(sky);
    }

    private void createTerrain() {
        // 1. Create terrain material and load four textures into it.
        mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/Terrain.j3md");
        /* Use this material definition later on (Phong illumination)
        mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/TerrainLighting.j3md");
        */

        // 1.2) Add GRASS texture into the red layer (Tex1).
        Texture grass = assetManager.loadTexture(
            "Tracks/Grass Hill/Textures/Terrain/simple/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);
        // mat_terrain.setFloat("Tex1Scale", 2048f);

        // 2. Create the height map
        AbstractHeightMap heightmap = null;
        // Texture heightMapImage = assetManager.loadTexture(
                // "Tracks/Grass Hill/Textures/Terrain/splat/mountains512.png");
        // Texture heightMapImage = assetManager.loadTexture(
                // "Tracks/Grass Hill/Textures/Terrain/simple/flat.png");
        Texture heightMapImage = assetManager.loadTexture(
                "Tracks/Grass Hill/Textures/Terrain/simple/terrain_1_node.png");
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
    }

    private void createStartingPoint() {
        Spatial startingpoint = assetManager.loadModel("Tracks/Grass Hill/Models/startingpoint_1/startingpoint_1.j3o");
        parent.attachChild(startingpoint);
    }
    
    private void createLight()  {
        // You must add a light to make the model visible
        // Create sun (DirectionalLight) here
        // For now in GameState
    }
}