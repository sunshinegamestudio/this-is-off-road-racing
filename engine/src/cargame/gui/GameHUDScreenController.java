/*
CarGame, a game where you can drive with a car.
Copyright (C) 2010  Vortex GameStudio

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
import cargame.core.statetasks.ChangeTouchPressedStateTask;
import cargame.core.statetasks.ChangeTouchPressedStateTask.TouchPressedKey;

/**
 *
 * @author Vortex
 */
    public class GameHUDScreenController implements ScreenController   {
        private float accelerateBrake;
        private float steer;
        private int gearbox;
        
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

        public void accelerateBrake_onClick(int x, int y)   {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("accelerateBrake_onClick: " + x + " "+ y);
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.ACCELARATE_BRAKE, y, this));
            // Change -> Call the analog listener directly !!!
        }

        public void accelerateBrake_onClickMouseMove(int x, int y)   {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("accelerateBrake_onClickMouseMove: " + x + " "+ y);
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.ACCELARATE_BRAKE, y, this));
            // Change -> Call the analog listener directly !!!
        }

        public void accelerateBrake_onRelease()   {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("accelerateBrake_onRelease");
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.ACCELARATE_BRAKE, 0, this));
            // Change -> Call the analog listener directly !!!
        }

        public void steer_onClick(int x, int y) {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("steer_onClick: " + x + " "+ y);
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.STEER, x, this));
            // Change -> Call the analog listener directly !!!
        }

        public void steer_onClickMouseMove(int x, int y) {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("steer_onClickMouseMove: " + x + " "+ y);
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.STEER, x, this));
            // Change -> Call the analog listener directly !!!
        }

        public void steer_onRelease() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("steer_onRelease");
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.STEER, 0, this));
            // Change -> Call the analog listener directly !!!
        }

        public void gearbox() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("gearbox");
            /*
            GameState<AppState> gameState = CarGame.getApp().getStateManager().getState(GameState<AppState>gameState);
             * Start new game from CarGame instead of directly from gamestate
             */

            /*
             * Switch appstate with a Callable object (see jME forum + Desktop)
             */
            // CarGame.getApp().loadTrackSelector();
            // Change -> Call the action listener directly !!!
        }

        public void exit()  {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("exit");
            CarGame.getApp().stop();
        }

    /**
     * @return the accelerateBrake
     */
    public float getAccelerateBrake() {
        return accelerateBrake;
    }

    /**
     * @param accelerateBrake the accelerateBrake to set
     */
    public void setAccelerateBrake(float accelerateBrake) {
        this.accelerateBrake = accelerateBrake;
    }

    /**
     * @return the steer
     */
    public float getSteer() {
        return steer;
    }

    /**
     * @param steer the steer to set
     */
    public void setSteer(float steer) {
        this.steer = steer;
    }

    /**
     * @return the gearbox
     */
    public int getGearbox() {
        return gearbox;
    }

    /**
     * @param gearbox the gearbox to set
     */
    public void setGearbox(int gearbox) {
        this.gearbox = gearbox;
    }
    }
