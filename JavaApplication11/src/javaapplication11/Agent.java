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






/**
 *
 * @author Ali
 */
public class Agent implements Steppable {
    
     public double learningRate=2.0;
    
     public boolean brave=true;
     public boolean coward=false;
    
    
 public static final double MAX_FORCE = 3.0;
double friendsClose = 0.0; // initially very close to my friends
double enemiesCloser = 10.0; // WAY too close to my enemies
    private Object agents;
public double getAgitation() { return friendsClose + enemiesCloser; }
public String toString() { return "[" + System.identityHashCode(this) + "] agitation: " + getAgitation(); }







public void step(SimState state)
{
FriendsvsEnemies agents = (FriendsvsEnemies) state;
Continuous2D yard = agents.yard;
Double2D me = agents.yard.getObjectLocation(this);
MutableDouble2D sumForces = new MutableDouble2D();
friendsClose = enemiesCloser = 0.0;
// Go through my buddies and determine how much I want to be near them
MutableDouble2D forceVector = new MutableDouble2D();
Bag out = agents.buddies.getEdges(this, null);
int len = out.size();
for(int buddy = 0 ; buddy < len; buddy++)
{
Edge e = (Edge)(out.get(buddy));
double buddiness = ((Double)(e.info)).doubleValue();
// I could be in the to() end or the from() end. getOtherNode is a cute function
// which grabs the guy at the opposite end from me.
Double2D him = agents.yard.getObjectLocation(e.getOtherNode(this));
if (buddiness >= 0) // the further I am from him the more I want to go to him
 
{
forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
if (forceVector.length() > MAX_FORCE) // I’m far enough away
forceVector.resize(MAX_FORCE);
friendsClose += forceVector.length();




}
else // the nearer I am to him the more I want to get away from him, up to a limit
{
forceVector.setTo((him.x - me.x) * buddiness, (him.y - me.y) * buddiness);
if (forceVector.length() > MAX_FORCE) // I’m far enough away
forceVector.resize(0.0);
else if (forceVector.length() > 0)
forceVector.resize(MAX_FORCE - forceVector.length()); // invert the distance
enemiesCloser += forceVector.length();
}
sumForces.addIn(forceVector);
}
// add in a vector to the "teacher" -- the center of the yard, so we don’t go too far away
sumForces.addIn(new Double2D((yard.width * 0.5 - me.x) * agents.forceToSchoolMultiplier,
(yard.height * 0.5 - me.y) * agents.forceToSchoolMultiplier));
// add a bit of randomness
sumForces.addIn(new Double2D(agents.randomMultiplier * (agents.random.nextDouble() * 1.0 - 0.5),
agents.randomMultiplier * (agents.random.nextDouble() * 1.0 - 0.5)));
sumForces.addIn(me);
agents.yard.setObjectLocation(this, new Double2D(sumForces));



Agent otherAgent=new Agent();
//learn(state, otherStudent);//


}
 //Social Learning
//public void learn(SimState state, Agent otherAgent) {
  //  FriendsvsEnemies agents = (FriendsvsEnemies) state;
    //FriendsvsEnemies agents = (FriendsvsEnemies) state;
   // Edge e = agents.buddies.getEdge(this, otherAgent);
   // double currentBuddiness = e.getWeight();//
   // double learningRate=1.0;
    //double currentBuddiness = agents.buddies.getEdge(this, otherAgent).weight;
    //double currentBuddiness = e.getWeight();
  //  if (this.getAgitation() < otherStudent.getAgitation()) {
        // I am less agitated than the other player, so I want to be closer to them
    //    double newBuddiness = currentBuddiness + agents.learningRate;
     //   if (newBuddiness > 1.0) {
       //     newBuddiness = 1.0;

    
        }
    //    e.setWeight(newBuddiness);
   // } else {
        // I am more agitated than the other player, so I want to be farther away from them
   //     double newBuddiness = currentBuddiness - agents.learningRate;
  //      if (newBuddiness < -1.0) {
         //   newBuddiness = -1.0;
      ////  }
       // e.setWeight(newBuddiness);
   // }//
//}//










    
//}

    
    
    
    

