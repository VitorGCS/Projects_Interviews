package uk.nominet.techtest.VitorSilva.towers.optimisers;

import uk.nominet.techtest.VitorSilva.towers.model.Result;
import uk.nominet.techtest.VitorSilva.towers.model.Scenario;

//describe a testScenario
public class TestScenario {
    public final Scenario scenario;//scenario (List of transmitter and receiver)
    public final Result expectedResult;//result (List of transmitter optimized)

    public TestScenario(Scenario scenario, Result expectedResult) {
        this.scenario = scenario;
        this.expectedResult = expectedResult;
    }
}
