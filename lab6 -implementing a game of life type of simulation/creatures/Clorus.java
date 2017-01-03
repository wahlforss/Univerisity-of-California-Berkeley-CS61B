package creatures;

import huglife.*;

import java.awt.Color;
import java.util.Map;
import java.util.List;

/**
 * Created by Alfred on 13/12/16.
 */
public class Clorus extends Creature {
    /** red color */
    private final int r = 34;

    /** green color */
    private final int g = 0;

    /** blue color */
    private final int b = 231;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r, g, b);

    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Clorus replicate() {
        Clorus daugther = new Clorus(energy*0.5);
        energy = energy*0.5;
        return daugther;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors,"empty");
        List<Direction> plip = getNeighborsOfType(neighbors,"plip");

        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plip.size() > 0) {
            Direction moveDir = HugLifeUtils.randomEntry(plip);
            return new Action(Action.ActionType.ATTACK, moveDir);
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(empties));
        } else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }

    }

}
