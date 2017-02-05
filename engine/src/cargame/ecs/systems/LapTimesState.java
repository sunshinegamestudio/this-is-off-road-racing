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

package cargame.ecs.systems;

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
import cargame.ecs.entities.StartingPoint;
import cargame.ecs.entities.SimpleCarPlayer;
import cargame.other.Time;

public class LapTimesState extends AbstractAppState implements CleanupManualInterface    {

    // protected Node rootNode = new Node("Root Node");
    private Node rootNode;
    // protected Node guiNode = new Node("Gui Node");
    private Node guiNode;

    private CarGame game = null;
    
    // private StartingPointState passThroughZoneDetectionState = null;
    private PassThroughZoneDetectionState passThroughZoneDetectionState = null;

    private BitmapText fpsText;
    private BitmapText menuText;
    private BitmapText currentLapText;
    private BitmapText maxLapsText;
    private BitmapText fastestLapTimeText;
    private BitmapText currentLapTimeText;
    private BitmapText currentTimeText;
    private BitmapFont guiFont;

    private boolean newLap = false;
    private int currentLap = 0;
    private int maxLaps = 4;
    // private int maxLaps = 1;
    // private long fastestLapTime = 0;
    private float fastestLapTime = 0;
    private Time fastestLapTimeTime;
    // private long currentLapTime = 0;
    private float currentLapTime = 0;
    private Time currentLapTimeTime;
    // private long lastLapTime = 0;
    private float lastLapTime = 0;
    // private long currentTime = 0;
    private float currentTime = 0;
    private Time currentTimeTime;
    // private long lapTimes[];
    private float lapTimes[];

    private boolean cleanedupManual = false;
    
    public LapTimesState(CarGame game) {
    	this.game = game;

        rootNode = this.game.getRootNode();
	guiNode = this.game.getGuiNode();
        
        // passThroughZoneDetectionState = game.getStateManager().getState(StartingPointState.class);
        passThroughZoneDetectionState = game.getStateManager().getState(PassThroughZoneDetectionState.class);
        
        this.game.getLogger().log(Level.SEVERE, "TrackSelectorState created.");
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        loadText();
        
        currentLap = 0;
        // lapTimes = new long[10];
        lapTimes = new float[10];
        for(int i=0; i<maxLaps; i++)    {
            lapTimes[i] = 0;
        }
        fastestLapTimeTime = new Time();
        currentLapTimeTime = new Time();
        currentTimeTime = new Time();

        // Load the fastest laptime (still to implement !!!)
        /*
        try {
        trackStatistics = (TrackStatistics) SaveGame.loadGame("com/sunshinegamestudio/cargame", "Default_Track");
        } catch (NullPointerException nullPointerException) {
            trackStatistics = new TrackStatistics();
            trackStatistics.setFastestLapTime(fastestLapTime);
            SaveGame.saveGame("com/sunshinegamestudio/cargame", "Default_Track", trackStatistics);
        }
        trackStatistics = (TrackStatistics) SaveGame.loadGame("com/sunshinegamestudio/cargame", "Default_Track");
        fastestLapTime = trackStatistics.getFastestLapTime();
         * Temperairy disabled until fixed
         */

        cleanedupManual = false;
    }

    public void loadText(){
        /* currentLapText;
        fastestLapTimeText;
        currentLapTimeText;
         * Create all the BitmapText.
         */

        guiFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        menuText = new BitmapText(guiFont, false);
        currentLapText = new BitmapText(guiFont, false);
        currentLapText.setSize(guiFont.getCharSet().getRenderedSize());
        currentLapText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-100, 0);
        currentLapText.setText("Current Lap: ");
        guiNode.attachChild(currentLapText);

