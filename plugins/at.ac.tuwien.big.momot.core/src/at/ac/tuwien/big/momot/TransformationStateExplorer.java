package at.ac.tuwien.big.momot;

import at.ac.tuwien.big.momot.problem.solution.TransformationSolution;
import at.ac.tuwien.big.momot.search.fitness.IEGraphMultiDimensionalFitnessFunction;
import at.ac.tuwien.big.momot.space.exploration.ExplorationMonitor;
import at.ac.tuwien.big.momot.space.exploration.MomotStateSpaceManager;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.impl.ModelImpl;
import org.eclipse.emf.henshin.statespace.impl.StateImpl;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceImpl;
import org.eclipse.emf.henshin.statespace.util.StateSpaceExplorationHelper;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Population;

public class TransformationStateExplorer {

   public interface MyInterface {
      String solutionRepresentation(TransformationSolution solution);
   }

   protected org.eclipse.emf.henshin.model.Module module;
   protected String problemGraph;
   // protected int nrOfObjectives;
   // protected int nrOfConstraints;
   protected IEGraphMultiDimensionalFitnessFunction function;
   protected HenshinResourceSet henshinResourceSet;
   protected MomotStateSpaceManager manager;
   protected StateSpaceExplorationHelper helper;
   protected ExplorationMonitor monitor;
   protected EGraph initialGraph;
   protected double startTime;

   protected double elapsedTime;

   public TransformationStateExplorer(final Module module, final String problemGraph,
         final IEGraphMultiDimensionalFitnessFunction function) {
      this.module = module;
      this.problemGraph = problemGraph;
      this.function = function;
      this.henshinResourceSet = new HenshinResourceSet();
      this.monitor = new ExplorationMonitor();
      final StateSpace stateSpace = new StateSpaceImpl(this.module);
      final Resource modelResource = this.henshinResourceSet.getResource(problemGraph);
      final Model initialModel = new ModelImpl(modelResource);
      this.initialGraph = initialModel.getEGraph();

      final State initialState = new StateImpl(0);

      initialState.setModel(initialModel);

      manager = new MomotStateSpaceManager(stateSpace, initialModel, initialGraph, function, monitor);
      helper = new StateSpaceExplorationHelper(manager);
   }

   public double getElapsedTime() {
      return this.elapsedTime;
   }

   public Population getSolutions(final boolean buildParetoSet) {
      // final Population p = buildParetoSet ? new NondominatedPopulation() : new Population();
      // for(final List<List<ITransformationVariable>> stateChains : this.manager.getStateToTrafoVars().values()) {
      // for(final List<ITransformationVariable> chain : stateChains) {
      // final TransformationSolution ts = new TransformationSolution(initialGraph, chain,
      // function.getObjectiveNames().size(), function.getConstraintNames().size());
      // ts.execute();
      // function.evaluate(ts);
      // if(ts.getObjectives()[0] != IFitnessEvaluation.WORST_FITNESS) {
      // p.add(ts);
      // }
      // }
      // }
      return buildParetoSet ? new NondominatedPopulation(this.manager.getPopulation()) : this.manager.getPopulation();
   }

   public void run() {
      try {
         helper.doExploration(-1, monitor);
      } catch(final StateSpaceException e) {
         e.printStackTrace();
      }
   }

   public void setSolutionReprFunction(final MyInterface f) {
      this.manager.setSolutionReprFunction(f);
   }

}
