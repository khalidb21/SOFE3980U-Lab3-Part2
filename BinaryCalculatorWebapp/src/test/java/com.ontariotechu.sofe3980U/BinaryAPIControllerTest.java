package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@RunWith(SpringRunner.class)
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("10001"));
    }
	@Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","111").param("operand2","1010"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
			.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
			.andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }
    
    // New additions
    // additions with large numbers
    @Test
    public void apiAddLargeNumbers() throws Exception {
        this.mvc.perform(get("/add").param("operand1","1111").param("operand2","1"))
            .andExpect(status().isOk())
            .andExpect(content().string("10000"));
    }

    // Json format test
    @Test
    public void apiAddJsonFormat() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1","10").param("operand2","11"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("101"))
            .andExpect(jsonPath("$.operator").value("add"));
    }

    // Invalid input
    @Test
    public void apiAddWithInvalidInput() throws Exception {
        this.mvc.perform(get("/add").param("operand1","abc").param("operand2","1"))
            .andExpect(status().isOk())
            .andExpect(content().string("1")); // Invalid "abc" becomes "0"
    }

    // New Operators API Tests
    /**
     * Test The multiply API with two larger binary numbers
     */
    @Test
    public void apiMultiply3() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1","1011101010101").param("operand2","10000"))
            .andExpect(status().isOk())
            .andExpect(content().string("10111010101010000"));
        // URL for manual check: http://localhost:8080/multiply?operand1=1011101010101&operand2=10000
    }

    /**
     * Test The bitwiseOR API with all zeroes
     */
    @Test
    public void apiBitwiseOR2() throws Exception {
        this.mvc.perform(get("/or").param("operand1","000000").param("operand2","110011"))
            .andExpect(status().isOk())
            .andExpect(content().string("110011"));
        // URL for manual check: http://localhost:8080/or?operand1=000000&operand2=110011
    }

    /**
     * Test The bitwiseAND API with all ones
     */
    @Test
    public void apiBitwiseAND2() throws Exception {
        this.mvc.perform(get("/and").param("operand1","111111").param("operand2","110011"))
            .andExpect(status().isOk())
            .andExpect(content().string("110011"));
        // URL for manual check: http://localhost:8080/and?operand1=111111&operand2=110011
    }
}