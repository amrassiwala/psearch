package com.psearch.dao;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.psearch.model.SProduct;

public class ImportHelper {

	public void importData(String data) {
		try {
			Gson gson = new Gson();
			List<SProduct> l = Arrays.asList(gson.fromJson(data, SProduct[].class));
			ElasticSearchClient.getInstance().importData(l);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
