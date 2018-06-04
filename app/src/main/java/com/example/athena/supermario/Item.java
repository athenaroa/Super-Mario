package com.example.athena.supermario;

public abstract class Item implements GameItems {

    int value;
    int x;
    int y;

    //Write a constuctor here, if necessary

    public int getValue(){
        return value;
    }

    public int getPoints(SuperMarioVisitor visitor){
        return visitor.visit(this);
    }
}
