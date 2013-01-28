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

package cargame.core.statetasks;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.ViewPort;
import java.util.concurrent.Callable;

public class ChangeStateTask implements Callable
{
    private AbstractAppState oldState;
    private AbstractAppState newState;
    private ViewPort viewPort;
    private AppStateManager stateManager;

    public ChangeStateTask(AbstractAppState oldState, AbstractAppState newState, ViewPort view, AppStateManager sm)
    {
        this.oldState=oldState;
        this.newState=newState;
        this.viewPort=view;
        this.stateManager=sm;
    }

    public Object call() throws Exception
    {
        System.out.println("ChangeStateTask called");

        //viewPort.detachScene(oldState.getRootNode());
        stateManager.detach(oldState);

        //viewPort.attachScene(newState.getRootNode());
        stateManager.attach(newState);

        return null;
    }
}