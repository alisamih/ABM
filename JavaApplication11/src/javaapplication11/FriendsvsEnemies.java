/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import java.io.*;
import java.util.*;






/**
 *
 * @author Ali
 */
public class FriendsvsEnemies extends SimState{
    
     public Continuous2D yard = new Continuous2D(1.0,100,100);
 
 public double TEMPERING_CUT_DOWN = 0.99;
public double TEMPERING_INITIAL_RANDOM_MULTIPLIER = 10.0;
public boolean tempering = true;

   

public void load3DStudents()
{
Bag students = buddies.getAllNodes();
for(int i = 0; i < students.size(); i++)
{
Agent student = (Agent)(students.get(i));
Double2D loc = (Double2D)(yard.getObjectLocation(student));
// we multiply by 5 in order to scale the agitation roughly with the student dispersion
// in the other two dimensions

}
}





public boolean isTempering() { return tempering; }
public void setTempering(boolean val) { tempering = val; }
 
 
 
 public double learningRate=2.0;
 
public int numAgents = 50;
double forceToSchoolMultiplier = 0.01;
double randomMultiplier = 0.1;
public int getNumFriendsvsEnemies() { return numAgents; }
public void setNumFriendsvsEnemies(int val) { if (val > 0) numAgents = val; }
public double getForceToSchoolMultiplier() { return forceToSchoolMultiplier; }
public void setForceToSchoolMultiplier(double val)
{ if (forceToSchoolMultiplier >= 0.0) forceToSchoolMultiplier = val; }
public double getRandomMultiplier() { return randomMultiplier; }
public void setRandomMultiplier(double val) { if (randomMultiplier >= 0.0) randomMultiplier = val; }
public Object domRandomMultiplier() { return new sim.util.Interval(0.0, 100.0); }
public double[] getAgitationDistribution()
{
Bag FriendsvsEnemies = buddies.getAllNodes();
double[] distro = new double[FriendsvsEnemies.numObjs];
for(int i = 0; i < FriendsvsEnemies.numObjs; i++)

distro[i] = ((Agent)(FriendsvsEnemies.objs[i])).getAgitation();
return distro;
}
public Network buddies = new Network(false);
public FriendsvsEnemies(long seed)
{
super(seed);
}
public void start()
{
super.start();

// add the tempering agent
if (tempering)
{
randomMultiplier = TEMPERING_INITIAL_RANDOM_MULTIPLIER;
schedule.scheduleRepeating(schedule.EPOCH, 1, new Steppable()
{
public void step(SimState state) { if (tempering) randomMultiplier *= TEMPERING_CUT_DOWN; }
});
}






// clear the yard
yard.clear();
// clear the buddies
buddies.clear();
// add some students to the yard






for(int i = 0; i < numAgents; i++)
{
Agent a = new Agent();
yard.setObjectLocation(a,
new Double2D(yard.getWidth() * 0.5 + random.nextDouble() - 0.5,
yard.getHeight() * 0.5 + random.nextDouble() - 0.5));
buddies.addNode(a);
schedule.scheduleRepeating(a);






}
// define like/dislike relationships
Bag students = buddies.getAllNodes();
for(int i = 0; i < students.size(); i++)
{
Object student = students.get(i);
// who does he like?
Object studentB = null;
do
{
studentB = students.get(random.nextInt(students.numObjs));
} while (student == studentB);
double buddiness = random.nextDouble();
buddies.addEdge(student, studentB, new Double(buddiness));
// who does he dislike?
do
{
studentB = students.get(random.nextInt(students.numObjs));
} while (student == studentB);
buddiness = random.nextDouble();
buddies.addEdge(student, studentB, new Double( -buddiness));
}




}
public static void main(String[] args)
{
doLoop(FriendsvsEnemies.class, args);
System.exit(0);
}






    
    
    
    
}
