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

import cargame.ecs.entities.Entity;
import cargame.ecs.systems.CleanupManualInterface;
import cargame.ecs.entities.Entity_AppState;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

import com.jme3.light.DirectionalLight;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Sunshine GameStudio
 */
// public class StartingPoint extends Entity_AppState implements CleanupManualInterface {
public class StartingPoint extends Entity implements CleanupManualInterface {
    Spatial startingpoint;
    
    private boolean cleanedupManual = false;

    public StartingPoint(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

    }

    // @Override
    public void initialize(AppStateManager stateManager, Application app) {
        // super.initialize(stateManager, app);

        startingpoint = getAssetManager().loadModel("Tracks/Grass Hill/Models/startingpoint_1/startingpoint_1.j3o");
        getParent().attachChild(startingpoint);

        cleanedupManual = false;
    }

    // @Override
    public void cleanupManual() {
        // cleanup
        getParent().detachChild(startingpoint);

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
