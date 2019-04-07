package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.ReadData;
import backend.ReadDataServer;
import backend.Restaurant;

public class SearchServlet extends HttpServlet {

	// Default search radius
	private final int MAX_RESULTS = 10;

	ReadDataServer data;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		data = (ReadDataServer) getServletContext().getAttribute("data");

		// Get the passed parameters
		String query = (String) request.getParameter("query");
		int max = MAX_RESULTS;

		// Try to read in the max, just use default if it fails
		try {
			max = Integer.parseInt(request.getParameter("max"));
		} catch (NumberFormatException e) {
		}

		// Format of the response is UTF-8
		response.setCharacterEncoding("utf-8");

		// If there is a query
		if (query != null) {

			// Get the search results
			ArrayList<Restaurant> results = data.searchByName(query, max);

			// If the restaurant exists, it is okay. Send the restaurant name
			if (results.size() != 0) {
				response.setStatus(HttpServletResponse.SC_OK);
				try {
					BufferedWriter bw = new BufferedWriter(response.getWriter());
					for (Restaurant r : results) {
						bw.write(r.toJSON());
						bw.write("\n");
					}
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
