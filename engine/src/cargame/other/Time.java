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

package cargame.other;

/**
 *
 * @author Vortex
 */
public class Time {
    private long hour;
    private long minute;
    private long second;
    private long milisecond;
    
    public void setTime(long time)    {
        // To implement
        // See timer doc for bettes names
        // Use timer.getTimeInSeconds() as parameter count
        // Count is in seconds
        setHour(time/(60*60));
        setMinute(time/(60));
        setSecond(time);
        setMilisecond(time - ((getHour()*(60*60)) + (getMinute() * (60) + (getSecond()))) * 1000);
    }

    /**
     * @return the hour
     */
    public long getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(long hour) {
        this.hour = hour;
    }

    /**
     * @return the minute
     */
    public long getMinute() {
        return minute;
    }

    /**
     * @param minute the minute to set
     */
    public void setMinute(long minute) {
        this.minute = minute;
    }

    /**
     * @return the second
     */
    public long getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(long second) {
        this.second = second;
    }

    /**
     * @return the milisecond
     */
    public long getMilisecond() {
        return milisecond;
    }

    /**
     * @param milisecond the milisecond to set
     */
    public void setMilisecond(long milisecond) {
        this.milisecond = milisecond;
    }
}
