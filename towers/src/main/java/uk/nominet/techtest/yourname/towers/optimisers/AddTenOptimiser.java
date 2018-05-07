package uk.nominet.techtest.yourname.towers.optimisers;

import uk.nominet.techtest.yourname.towers.model.Result;
import uk.nominet.techtest.yourname.towers.model.Scenario;
import uk.nominet.techtest.yourname.towers.model.Transmitter;

import java.util.stream.Collectors;

public class AddTenOptimiser implements PowerOptimiser {
    // This is fairly obviously not a very good implementation

    public AddTenOptimiser() {
    }

    @Override
    public Result optimise(Scenario scenario) {
        return new Result(
                scenario.transmitters.stream().map(t -> new Transmitter(t.id, t.location, t.power + 10)).collect(Collectors.toList())
        );
    }
}
