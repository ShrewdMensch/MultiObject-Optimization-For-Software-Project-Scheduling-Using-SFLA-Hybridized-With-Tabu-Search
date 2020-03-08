/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabusearch;

import multiobjectiveprojectschedulingwithsfla.Frog;

/**
 *
 * @author abdulazeez
 */
public class StopCondition {
    private final Integer maxIteration;
    
    public StopCondition(){
        maxIteration= 10;
    }
    
    public StopCondition(Integer maxIteration){
        this.maxIteration = maxIteration;
    }
    
    boolean mustStop(Frog bestSolution) {
        return bestSolution.getFitness() <=2;
    }
    
     boolean mustStop(int it) {
        return it== this.maxIteration;
    }
}
