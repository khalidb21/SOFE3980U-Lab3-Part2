package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	@Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }

    // New additions
    @Test
    public void postAdditionWithZero() throws Exception {
        this.mvc.perform(post("/").param("operand1","0").param("operator","+").param("operand2","1010"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "1010"));
    }

    @Test
    public void postAdditionDifferentLengths() throws Exception {
        this.mvc.perform(post("/").param("operand1","1").param("operator","+").param("operand2","111"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "1000"));
    }

    @Test
    public void postAdditionEmptyOperand() throws Exception {
        this.mvc.perform(post("/").param("operand1","").param("operator","+").param("operand2","11"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "11")); // Binary class defaults empty to 0
    }

    // New Operators Web Tests
    /**
     * Test The multiply method via Web POST with equal length numbers
     */
    @Test
    public void postMultiply() throws Exception {
        this.mvc.perform(post("/").param("operand1","1010").param("operator","*").param("operand2","1000"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1010000"));
    }

    /**
     * Test The multiply method via Web POST with different lengths
     */
    @Test
    public void postMultiply2() throws Exception {
        this.mvc.perform(post("/").param("operand1","101").param("operator","*").param("operand2","1101"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "1000001"));
    }

    /**
     * Test The bitwiseOR method via Web POST with mixed bits
     */
    @Test
    public void postBitwiseOR() throws Exception {
        this.mvc.perform(post("/").param("operand1","101010").param("operator","|").param("operand2","110011"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "111011"));
    }

    /**
     * Test The bitwiseOR method via Web POST with different length binary numbers
     */
    @Test
    public void postBitwiseOR3() throws Exception {
        this.mvc.perform(post("/").param("operand1","101").param("operator","|").param("operand2","110011"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "110111"));
    }

    /**
     * Test The bitwiseAND method via Web POST with mixed bits
     */
    @Test
    public void postBitwiseAND() throws Exception {
        this.mvc.perform(post("/").param("operand1","101010").param("operator","&").param("operand2","110011"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "100010"));
    }

    /**
     * Test The bitwiseAND method via Web POST with different length binary numbers
     */
    @Test
    public void postBitwiseAND3() throws Exception {
        this.mvc.perform(post("/").param("operand1","101").param("operator","&").param("operand2","110011"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("result", "1"));
    }

}