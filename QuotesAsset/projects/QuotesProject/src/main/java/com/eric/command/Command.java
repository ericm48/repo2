package com.eric.command;

public interface Command 
{
	public boolean execute();
	public boolean undo();
	public Object  getResult();
	
}
