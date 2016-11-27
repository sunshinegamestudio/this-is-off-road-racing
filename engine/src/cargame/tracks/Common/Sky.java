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

import cargame.appstates.CleanupManualInterface;
import cargame.entities.Entity;
import cargame.entities.Entity_AppState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
//import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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

/**
 *
 * @author Sunshine GameStudio
 */
public class Sky extends Entity_AppState implements CleanupManualInterface {
    String texture_west_name;
    String texture_east_name;
    String texture_north_name;
    String texture_south_name;
    String texture_top_name;
    String texture_bottom_name;
    
    Texture texture_west;
    Texture texture_east;
    Texture texture_north;
    Texture texture_south;
    Texture texture_top;
    Texture texture_bottom;
    
    private boolean cleanedupManual = false;

    public Sky(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace, String texture_west_name, String texture_east_name, String texture_north_name, String texture_south_name, String texture_top_name, String texture_bottom_name) {
        super(assetManager, parent, physicsSpace);
        
        this.texture_west_name = texture_west_name;
        this.texture_east_name = texture_east_name;
        this.texture_north_name = texture_north_name;
        this.texture_south_name = texture_south_name;
        this.texture_top_name = texture_top_name;
        this.texture_bottom_name = texture_bottom_name;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        // Initialize
        
        // getParent().attachChild(SkyFactory.createSky(getAssetManager(), "Textures/Sky/Bright/BrightSky.dds", false));

        texture_west = getAssetManager().loadTexture(texture_west_name);
        texture_east = getAssetManager().loadTexture(texture_east_name);
        texture_north = getAssetManager().loadTexture(texture_north_name);
        texture_south = getAssetManager().loadTexture(texture_south_name);
        texture_top = getAssetManager().loadTexture(texture_top_name);
        texture_bottom = getAssetManager().loadTexture(texture_bottom_name);

        getParent().attachChild(SkyFactory.createSky(getAssetManager(), texture_west, texture_east, texture_north, texture_south, texture_top, texture_bottom));

        cleanedupManual = false;
    }

    @Override
    public void cleanupManual() {
        // cleanup

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
