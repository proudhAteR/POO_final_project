package Doctrina.Entities;

import Doctrina.Physics.CollidableRepository;

public interface Collidable {
    default void canCollide(StaticEntity e) {
        CollidableRepository.getInstance().registerEntity(e);
    }
}
