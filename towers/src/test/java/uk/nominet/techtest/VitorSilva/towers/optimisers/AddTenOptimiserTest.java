package uk.nominet.techtest.VitorSilva.towers.optimisers;

import org.junit.Test;

import uk.nominet.techtest.VitorSilva.towers.optimisers.AddTenOptimiser;
import uk.nominet.techtest.VitorSilva.towers.optimisers.PowerOptimiser;

import static org.junit.Assert.assertEquals;
import static uk.nominet.techtest.VitorSilva.towers.optimisers.TestScenarios.testScenarios;


public class AddTenOptimiserTest {
    private final PowerOptimiser subject = new AddTenOptimiser();

    @Test
    public void testScenario0() throws Exception {
        assertEquals(testScenarios.get(0).expectedResult, subject.optimise(testScenarios.get(0).scenario));
    }

    @Test
    public void testScenario1() throws Exception {
        assertEquals(testScenarios.get(1).expectedResult, subject.optimise(testScenarios.get(1).scenario));
    }

    @Test
    public void testScenario2() throws Exception {
        assertEquals(testScenarios.get(2).expectedResult, subject.optimise(testScenarios.get(2).scenario));
    }

    @Test
    public void testScenario3() throws Exception {
        assertEquals(testScenarios.get(3).expectedResult, subject.optimise(testScenarios.get(3).scenario));
    }

    @Test
    public void testScenario4() throws Exception {
        assertEquals(testScenarios.get(4).expectedResult, subject.optimise(testScenarios.get(4).scenario));
    }

    @Test
    public void testScenario5() throws Exception {
        assertEquals(testScenarios.get(5).expectedResult, subject.optimise(testScenarios.get(5).scenario));
    }
    
    @Test
    public void testScenario6() throws Exception {
        assertEquals(testScenarios.get(6).expectedResult, subject.optimise(testScenarios.get(6).scenario));
    }
}