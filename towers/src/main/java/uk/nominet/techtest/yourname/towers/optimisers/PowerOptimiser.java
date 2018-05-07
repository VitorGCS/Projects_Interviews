package uk.nominet.techtest.yourname.towers.optimisers;

import uk.nominet.techtest.yourname.towers.model.Result;
import uk.nominet.techtest.yourname.towers.model.Scenario;

public interface PowerOptimiser {
    Result optimise(Scenario scenario);
}
