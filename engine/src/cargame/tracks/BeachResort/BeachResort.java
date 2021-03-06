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

import cargame.ecs.systems.CleanupManualInterface;
import cargame.tracks.BeachResort.*;
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
import cargame.ecs.entities.SimpleCarPlayer;
import cargame.tracks.Common.Sky;
import cargame.tracks.Common.StartingPoint;
import cargame.tracks.Common.Sun;
import cargame.tracks.Common.Terrain;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
// import com.jme3.terrain.Terrain;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;

// public class BeachResort extends AbstractAppState implements CleanupManualInterface    {
public class BeachResort implements CleanupManualInterface    {

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

    private Sky sky;
    private Terrain terrain_node;
    private StartingPoint startingPoint; 
    private Trees trees;
    private BeachHouse beachHouse;
    private Sun sun;

    private boolean cleanedupManual = false;
    
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
    
    // @Override
    public void initialize(AppStateManager stateManager, Application app) {
        // super.initialize(stateManager, app);
        
        createSkybox();
        createTerrain();
        createStartingPoint();
        createTrees();
        createBeachHouse();
        createLight();
        
        cleanedupManual = false;
    }

    // @Override
    public void update(float tpf) {
        // super.update(tpf);
    }
    
    // @Override
    public void render(RenderManager rm) {
    }
    
    // @Override
    public void cleanupManual() {
        // cleanup
        // ToDo: Add cleanup code.
        sun.cleanupManual();
        // game.getStateManager().detach(sun);
        // sun.cleanupManual();

        beachHouse.cleanupManual();
        // game.getStateManager().detach(beachHouse);

        trees.cleanupManual();
        // game.getStateManager().detach(trees);
        
        startingPoint.cleanupManual();
        // game.getStateManager().detach(startingPoint);

        terrain_node.cleanupManual();
        // game.getStateManager().detach(terrain_node);
        terrain_node.cleanupManual();

        sky.cleanupManual();
        // game.getStateManager().detach(sky);
        sky.cleanupManual();
        
        cleanedupManual=true;
    }

    // @Override
    public void cleanup() {
        // super.cleanup();

        if(cleanedupManual == false) {
            cleanupManual();
        }
    }
    
    private void createSkybox()  {
        sky = new Sky(
            assetManager,
            parent,
            physicsSpace,
            "Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_w.jpg",
            "Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_e.jpg",
            "Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_n.jpg",
            "Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_s.jpg",
            "Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_t.jpg",
            "Tracks/Grass Hill/Textures/Sky/Bright_2/bright_1_b.jpg"
        );
        // game.getStateManager().attach(sky);
        sky.initialize(game.getStateManager(), game);
    }

    private void createTerrain() {
        terrain_node = new cargame.tracks.Common.Terrain(camera, assetManager, parent, physicsSpace, "Tracks/Beach Resort/Textures/Terrain/simple/sand.jpg", "Tracks/Beach Resort/Textures/Terrain/simple/terrain_1_node.png");
        // game.getStateManager().attach(terrain_node);
        terrain_node.initialize(game.getStateManager(), game);
    }

    private void createStartingPoint() {
        // Spatial startingpoint = assetManager.loadModel("Tracks/Grass Hill/Models/startingpoint_1/startingpoint_1.j3o");
        
        startingPoint = new StartingPoint(assetManager, parent, physicsSpace);
        // parent.attachChild(startingpoint);
        startingPoint.initialize(game.getStateManager(), game);
    }
    
    private void createTrees()  {
        trees = new Trees("trees", assetManager, parent, physicsSpace);
        // game.getStateManager().attach(trees);
        trees.initialize(game.getStateManager(), game);
    }

    private void createBeachHouse()  {
        Vector3f initialTranslation = new Vector3f(-20, 0, -50);
        beachHouse = new BeachHouse("beachhouse", assetManager, parent, physicsSpace, initialTranslation);
        // game.getStateManager().attach(beachHouse);
        beachHouse.initialize(game.getStateManager(), game);
    }

    private void createLight()  {
        // You must add a light to make the model visible
        // Create sun (DirectionalLight) here
        // For now in GameState

        sun = new Sun(assetManager, parent, physicsSpace);
        // game.getStateManager().attach(sun);
        sun.initialize(game.getStateManager(), game);
    }
}