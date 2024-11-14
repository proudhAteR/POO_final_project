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

    public void setRange(int range) {
        this.range = range;
    }

    public void decreaseRange() {
        this.range--;
    }

    public void decreaseDamage(){
        this.damage--;
    }

    public void decreaseProps(){
        this.damage--;
        this.range --;
    }
   
}
