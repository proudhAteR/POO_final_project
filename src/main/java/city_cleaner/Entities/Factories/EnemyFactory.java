package city_cleaner.Entities.Factories;


import Doctrina.Entities.Properties.AttackProperties;
import city_cleaner.Entities.Enemy;

public class EnemyFactory extends Factory{

    public static Enemy generateEnemy() {
        Enemy enemy = new Enemy();
        enemy.setAttackProperties(new AttackProperties(1, 0));
        enemy.canCollide(enemy);
        enemy.teleport(generateRandomPosition());

        return enemy;
    }
}
