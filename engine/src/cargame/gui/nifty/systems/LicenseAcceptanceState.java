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

package cargame.gui.nifty.systems;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.RenderState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.*;

import java.util.logging.Level;

import cargame.core.CarGame;

public class LicenseAcceptanceState extends AbstractAppState implements ActionListener{

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    protected BitmapText menuText;
    protected BitmapFont menuFont;

    private NiftyJmeDisplay niftyDisplay = null;
    private Nifty nifty = null;
    
    private CarGame game = null;
    
    public LicenseAcceptanceState(CarGame game) {
    	this.game = game;
        
        rootNode = this.game.getRootNode();
        guiNode = this.game.getGuiNode();

        this.game.getLogger().log(Level.SEVERE, "LicenceAcceptanceState created.");
    }
    
    public void onAction(String name, boolean value, float tpf) {
        if (!value)
            return;
        // Load other state
        // game.loadGame("Default");
    }

    public void start() {
        // game.loadGame("Default");
    }
    
    public void loadFPSText(){
    	menuFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
    	menuText = new BitmapText(menuFont, false);
    	menuText.setSize(menuFont.getCharSet().getRenderedSize());
    	menuText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f), 0);
    	//menuText.setText("Frames per second");
        guiNode.attachChild(menuText);
    }

    private void loadMenu() {
        niftyDisplay = game.getNiftyDisplay();
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("General/Interface/LicenseAcceptanceMenu.xml", "LicenseAcceptanceMenuScreen");

        // attach the nifty display to the gui view port as a processor
        // game.getGUIViewPort().addProcessor(niftyDisplay);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        // enable depth test and back-face culling for performance
        app.getRenderer().applyRenderState(RenderState.DEFAULT);

        guiNode.setQueueBucket(Bucket.Gui);
        guiNode.setCullHint(CullHint.Never);
        loadFPSText();
        loadMenu();

        // Init input
        if (game.getInputManager() != null){
            game.getInputManager().addMapping("CARGAME_Exit1", new KeyTrigger(KeyInput.KEY_ESCAPE));
        }

        game.getInputManager().addListener(this, "CARGAME_Exit1");
        if(niftyDisplay != null)    {
            game.getGUIViewPort().addProcessor(niftyDisplay);
        }
        game.getInputManager().setCursorVisible(true);
        
        // game.getViewPort().attachScene(rootNode);
        // game.getGUIViewPort().attachScene(guiNode);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
       
        menuText.setText("Press [Escape] to go to the Game-State");
    }

    @Override
    public void render(RenderManager rm) {
    }

    @Override
    public void cleanup() {
        super.cleanup();

        game.getInputManager().setCursorVisible(false);
        game.getGUIViewPort().removeProcessor(niftyDisplay);
        game.getInputManager().removeListener(this);
    	
        // game.getViewPort().detachScene(rootNode);
        // game.getGUIViewPort().detachScene(guiNode);
    }
}