package at.ac.tuwien.big.momot.search.algorithm.reinforcement.environment;

import at.ac.tuwien.big.moea.search.algorithm.local.IFitnessComparator;
import at.ac.tuwien.big.moea.search.algorithm.reinforcement.datastructures.IApplicationState;
import at.ac.tuwien.big.moea.search.algorithm.reinforcement.domain.IEncodingStrategy;
import at.ac.tuwien.big.moea.search.algorithm.reinforcement.domain.IRewardStrategy;
import at.ac.tuwien.big.moea.search.algorithm.reinforcement.environment.ISOEnvironment;
import at.ac.tuwien.big.moea.search.algorithm.reinforcement.environment.SOEnvResponse;
import at.ac.tuwien.big.moea.search.algorithm.reinforcement.utils.LocalSearchStrategy;
import at.ac.tuwien.big.momot.problem.solution.TransformationSolution;
import at.ac.tuwien.big.momot.search.fitness.IEGraphMultiDimensionalFitnessFunction;

import java.util.List;

import org.moeaframework.core.Solution;
import org.nd4j.linalg.api.ndarray.INDArray;

public class BaseEnvironment<S extends Solution> extends AbstractEnvironment<S> implements ISOEnvironment<S> {

   protected IFitnessComparator<?, Solution> fitnessComparator;
   private final IRewardStrategy<S> rewardStrategy;
   private final String objectiveName;
   private final IEGraphMultiDimensionalFitnessFunction function;

   public BaseEnvironment(final IEGraphMultiDimensionalFitnessFunction function,
         final IFitnessComparator<?, Solution> fitnessComparator, final String objectiveName,
         final IRewardStrategy<S> rewardStrategy, final IEncodingStrategy<S> encodingStrategy) {
      super(encodingStrategy);
      this.rewardStrategy = rewardStrategy;
      this.fitnessComparator = fitnessComparator;
      this.objectiveName = objectiveName;
      this.function = function;

   }

   private double determineReward(final S state) {
      if(this.rewardStrategy != null && this.rewardStrategy.getRewardMap() != null) {

         return this.rewardStrategy.determineAdditionalReward(currentState, state);
      }
      return this.determineRewardByFitnessFunction(state);
   }

   private double determineRewardByFitnessFunction(final S state) {
      return (Double) fitnessComparator.getValue(state) * -1;
   }

   // private double determineRewardByTransformations(final List<IApplicationState> ruleAssignments) {
   // double reward = 0;
   // for(final IApplicationState actionRule : ruleAssignments) {
   // for(final RuleApplication rule : actionRule.getAppliedRules()) {
   // if(rewardStrategy.getRewardMap().containsKey(rule.getRule().getName())) {
   // reward += rewardStrategy.getRewardMap().get(rule.getRule().getName());
   // }
   // }
   // }
   //
   // return reward;
   // }

   @Override
   public String getFunctionName() {
      return this.objectiveName;
   }

   @Override
   public IEncodingStrategy<S> getProblemEncoder() {
      return this.encodingStrategy;
   }

   @Override
   public double getReward(final S state) {
      return this.determineReward(state);
   }

   public SOEnvResponse<S> step(final int action) {
      final SOEnvResponse<S> response = new SOEnvResponse<>();

      final S nextState = this.solutionProvider.generateExtendedSolution(this.encodingStrategy, currentState, action);

      response.setState(nextState);
      response.setAppliedActionId(action);

      function.evaluate((TransformationSolution) nextState);

      response.setReward(determineReward(nextState));
      response.setDone(determineIsEpisodeDone(nextState));

      currentState = nextState;

      return response;
   }

   @Override
   public SOEnvResponse<S> step(final LocalSearchStrategy strategy, final List<IApplicationState> action,
         final int explorationSteps) {
      throw new RuntimeException("Not appropriate environment!");
   }

   @Override
   public SOEnvResponse<S> step(final S solution, final INDArray actionProbs) {
      throw new RuntimeException("Not appropriate environment!");
   }

   @Override
   public SOEnvResponse<S> step(final S solution, final int action) {
      final SOEnvResponse<S> response = new SOEnvResponse<>();

      final S nextState = this.solutionProvider.generateExtendedSolution(this.encodingStrategy, solution, action);

      response.setState(nextState);
      response.setAppliedActionId(action);

      function.evaluate((TransformationSolution) nextState);

      response.setReward(determineReward(nextState));
      response.setDone(determineIsEpisodeDone(nextState));

      currentState = nextState;

      return response;
   }
}