        maxLapsText = new BitmapText(guiFont, false);
        maxLapsText.setSize(guiFont.getCharSet().getRenderedSize());
        maxLapsText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-80, 0);
        maxLapsText.setText("Max Laps: ");
        guiNode.attachChild(maxLapsText);

        fastestLapTimeText = new BitmapText(guiFont, false);
        fastestLapTimeText.setSize(guiFont.getCharSet().getRenderedSize());
        fastestLapTimeText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-60, 0);
        fastestLapTimeText.setText("Fastest Lap: ");
        guiNode.attachChild(fastestLapTimeText);
        
        currentLapTimeText = new BitmapText(guiFont, false);
        currentLapTimeText.setSize(guiFont.getCharSet().getRenderedSize());
        currentLapTimeText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-40, 0);
        currentLapTimeText.setText("Current Lap: ");
        guiNode.attachChild(currentLapTimeText);

        currentTimeText = new BitmapText(guiFont, false);
        currentTimeText.setSize(guiFont.getCharSet().getRenderedSize());
        currentTimeText.setLocalTranslation(0, (game.getContext().getSettings().getHeight()/2f)-(menuText.getLineHeight()/2f)-20, 0);
        currentTimeText.setText("Current Time: ");
        guiNode.attachChild(currentTimeText);
    }

    /*
    private void loadMenu() {
    }
        
    }
    */

    @Override
    public void update(float tpf) {
        super.update(tpf);

        // Check for new lap
        if(passThroughZoneDetectionState != null)  {
            newLap = passThroughZoneDetectionState.checkForNewLap();

            if (newLap == true)
            {
                // Calculate the current laptime
                currentLapTime = game.getTimer().getTime() - lastLapTime;
                // currentLapTime+=tpf;

                // Set the current currentLapTime in the lapTimes array
                lapTimes[currentLap]=currentLapTime;

                // Check if there is a new fastest laptime
                if ((fastestLapTime > currentLapTime) || (currentLap == 0))
                {
                    // Make the current laptime the fastest laptime
                    fastestLapTime = currentLapTime;
                    // Save the fastest laptime (still to implement !!!)
                    /*
                    trackStatistics.setFastestLapTime(fastestLapTime);
                    SaveGame.saveGame("com/sunshinegamestudio/cargame", "Default_Track", trackStatistics);
                     * Temperairy disabled until fixed
                     */
                }

                // Set the last laptime and increment the current lap
                lastLapTime = currentLapTime;
                currentLap = currentLap + 1;
            }

            // Update current time
            currentTime = game.getTimer().getTime() - currentLapTime;

            // Update HUD
            currentLapText.setText("Current Lap: " + currentLap);
            maxLapsText.setText("Max Laps: " + maxLaps);
            fastestLapTimeTime.setTime(fastestLapTime / game.getTimer().getResolution());
            // fastestLapTimeText.setText("Fastest LapTime: " + fastestLapTime);
            fastestLapTimeText.setText("Fastest LapTime: " + fastestLapTimeTime.getHour() + ":" + fastestLapTimeTime.getMinute() + ":" + fastestLapTimeTime.getSecond() + ":" + fastestLapTimeTime.getMilisecond());
            currentLapTimeTime.setTime(currentLapTime / game.getTimer().getResolution());
            // currentLapTimeText.setText("Current LapTime: " + currentLapTime);
            currentLapTimeText.setText("Current LapTime: " + currentLapTimeTime.getHour() + ":" + currentLapTimeTime.getMinute() + ":" + currentLapTimeTime.getSecond() + ":" + currentLapTimeTime.getMilisecond());
            currentTimeTime.setTime(currentTime / game.getTimer().getResolution());
            // currentTimeText.setText("Current Time: " + currentTime);
            currentTimeText.setText("Current Time: " + currentTimeTime.getHour() + ":" + currentTimeTime.getMinute() + ":" + currentTimeTime.getSecond() + ":" + currentTimeTime.getMilisecond());
        }
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
    
    public int getCurrentLap()  {
        return currentLap;
    }
    
    public int getMaxLaps() {
        return maxLaps;
    }
    
    // public long getLapTime(int i)   {
    public float getLapTime(int i)   {
        return lapTimes[i];
    }

}