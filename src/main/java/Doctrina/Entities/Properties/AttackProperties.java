package Doctrina.Entities.Properties;

public class AttackProperties {
    protected int damage;
    protected int range;
    protected int force;

    public AttackProperties(int damage, int range) {
        this.damage = damage;
        this.force = damage * 2;
        this.range = range;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void decreaseRange() {
        this.range--;
    }

    public void decreaseDamage() {
        this.damage--;
    }

    public void decreaseProps() {
        this.damage--;
        this.range--;
        this.force--;
    }

    public boolean isAttackOutOfRange() {
        return this.getRange() <= 0;
    }

    public int getForce() {
        return force;
    }
}
