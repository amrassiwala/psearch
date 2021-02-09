package com.psearch.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psearch.dao.ElasticSearchClient;
import com.psearch.model.ProductSearchQuery;
import com.psearch.model.SProduct;

@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Search() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductSearchQuery psq = new ProductSearchQuery();
		psq.query = request.getParameter("query");
		psq.price_range = request.getParameter("price_range");
		psq.category = request.getParameter("category");
		psq.ships_from = request.getParameter("ships_from");
		psq.supplier_name = request.getParameter("supplier_name");
		if(request.getParameter("premium")!=null)
			psq.premium = Boolean.parseBoolean(request.getParameter("premium"));
		if(request.getParameter("page")!=null)
			psq.page = Integer.parseInt(request.getParameter("page"));
		
		
		List<SProduct> plist = ElasticSearchClient.getInstance().search(psq);
		
		
		request.setAttribute("results", plist);
		request.setAttribute("psq", psq);
		
		
		request.getRequestDispatcher("index.jsp").forward(request, response); 
	}

}
