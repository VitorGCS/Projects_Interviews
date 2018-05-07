package uk.nominet.techtest.VitorSilva.towers.optimisers;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import uk.nominet.techtest.VitorSilva.towers.model.Point;
import uk.nominet.techtest.VitorSilva.towers.model.Receiver;
import uk.nominet.techtest.VitorSilva.towers.model.Result;
import uk.nominet.techtest.VitorSilva.towers.model.Scenario;
import uk.nominet.techtest.VitorSilva.towers.model.Transmitter;

public class AddTenOptimiser implements PowerOptimiser {

	// constructor
	public AddTenOptimiser() {
	}


	/**
	 * method to calculator the Chebyshev distance between two points
	 * @param a point initial
	 * @param b point final 
	 * @return int Chebyshev distance between two points
	 */
	private int chebyshevDist(Point a, Point b) {
		int x = Math.abs(a.x - b.x);
		int y = Math.abs(a.y - b.y);
		return x > y ? x : y;
	}


	/**
	 * method to find all receivers that have no coverage
	 * @param trans transmitters in the scenario
	 * @param rec receivers in the scenario
	 * @return List<Receiver> List of all receivers that have no coverage
	 */
	private List<Receiver> receiversNotCover(List<Transmitter> trans, List<Receiver> rec) {
		List<Receiver> notCover = rec.stream().filter(r -> !IsCovered(trans, r)).collect(Collectors.toList());
		return notCover;
	}


	/**
	 * method to calculates a distance of coverage gap
	 * @param t one transmitter
	 * @param p specific point
	 * @return int distance of coverage gap
	 */
	private int gapCoverage(Transmitter t, Point p) {
		int dis = chebyshevDist(t.location, p);
		return dis - t.getPower();
	}

	
	/**
	 * method to test if one receptor is covered
	 * @param t List of Transmitter
	 * @param r specific receiver
	 * @return boolean indicator if the receptor have covered
	 */
	public boolean IsCovered(List<Transmitter> t, Receiver r) {
		Optional<Transmitter> opt = t.stream().filter(a -> chebyshevDist(a.location, r.location) <= a.getPower())
				.findAny();
		return opt.isPresent();
	}

	/**
	 * method to optimize the scenario
	 * this method uses two different algorithms to find the best optimization/efficient
	 * @param Scenario
	 */
	@Override
	public Result optimise(Scenario scenario) {
		//trans is used to find the first optimized Result
		List<Transmitter> trans = scenario.transmitters;
		//copTrans is used to find the first optimized Result
		List<Transmitter> copTrans = scenario.transmitters.stream().map(item -> item.clone()).collect(Collectors.toList());

		List<Receiver> rec = scenario.receivers;
	
 		//Recursive algorithm:
		List<Transmitter> trans1 = Optimiser1(trans, rec);
		Result opt1 = new Result(trans1);
		int pw1 = opt1.getTotalPower();
		
		//Iterative algorithm
		List<Transmitter> trans2 = Optimiser2(copTrans, rec);
		Result opt2 = new Result(trans2);
		int pw2 = opt2.getTotalPower();

		// find the best scenario/more efficient (less power used)
		return (pw1 < pw2)? opt1:opt2;
	}

	/**
	 * method implements a recursive algorithm to optimize the scenario - many transmitters can be changed 
	 * 1) find the receiver with the greater distance of coverage gap
	 * 2) I raise the power of the closest transmitter in order to cover this receiver;
	 * 3) re-analyze the scenario and call again the method;
	 * @param trans List of Transmitters
	 * @param rec List of Receivers
	 * @return List<Transmitter> List of transmitters optimized 
	 */
	private List<Transmitter> Optimiser1(List<Transmitter> trans, List<Receiver> rec) {
		List<Receiver> notCover = receiversNotCover(trans, rec);
		if (notCover.isEmpty()) {
			return trans;
		} 
		else {
			//Find among all uncovered receivers which has the highest gap
			int minGapPerTrans[] = new int[notCover.size()];//
			int ix = 0;
			for (Receiver r : notCover) {
				OptionalInt RecDis = trans.stream().mapToInt(t -> gapCoverage(t, r.location)).min();
				minGapPerTrans[ix++] = RecDis.getAsInt();
			}

			int index = 0;//save the index of the receiver to cover (index match with notCover)
			int power = 0;//save the value of power to increment 
			for (int i = 0; i < minGapPerTrans.length; i++) {
				if (minGapPerTrans[i] > power) {
					power = minGapPerTrans[i];
					index = i;
				}
			}
			
			//this two variables only exists because is necessary be final to be available inside the stream operations
			final int fpower_inc = power;
			final int findxReceiver = index;
			// Get the transmitter that have the same distance to receiver to be covered
			Optional<Transmitter> aux = trans.stream()
					.filter(t -> gapCoverage(t, notCover.get(findxReceiver).location) == fpower_inc).findFirst();

		
			// update the power of that transmitter
			aux.get().setPower(aux.get().getPower() + fpower_inc);
		}
		
		//recursive call
		Optimiser1(trans, rec);

		return trans;
	}

	
	/**
	 * method implements an iterative algorithm to optimize the scenario - only one transmitter is changed 
	 * 1)Find, for all receivers that are without coverage, which transmitter is the further and at what distance/power
	 * 2)Add the necessary power to the found transmitter
	 * @param trans List of Transmitters
	 * @param rec List of Receivers
	 * @return List<Transmitter> List of transmitters optimized 
	 */
	private List<Transmitter> Optimiser2(List<Transmitter> trans, List<Receiver> rec) {
		List<Receiver> notCover = receiversNotCover(trans, rec);

		int transIndx = 0;
		//Find for all receivers that are without coverage, which transmitter is the further and at what distance/power
		int[] powerChg = new int[trans.size()];
		
		for (Transmitter tr : trans) {
			OptionalInt opt = notCover.stream().mapToInt(r -> gapCoverage(tr, r.location)).max();
			powerChg[transIndx++] = opt.getAsInt();
		}

		// Find the min distance/power and the respective index/transmitter
		int addpower = Integer.MAX_VALUE;
		int transChg = 0;
		for (int i = 0; i < powerChg.length; i++) {
			if (powerChg[i] < addpower) {
				addpower = powerChg[i];
				transChg = i;
			}
		}

		// update the power
		trans.get(transChg).setPower(trans.get(transChg).getPower() + addpower);

		return trans;
	}
}
