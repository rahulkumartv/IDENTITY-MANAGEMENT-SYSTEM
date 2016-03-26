package fr.tbr.iam.iamweb.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.tbr.iam.iamweb.services.utils.Utils;
import fr.tbr.iamcore.dao.IdentityJdbcDAO;
import fr.tbr.iamcore.datamodel.Identity;

@WebServlet(description = "The main servlet for Identity updation", urlPatterns = { "/search" })
public final class Search extends HttpServlet {
	 public Search() {
	        // TODO Auto-generated constructor stub
	    }

		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			Map<String, String[]> parameterMap = request.getParameterMap();
			Identity identity = new Identity();
			
			identity.setDisplayName(Utils.extractParameterValue(parameterMap, "displayName"));
			identity.setEmail(Utils.extractParameterValue(parameterMap, "emailAddress"));			
			IdentityJdbcDAO dao = new IdentityJdbcDAO();
			List<Identity> searchRslt = dao.search(identity);
			dao.close();
			request.setAttribute("searchresult", searchRslt);
			getServletConfig().getServletContext().getRequestDispatcher("/list.jsp").forward(request,response);
		}


}
