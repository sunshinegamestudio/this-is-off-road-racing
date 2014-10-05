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

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.VehicleControl;
//import com.jme3.bullet.nodes.PhysicsNode;
//import com.jme3.bullet.nodes.PhysicsVehicleNode;
//import com.jme3.bullet.nodes.PhysicsVehicleWheel;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.texture.Texture.WrapMode;

/**
 *
 * @author Vortex
 */
public class SimpleCarPlayer extends Entity_AppState  {
    private VehicleControl vehicle;
    private Node vehicleNode;

    private Box chassisMesh;
    private Geometry chassisGeometry;
    private Node chassisNode;

    private Node node1;
    private Geometry wheels1;
    private Node node2;
    private Geometry wheels2;
    private Node node3;
    private Geometry wheels3;
    private Node node4;
    private Geometry wheels4;
    
    private final float accelerationForce = 1000.0f;
    private final float brakeForce = 100.0f;
    private float steeringValue = 0;
    private float accelerationValue = 0;
    private int gear = 1;
    private Vector3f jumpForce = new Vector3f(0, 3000, 0);

    public SimpleCarPlayer(AssetManager assetManager, Node parent, PhysicsSpace physicsSpace) {
        super(assetManager, parent, physicsSpace);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    
        //Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/WireColor.j3md");
        //mat.setColor("Color", ColorRGBA.Red);
        /*
        Material mat = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Red);
        * Unshaded materials; should be removed later.
        * /

        /*
        Material matBlue = new Material(getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.getAdditionalRenderState().setWireframe(true);
        matBlue.setColor("Color", ColorRGBA.Blue);
        * Unshaded materials; should be removed later.
        */

        Material mat = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors",true);
        mat.setColor("Diffuse", ColorRGBA.Red);
        // mat.setColor("Specular",ColorRGBA.Red);
        // mat.setFloat("Shininess", 64f);

        Material matBlue = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        matBlue.setBoolean("UseMaterialColors",true);
        matBlue.setColor("Diffuse", ColorRGBA.Blue);
        // matBlue.setColor("Specular",ColorRGBA.Blue);
        // matBlue.setFloat("Shininess", 64f);
        
        //create a compound shape and attach the BoxCollisionShape for the car body at 0,1,0
        //this shifts the effective center of mass of the BoxCollisionShape to 0,-1,0
        CompoundCollisionShape compoundShape = new CompoundCollisionShape();
        // BoxCollisionShape box = new BoxCollisionShape(new Vector3f(1.2f, 0.5f, 2.4f));
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(1.2f, 2.4f, 2);
        // compoundShape.addChildShape(box, new Vector3f(0, 1, 0));
        compoundShape.addChildShape(capsule, new Vector3f(0, 1, 0));

        //create vehicle node
        vehicleNode=new Node("vehicleNode");
        vehicle = new VehicleControl(compoundShape, 800);
        vehicleNode.addControl(vehicle);
        
        // create chassis visual presentation
        chassisMesh = new Box(1.2f, 0.5f, 2.4f);
        chassisGeometry = new Geometry("chassis", chassisMesh);
        chassisGeometry.setMaterial(mat);
        chassisNode = new Node("chassisNode");
        chassisNode.move(0, 0.5f, 0);
        chassisNode.attachChild(chassisGeometry);
        vehicleNode.attachChild(chassisNode);
        
        //setting suspension values for wheels, this can be a bit tricky
        //see also https://docs.google.com/Doc?docid=0AXVUZ5xw6XpKZGNuZG56a3FfMzU0Z2NyZnF4Zmo&hl=en
        float stiffness = 60.0f;//200=f1 car
        float compValue = .3f; //(should be lower than damp)
        float dampValue = .4f;
        vehicle.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
        vehicle.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
        vehicle.setSuspensionStiffness(stiffness);
        vehicle.setMaxSuspensionForce(10000.0f);

        //Create four wheels and add them at their locations
        Vector3f wheelDirection = new Vector3f(0, -1, 0); // was 0, -1, 0
        Vector3f wheelAxle = new Vector3f(-1, 0, 0); // was -1, 0, 0
        float radius = 0.5f;
        float restLength = 0.3f;
        float yOff = 0.5f;
        float xOff = 1f;
        float zOff = 2f;

        Cylinder wheelMesh = new Cylinder(16, 16, radius, radius * 0.6f, true);

        node1 = new Node("wheel 1 node");
        wheels1 = new Geometry("wheel 1", wheelMesh);
        node1.attachChild(wheels1);
        wheels1.rotate(0, FastMath.HALF_PI, 0);
        wheels1.setMaterial(matBlue);
        vehicle.addWheel(wheels1, new Vector3f(-xOff, yOff, zOff),
                wheelDirection, wheelAxle, restLength, radius, false);
        vehicleNode.attachChild(node1);

        node2 = new Node("wheel 2 node");
        wheels2 = new Geometry("wheel 2", wheelMesh);
        node2.attachChild(wheels2);
        wheels2.rotate(0, FastMath.HALF_PI, 0);
        wheels2.setMaterial(matBlue);
        vehicle.addWheel(wheels2, new Vector3f(xOff, yOff, zOff),
                wheelDirection, wheelAxle, restLength, radius, false);
        vehicleNode.attachChild(node2);

        node3 = new Node("wheel 3 node");
        wheels3 = new Geometry("wheel 3", wheelMesh);
        node3.attachChild(wheels3);
        wheels3.rotate(0, FastMath.HALF_PI, 0);
        wheels3.setMaterial(matBlue);
        vehicle.addWheel(wheels3, new Vector3f(-xOff, yOff, -zOff),
                wheelDirection, wheelAxle, restLength, radius, true);
        vehicleNode.attachChild(node3);

        node4 = new Node("wheel 4 node");
        wheels4 = new Geometry("wheel 4", wheelMesh);
        node4.attachChild(wheels4);
        wheels4.rotate(0, FastMath.HALF_PI, 0);
        wheels4.setMaterial(matBlue);
        vehicle.addWheel(wheels4, new Vector3f(xOff, yOff, -zOff),
                wheelDirection, wheelAxle, restLength, radius, true);
        vehicleNode.attachChild(node4);

        //vehicle.attachDebugShape(assetManager);

        getParent().attachChild(vehicleNode);

        getPhysicsSpace().add(vehicle);
    }

