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
import com.jme3.app.state.AbstractAppState;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author Sunshine GameStudio
 */
    public class GameHUDScreenController_Digital implements ScreenController   {
        private float accelerate;
        private int acceleratePos;
        private float brake;
        private int brakePos;        
        private float steerLeft;
        private int steerLeftPos;
        private float steerRight;
        private int steerRightPos;
        private int gearbox;
        private GameState gameState;
        private ActionListener touchScreenInputController;
        
        public void MainMenuScreenController()  {
            // With this constructor implemented, this class can be implemented in MainMenuState !!!
            // First try this, before removing this code !!!
        }

        public void bind(Nifty nifty, Screen screen) {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("bind");
            
            touchScreenInputController = (ActionListener)gameState.getInputController();
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
            // gameState.onAction("Ups", true, 0);
            touchScreenInputController.onAction("Ups", true, 0.0f);
        }

        public void accelerate_onClickRepeat(int x, int y)   {
            // gameState.onAction("Ups", true, 0);
            touchScreenInputController.onAction("Ups", true, 0);
        }

        public void accelerate_onRelease()   {
            // gameState.onAction("Ups", false, 0);
            touchScreenInputController.onAction("Ups", false, 0);
        }

        public void brake_onClick(int x, int y)   {
            // gameState.onAction("Downs", true, 0);
            touchScreenInputController.onAction("Downs", true, 0);
        }

        public void brake_onClickRepeat(int x, int y)   {
            // gameState.onAction("Downs", true, 0);
            touchScreenInputController.onAction("Downs", true, 0);
        }

        public void brake_onRelease()   {
            // gameState.onAction("Downs", false, 0);
            touchScreenInputController.onAction("Downs", false, 0);
        }
        
        public void steerLeft_onClick(int x, int y) {
            // gameState.onAction("Lefts", true, 0);
            touchScreenInputController.onAction("Lefts", true, 0);
        }

        public void steerLeft_onClickRepeat(int x, int y) {
            // gameState.onAction("Lefts", true, 0);
            touchScreenInputController.onAction("Lefts", true, 0);
        }

        public void steerLeft_onRelease() {
            // gameState.onAction("Lefts", false, 0);
            touchScreenInputController.onAction("Lefts", false, 0);
        }

        public void steerRight_onClick(int x, int y) {
            // gameState.onAction("Rights", true, 0);
            touchScreenInputController.onAction("Rights", true, 0);
        }

        public void steerRight_onClickRepeat(int x, int y) {
            // gameState.onAction("Rights", true, 0);
            touchScreenInputController.onAction("Rights", true, 0);
        }

        public void steerRight_onRelease() {
            // gameState.onAction("Rights", false, 0);
            touchScreenInputController.onAction("Rights", false, 0);
        }

        public void gearbox() {
            //throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("gearbox");
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
    public float getAccelerate() {
        return accelerate;
    }

    /**
     * @param accelerate the accelerate to set
     */
    public void setAccelerate(float accelerate) {
        this.accelerate = accelerate;
    }

    /**
     * @return the steer
     */
    public float getSteerLeft() {
        return steerLeft;
    }

    /**
     * @param steer the steer to set
     */
    public void setSteerLeft(float steerLeft) {
        this.steerLeft = steerLeft;
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
