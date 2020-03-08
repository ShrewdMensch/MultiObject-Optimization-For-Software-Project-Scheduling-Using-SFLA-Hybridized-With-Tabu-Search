/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabusearch;
 
import java.util.Collections; 
import java.util.Comparator; 
import java.util.List; 
 
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import multiobjectiveprojectschedulingwithsfla.Frog;

/**
 *
 * @author abdulazeez
 */
public final class NeighborSolutionLocator {

 public Frog findBestNeighbor(final List<Frog> neighborsSolutions,final TabuList solutionsInTabu) { 
     
//  //remove any neighbor that is in tabu list 
//  CollectionUtils.filterInverse(neighborsSolutions, new Predicate<Frog>() { 
//   @Override 
//   public boolean evaluate(Frog neighbor) { 
//    return solutionsInTabu.contains(neighbor); 
//   } 
//  }); 
//   
//  //sort the neighbors 
//  Collections.sort(neighborsSolutions, new Comparator<Frog>() { 
//   @Override 
//   public int compare(Frog o1, Frog o2) {
//				if (o1.getFitness() < o2.getFitness()) {
//					return -1;
//				} else if (o1.getFitness() > o2.getFitness()) {
//					return 1;
//				}
//				return 0;
//			}
//  }); 
//   
//  //get the neighbor with lowest value 
//  return neighborsSolutions.get(0); 
    Frog bestNeighbor = neighborsSolutions.get(0);
    for(Frog sNeighbor : neighborsSolutions ) {
        if((!solutionsInTabu.contains(sNeighbor) ) && 
                (sNeighbor.getFitness()< bestNeighbor.getFitness())) {
        bestNeighbor = (sNeighbor);
    }
 
 }
    return bestNeighbor;
 
}
}
