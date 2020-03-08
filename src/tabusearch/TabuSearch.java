 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabusearch;

import org.apache.commons.collections4.IteratorUtils;
import java.util.List;

/**
 *
 * @author abdulazeez
 */
public class TabuSearch {
    private TabuList tabuList; 
    private StopCondition stopCondition; 
    private NeighborSolutionLocator solutionLocator; 
    public static int noOfIterations = 0;
    
    public TabuSearch(TabuList tabuList, StopCondition stopCondition, NeighborSolutionLocator solutionLocator) { 
  this.tabuList = tabuList; 
  this.stopCondition = stopCondition; 
  this.solutionLocator = solutionLocator; 
 } 
  
 /**
  * Execute the algorithm to perform a minimization. 
  * @param initialSolution the start point of the algorithm 
     * @param project 
  * @return the best solution found in the given conditions 
  */ 
 public Frog run(Frog initialSolution,ProjectSchedule project){ 
  Frog bestSolution = (initialSolution).deepclone(); 
  Frog currentSolution = (initialSolution).deepclone(); 
   
  Integer currentIteration = 0; 
  while (!stopCondition.mustStop(++currentIteration)) { 
   List<Frog> candidateNeighbors = currentSolution.generateNeighbors(project); 
    
   Frog bestNeighborFound = solutionLocator.findBestNeighbor(candidateNeighbors, this.tabuList); 
   if (bestNeighborFound.getFitness() < bestSolution.getFitness()) { 
    bestSolution = bestNeighborFound.deepclone(); 
   } 
    
   currentSolution = bestSolution.deepclone();
   this.tabuList.update(bestSolution.deepclone());  
        this.noOfIterations++;
//   System.out.println(currentSolution.toString()+" Neighbours: \n");
//   //for ( Frog i : currentSolution.getNeighbors()) {
//       //System.out.println(i.toString()+"\n");
//   //}

  } 
   
  return bestSolution; 
 } 
}