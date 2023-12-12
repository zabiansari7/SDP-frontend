package de.srh.toolify.frontend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Converter {
	
	public Object convet(Object object, Class<?> c) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(object, c);
	}

}
