package com.brainacad.lms.fastivets.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;

public class FileOperations<E> {
	
	public static<E> void saveToFile(Collection<E> data, String thisClassName) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(String.format("%s.ser", thisClassName)))) {
			for (Object obj: data){
				out.writeObject(obj);
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static <E> TreeMap<Integer, E> readFromFile(String thisClassName) {
		TreeMap<Integer, E> list = new TreeMap<>();
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(String.format("%s.ser", thisClassName)))) {						
			while (true){
				@SuppressWarnings("unchecked")
				E e = (E)in.readObject();
				list.put((Integer)e.getClass().getMethod("getID").invoke(e), e);
			}
		} catch (IOException e) {
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void saveGradeBookToFile(String file, String data){
		try (PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file))); Scanner scan = new Scanner(data).useDelimiter("\n")){
			while (scan.hasNext()){
				out.println(scan.next());
			}		
			out.flush();
			System.out.printf("File name: %s\nFile successfully saved\n", file);
		} catch (IOException e) {
			System.err.printf("Unable to save gradebook to file %s\n", file);
			e.printStackTrace();
		}
	}
}
