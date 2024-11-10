package Doctrina.Entities.Properties;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.CollidableRepository;

public interface Collidable {
    default void canCollide(StaticEntity e) {
        CollidableRepository.getInstance().registerEntity(e);
    }
}
