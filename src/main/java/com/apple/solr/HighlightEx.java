package com.apple.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;

import java.io.IOException;

public class HighlightEx {

    public void highlighting() throws IOException, SolrServerException {

//        String query = request.query;

        SolrQuery queryParams = new SolrQuery();

        queryParams.set(CommonParams.Q, "Action");


//Setting Highlighting parameters using Solrj

        //this will set the all the highlighting parameter to default values

        queryParams.setHighlight(true).setHighlightSnippets(1);
        queryParams.setParam("hl.fl", "*");

        queryParams.setParam("hl.fragsize", "0");


        SolrClient client = SolrConn.getSolrClient();

        final QueryResponse response = client.query("films", queryParams);
        response.getResponse().asShallowMap().entrySet().forEach(System.out::println);

        final SolrDocumentList documents = response.getResults();

    }
}
