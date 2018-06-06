package com.example.athena.supermario;

public class PointsAggregator {
    private int currScore;
/*
    public int addPoints(GameItems gameItem){
        SuperMarioVisitor visitor = new SuperMarioVisitorImpl();
        currScore += gameItem.getPoints(visitor);
        return currScore;
    }
*/
    public void addPoints(GameItems gameItem){
        SuperMarioVisitor visitor = new SuperMarioVisitorImpl();
        currScore += gameItem.getPoints(visitor);
    }

    public int getCurrScore(){
        return currScore;
    }



}
