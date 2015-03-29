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

package cargame.gui;

import com.jme3.app.state.AppState;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.elements.render.TextRenderer;

import cargame.core.CarGame;
import cargame.appstates.GameState;
import cargame.other.Time;

/**
 *
 * @author Sunshine GameStudio
 */
    public class ResultsMenuScreenController implements ScreenController   {
        private Screen screen;
        // private long lapTimes[];
        private float lapTimes[];
        // private long lap0;
        private float lap0;
        // private long lap1;
        private float lap1;
        // private long lap2;
        private float lap2;
        // private long lap3;
        private float lap3;
        private Time lap0Time;
        private Time lap1Time;
        private Time lap2Time;
        private Time lap3Time;
        private int maxLaps = 4;

        public void MainMenuScreenController()  {
            // With this constructor implemented, this class can be implemented in MainMenuState !!!
            // First try this, before removing this code !!!
        }

        public void bind(Nifty nifty, Screen screen) {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("bind");

            this.screen=screen;
            
            // lapTimes = new long[10];
            lapTimes = new float[10];
            for(int i=0; i<maxLaps; i++)    {
                lapTimes[i] = 0;
            }
        }

        public void onStartScreen() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("onScreenStart");
            
            screen.findElementByName("result_0").getRenderer(TextRenderer.class).setText("Lap 0: " + lap0Time.getHour() + ":" + lap0Time.getMinute() + ":" + lap0Time.getSecond() + ":" + lap0Time.getMilisecond());
            screen.findElementByName("result_1").getRenderer(TextRenderer.class).setText("Lap 1: " + lap1Time.getHour() + ":" + lap1Time.getMinute() + ":" + lap1Time.getSecond() + ":" + lap1Time.getMilisecond());
            screen.findElementByName("result_2").getRenderer(TextRenderer.class).setText("Lap 2: " + lap2Time.getHour() + ":" + lap2Time.getMinute() + ":" + lap2Time.getSecond() + ":" + lap2Time.getMilisecond());
            screen.findElementByName("result_3").getRenderer(TextRenderer.class).setText("Lap 3: " + lap3Time.getHour() + ":" + lap3Time.getMinute() + ":" + lap3Time.getSecond() + ":" + lap3Time.getMilisecond());
        }

        public void onEndScreen() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("onScreenEnd");
        }

        public void start() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("start");
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
        }
        
        public void proceed()   {
            CarGame.getApp().loadResultsMenu_return();
        }
        
        // public void setLapTimes(long lap0, long lap1, long lap2, long lap3, long timerResolution) {
        public void setLapTimes(float lap0, float lap1, float lap2, float lap3, long timerResolution) {
            /*
            lapTimes[0]=lap0;
            lapTimes[1]=lap1;
            lapTimes[2]=lap2;
            lapTimes[3]=lap3;
            */
            
            this.lap0=lap0;
            this.lap1=lap1;
            this.lap2=lap2;
            this.lap3=lap3;
            
            lap0Time = new Time();
            lap1Time = new Time();
            lap2Time = new Time();
            lap3Time = new Time();
            lap0Time.setTime(this.lap0 / timerResolution);
            lap1Time.setTime(this.lap1 / timerResolution);
            lap2Time.setTime(this.lap2 / timerResolution);
            lap3Time.setTime(this.lap3 / timerResolution);
        }

        public void exit()  {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("exit");
            CarGame.getApp().stop();
        }
    }
