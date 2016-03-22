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

package cargame.tracks;

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
import com.jme3.light.DirectionalLight;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

public class GrassHill extends AbstractAppState implements CleanupManualInterface    {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    private SimpleCarPlayer player;
    private CarGame game = null;

    private BitmapFont guiFont;
    private BitmapText fpsText;
    private BitmapText menuText;

    private boolean cleanedupManual = false;
    
    public GrassHill(CarGame game) {
    	this.game = game;

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

        int fps = (int) game.getTimer().getFrameRate();
        fpsText.setText("Frames per second: "+fps);
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
        /*
        Texture west = assetManager.loadTexture("Tracks/Grass Hill/Textures/Bright_2/bright_1_w.jpg");
        Texture east = assetManager.loadTexture("Tracks/Grass Hill/Textures/Bright_2/bright_1_e.jpg");
        Texture north = assetManager.loadTexture("Tracks/Grass Hill/Textures/Bright_2/bright_1_n.jpg");
        Texture south = assetManager.loadTexture("Tracks/Grass Hill/Textures/Bright_2/bright_1_s.jpg");
        Texture top = assetManager.loadTexture("Tracks/Grass Hill/Textures/Bright_2/bright_1_t.jpg");
        Texture bottom = assetManager.loadTexture("Tracks/Grass Hill/Textures/Bright_2/bright_1_b.jpg");
        
        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, top, bottom);
        rootNode.attachChild(sky);
        */
    }

    private void createTerrain() {
        /*
        // 1. Create terrain material and load four textures into it.
        mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/Terrain.j3md");

        // 1.2) Add GRASS texture into the red layer (Tex1).
        Texture grass = assetManager.loadTexture(
            "Grass Hill/Textures/Terrain/simple/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);

        // 2. Create the height map
        AbstractHeightMap heightmap = null;
        Texture heightMapImage = assetManager.loadTexture(
                "Grass Hill/Textures/Terrain/splat/mountains512.png");
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        
        quad.addControl(new RigidBodyControl(new HeightfieldCollisionShape(quad.getHeightMap(), terrain.getLocalScale()), 0));
        bulletAppState.getPhysicsSpace().add(quad);


        LoadHeightmap;
        CreateTerrain;
        SetTranslation;
        CreatePhysics;
        */
    }

    private void createStartingPoint() {
        /*
        Spatial startingpoint = assetManager.loadModel("Grass Hill/Models/startingpoint_1/startingpoint_1.j3o");
        rootNode.attachChild(startingpoint);
        */
    }
    
    private void createLight()  {
        // You must add a light to make the model visible
        // Create sun (DirectionalLight) here
    }
}