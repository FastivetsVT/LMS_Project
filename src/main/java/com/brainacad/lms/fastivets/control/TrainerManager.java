package com.brainacad.lms.fastivets.control;

import com.brainacad.lms.fastivets.model.Trainer;
import com.brainacad.lms.fastivets.util.FileOperations;
import com.brainacad.lms.fastivets.util.QuitException;

public class TrainerManager extends PersonManager {
	private static TrainerManager instance = new TrainerManager();

	{
		thisClassName = Trainer.class.getSimpleName();
		list = FileOperations.readFromFile(thisClassName);
	}
	
	private TrainerManager(){}
	
    public static TrainerManager getInstance(){
        return (TrainerManager)instance;
    }
    
    @Override
	public void createObject() throws QuitException {
		int id = getNewID();
		Trainer newTrainer = new Trainer(id);		
		changeFirstName(newTrainer);
		changeLastName(newTrainer);
		changeCourses(newTrainer);
		finishCreation(id, newTrainer);
		showLong(id);		
	}
	
	@Override
	public void showLong (Integer trainerID){
		try {
			Trainer trainer = (Trainer)getObject(trainerID);
			System.out.printf("ID: %d\nName: %s\nCourse(s):\n", trainer.getID(), trainer.getName());
			CourseManager.getInstance().show(trainer.getCourses());
		} catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
}
