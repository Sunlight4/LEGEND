package com.sunlight4.legend;

public abstract class AI {
Character owner;
public abstract void addCard(Card card);
public abstract void update();
public AI(Character o) {
	owner=o;
}
}
