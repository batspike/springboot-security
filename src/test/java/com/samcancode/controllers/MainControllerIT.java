package com.samcancode.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
class MainControllerIT {
	@Autowired
	WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.apply(springSecurity())
				.build();
	}

	@Test
	void testUserPage() throws Exception {
		mockMvc.perform(get("/user").with(httpBasic("user", "user")))
				.andExpect(status().isOk())
				.andExpect(view().name("result"))
				.andExpect(model().attributeExists("message"))
				;
	}

//	@Test
//	void testAdminPage() throws Exception {
//		mockMvc.perform(get("/admin").with(httpBasic("admin", "admin")))
//				.andExpect(status().isOk())
//				.andExpect(view().name("result"))
//				.andExpect(model().attributeExists("message"))
//				;
//	}
//
//	@Test
//	void testCustPage() throws Exception {
//		mockMvc.perform(get("/customer").with(httpBasic("cust", "cust")))
//				.andExpect(status().isOk())
//				.andExpect(view().name("result"))
//				.andExpect(model().attributeExists("message"))
//				;
//	}

}
