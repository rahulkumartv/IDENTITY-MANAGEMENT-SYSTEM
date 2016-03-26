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
import fr.tbr.iamcore.dao.exceptions.DaoUpdateException;
import fr.tbr.iamcore.datamodel.Identity;

@WebServlet(description = "The main servlet for Identity updation", urlPatterns = { "/update" })
public final class Update extends HttpServlet {
	 public Update() {
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
			identity.setUid(Utils.extractParameterValue(parameterMap, "Uid"));
			String dateAsString = Utils.extractParameterValue(parameterMap, "birthDate");
			try{
				
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);
				identity.setBirthDate(date);
			}catch(Exception e){
				e.printStackTrace();
			}
			IdentityJdbcDAO dao = new IdentityJdbcDAO();
			try {
				dao.update(identity);
			} catch (DaoUpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.close();
			
		}

}
