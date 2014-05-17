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

import cargame.core.CarGame;
import cargame.appstates.GameState;

/**
 *
 * @author Vortex
 */
    public class MainMenuScreenController implements ScreenController   {
        public void MainMenuScreenController()  {
            // With this constructor implemented, this class can be implemented in MainMenuState !!!
            // First try this, before removing this code !!!
        }

        public void bind(Nifty nifty, Screen screen) {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("bind");
        }

        public void onStartScreen() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("onScreenStart");
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
            CarGame.getApp().loadTrackSelector();
        }
        
        public void selectController()   {
            CarGame.getApp().loadMenu_return("selectCotroller");
        }

        public void loadLicenseViewMenu()   {
            CarGame.getApp().loadMenu_return("loadLicenseViewMenu");
        }

        public void exit()  {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("exit");
            CarGame.getApp().stop();
        }
    }
