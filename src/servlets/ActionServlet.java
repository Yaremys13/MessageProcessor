package servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import utilities.Utilities;
import model.Message;



public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType( "text/html; charset=iso-8859-1" );
		
		//Getting root for data file path
		String root = getServletContext().getRealPath("");
		PrintWriter out = response.getWriter();

		// Getting data coming from form
		Message message = new Message();
		message.setUserId(request.getParameter("userId"));
		message.setCurrencyFrom(request.getParameter("currencyFrom"));
		message.setCurrencyTo(request.getParameter("currencyTo"));
		message.setAmountSell((!request.getParameter("amountSell").equals("")?Double.parseDouble(request.getParameter("amountSell")):0.0));
		message.setAmountBuy((!request.getParameter("amountBuy").equals("")?Double.parseDouble(request.getParameter("amountBuy")):0.0));
		message.setRate((!request.getParameter("rate").equals("")?Double.parseDouble(request.getParameter("rate")):0.0));
		message.setTimePlaced("14-JAN-15 10:27:44");
		message.setOriginatingCountry(request.getParameter("originatingCountry"));
		
		//Creating JSON Object to be load up on the data file
		JSONObject obj = new JSONObject();
		obj.put("userId", message.getUserId());
		obj.put("currencyFrom", message.getCurrencyFrom());
		obj.put("currencyTo", message.getCurrencyTo());
		obj.put("amountSell", message.getAmountSell());
		obj.put("amountBuy", message.getAmountBuy());
		obj.put("rate",message.getRate());
		obj.put("timePlaced", message.getTimePlaced());
		obj.put("originatingCountry", message.getOriginatingCountry());
		
		String newContent = obj.toJSONString();
		
		//Bringing original file
		String originalFile = Utilities.bringingOriginalFile(root,out);
		
		//Adding new data into the file
		Utilities.writingFile(root, originalFile, newContent,out);
		
		//Processing data
		Utilities.processingFile(root, out);
		

	}

}
