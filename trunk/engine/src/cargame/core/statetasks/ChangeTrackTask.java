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

import cargame.core.CarGame;

public class ChangeTrackTask implements Callable
{
    private String track;
    private CarGame game;

    public ChangeTrackTask(String track, CarGame game)
    {
        this.track=track;
        this.game=game;
    }

    public Object call() throws Exception
    {
        System.out.println("ChangeTrackeTask called");

        game.setTrack(track);

        return null;
    }
}