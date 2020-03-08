/*
 * The Frog class, a container for a frog (solution)
 */
package multiobjectiveprojectschedulingwithtabu;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Bolarinwa
 */
public final class Frog  implements FrogInterface, Cloneable, Serializable
{
    private  int fitness= 0;
    private int[][] memes;
    private ArrayList<Frog> neighbors; 
    
    
    
    //constructor
    public Frog(ProjectSchedule project)
    {
        int numWp= project.getNumberOfWorkPackages();
        this.memes= new int[numWp][2];
        this.neighbors = new ArrayList<Frog>(numWp *2);
        
        int count=0;
        //Add Random WorkPackage Ids
        while(count<numWp)
        {
            for(int id:project.getRandomWorkPackageId()) 
            {
                this.memes[count++][0]=id;
            }
        }
        
        //Add Random Employee Assignment
        count=0;
        while(count<numWp)
        {
            for(int assignment:project.getRandomEmployeeAssignment()) 
            {
                this.memes[count++][1]=assignment;
            }
        }
        
    }
    
    @Override
    protected Object clone(){
        try{
        super.clone();
        return this.deepclone();
        }
        catch(CloneNotSupportedException ex) {
            return null;
        }
    }
    
    //To get a deep clone of a Frog (without referencing same address)
    public Frog deepclone() {
        
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Frog)ois.readObject();
        }
        catch(IOException | ClassNotFoundException ex){
            return null;
        }
    }
    
    //Clone a frog with this constructor
    public Frog(Frog frog) 
    {
        int numWp= frog.getSize();
        this.memes= new int[numWp][2];
        this.neighbors = new ArrayList<Frog>(numWp *2);
        
        for (int i=0;i< frog.getSize();i++)
        {
            this.setMemes(i, frog.getMemotype(i));
        }
        
        //this.elements= frog.getElements();
        this.fitness= frog.getFitness();
    }
    
    //set Frog's fitness
    public void setFitness(Integer fitness)
    {
        this.fitness= fitness;
    }
    
    @Override
    public void setFitness(ProjectSchedule project)
    {
        Integer fitnessValue= this.getDependenciesViolations(project.getDSM());
            fitnessValue+=skillMismatches(project);
            this.setFitness(fitnessValue);
    }
    
    //get Frog's fitnes
    public int getFitness()
    {
        return this.fitness;
    }
    
    
    @Override
     public void setMemes(int offset,int a[])
     {
            this.memes[offset]= a;
     }
     
     public void setId(int row,int value) {
         this.memes[row][0] = value;
     }
    
     public void setEmployeeAssignment(int row,int value) {
          this.memes[row][1] = value;
     }
     
     @Override
    //Get Frog's elements
    public int[][] getMemes()
    {
        return this.memes;
    }
    
    @Override
    //Get Frog's element
    public int[] getMemotype(int index)
    {
        return this.memes[index];
    }
    
    @Override
    //get  WP's id at location 'position' in Frog
    public int getId(int position) 
    {
        return this.memes[position][0];
    }
    
    @Override
     //get  WP's employee assignment at location 'position' in Frog
    public int getEmployeeAssignment(int position)
    {
        return this.memes[position][1];
    }
    
    //get Frog's size
    public int getSize()
    {
        return this.memes.length;
    }
    
      @Override
    public String toString()
    {
        String str = "\n";
        for(int i=0;i<this.getSize();i++){
            str+=this.getId(i)+" "+this.getEmployeeAssignment(i)+"\n";
        }
        str+="Fitness: "+this.getFitness();
        return str;
    }
    
    //get number of dependency/precedence violation in Frog
     public int getDependenciesViolations(int[][] DSM)
     {
        boolean violationOccur = false;
        int noViol=0;
        try
        {
            //loop each memes in a frog
        for(int i=0;i<this.memes.length;i++)
        {
            
            int k= (this.memes[i][0])-1;
            
            //Case where task doesn't have dependency
               if(DSM[k].length==0) 
               {
                   //Do nothing
                    ;
               }
               else
               {
                   /* Loop through all the dependency(ies) of the current WP
                   *  as defined in the DSM
                   */
                    for(int m=0;m<DSM[k].length;m++) 
                    {
                        violationOccur = true;
                        //loop through all the preceeding WP(s)
                        for(int n=0;n<i;n++)
                        {
                            //Check if all the dependency(ies) has all preceded the current WP
                        if(this.memes[n][0] == DSM[k][m])
                                violationOccur= false;
                         
                        }
                       if (violationOccur == true)
                            noViol++;
                    }
            
               }
            
        }
        
        }
        catch(Exception ex){
        ex.printStackTrace();
        }
         return noViol;
    }
     
     @Override
     //Method that calculates wrong no of employee assignment
    public int skillMismatches(ProjectSchedule projectSchedule){
        int totalMismatches= 0;
        int misMatches=0;
        
        boolean match=false;
        ArrayList<Integer> availableCompetences = new ArrayList<Integer>();
        ArrayList<Integer> totalCompetences = new ArrayList<Integer>();
       
            //Loop through each elements of a given frog to get info about the WPs
            for(int i=0;i<this.getSize();i++){
                
                Skill wpCompetence[]= projectSchedule.getWorkPackage(
                        this.getId(i)).getWpRequiredCompetences();
                
            ArrayList<Integer> employeeAss = bitPositions(this.getEmployeeAssignment(i),
                    projectSchedule.getNumberOfEmployees());
            
            totalCompetences = new ArrayList<Integer>();
            
            //Loop through employee assigned
                for(int k:employeeAss)
                {
                    match= false;
                    availableCompetences = new ArrayList<Integer>();
                    for(int m =0;m< projectSchedule.getEmployee(k).getNumberOfSkill();m++) {
                    availableCompetences.add(projectSchedule.getEmployee(k).getSkill(m).getSkillId());
                    
                    }
                    totalCompetences.addAll(availableCompetences);
                    
            //Loop through the current Wp's required competences
                    for (Skill j:wpCompetence){
                         if(availableCompetences.contains(j.getSkillId())){
                            match=true;
                         }
                    }
                    //check if there was no required competence(s) match
                    if(!match) {
                        misMatches++;
                    }
                    
                }
                for(Skill j:wpCompetence) {
                    if (!totalCompetences.contains(j.getSkillId()))
                         misMatches++;
                }
                
                
            }
           
        return misMatches;
    }
 
    
    @Override
    //Function that convert the binary representation of staff allocation to readable form
    public ArrayList<Integer> bitPositions(int numb,int numEmp){
        String binaryEq= String.format("%"+numEmp+"s",Integer.toBinaryString(numb)).replace(' ','0');
        ArrayList<Integer> integerList = new ArrayList<>();
        for(int i=0;i<binaryEq.length();i++){
        if(binaryEq.charAt(i)=='1'){
            integerList.add(i+1);
        }
        }
        return integerList;
    }
    
    public Frog swap(Integer row1, Integer row2) {
        Random rnd = new Random();
        int col = rnd.nextInt(2); //generate random no between 0 and 1
        int temp;
        
            temp = this.memes[row1][col];
            this.memes[row1][col] = this.memes[row2][col];
            this.memes[row2][col] = temp;
        return this;
    }
    
    private int getRandomInt(int min, int max) {
    Random random = new Random();

    return random.nextInt((max - min) + 1) + min;
}
    
    public ArrayList<Frog> generateNeighbors(ProjectSchedule project) {
         int row1, row2;
            Random rndNo = new Random();
            Frog copy = this.deepclone();
            int count= 0;
        while(count++ < (this.getSize() * 2)){
            //copy = new Frog(this);
            row1 = rndNo.nextInt(this.getSize());
            row2 = rndNo.nextInt(this.getSize());
            
            while(row2 == row1) { //Ensuring row1 is not the same as row2
                row2 = rndNo.nextInt(this.getSize());
            }
            Frog tempFrog = copy.swap(row1,row2).deepclone();
            tempFrog.setFitness(project);
            this.neighbors.add(tempFrog);
        }
        return this.neighbors;
    }
    
     public ArrayList<Frog> getNeighbors(){
         return this.neighbors;
     }
}
