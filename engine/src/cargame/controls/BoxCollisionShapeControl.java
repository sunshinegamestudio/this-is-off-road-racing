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

import cargame.core.CarGame;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Sunshine GameStudio
 */
public class BoxCollisionShapeControl extends AbstractControl implements Savable, Cloneable   {
    private PhysicsSpace physicsSpace;
    private RigidBodyControl rigidBodyControl;
    
    @Override
    public void setSpatial(Spatial boxNode) {
        super.setSpatial(boxNode);
        if (boxNode != null){
            // initialize
            spatial=boxNode;
            
            rigidBodyControl = new RigidBodyControl();

            BoxCollisionShape boxShape = new BoxCollisionShape(boxNode.getLocalScale());
            rigidBodyControl.setCollisionShape(boxShape);
            rigidBodyControl.setMass(0);
            boxNode.addControl(rigidBodyControl);

            try {
                CarGame.getApp().getPhysicsSpace().addAll(boxNode);
            }
            catch (Exception e) {
                
            }
        }else{
            // cleanup
            try {
                CarGame.getApp().getPhysicsSpace().remove(boxNode);
            }
            catch (Exception e) {
            }
            spatial.removeControl(rigidBodyControl);
        }
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Control cloneForSpatial(Spatial spatial){
        final BoxCollisionShapeControl control = new BoxCollisionShapeControl();
        /* Optional: use setters to copy userdata into the cloned control */
        // control.setIndex(i); // example
        control.setSpatial(spatial);
        return control;
    }

    @Override
      public void read(JmeImporter im) throws IOException {
          super.read(im);
          // im.getCapsule(this).read(...);
      }

      @Override
      public void write(JmeExporter ex) throws IOException {
          super.write(ex);
          // ex.getCapsule(this).write(...);
      }
}
