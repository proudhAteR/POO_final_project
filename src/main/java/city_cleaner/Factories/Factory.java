package city_cleaner.Factories;

import java.util.Random;

import Doctrina.Core.Camera;
import Doctrina.Core.RenderingEngine;
import Doctrina.Physics.Position;

public abstract class Factory {
    protected static Random random = new Random();

    protected static Position generateRandomPosition() {
        final Camera cam = RenderingEngine.getInstance().getCamera();

        int camX = cam.getEntityOnFocus().getX();
        int camY = cam.getEntityOnFocus().getY();

        int originX = Math.min(camX / 2, camX * 2);
        int boundX = Math.max(camX / 2, camX * 2);

        int originY = Math.min(camY / 2, camY * 2);
        int boundY = Math.max(camY / 2, camY * 2);

        int x = random.nextInt(originX, boundX);
        int y = random.nextInt(originY, boundY);

        return new Position(x, y);
    }
}
