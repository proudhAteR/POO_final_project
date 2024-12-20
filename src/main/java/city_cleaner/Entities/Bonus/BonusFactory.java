package city_cleaner.Entities.Bonus;

import city_cleaner.Entities.Bonus.PermanentBonus.HealthBonus;
import city_cleaner.Entities.Bonus.PermanentBonus.PermanentBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.SightBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.SpeedBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.TemporaryBonus;

import java.util.Random;

import Doctrina.Physics.Position;
import Doctrina.Rendering.Camera;
import Doctrina.Rendering.RenderingEngine;

public class BonusFactory {

    private static final Random random = new Random();

    public static Bonus createRandomBonus() {
        boolean isTemporary = random.nextBoolean();

        if (isTemporary) {
            return createRandomTemporaryBonus();
        } else {
            return createRandomPermanentBonus();
        }
    }

    private static TemporaryBonus<?> createRandomTemporaryBonus() {
        switch (0) {
            case 0:
                return new SightBonus(generateRandomPosition(), generateRandomValue(10), generateRandomValue(10));
            case 1:
                return new SpeedBonus(generateRandomPosition(), generateRandomValue(5), generateRandomValue(10));
            default:
                return new SightBonus(generateRandomPosition(), generateRandomValue(10), generateRandomValue(10));
        }
    }

    private static int generateRandomValue(int value) {
        return random.nextInt(value) + 1;
    }

    private static PermanentBonus createRandomPermanentBonus() {
        return new HealthBonus(generateRandomPosition(), generateRandomValue(10));
    }

    private static Position generateRandomPosition() {
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
