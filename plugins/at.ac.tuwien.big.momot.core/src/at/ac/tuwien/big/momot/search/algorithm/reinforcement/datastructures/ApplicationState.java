package at.ac.tuwien.big.momot.search.algorithm.reinforcement.datastructures;

import at.ac.tuwien.big.moea.search.algorithm.reinforcement.datastructures.IApplicationState;
import at.ac.tuwien.big.momot.problem.solution.variable.RuleApplicationVariable;
import at.ac.tuwien.big.momot.problem.solution.variable.UnitApplicationVariable;
import at.ac.tuwien.big.momot.util.MomotUtil;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.henshin.interpreter.UnitApplication;

@JsonIgnoreProperties(value = { "unit, appliedRuless" })
public class ApplicationState implements Serializable, IApplicationState {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   public static ApplicationState of(final UnitApplication ua) {
      Map<String, Object> paramValues = new HashMap<>();
      if(ua instanceof UnitApplicationVariable) {
         paramValues = MomotUtil.extractParameters(((UnitApplicationVariable) ua).getAssignment());
      } else if(ua instanceof RuleApplicationVariable) {
         final RuleApplicationVariable[] rarr = { (RuleApplicationVariable) ua };
         paramValues = MomotUtil.extractParameters(rarr);
      }

      return new ApplicationState(ua.getUnit().getName(), paramValues);
   }

   private Map<String, Object> paramValues;
   private String unitName;

   private transient int hashCode;

   public ApplicationState() {
      this.unitName = null;
      this.paramValues = new HashMap<>();
   }

   public ApplicationState(final String unitName, final Map<String, Object> paramValues) {
      this.unitName = unitName;
      this.paramValues = paramValues;
   }

   @Override
   public boolean equals(final Object o) {
      if(o == this) {
         return true;
      }

      if(!(o instanceof ApplicationState)) {
         return false;
      }

      final ApplicationState other = (ApplicationState) o;

      return this.getUnitName().compareTo(other.getUnitName()) == 0
            && this.getParamValues().equals(other.getParamValues());
   }

   @Override
   @JsonAnyGetter
   public Map<String, Object> getParamValues() {
      return paramValues;
   }

   @Override
   @JsonProperty("unitName")
   public String getUnitName() {
      return unitName;
   }

   @Override
   public int hashCode() {
      int result = hashCode;
      if(result == 0) {
         result = this.getUnitName().hashCode();
         for(final Entry<String, Object> p : this.getParamValues().entrySet()) {
            result = (31 * result + p.getKey().hashCode()) * 31 + p.getValue().hashCode();
         }
         hashCode = result;
      }

      return result;
   }

   private void readObject(final ObjectInputStream o) throws IOException, ClassNotFoundException {
      this.setUnitName((String) o.readObject());
      this.setParamValues((Map<String, Object>) o.readObject());

   }

   public void setParamValues(final Map<String, Object> paramValues) {
      this.paramValues = paramValues;
   }

   public void setUnitName(final String unitName) {
      this.unitName = unitName;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder(this.unitName);
      for(final Entry<String, Object> e : this.paramValues.entrySet()) {
         sb.append("\n\t" + e.getKey() + ": ");
         final Object val = e.getValue();
         if(val instanceof Number) {
            sb.append(val);
         } else {
            sb.append("\"" + val + "\"");
         }
      }
      return sb.toString();

   }

   // private void writeObject(final ObjectOutputStream o) throws IOException {
   // o.writeObject(this.getUnit().getName());
   // o.writeObject(this.getParamValues());
   // }
}
