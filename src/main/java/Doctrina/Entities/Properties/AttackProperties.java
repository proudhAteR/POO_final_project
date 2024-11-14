package Doctrina.Entities.Properties;

public class AttackProperties {
    protected int damage;
    protected int range;

    public AttackProperties(int damage, int range){
        this.damage = damage;
        this.range = range;

    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

   
}
