/*
 * Copyright 2015 OpenCB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opencb.cellbase.mongodb.impl;

import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.opencb.biodata.models.variant.Variant;
import org.opencb.cellbase.core.CellBaseConfiguration;
import org.opencb.cellbase.core.api.DBAdaptorFactory;
import org.opencb.cellbase.core.api.VariantDBAdaptor;
import org.opencb.cellbase.mongodb.GenericMongoDBAdaptorTest;
import org.opencb.commons.datastore.core.Query;
import org.opencb.commons.datastore.core.QueryOptions;
import org.opencb.commons.datastore.core.QueryResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by imedina on 12/02/16.
 */
public class VariantMongoDBAdaptorTest extends GenericMongoDBAdaptorTest {

//    private static DBAdaptorFactory dbAdaptorFactory;

//    @Ignore
//    @Test
//    public VariantMongoDBAdaptorTest() {
//        try {
//            Path inputPath = Paths.get(getClass().getResource("/configuration.json").toURI());
//            CellBaseConfiguration cellBaseConfiguration = CellBaseConfiguration.load(new FileInputStream(inputPath.toFile()));
//            dbAdaptorFactory = new MongoDBAdaptorFactory(cellBaseConfiguration);
//        } catch (URISyntaxException | IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    @BeforeClass
    public static void setUp() throws Exception {
    }

    // TODO: to be finished - properly implemented
    @Ignore
    @Test
    public void testGetFunctionalScoreVariant() throws Exception {
        VariantDBAdaptor variationDBAdaptor = dbAdaptorFactory.getVariationDBAdaptor("hsapiens", "GRCh37");
        QueryResult functionalScoreVariant = variationDBAdaptor.getFunctionalScoreVariant(Variant.parseVariant("10:130862563:A:G"),
                new QueryOptions());
    }

    @Test
    public void testGet() {
        VariantDBAdaptor variationDBAdaptor = dbAdaptorFactory.getVariationDBAdaptor("hsapiens", "GRCh37");
        QueryResult<Variant> result = variationDBAdaptor
                .get(new Query(VariantDBAdaptor.QueryParams.GENE.key(), "ATRNL1"), new QueryOptions());
        assertEquals(result.getNumResults(), 1);

        QueryResult<Variant> resultENSEMBLGene = variationDBAdaptor
                .get(new Query(VariantDBAdaptor.QueryParams.GENE.key(), "ENSG00000107518"), new QueryOptions());
        assertEquals(result.getResult(), resultENSEMBLGene.getResult());

        // ENSEMBL transcript ids are also allowed for the GENE query parameter - this was done on purpose
        QueryResult<Variant> resultENSEMBLTranscript = variationDBAdaptor
                .get(new Query(VariantDBAdaptor.QueryParams.GENE.key(), "ENST00000424738"), new QueryOptions());
        assertEquals(result.getResult(), resultENSEMBLTranscript.getResult());
    }

    @Test
    public void testNativeGet() {
        VariantDBAdaptor variationDBAdaptor = dbAdaptorFactory.getVariationDBAdaptor("hsapiens", "GRCh37");
        QueryResult variantQueryResult = variationDBAdaptor.nativeGet(new Query(VariantDBAdaptor.QueryParams.ID.key(), "rs666"),
                new QueryOptions());
        assertEquals(variantQueryResult.getNumResults(), 1);
        assertEquals(((Document) variantQueryResult.getResult().get(0)).get("chromosome"), "17");
        assertEquals(((Document) variantQueryResult.getResult().get(0)).get("start"), new Integer(64224271));
        assertEquals(((Document) variantQueryResult.getResult().get(0)).get("reference"), "C");
        assertEquals(((Document) variantQueryResult.getResult().get(0)).get("alternate"), "T");
    }

    @Test
    public void testGetByVariant() {
        VariantDBAdaptor variationDBAdaptor = dbAdaptorFactory.getVariationDBAdaptor("hsapiens", "GRCh37");
        QueryResult<Variant> variantQueryResult
                = variationDBAdaptor.getByVariant(Variant.parseVariant("10:118187036:T:C"), new QueryOptions());
        assertEquals(variantQueryResult.getNumResults(), 1);
        assertEquals(variantQueryResult.getResult().get(0).getChromosome(), "10");
        assertEquals(variantQueryResult.getResult().get(0).getStart(), new Integer(118187036));
        assertEquals(variantQueryResult.getResult().get(0).getReference(), "T");
        assertEquals(variantQueryResult.getResult().get(0).getAlternate(), "C");
        assertEquals(variantQueryResult.getResult().get(0).getId(), "rs191078597");
    }
}