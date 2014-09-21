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

package cargame.other;

import cargame.core.CarGame;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.objects.PhysicsRigidBody;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class PhysicsSpacePrinter {
    CarGame game;
    PhysicsSpace physicsSpace;
    
    public PhysicsSpacePrinter(CarGame game)  {
    	this.game = game;
        this.physicsSpace = game.getPhysicsSpace();
    }
    
    public void print()    {
        game.getLogger().log(Level.SEVERE, "PhysicsSpacePrinter:");

        List<PhysicsRigidBody> rigidBodyControls = (List<PhysicsRigidBody>) physicsSpace.getRigidBodyList();
        Iterator it = rigidBodyControls.listIterator();
        while(it.hasNext()) {
            PhysicsRigidBody physicsRigidBody = (PhysicsRigidBody) it.next();
            game.getLogger().log(Level.SEVERE, physicsRigidBody.toString());
        }
        
    }
}
