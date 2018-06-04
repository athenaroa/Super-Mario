package com.example.athena.supermario;

public class SuperMarioVisitorImpl implements SuperMarioVisitor {
    @Override
    public int visit(Enemy enemy) {
        return enemy.getReward();
    }

    @Override
    public int visit(Item item) {
        return item.getValue();
    }
}
