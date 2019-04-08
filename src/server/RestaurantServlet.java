package server;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.ReadDataServer;
import backend.Restaurant;

public class RestaurantServlet extends HttpServlet {

	ReadDataServer data;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		data = (ReadDataServer) getServletContext().getAttribute("data");
		
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
					bw.write(r.toJSON());
					bw.close();
				} catch (IOException e) {
					// Not sure what to do if we can't write to the response
					e.printStackTrace();
				}
			} else {
				// Give them a 404 to indicate that the restaurant was not found
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
		} else {
			// Tell the requester that their syntax was invalid
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}

}
