package rest;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.ReadData;
import backend.Restaurant;
import data.Data;

public class GetName extends HttpServlet {

	ReadData data;

	@Override
	public void init() {
		// TODO: run this centrally so that it only runs once and is used by all servlets
		data = new ReadData();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		// Get the passed restaurant ID
		String id = (String) request.getParameter("id");

		// Format of the response is UTF-8
		response.setCharacterEncoding("utf-8");

		// If the expression is well-formed (there is an id)
		if (id != null) {

			// Check for the name of the restaurant
			Restaurant r = data.getRestaurant(id);

			// If the restaurant exists, it is okay. Send the restaurant name
			if (r != null) {
				response.setStatus(HttpServletResponse.SC_OK);
				try {
					BufferedWriter bw = new BufferedWriter(response.getWriter());
					bw.write(r.getName());
					bw.close();
				} catch (IOException e) {
					// Not sure what to do if we can't write to the response
					e.printStackTrace();
				}
			} else {
				// Give them a 404 to indicate that the restaurant was not found
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				try {
					BufferedWriter bw = new BufferedWriter(response.getWriter());
					bw.write(id + " not found");
					bw.close();
				} catch (IOException e) {
					// Not sure what to do if we can't write to the response
					e.printStackTrace();
				}
			}
		} else {
			// Tell the requester that their syntax was invalid
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			try {
				BufferedWriter bw = new BufferedWriter(response.getWriter());
				bw.write("Bad request");
				bw.close();
			} catch (IOException e) {
				// Not sure what to do if we can't write to the response
				e.printStackTrace();
			}
		}
		
		
		
	}

}