    @Override
    public void cleanup()   {
        super.cleanup();

        getPhysicsSpace().remove(vehicle);
        vehicleNode.removeControl(vehicle);

        wheels1.removeFromParent();
        node1.removeFromParent();
        
        wheels2.removeFromParent();
        node1.removeFromParent();

        wheels3.removeFromParent();
        node3.removeFromParent();

        wheels4.removeFromParent();
        node4.removeFromParent();

        chassisGeometry.removeFromParent();
        chassisNode.removeFromParent();
        
        vehicleNode.removeFromParent();
}
    
    public Node getNode()   {
        return vehicleNode;
    }

    // Implement methods steer, accelerat and brake in interface Moveble
    // Derive (implements) Player from interface Moveble
    public void steer(float steeringValue)  {
        this.steeringValue+=steeringValue;
        vehicle.steer(this.steeringValue);
    }

    public void accelerate(float accelerationValue)  {
        if(getGear()>0)  {
            this.accelerationValue+=accelerationValue;
        }
        else if (getGear()<0)    {
            this.accelerationValue-=accelerationValue;
        }
        vehicle.accelerate(this.accelerationValue);
    }

    public void brake(float brakingValue)  {
        if(getGear()>0)  {
            vehicle.brake(brakingValue);
        }
        else if (getGear()<0)    {
            vehicle.brake(-brakingValue);
        }
    }

    /**
     * @return the gear
     */
    public int getGear() {
        return gear;
    }

    /**
     * @param gear the gear to set
     */
    public void setGear(int gear) {
        this.gear = gear;
    }
}
