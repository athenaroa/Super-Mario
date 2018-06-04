package com.example.athena.supermario;

public interface SuperMarioVisitor {
    public int visit(Enemy enemy);
    public int visit(Item item);
}
