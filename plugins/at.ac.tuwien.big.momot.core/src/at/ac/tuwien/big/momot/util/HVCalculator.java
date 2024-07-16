package at.ac.tuwien.big.momot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.indicator.Hypervolume;

public class HVCalculator {
   private static boolean INCLUDE_INITIAL_MODEL_SOLUTION = false;

   public static Double[] averageRow(final double[][] a2) {
      double rowTotal = 0;
      double average = 0;
      final Double[] averages = new Double[a2.length];

      int i = 0;
      for(final double[] element : a2) {
         for(final double element2 : element) {
            rowTotal += element2;
         }
         average = rowTotal / element.length; // calc average
         // System.out.println(average); // print the row average
         averages[i++] = average;
         rowTotal = 0; // start over (for next row)
      }
      return averages;
   }

   public static NondominatedPopulation buildReferenceSet(final String experimentPath, final String... algorithmPaths) {
      final NondominatedPopulation ref = new NondominatedPopulation();
      FileInputStream fout = null;
      ObjectInputStream oos = null;
      List<double[]> popList = null;

      for(final String algorithmPath : algorithmPaths) {
         final String algoPath = experimentPath + algorithmPath + "/runs";
         final File file = new File(algoPath);
         final String[] directories = file.list((current, name) -> new File(current, name).isDirectory());

         for(final String dir : directories) {
            final File curRun = new File(algoPath + "/" + dir);
            final List<String> curPops = Arrays.asList(curRun.list());
            Collections.sort(curPops, new Comparator<String>() {
               @Override
               public int compare(final String o1, final String o2) {
                  return extractInt(o1) - extractInt(o2);
               }

               int extractInt(final String s) {
                  final String num = s.replaceAll("\\D", "");
                  // return 0 if no digits found
                  return num.isEmpty() ? 0 : Integer.parseInt(num);
               }
            });
            final String lastPopPath = curPops.get(curPops.size() - 1);
            try {
               fout = new FileInputStream(algoPath + "/" + dir + "/" + lastPopPath);
               oos = new ObjectInputStream(fout);
               popList = (List<double[]>) oos.readObject();
               for(final double[] element : popList) {
                  if(!INCLUDE_INITIAL_MODEL_SOLUTION && element[0] == 0) {
                     continue;
                  }

                  ref.add(new Solution(element));
               }
               fout.close();
               oos.close();
            } catch(final IOException | ClassNotFoundException e) {
               e.printStackTrace();
            } finally {

            }

         }

      }
      return ref;

   }

