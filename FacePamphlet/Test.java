

import acm.graphics.GImage;
import acm.program.ConsoleProgram;

public class Test extends ConsoleProgram{
	private FacePamphletProfile profile = new FacePamphletProfile("Saba");
	private GImage image;
	public void run(){
		profile.setImage(image);
		profile.setStatus("Programming");
		
		profile.addFriend("	Kawhi Leonard");
		profile.addFriend("Saba");
		
		println(profile.toString());
		
		
	}

}
