package at.ac.tuwien.big.moea.search.algorithm.reinforcement.datastructures;

import java.util.Map;

public interface IApplicationState {

   public Map<String, Object> getParamValues();

   public String getUnitName();

}
