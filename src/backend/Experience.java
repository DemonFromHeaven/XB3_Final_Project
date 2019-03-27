package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A library of functions for reading and writing experience to storage,
 * and storing it as an ADT.
 * This is so that the application can keep track of this list between use
 * sessions.
 * @author David Thompson
 */
public class Experience {

	// Holds which restaurants this user has been to
	private ArrayList<String> beenTo;
	private String filepath;
	
	/**
	 * Make a new Experience object give a filepath with the information
	 * @param filepath
	 */
	public Experience(String filepath) {
		this.filepath = filepath;
		this.beenTo = readExperience();
	}
	
	/**
	 * Get which restaurants this user has been to
	 * @return An array list of the restaurant IDs of the restaurants
	 * this person has been to.
	 */
	public ArrayList<String> getExperience() {
		return beenTo;
	}
	
	/**
	 * Write a newly visited restaurant into this experience.
	 * @param experienced The new restaurant that was visited
	 */
	public void addExperience(Restaurant experienced) {
		beenTo.add(experienced.getID());
	}
	
	/**
	 * Write the experience to storage.
	 * @throws IOException if the write to storage fails
	 */
	public void writeExperience() throws IOException {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
			for (String s: beenTo) {
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch(IOException ioe) {
			throw new IOException("Unable to write user data to disk");
		}
	}
	
	/**
	 * Read the restaurants that the user has been to from storage.
	 * Returns an empty ArrayList if the file does not exist
	 * @return An array of Restaurants the user has been to
	 */
	private ArrayList<String> readExperience() {
		ArrayList<String> exp = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			String line;
			while(!(line = br.readLine()).equals("")) {
				exp.add(line);
			}
			br.close();
		} catch (IOException e) {
			System.err.println("Unable to read the input file.");
			e.printStackTrace();
		}
		return exp;
	}
	
}
