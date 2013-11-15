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

import cargame.core.CarGame;
import cargame.appstates.ResultsMenuState;

public class ChangeResultsLapTimesTask implements Callable
{
    private ResultsMenuState resultsMenuState;
    private long lap0;
    private long lap1;
    private long lap2;
    private long lap3;

    public ChangeResultsLapTimesTask(ResultsMenuState resultsMenuState, long lap0, long lap1, long lap2, long lap3)
    {
        this.resultsMenuState=resultsMenuState;
        this.lap0=lap0;
        this.lap1=lap1;
        this.lap2=lap2;
        this.lap3=lap3;
    }

    public Object call() throws Exception
    {
        System.out.println("ChangeResultsLapTimesTask called");

        // resultsMenuState.setLapTimes(lap0, lap1, lap2, lap3);

        return null;
    }
}