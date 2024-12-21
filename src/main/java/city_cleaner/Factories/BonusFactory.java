package city_cleaner.Factories;

import city_cleaner.Entities.Bonus.Bonus;
import city_cleaner.Entities.Bonus.PermanentBonus.HealthBonus;
import city_cleaner.Entities.Bonus.PermanentBonus.PermanentBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.SightBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.SpeedBonus;
import city_cleaner.Entities.Bonus.TemporaryBonus.TemporaryBonus;

import java.util.Random;

import Doctrina.Physics.Position;

public class BonusFactory extends Factory {

    private static final Random random = new Random();

    public static Bonus createRandomBonus() {
        boolean isTemporary = random.nextBoolean();
        Bonus bonus = isTemporary ? createTempBonus(generateRandomPosition())
                : createPermanentBonus(generateRandomPosition());

        return bonus;
    }

    private static TemporaryBonus<?> createTempBonus(Position pos) {
        int type = generateRandomValue(0, 2);

        switch (type) {
            case 0:
                return new SightBonus(
                        pos,
                        generateRandomValue(2, 10),
                        generateRandomValue(5, 10));
            case 1:
                return new SpeedBonus(
                        pos,
                        generateRandomValue(2, 5),
                        generateRandomValue(5, 10));
            default:
                return new SightBonus(
                        pos,
                        generateRandomValue(2, 10),
                        generateRandomValue(5, 10));
        }
    }

    private static PermanentBonus createPermanentBonus(Position pos) {
        return new HealthBonus(
                pos,
                generateRandomValue(20, 100));
    }

    public static Bonus dropBonus(Position pos) {
        boolean isTemporary = random.nextBoolean();

        Bonus bonus = isTemporary ? createTempBonus(pos) : createPermanentBonus(pos);

        return bonus;
    }

    private static int generateRandomValue(int origin, int limit) {
        return random.nextInt(origin, limit) + 1;
    }

}
