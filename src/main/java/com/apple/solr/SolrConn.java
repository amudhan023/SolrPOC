package com.apple.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SolrConn {
    public static HttpSolrClient getSolrClient() {
        //  SolrClient solr = new CloudSolrClient.Builder().withSolrUrl("http://localhost:8983/solr").build();
        String solrUrl = "http://localhost:8983/solr";
        return new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    public void getProductDocs() throws IOException, SolrServerException {
        final SolrClient client = getSolrClient();

        final Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("q", "*:*");
        queryParamMap.put("fl", "id, name");
        queryParamMap.put("sort", "id asc");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        final QueryResponse response = client.query("techproducts", queryParams);
        final SolrDocumentList documents = response.getResults();

        System.out.println("Found " + documents.getNumFound() + " documents");
        for (SolrDocument document : documents) {
            final String id = (String) document.getFirstValue("id");
            final String name = (String) document.getFirstValue("name");

            System.out.println("id: " + id + "; name: " + name);
        }
    }

    public void addProductDocs() throws IOException, SolrServerException {
        final SolrClient client = getSolrClient();

        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", UUID.randomUUID().toString());
        doc.addField("name", "Amazon Kindle Paperwhite");

        final UpdateResponse updateResponse = client.add("techproducts", doc);

        // Indexed documents must be committed
        client.commit("techproducts");
        System.out.println(" Added document ");
    }


    public static void updateDocs() throws SolrServerException, IOException {
        //Preparing the Solr client
        final SolrClient client = getSolrClient();
        //Preparing the Solr document
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "002");
        doc.addField("name", "Fire Tablet");

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
        updateRequest.add(doc);

        UpdateResponse rsp = updateRequest.process(client, "techproducts");
        System.out.println("Documents Updated");
    }

    public static void deleteDocs() throws SolrServerException, IOException {
        //Preparing the Solr client
        final SolrClient client = getSolrClient();
        //Preparing the Solr document
        SolrInputDocument doc = new SolrInputDocument();
        //Deleting the documents from Solr
        client.deleteByQuery("*");
        //Saving the document
        client.commit();
        System.out.println("Documents deleted");
    }

    public static void main(String a[]) throws IOException, SolrServerException {
        SolrConn obj = new SolrConn();
        obj.getProductDocs();
        obj.addProductDocs();
    }
}
