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

import cargame.appstates.CleanupManualInterface;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;

import com.jme3.light.DirectionalLight;
import com.jme3.scene.Node;

/**
 *
 * @author Vortex
 */
public class Sun extends Entity_AppState implements CleanupManualInterface {
    DirectionalLight sun;
    
    private boolean cleanedupManual = false;

    public Sun(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1).normalizeLocal());
        getParent().addLight(sun);

        cleanedupManual = false;
    }

    @Override
    public void cleanupManual() {
        // cleanup
        getParent().removeLight(sun);

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
