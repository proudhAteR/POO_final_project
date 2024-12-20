package city_cleaner.Entities.Bonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BonusesRepository implements Iterable<Bonus> {
    private static BonusesRepository instance;
    private final List<Bonus> registeredBonuses;

    private BonusesRepository() {
        registeredBonuses = new ArrayList<>();
    }

    public List<Bonus> getRegisteredBonuses() {
        return registeredBonuses;
    }

    public static BonusesRepository getInstance() {
        if (instance == null) {
            instance = new BonusesRepository();
        }
        return instance;
    }
    public boolean isEmpty(){
        return registeredBonuses.isEmpty();
    }
    public void registerBonus(Bonus bonus) {
        registeredBonuses.add(bonus);
    }

    public void unregisterBonus(Bonus bonus) {
        registeredBonuses.remove(bonus);
    }

    public void registerBonuses(Collection<Bonus> bonuses) {
        registeredBonuses.addAll(bonuses);
    }

    public void unregisterBonuses(Collection<Bonus> bonuses) {
        registeredBonuses.removeAll(bonuses);
    }

    public int count() {
        return registeredBonuses.size();
    }

    @Override
    public Iterator<Bonus> iterator() {
        return registeredBonuses.iterator();
    }

}
