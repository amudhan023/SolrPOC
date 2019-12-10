package com.apple.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacetEx {

    public void fieldValFacet() throws IOException, SolrServerException {
        final SolrClient client = SolrConn.getSolrClient();

        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", "*:*");
        queryParamMap.put("facet", "true");
        queryParamMap.put("facet.field", "genre_str");
        queryParamMap.put("rows", "0");

        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response = client.query("films", queryParams);

        System.out.println(response.getResults().toString());

        response.getFacetFields().forEach(System.out::println);

        final SolrDocumentList documents = response.getResults();

        System.out.println("Found " + documents.getNumFound() + " documents");

//        List<FacetField> facetFields = response.getFacetFields();
//        for (int i = 0; i > facetFields.size(); i++) {
//            FacetField facetField = facetFields.get(i);
//            List<Count> facetInfo = facetField.getValues();
//            for (Count facetInstance : facetInfo) {
//                System.out.println(facetInstance.getName() + " : " +
//                        facetInstance.getCount() + " [drilldown qry:" +
//                        facetInstance.getAsFilterQuery());
//            }
//
//        }

    }

    public void rangeFacet() throws IOException, SolrServerException {
        final SolrClient client = SolrConn.getSolrClient();

        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", "*:*");
        queryParamMap.put("rows", "0");
        queryParamMap.put("facet", "true");
        queryParamMap.put("facet.range", "initial_release_date");
        queryParamMap.put("facet.range.start", "NOW-20YEAR");
        queryParamMap.put("facet.range.end", "NOW");
        queryParamMap.put("facet.range.gap", "+1YEAR");


        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response = client.query("films", queryParams);
        response.getResponse().asShallowMap().entrySet().forEach(System.out::println);
//        System.out.println(response.getResponse());
//        System.out.println(response.getResponse().findRecursive("facet_counts"));
//        System.out.println(response.getResults().toString());
//        System.out.println(" Range facets" + response.getFacetRanges());
//        response.getFacetFields().forEach(System.out::println);
//        System.out.println(" Range facets" + response.getFacetDates());
//        response.getFacetDates().forEach(System.out::println);
//        System.out.println(response.getGroupResponse());
//        response.getFacetQuery().entrySet().forEach(System.out::println);

        final SolrDocumentList documents = response.getResults();
//        documents.stream().forEach(System.out::println);

    }



    public void pivotFacet() throws IOException, SolrServerException {
        final SolrClient client = SolrConn.getSolrClient();

        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", "*:*");
        queryParamMap.put("rows", "0");
        queryParamMap.put("facet", "true");
        queryParamMap.put("facet.pivot", "cat,popularity,inStock");
        queryParamMap.put("facet.field", "cat");
        queryParamMap.put("facet.pivot.mincount", "2");
        queryParamMap.put("facet.limit", "5");


        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response = client.query("techproducts", queryParams);
        response.getResponse().asShallowMap().entrySet().forEach(System.out::println);

        final SolrDocumentList documents = response.getResults();
//        documents.stream().forEach(System.out::println);

    }

}
