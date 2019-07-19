package com.ffam.workdistapi.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {
	
	public static final String ERR001 = "ERR-001";
	public static final String ERR_DESCRIPTION_NO_AGENTS_AVLBL_TO_ASSIGN_TASKS = "No Agents available to work on the Task";
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
