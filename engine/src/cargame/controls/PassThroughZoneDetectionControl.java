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

package cargame.controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;

/**
 *
 * @author Vortex
 */
public class PassThroughZoneDetectionControl extends GhostControl implements PhysicsTickListener, PhysicsCollisionListener   {
    private PhysicsSpace physicsSpace;
    
    private boolean onPassThroughDetectionZone = false;

    public PassThroughZoneDetectionControl(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
    }
    
    public void prePhysicsTick(PhysicsSpace space, float f) {
        onPassThroughDetectionZone = false;
    }
    
    public void physicsTick(PhysicsSpace space, float f){
        //throw new UnsupportedOperationException("Not supported yet.");
    }    
    
    public void collision(PhysicsCollisionEvent event) {
        // System.out.println("NodeA: " + event.getNodeA().getName());
        // System.out.println("NodeB: " + event.getNodeB().getName());
        
        if ("Box".equals(event.getNodeA().getName()) || "Box".equals(event.getNodeB().getName())) {
            if ("bullet".equals(event.getNodeA().getName()) || "bullet".equals(event.getNodeB().getName())) {
                //fpsText.setText("You hit the box!");
            }
        }

        if ("vehicleNode".equals(event.getNodeA().getName()) && "startingpoint_1-ogremesh".equals(event.getNodeB().getName()) || 
                "startingpoint_1-ogremesh".equals(event.getNodeA().getName()) && "vehicleNode".equals(event.getNodeB().getName()))
        {
            // System.out.println("Collison: vehicleNode-startingpoint_1-ogremesh");

            // By collision op LevelExit -> onPassThroughDetectionZone = false;
            onPassThroughDetectionZone = true;
            
            /*
            //Place Player1 and SimpleEnemy1 back, so they are not colliding !!!!!!!!!!!!
            if("simple_enemy_1-ogremesh".equals(event.getNodeA().getName()))    {
                //event.getNodeA().move(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-tpf));
                event.getNodeA().move(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-16));
            }
            if("simple_enemy_1-ogremesh".equals(event.getNodeB().getName()))    {
                //event.getNodeA().move(event.getNodeA().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-tpf));
                event.getNodeB().move(event.getNodeB().getLocalRotation().multLocal(new Vector3f(0,0,1)).multLocal(-16));
            }
             * 
             */
        }
    }
    
    public boolean isOnPassThroughDetectionZone() {
        return onPassThroughDetectionZone;
    }
}
