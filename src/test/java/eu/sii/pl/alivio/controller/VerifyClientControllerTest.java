package eu.sii.pl.alivio.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VerifyClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rendersForm() throws Exception {
        //when
        MockHttpServletRequestBuilder createForm = get("/form");

        //then
        mockMvc.perform(createForm)
                .andExpect(content().string(containsString("Enter details")));
    }

    @Test
    public void checkDebtorInfoWhenNameMissingThenFailure() throws Exception {

        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("lastName", "Foo")
                .param("ssn", "123-456-789");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkDebtorInfoWhenNameTooShortThenFailure() throws Exception {
        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("firstName", "F")
                .param("lastName", "Bar")
                .param("ssn", "123-456-789");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkDebtorInfoWhenLastNameMissingThenFailure() throws Exception {
        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("firstName", "Foo")
                .param("ssn", "123-456-789");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkDebtorInfoWhenLastNameTooShortThenFailure() throws Exception {
        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("firstName", "Foo")
                .param("lastName", "B")
                .param("ssn", "123-456-789");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkDebtorInfoWhenSsnMissingThenFailure() throws Exception {
        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("firstName", "Foo")
                .param("lastName", "Bar");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkDebtorInfoWhenSsnTooShortThenFailure() throws Exception {
        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("firstName", "Foo")
                .param("lastName", "Bar")
                .param("ssn", "123-456");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasErrors());
    }

    @Test
    public void checkDebtorInfoWhenValidRequestThenSuccess() throws Exception {
        //when
        MockHttpServletRequestBuilder createDebtor = post("/form")
                .param("firstName", "Joe")
                .param("lastName", "Doe")
                .param("ssn", "980-122-111");
        //then
        mockMvc.perform(createDebtor)
                .andExpect(model().hasNoErrors());
    }
}