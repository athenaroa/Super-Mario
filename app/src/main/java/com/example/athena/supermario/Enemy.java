package com.example.athena.supermario;

public abstract class Enemy implements GameItems {
    int reward;
    int posx;
    int posy;

    public int getReward() {
        return reward;
    }

    public int getPosition(){
        return 0;
    }

    public int getPoints(SuperMarioVisitor visitor){
        return visitor.visit(this);
    }
}
