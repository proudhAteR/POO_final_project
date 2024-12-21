package city_cleaner.Factories;

import city_cleaner.Entities.Bonus.Bonus;
import city_cleaner.Entities.Bonus.PermanentBonus.HealthBonus;
import city_cleaner.Entities.Bonus.PermanentBonus.PermanentBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.SightBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.SpeedBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.TemporaryBonus;

import java.util.Random;

public class BonusFactory extends Factory {

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
        int type = generateRandomValue(0, 2);
        switch (type) {
            case 0:
                return new SightBonus(
                        generateRandomPosition(),
                        generateRandomValue(2, 10),
                        generateRandomValue(5, 10));
            case 1:
                return new SpeedBonus(
                        generateRandomPosition(),
                        generateRandomValue(2, 5),
                        generateRandomValue(5, 10));
            default:
                return new SightBonus(
                        generateRandomPosition(),
                        generateRandomValue(2, 10),
                        generateRandomValue(5, 10));
        }
    }

    private static int generateRandomValue(int origin, int limit) {
        return random.nextInt(origin, limit) + 1;
    }

    private static PermanentBonus createRandomPermanentBonus() {
        return new HealthBonus(generateRandomPosition(), generateRandomValue(20, 100));
    }

}
