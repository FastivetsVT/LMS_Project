package com.brainacad.lms.fastivets.control;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.brainacad.lms.fastivets.util.FileOperations;
import com.brainacad.lms.fastivets.util.QuitException;

public abstract class Manager<E> {	
	protected static Scanner scanInput;
	protected String thisClassName;
	protected TreeMap<Integer,E> list;
	
	protected Manager(){}
    
    public static void setScanInput(Scanner scanInput) {
    	Manager.scanInput = scanInput;
	}

	abstract void createObject() throws QuitException;
	
	protected int getNewID (){
		return isEmpty(list)?1:list.lastKey()+1;
	}
	
	protected void finishCreation(Integer id, E obj){
		list.put(id, obj);
		System.out.printf("\nNew %s has been successfully created:\n\n", thisClassName);
		save();
	}
	
	protected boolean isNull (Object o){
		return o==null;
	}

	protected boolean isEmpty (String s){
		return isNull(s) || s.trim().isEmpty();
	}
	
	protected boolean isEmpty (Collection<Integer> ids){
		return isNull(ids) || ids.isEmpty();
	}
	
	protected boolean isEmpty (Map<Integer, E> list){
		return isNull(list) || list.isEmpty();
	}	
	
	public void show(){
		if (isEmpty(list)){
			System.err.printf("No %ss available yet", thisClassName);
		} else {
			show(list.keySet());
		}
	}
	
	abstract void showLong(Integer id);
	
	public void showShort(Integer id){
		System.out.println(getObject(id));
	}
	
	public void show(Collection<Integer> ids){
		if (isEmpty(ids)){
			System.err.printf("No %ss in list", thisClassName);
		} else {
			for (Integer id: ids){
				showShort(id);
			}	
		}			
	}
	
	abstract String getName(Integer id);
	
	public void save(){
		FileOperations.saveToFile(list.values(), thisClassName);
	}
	
	protected E getObject(Integer id) throws IllegalArgumentException{
		E obj = list.get(id);
		if (obj == null) throw new IllegalArgumentException(String.format("%s %d doesn't exist", thisClassName, id));
		return obj;
	}
	
	public int getNumberOfObjects(){
		return list.size();
	}
}
