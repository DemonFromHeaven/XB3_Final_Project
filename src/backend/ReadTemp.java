package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public class ReadTemp {
	
	public static void main(String[] args) {
		ArrayList<String> reviewSet = new ArrayList<String>();
		BufferedReader reviewReader;
		FileWriter reviewWriter;
		BufferedReader userReader;
		FileWriter userWriter;
		String line;
		
		try {
			reviewReader = new BufferedReader(new FileReader(Filepaths.REVIEW_FILEPATH));
			reviewWriter = new FileWriter(new File("data/reviewSample.json"));
			int i = 0;
			while ((line = reviewReader.readLine()) != null && i < 200000) {
				Review review = Review.fromJSON(line);
				reviewSet.add(review.getUserID());
				reviewWriter.write(line + "\n");
				++i;
			} reviewReader.close();
			reviewWriter.close();
			
			System.out.println("Review Users Sampled");
			
			Collections.sort(reviewSet);
			
			System.out.println("Review Users sorted");
			
			userReader = new BufferedReader(new FileReader(Filepaths.USER_FILEPATH));
			userWriter = new FileWriter(new File("data/userSample.json"));
			while ((line = userReader.readLine()) != null) {
				User user = User.fromJSON(line);
				if (user != null) {
					if (Collections.binarySearch(reviewSet, user.getID()) != -1) {
						userWriter.write(line + "\n");
					}
				}
			} userReader.close();
			userWriter.close();
			
		} catch (Exception e) { e.printStackTrace(); }
		
		System.out.println("DONE");
	}
}
