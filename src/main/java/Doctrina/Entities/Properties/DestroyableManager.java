package Doctrina.Entities.Properties;

import java.util.Collection;

import Doctrina.Entities.StaticEntity;
import Doctrina.Physics.CollidableRepository;

public class DestroyableManager {
    public static void destroy(StaticEntity e) {
        CollidableRepository.getInstance().unregisterEntity(e);
    }

    public static void destroyAll(Collection<StaticEntity> e) {
        CollidableRepository.getInstance().unregisterEntities(e);
    }
}
