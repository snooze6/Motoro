package Collision;

import Utilities.Vector;

/**
 * Created by Denis on 23/05/2015.
 */
public class BBGap extends BBSphere {
    public BBGap() {
    }

    public BBGap(Vector point, float mass, float size) {
        super(point, mass, size);
    }

    public BBGap(Vector point, Vector velocity, float mass, float size) {
        super(point, velocity, mass, size);
    }

    public BBGap(float x, float y, float z, float mass, float size) {
        super(x, y, z, mass, size);
    }
}