   public static StringBuilder computeStatistics(final Problem problem, final NondominatedPopulation referenceSet,
         final String experimentPath, final StringBuilder sb, final String hvSavePath, final String... algorithmPaths)
         throws IOException {
      final Hypervolume h = new Hypervolume(problem, referenceSet);

      FileInputStream fout = null;
      ObjectInputStream oos = null;
      List<double[]> popList = null;
      final List<Double[]> avgHs = new ArrayList<>();
      final StringBuilder sbHV = new StringBuilder();
      final StringBuilder meanOuts = new StringBuilder();
      final Map<String, double[]> algToFinalHVs = new HashMap<>();

      for(final String algorithmPath : algorithmPaths) {
         final String algoPath = experimentPath + algorithmPath + "/runs";
         final File file = new File(algoPath);
         final String[] directories = file.list((current, name) -> new File(current, name).isDirectory());

         final double[] finalHvs = new double[directories.length];
         final File firstAlgoDir = new File(algoPath + "/" + directories[0]);
         final double[][] hvs = new double[firstAlgoDir.list().length][directories.length];

         final double[] convergedIter = new double[directories.length];
         final double[] execTimes = new double[directories.length];

         int i = 0;

         final NondominatedPopulation curNonDomSet = new NondominatedPopulation();

         for(final String dir : directories) {
            final File curRun = new File(algoPath + "/" + dir);
            final List<String> curPops = Arrays.asList(curRun.list());

            Collections.sort(curPops, new Comparator<String>() {
               @Override
               public int compare(final String o1, final String o2) {
                  return extractInt(o1) - extractInt(o2);
               }

               int extractInt(final String s) {
                  final String num = s.replaceAll("\\D", "");
                  // return 0 if no digits found
                  return num.isEmpty() ? 0 : Integer.parseInt(num);
               }
            });
            final NondominatedPopulation curNonDomPop = new NondominatedPopulation();

            double maxHV = 0;
            int maxIterPop = 0;
            int curIterPop = 0;
            int PopIdx = 0;

            for(final String pop : curPops) {
               try {
                  fout = new FileInputStream(algoPath + "/" + dir + "/" + pop);
                  curIterPop++;
                  oos = new ObjectInputStream(fout);
                  popList = (List<double[]>) oos.readObject();
                  for(final double[] element : popList) {
                     if(!INCLUDE_INITIAL_MODEL_SOLUTION && element[1] == 0) {
                        continue;
                     }
                     curNonDomPop.add(new Solution(element));

                  }

                  final double curHV = h.evaluate(curNonDomPop);
                  hvs[PopIdx++][i] = curHV;

                  if(curHV > maxHV) {
                     maxHV = curHV;
                     maxIterPop = curIterPop * 100;
                  }
                  fout.close();
                  oos.close();
               } catch(IOException | ClassNotFoundException e) {
                  e.printStackTrace();
               }

            }
            convergedIter[i] = maxIterPop;

            final BasicFileAttributes fAttr = Files.readAttributes(Path.of(algoPath, dir, curPops.get(0)),
                  BasicFileAttributes.class);

            final FileTime firstCreationTime = fAttr.lastModifiedTime();

            final String lastPopPath = curPops.get(curPops.size() - 1);

            final BasicFileAttributes lAttr = Files.readAttributes(Path.of(algoPath, dir, lastPopPath),
                  BasicFileAttributes.class);

            final FileTime lastCreationTime = lAttr.lastModifiedTime();

            final long differenceInMillis = Math.abs(lastCreationTime.toMillis() - firstCreationTime.toMillis());

            execTimes[i] = Duration.ofMillis(differenceInMillis).getSeconds();

            try {
               fout = new FileInputStream(algoPath + "/" + dir + "/" + lastPopPath);
               oos = new ObjectInputStream(fout);
               popList = (List<double[]>) oos.readObject();
               for(final double[] element : popList) {
                  if(!INCLUDE_INITIAL_MODEL_SOLUTION && element[1] == 0) {
                     continue;
                  }

                  curNonDomPop.add(new Solution(element));
                  curNonDomSet.add(new Solution(element));
               }
               fout.close();
               oos.close();

            } catch(final IOException | ClassNotFoundException e) {
               e.printStackTrace();
            }

            finalHvs[i++] = h.evaluate(curNonDomPop);

         }
         sb.append("\n" + algorithmPath + "\n");
         meanOuts.append("\n" + algorithmPath + "\n");
         for(final Solution s : curNonDomSet) {
            final String objS = Arrays.toString(s.getObjectives());
            sb.append(objS.substring(1, objS.length() - 1) + "\n");
         }

         final StandardDeviation sd = new StandardDeviation();

         algToFinalHVs.put(algorithmPath, finalHvs);
         final DoubleSummaryStatistics stat = Arrays.stream(finalHvs).summaryStatistics();
         meanOuts.append("Average: " + stat.getAverage() + "\n");
         meanOuts.append("Standard Deviation: " + sd.evaluate(finalHvs) + "\n");
         meanOuts.append("Max: " + stat.getMax() + "\n");
         meanOuts.append("Min: " + stat.getMin() + "\n");
         meanOuts.append("Count: " + stat.getCount() + "\n");
         meanOuts.append(
               "Converged after iter. (avg): " + Arrays.stream(convergedIter).summaryStatistics().getAverage() + "\n");

         final DoubleSummaryStatistics timeStat = Arrays.stream(execTimes).summaryStatistics();
         meanOuts.append("Execution time (avg): " + timeStat.getAverage() + "\n");

         final Double[] avgHvs = averageRow(hvs);
         avgHs.add(avgHvs);

         if(algorithmPath.compareTo(algorithmPaths[algorithmPaths.length - 1]) == 0) {
            sbHV.append(algorithmPath + "\n");

         } else {
            sbHV.append(algorithmPath + ";");
         }

      }

      for(int i = 0; i < avgHs.get(0).length; i++) {
         for(int j = 0; j < avgHs.size(); j++) {
            if(j == avgHs.size() - 1) {
               sbHV.append(avgHs.get(j)[i] + "\n");
            } else {
               sbHV.append(avgHs.get(j)[i] + ";");
            }
         }

      }

      try {
         final PrintWriter writer = new PrintWriter(new File(hvSavePath + "hvs.csv"));

         writer.write(sbHV.toString());
         writer.close();

      } catch(final IOException e) {
         e.printStackTrace();
      }

      Files.write(Paths.get(hvSavePath, "cum.txt"), meanOuts.toString().getBytes());

      writeDataToCSV(algToFinalHVs, Paths.get(hvSavePath, "hvs_runs.csv").toString());

      return sb;

   }

   public static void writeDataToCSV(final Map<String, double[]> data, final String fileName) throws IOException {
      try(FileWriter csvWriter = new FileWriter(fileName)) {
         // Write the header
         final String[] headers = data.keySet().toArray(new String[0]);
         for(int i = 0; i < headers.length; i++) {
            csvWriter.append(headers[i]);
            if(i < headers.length - 1) {
               csvWriter.append(",");
            }
         }
         csvWriter.append("\n");

         // Find the length of the arrays (assumed to be the same for all algorithms)
         final int numRows = data.values().iterator().next().length;

         // Write the data rows
         for(int i = 0; i < numRows; i++) {
            for(int j = 0; j < headers.length; j++) {
               csvWriter.append(String.valueOf(data.get(headers[j])[i]));
               if(j < headers.length - 1) {
                  csvWriter.append(",");
               }
            }
            csvWriter.append("\n");
         }
      }
   }
}
