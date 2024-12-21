package city_cleaner.Factories;

import java.util.Collection;

import Doctrina.Entities.MovableEntity;
import city_cleaner.Entities.Enemy;

public class EnemySpawner {
  private static EnemySpawner instance;

  public static EnemySpawner getInstance() {
    if (instance == null) {
      instance = new EnemySpawner();
    }
    return instance;
  }

  public void startSpawning(Collection<Enemy> enemies, Collection<MovableEntity> entities) {
    if (Math.random() < 0.010 && enemies.size() < 20) {
      generateEnemy(enemies, entities);
    }
  }

  private void generateEnemy(Collection<Enemy> enemies, Collection<MovableEntity> entities) {
    Enemy enemy = EnemyFactory.generateEnemy();
    enemies.add(enemy);
    entities.add(enemy);
  }

}
