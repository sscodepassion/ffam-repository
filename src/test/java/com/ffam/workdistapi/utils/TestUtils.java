package com.ffam.workdistapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
