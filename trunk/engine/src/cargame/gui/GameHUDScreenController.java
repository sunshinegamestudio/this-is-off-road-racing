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

/**
 *
 * @author Vortex
 */
    public class GameHUDScreenController implements ScreenController   {
        private float accelerate;
        private int acceleratePos;
        private float brake;
        private int brakePos;        
        private float steer;
        private int steerPos;
        private int gearbox;
        private GameState gameState;
        
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

        public void accelerate_onClick(int x, int y)   {
            System.out.println("accelerate_onClick: " + x + " "+ y);
            acceleratePos = y;
            accelerate = 0;
            gameState.onAnalog("LeftStick Up", accelerate, 0);
        }

        public void accelerate_onClickMouseMove(int x, int y)   {
            System.out.println("accelerate_onClickMouseMove: " + x + " "+ y);
            accelerate = acceleratePos - y;
            gameState.onAnalog("LeftStick Up", accelerate, 0);
        }

        public void accelerate_onRelease()   {
            System.out.println("accelerate_onRelease");
            acceleratePos = 0;
            accelerate = 0;
            gameState.onAnalog("LeftStick Up", accelerate, 0);
        }

        public void brake_onClick(int x, int y)   {
            System.out.println("brake_onClick: " + x + " "+ y);
            brakePos = y;
            brake = 0;
            gameState.onAnalog("LeftStick Down", brake, 0);
        }

        public void brake_onClickMouseMove(int x, int y)   {
            System.out.println("brake_onClickMouseMove: " + x + " "+ y);
            brake = brakePos - y;
            gameState.onAnalog("LeftStick Down", brake, 0);
        }

        public void brake_onRelease()   {
            System.out.println("brake_onRelease");
            brakePos = 0;
            brake = 0;
            gameState.onAnalog("LeftStick Down", brake, 0);
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
            // CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.STEER, x, this));
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
            // CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.STEER, x, this));
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
            // CarGame.getApp().enqueue(new ChangeTouchPressedStateTask(TouchPressedKey.STEER, 0, this));
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
     * @return the accelerate
     */
    public float getAccelerateBrake() {
        return accelerate;
    }

    /**
     * @param accelerate the accelerate to set
     */
    public void setAccelerateBrake(float accelerate) {
        this.accelerate = accelerate;
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
    
    public void setGameState(GameState gameState)   {
        this.gameState = gameState;
    }
    }
