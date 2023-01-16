import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;


public class FacePamphlet extends Program
					implements FacePamphletConstants {
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		drawInteractors();
		
		add(canvas);
		
		database = new FacePamphletDatabase();
		
		currentProfile = null;
		
		addActionListeners();
		
	}
    
  
/*
 * This method is responsible for drawing text fields and buttons on the canvas
 */
    private void drawInteractors() {
    	JLabel name = new JLabel("Name");
		add(name,NORTH);
		
		inputName = new JTextField(TEXT_FIELD_SIZE);
		add(inputName,NORTH);
		
		addButton = new JButton("Add");
		add(addButton,NORTH);
		
		deleteButton = new JButton("Delete");
		add(deleteButton,NORTH);
		
		lookupButton = new JButton("Lookup");
		add(lookupButton,NORTH);
		
		
		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField,WEST);
		statusField.addActionListener(this);
		
		statusButton = new JButton("Change Status");
		add(statusButton,WEST);
		
		
		emptySpace();
		
		pictureText = new JTextField(TEXT_FIELD_SIZE);
		add(pictureText,WEST);
		pictureText.addActionListener(this);

		
		pictureButton = new JButton("Change Picture");
		add(pictureButton,WEST);
		
		emptySpace();
		
		friendText = new JTextField(TEXT_FIELD_SIZE);
		add(friendText,WEST);
		friendText.addActionListener(this);
		
		friendButton = new JButton("Add Friend");
		add(friendButton,WEST);
		
		checkGender();
    	
		
	}

    private void checkGender() {
		maleButton = new JRadioButton("Male");
		add(maleButton,WEST);
		
		femaleButton = new JRadioButton("Female");
		add(femaleButton,WEST);
		
		ButtonGroup group = new ButtonGroup();
		group.add(maleButton);
		group.add(femaleButton);
		
}


/*
 * This method separates buttons, which are located on the left side of the canvas
 */
	private void emptySpace() {
		JLabel emptyLabel = new JLabel(EMPTY_LABEL_TEXT);
		add(emptyLabel,WEST);
	}
	
	
	


	/**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == addButton && !inputName.getText().equals("")) {
    		if(!database.containsProfile(inputName.getText())){
    			FacePamphletProfile profile = new FacePamphletProfile(inputName.getText());
    			currentProfile = profile;
    			database.addProfile(profile);
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("New profile created");
    		}else{
    			FacePamphletProfile profile = database.getProfile(inputName.getText());
    			currentProfile = profile;
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("A profile with the name " + profile.getName() + " aldready exists");
    		}
    	}
    	
    	if (e.getSource() == deleteButton && !inputName.getText().equals("")){
    		currentProfile = null;
    		if(database.containsProfile(inputName.getText())){
    			database.deleteProfile(inputName.getText());
    			canvas.removeProfile();
    			canvas.showMessage("Profile of " + inputName.getText() + " deleted");
    			
    		}else{
    			canvas.showMessage("A profile with name " + inputName.getText()+  " doesn't exists");
    		}
    	}
    	
    	if(e.getSource() == lookupButton && !inputName.getText().equals("")){
    		if(database.containsProfile(inputName.getText())){
    			FacePamphletProfile profile = database.getProfile(inputName.getText());
    			currentProfile = profile;
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Displaying " + currentProfile.getName());			
    		}else{
    			canvas.removeProfile();
    			canvas.showMessage("Profile with the name "  + inputName.getText() + " doesn't exists");
    			currentProfile = null;  			
    		}
    	}

    	if(e.getSource() == statusField || e.getSource() == statusButton){
    		if(currentProfile != null){
    			currentProfile.setStatus(statusField.getText());
    			canvas.displayProfile(currentProfile);
    			canvas.showMessage("Status updated to " + currentProfile.getStatus());
    		}else{
    			canvas.showMessage("Please select a profile to change status");
    		}
    		
    	}
    	
    	if(e.getSource() == pictureText || e.getSource() == pictureButton){
    		if (currentProfile != null) {
				GImage image = null; 
				try { 
					image = new GImage(pictureText.getText()); 
				} catch (ErrorException ex) { 
					canvas.showMessage("Unable to open image file: " + pictureText.getText()); 
				} 
				
				if (image != null) {
					currentProfile.setImage(image);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Picture updated");
				}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
    	}


    	
    	if(e.getSource() == friendText && !friendText.getText().equals("") || e.getSource() == friendButton){
    		if(currentProfile != null){
    			if(database.containsProfile(friendText.getText())){
    				if(currentProfile.addFriend(friendText.getText())){
    					currentProfile.addFriend(friendText.getText());
    					FacePamphletProfile friend = database.getProfile(friendText.getText());
    					friend.addFriend(currentProfile.getName());
    					canvas.showMessage(friendText.getText() + " added as a friend");
    					canvas.displayProfile(currentProfile);
    				}else{
    					canvas.showMessage(currentProfile.getName() + " aldready has " + friendText.getText() + " as a friend");
    				}
    			}else{
    				canvas.showMessage(friendText.getText() + " does not exists");
    			}
    			}
    		}
    }
  
    	


	private JButton addButton;
	private JTextField inputName;
	private JButton deleteButton;
	private JButton lookupButton;
	private JButton statusButton;
	private JTextField statusField;
	private JButton friendButton;
	private JButton pictureButton;
	private FacePamphletCanvas canvas = new FacePamphletCanvas();;
	private FacePamphletDatabase database;
	private FacePamphletProfile currentProfile;
	private JTextField pictureText;
	private JTextField friendText;
	private JRadioButton maleButton;
	private JRadioButton femaleButton;
}
