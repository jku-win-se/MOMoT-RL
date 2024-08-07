package at.ac.tuwien.big.moea.search.algorithm.reinforcement.domain;

import java.util.Map;

import org.moeaframework.core.Solution;

public interface IRewardStrategy<S extends Solution> {
   double determineAdditionalReward(S sOld, S sNew);

   Map<String, Double> getRewardMap();

}
