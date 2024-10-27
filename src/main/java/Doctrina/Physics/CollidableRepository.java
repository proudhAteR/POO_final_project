package Doctrina.Physics;

import java.util.List;

import Doctrina.Entities.StaticEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.lang.Iterable;
import java.util.Iterator;

public class CollidableRepository implements Iterable<StaticEntity> {
    private static CollidableRepository instance;
    private final List<StaticEntity> registeredEntities;

    private CollidableRepository() {
        registeredEntities = new ArrayList<>();
        // private constructor cause the class is a singleton
    }

    public static CollidableRepository getInstance() {
        if (instance == null) {
            instance = new CollidableRepository();
        }
        return instance;
    }

    public void registerEntity(StaticEntity entity) {
        registeredEntities.add(entity);
    }

    public void unregisterEntity(StaticEntity entity) {
        registeredEntities.remove(entity);
    }

    public void registerEntities(Collection<StaticEntity> entities) {
        registeredEntities.addAll(entities);
    }

    public void unregisterEntities(Collection<StaticEntity> entities) {
        registeredEntities.removeAll(entities);
    }

    public int count() {
        return registeredEntities.size();
    }

    @Override
    public Iterator<StaticEntity> iterator() {
        return registeredEntities.iterator();
    }
}
