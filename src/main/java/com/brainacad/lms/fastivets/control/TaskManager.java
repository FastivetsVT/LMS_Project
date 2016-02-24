package com.brainacad.lms.fastivets.control;

import com.brainacad.lms.fastivets.model.Course.Task;
import com.brainacad.lms.fastivets.util.FileOperations;
import com.brainacad.lms.fastivets.util.QuitException;

public class TaskManager extends Manager<Task> {
	private static TaskManager instance = new TaskManager();

	{
		thisClassName = Task.class.getSimpleName();
		list = FileOperations.readFromFile(thisClassName);
	}
	 
	private TaskManager(){}
	
    public static TaskManager getInstance(){
        return instance;
    }
    
    @Override
	public void createObject() throws QuitException {
    	Integer id = getNewID();
		Task newTask = new Task(id);
		changeTitle(newTask);
		changeCourse(newTask);		
		finishCreation(id, newTask);
		showLong(id);
    }
    
	private void changeTitle (Task thisTask) throws QuitException {
		while (true){
			try {
				System.out.print("Title: ");
				String title = scanInput.nextLine().trim();			
				if (isEmpty(title)) throw new IllegalArgumentException("Title shouldn't be empty");
				QuitException.check(title);
				thisTask.setTitle(title);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another title.\n", e.getMessage());
			}	
		}	 
	}
	
	private void changeCourse (Task thisTask) throws QuitException {
		while (true){
			try {
				System.out.print("Course: ");
				String courseString = scanInput.nextLine();
				QuitException.check(courseString);
				if (isEmpty(courseString = courseString.replaceAll("\\D+", ""))) throw new IllegalArgumentException("Course shouldn't be empty");
				Integer courseID = new Integer (courseString);
				CourseManager.getInstance().addTaskToCourse(courseID, thisTask.getID());
				thisTask.setCourse(courseID);
				break;
			} catch (IllegalArgumentException e){
				System.err.printf("%s. Please, enter another course.\n", e.getMessage());
			}	
		}	 
	}
    
	@Override
	public void showLong(Integer taskID){
		try {			
			Task task = getObject(taskID);
			System.out.printf("ID: %d\nTitle: %s\nCourse: ", task.getID(), task.getTitle());
			CourseManager.getInstance().showShort(task.getCourse());
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	protected String getName(Integer taskID){
		return getObject(taskID).getTitle();
	}

}
