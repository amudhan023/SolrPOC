package com.apple.poc;

import com.apple.solr.FacetEx;
import com.apple.solr.HighlightEx;
import com.apple.solr.SolrConn;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

public class Main {

    public static void main(String a[]) throws IOException, SolrServerException {
        SolrConn obj=new SolrConn();
//        obj.getProductDocs();
//        obj.addProductDocs();
        FacetEx facObj=new FacetEx();
       // facObj.fieldValFacet();
//        facObj.rangeFacet();
//        facObj.pivotFacet();

        HighlightEx highlight=new HighlightEx();
        highlight.highlighting();
    }
}
