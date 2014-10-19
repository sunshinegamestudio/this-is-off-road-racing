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

package cargame.appstates;

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

public class FPSState extends AbstractAppState implements CleanupManualInterface    {

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
    
    public FPSState(CarGame game) {
    	this.game = game;

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        guiFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        fpsText = new BitmapText(guiFont, false);
        fpsText.setSize(guiFont.getCharSet().getRenderedSize());
        fpsText.setLocalTranslation(0, fpsText.getLineHeight(), 0);
        //fpsText.setText("Frames per second");
        guiNode.attachChild(fpsText);
        
        menuText = new BitmapText(guiFont, false);
        menuText.setSize(guiFont.getCharSet().getRenderedSize());
        menuText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f), 0);
        menuText.setText("Press [M] to go back to the Menu");
        guiNode.attachChild(menuText);

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
}