package fr.tbr.iam.iamweb.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.tbr.iam.iamweb.services.utils.Utils;
import fr.tbr.iamcore.dao.IdentityJdbcDAO;
import fr.tbr.iamcore.datamodel.Identity;

/**
 * Servlet implementation class Create
 */
@WebServlet(description = "The main servlet for Identity Creation", urlPatterns = { "/create" })
public final class Create extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Create() {
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
		System.out.println("got a Post request");
		Map<String, String[]> parameterMap = request.getParameterMap();
		Identity identity = new Identity();
		
		identity.setDisplayName(Utils.extractParameterValue(parameterMap, "displayName"));
		identity.setEmail(Utils.extractParameterValue(parameterMap, "emailAddress"));
		String dateAsString = Utils.extractParameterValue(parameterMap, "birthDate");
		try{
			
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsString);
			identity.setBirthDate(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		identity.setUid(fr.tbr.iamcore.utils.Utils.generateGUID());
		IdentityJdbcDAO dao = new IdentityJdbcDAO();
		dao.create(identity);
		dao.close();
		
	}

	

}
