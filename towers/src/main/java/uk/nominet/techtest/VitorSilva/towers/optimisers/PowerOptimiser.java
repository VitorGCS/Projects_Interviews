package uk.nominet.techtest.VitorSilva.towers.optimisers;

import uk.nominet.techtest.VitorSilva.towers.model.Result;
import uk.nominet.techtest.VitorSilva.towers.model.Scenario;

public interface PowerOptimiser {
    Result optimise(Scenario scenario);
}
