/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cargame.core.statetasks;

/**
 *
 * @author Vortex
 */

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.ViewPort;
import java.util.concurrent.Callable;

import cargame.gui.GameHUDScreenController;

public class ChangeTouchPressedStateTask implements Callable
{
    private TouchPressedKey key;
    private float value;
    private GameHUDScreenController gameHUDScreenController;
    
    public enum TouchPressedKey {   ACCELARATE_BRAKE, STEER, GEARBOX  }

    public ChangeTouchPressedStateTask(TouchPressedKey key, float value, GameHUDScreenController gameHUDScreenController)
    {
        this.key=key;
        this.value=value;
    }

    public Object call() throws Exception
    {
        System.out.println("ChangeTouchPressedStateTask called");
        
        switch(key) {
            case ACCELARATE_BRAKE:
                gameHUDScreenController.setAccelerateBrake(value);
                break;
            case STEER:
                gameHUDScreenController.setSteer(value);
                break;
            case GEARBOX:
                // gameHUDScreenController.setGearbox(value);
                break;
        }

        return null;
    }
}