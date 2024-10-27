package Doctrina.Entities;

import java.util.Collection;

import Doctrina.Physics.CollidableRepository;

public interface Destroyable {
    static void destroy(StaticEntity e){
        CollidableRepository.getInstance().unregisterEntity(e);
    }
    static void destroyAll(Collection<StaticEntity> e){
        CollidableRepository.getInstance().unregisterEntities(e);
    }
}
