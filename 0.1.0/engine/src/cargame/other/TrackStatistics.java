/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cargame.other;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import java.io.IOException;

/**
 *
 * @author Vortex
 */
public class TrackStatistics implements Savable {

    private long fastestLapTime = 0; // some custom user data
    
    public long getFastestLapTime() {
        return fastestLapTime;
    }

    public void setFastestLapTime(long fastestLapTime) {
        this.fastestLapTime = fastestLapTime;
    }
    
    public void write(JmeExporter ex) throws IOException {
        OutputCapsule capsule = ex.getCapsule(this);
        capsule.write(fastestLapTime, "fastestLapTime", 0);
    }

    public void read(JmeImporter im) throws IOException {
        InputCapsule capsule = im.getCapsule(this);
        fastestLapTime = capsule.readLong("fastestLapTime", 0);
    }
    
}
