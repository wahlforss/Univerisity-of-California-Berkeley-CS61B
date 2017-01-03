package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/**
 * Created by Alfred on 13/12/16.
 */
public class TestClorus {

    @Test
    public void BasicTest() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(),0.01);
        c.stay();
        assertEquals(1.96, c.energy(), 0.01);
    }

    @Test
    public void AttackTest() {
        Plip p = new Plip(1);
        Clorus c = new Clorus(2);
        c.attack(p);
        assertEquals(3,c.energy(),0.01);

    }

    @Test
    public void ReplicateTest() {
        Clorus c = new Clorus(2);
        Clorus daughter = c.replicate();
        assertEquals(1, c.energy(), 0.01);
        assertEquals(1, daughter.energy(), 0.01);
        assertNotSame(daughter, c);
    }

    @Test
    public void TestChoose() {
        Clorus c = new Clorus(2);

        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action surroundedActual = c.chooseAction(surrounded);
        Action surroundedExpected = new Action(Action.ActionType.STAY);

        assertEquals(surroundedExpected, surroundedActual);


        //Attack Plip
        Plip p = new Plip(1);

        HashMap<Direction, Occupant> onePlip = new HashMap<>();
        onePlip.put(Direction.TOP, new Impassible());
        onePlip.put(Direction.BOTTOM, p);
        onePlip.put(Direction.LEFT, new Impassible());
        onePlip.put(Direction.RIGHT, new Empty());

        Action onePlipActual = c.chooseAction(onePlip);
        Action onePlipExpected= new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(onePlipExpected, onePlipActual);

        //One Empty test
        HashMap<Direction, Occupant> oneEmpty = new HashMap<>();
        oneEmpty.put(Direction.TOP, new Impassible());
        oneEmpty.put(Direction.BOTTOM, new Impassible());
        oneEmpty.put(Direction.LEFT, new Impassible());
        oneEmpty.put(Direction.RIGHT, new Empty());

        Action oneEmptyActual = c.chooseAction(oneEmpty);
        Action oneEmptyExpected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

        assertEquals(oneEmptyExpected, oneEmptyActual);

        Clorus cLowEnergy = new Clorus(0.5);

        Action oneEmptyLowActual = cLowEnergy.chooseAction(oneEmpty);
        Action oneEmptyLowExpected = new Action(Action.ActionType.MOVE, Direction.RIGHT);

        assertEquals(oneEmptyLowExpected, oneEmptyLowActual);

    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }

}
