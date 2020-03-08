/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabusearch;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import multiobjectiveprojectschedulingwithsfla.Frog;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.IteratorUtils;

/**
 *
 * @author abdulazeez
 */
public final class TabuList implements  Iterable<Frog> {
     private static CircularFifoQueue<Frog> tabuList; 
     private int size;
     
     public TabuList() {
         this.size = 10;
         this.tabuList = new CircularFifoQueue<Frog>(10); 
     }
     
     public TabuList(int size) {
          this.size = size;
          this.tabuList = new CircularFifoQueue<Frog>(this.size); 
     }
     
     public void add(Frog solution) {
         this.tabuList.add(solution);
     }
     
     public boolean contains(Frog solution) {
         List<Frog> solutionsInTabu = IteratorUtils.toList(tabuList.iterator()); 
         for(Frog sol : solutionsInTabu) {
             if(this.isSimilar(sol, solution)){
                 return true;
             }
         }
         return false;
     }
     
     public void update(Frog solution) {
         if(this.tabuList.size() == this.size)
             this.tabuList.remove();
         this.tabuList.add(solution);
     }
     
      @Override 
      public Iterator<Frog> iterator() { 
        return tabuList.iterator(); 
      }       
      
     
     public void Show() {
   List<Frog> solutionsInTabu = IteratorUtils.toList(tabuList.iterator()); 
         for(Frog solution : solutionsInTabu) {
             System.out.println(solution.toString());
         }
     }
     
     //Function that checks if 2 frogs are similar or not
    public boolean isSimilar(Frog frog1, Frog frog2){
        boolean similarityExist = true;
        
       for(int i= 0; i< frog1.getSize();i++){
           if( frog1.getId(i)!= frog2.getId(i) || 
                   frog1.getEmployeeAssignment(i)!= frog2.getEmployeeAssignment(i)
                            || (frog1.getFitness() != frog2.getFitness()) ) {
               similarityExist = false;
           return similarityExist;
           }
           
           
       }
       
        return similarityExist;
    }
}
