/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;


public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	private GLabel name;
	GLabel message;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		message = new GLabel("");
		add(message, (getWidth()), getHeight());
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message = new GLabel(msg,getWidth()/2,getHeight() - BOTTOM_MESSAGE_MARGIN);
		message.setFont(MESSAGE_FONT);
		add(message);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		profileName = profile.getName();
		profileStatus = profile.getStatus();
		name = new GLabel(profile.getName(), LEFT_MARGIN, TOP_MARGIN);
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name);

		remover();
		
		name = new GLabel(profile.getName(), LEFT_MARGIN, TOP_MARGIN);
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		add(name);
		
		if(profile.getImage() == null){
			GRect picture = new GRect (LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
			GLabel pictureText = new GLabel("No Image", LEFT_MARGIN + picture.getWidth()/2 - LEFT_MARGIN * 2, TOP_MARGIN+IMAGE_MARGIN + picture.getHeight()/2);
			pictureText.setFont(PROFILE_IMAGE_FONT);
			add(picture);
			add(pictureText);
		}else{
			GImage image = profile.getImage();
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			image.setLocation(LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
			add(image);
		}
		
		
		drawStatus();
		
		GLabel friends = new GLabel("Friends: ", getWidth()/2, TOP_MARGIN+IMAGE_MARGIN);
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends);
		Iterator<String> friends1 = profile.getFriends();
		int count = 1;
		while(friends1.hasNext()){
			friendsList = new GLabel(friends1.next());
			friendsList.setLocation(getWidth()/2, TOP_MARGIN + IMAGE_MARGIN + friendsList.getAscent() * count);
			add(friendsList);
		}
		
	}
	



/*
 * This method is responsible for remove the message on the canvas when its no more needed,
 * because messages not to merge with each other
 */
	private void remover() {
		removeAll();
		message.setLabel("");
		add(message);
	}





/*
 * This method draws status on the canvas, status drawn based on profiles state, whether
 * it's blank or not, if it's blank, method draws "No current status", if not method draws
 * profile name and the status, which this profile is doing.
 */
	public void drawStatus() {
		if(!profileStatus.equals("")){
			status = new GLabel(profileName + " is " + profileStatus);
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
		}else{
			status = new GLabel("No current status");
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + status.getAscent());
		}
	}
	




/*
 * This method is responsible to remove profile, when user clicks delete button, it is used
 * to messages not to merge with each other and clear the canvas	
 */
	public void removeProfile(){
		removeAll();
		message.setLabel("");
		add(message);
	}



	private GLabel status;
	GLabel friendsList;
	String profileFriends;
	private String profileStatus;
	private String profileName;

}