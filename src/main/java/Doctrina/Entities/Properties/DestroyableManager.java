package Doctrina.Entities.Properties;

import java.util.Collection;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.CollidableRepository;

public class DestroyableManager {
    static void destroy(StaticEntity e) {
        CollidableRepository.getInstance().unregisterEntity(e);
    }

    static void destroyAll(Collection<StaticEntity> e) {
        CollidableRepository.getInstance().unregisterEntities(e);
    }
}
