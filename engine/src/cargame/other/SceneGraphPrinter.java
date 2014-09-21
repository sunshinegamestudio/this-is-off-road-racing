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
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.logging.Level;

public class SceneGraphPrinter  {

    SceneGraphVisitor sceneGraphPrinterVisitor = new SceneGraphVisitor() {
    
        @Override
        public void visit(Spatial spatial)    {
            CarGame.getApp().getLogger().log(Level.SEVERE, spatial.getName());
            
            Control control;
            for(int i=0; i<(spatial.getNumControls()); i++)  {
                control = spatial.getControl(i);
                CarGame.getApp().getLogger().log(Level.SEVERE, control.toString());
            }
        }
    };
    
    public void print(Node node) {
        CarGame.getApp().getLogger().log(Level.SEVERE, "SceneGraphPrinter:");
        node.breadthFirstTraversal(sceneGraphPrinterVisitor);
    }
}
