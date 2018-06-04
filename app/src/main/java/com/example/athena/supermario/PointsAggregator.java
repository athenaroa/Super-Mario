package com.example.athena.supermario;

public class PointsAggregator {
    int currScore;

    public int addPoints(GameItems gameItem){
        SuperMarioVisitor visitor = new SuperMarioVisitorImpl();
        currScore += gameItem.getPoints(visitor);
        return currScore;
    }

}
