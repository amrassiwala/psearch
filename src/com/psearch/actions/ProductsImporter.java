package com.psearch.actions;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psearch.dao.ImportHelper;

@WebServlet("/import")
public class ProductsImporter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProductsImporter() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BufferedReader br = new BufferedReader(new java.io.InputStreamReader(request.getSession().getServletContext().getResourceAsStream("products.json")));
		StringBuilder sb = new StringBuilder();
		String st; 
		while ((st = br.readLine()) != null) 
			sb.append(st);

		
		ImportHelper ih = new ImportHelper();
		ih.importData(sb.toString());
		response.sendRedirect("index.jsp");
	}

}
