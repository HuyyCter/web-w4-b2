package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Album;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class AlbumListServlet
 */
@WebServlet("")
public class AlbumListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlbumListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Album> albumList = new ArrayList<>();
        albumList.add(new Album("8601", "86 (The Band) - True Life Songs and Pictures"));
        albumList.add(new Album("pf01", "Paddlefoot - The First CD"));
        albumList.add(new Album("pf02", "Paddlefoot - The Second CD"));
        albumList.add(new Album("jr01", "Joe Rut - Genuine Wood Grained Finish"));
        
        request.setAttribute("albumList", albumList);

        String url = "/index.jsp";
        request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
