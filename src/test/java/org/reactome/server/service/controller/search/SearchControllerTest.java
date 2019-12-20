package org.reactome.server.service.controller.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactome.server.service.utils.BaseTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.HashMap;

import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/test/resources-test/mvc-dispatcher-servlet-test.xml"})
@WebAppConfiguration
public class SearchControllerTest extends BaseTest {

    @Test
    public void spellcheckerSuggestions() throws Exception {

        mvcGetResult("/search/spellcheck/", "application/json;Charset=UTF-8", "query", "matablism");
    }

    @Test
    public void suggesterSuggestions() throws Exception {

        mvcGetResult("/search/suggest/", "application/json;Charset=UTF-8", "query", "cell");
    }

    @Test
    public void facet() throws Exception {
        mvcGetResult("/search/facet", "application/json;charset=UTF-8");
    }

    @Test
    public void facet_type() throws Exception {

        // mvcGetResult("/search/facet_query","application/json;Charset=UTF-8");

        //todo parameter is a list
        this.getMockMvc().perform(get("/search/facet_query")
                .param("query", "PTEN")
                .param("species", "Homo sapiens")
                .param("species", "Rattus norvegicus")
                .param("types", "Pathway")
                .param("compartments", "cotosol"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getResult() throws Exception {


//        MultiValuedMap<String, String> params = new ArrayListValuedHashMap<>();
//        params.put("query","enzme");
//        params.put("species","Homo sapiens");
//        params.put("species","Rattus norvegicus");
//        params.put("types","Pathway");
//        params.put("cluster","ture");

        //todo parameter is a list
        this.getMockMvc().perform(get("/search/query")
                .param("query", "enzyme")
                .param("species", "Homo sapiens")
                .param("species", "Rattus norvegicus")
                .param("types", "Pathway")
                .param("cluster", "true"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getFireworksResult() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("query", "RAF");
        params.put("species", "Homo sapiens");

        mvcGetResult("/search/fireworks", "application/json;charset=UTF-8", params);
    }

    @Test
    public void fireworksFlagging() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("query", "PTEN");
        params.put("species", "Homo sapiens");

        mvcGetResult("/search/fireworks/flag", "application/json;charset=UTF-8", params);
    }

    @Test
    public void getDiagramResult() throws Exception {

        mvcGetResult("/search/diagram/R-HSA-9612973", "application/json;charset=UTF-8", "query", "PTEN");
    }

    @Test
    public void getDiagramOccurrences() throws Exception {

        mvcGetResult("/search/diagram/R-HSA-9612973/occurrences/R-HSA-5672817", "application/json;Charset=UTF-8");
    }

    @Test
    public void getEntitiesInDiagramForIdentifier() throws Exception {

        mvcGetResult("/search/diagram/R-HSA-1632852/flag", "application/json;Charset=UTF-8", "query", "ATG13");
    }

    //##################### API Ignored  #####################//
    @Test
    public void diagramSearchSummary() throws Exception {

        Map<String, Object> params = new HashMap<>();
        params.put("query", "KIF");
        params.put("species", "Homo sapiens");
        params.put("diagram", "R-HSA-8848021");

        mvcGetResult("/search/diagram/summary", "application/json;Charset=UTF-8", params);
    }
}