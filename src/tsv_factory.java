package cs635m;//package it.unimi.di.mg4j;
import com.sun.org.apache.xpath.internal.operations.String;
import it.unimi.di.mg4j.document.*;
import it.unimi.di.mg4j.query.Query;
import it.unimi.di.mg4j.tool.IndexBuilder;
import it.unimi.dsi.fastutil.io.BinIO;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class tsv_factory  {
//    public tsv_factory() throws FileNotFoundException {
//        super("yago/yagoTransitiveType.tsv", "\\t", new java.lang.String[]{"ab","ab2","ab3","ab4","ab5"}, 1, new it.unimi.di.mg4j.document.tika.AutoDetectDocumentFactory());
//    }

    /**
     * Indexes a directory of HTML document files.
     * From command line, this can be run as
     * mvn exec:java -Dexec.inClass="iitb.cs635.TryMg4j"
     *     -Dexec.args="corpus index"
     * Then visit http://localhost:4242/Query
     * @param args [1]=/path/to/corpus/dir [2]=/path/to/index/dir
     * @throws Exception
     */
    public static void main(java.lang.String args[]) throws FileNotFoundException {

        java.lang.String yago="yago/yagoTransitiveType.tsv";
        java.lang.String index="yagoidex";
        ArrayList<java.lang.String> mg4jArgs;
        mg4jArgs = new ArrayList<java.lang.String>();
        File yagofile= new File(yago),
        indexDir =new File(index);

        File yagocollectionFile=  new File(indexDir,"yago.collection");
        mg4jArgs.add(yagocollectionFile.getAbsolutePath());
        mg4jArgs.add(yagofile.getAbsolutePath());
//        System.out.println(CSVDocumentCollection.factory());
        mg4jArgs.add("\\t");
//        mg4jArgs.add("id");
        mg4jArgs.add("entity");
        mg4jArgs.add("type");
        mg4jArgs.add("cat");
//        System.out.println(mg4jArgs.toString());

//        CSVDocumentCollection.main(mg4jArgs.toArray(new java.lang.String[]{}));
//        CSVDocumentCollection.main(new java.lang.String[]{yagofile.getAbsolutePath(),"\\t","ab,asd,asdsad,asd","1"});
        try {
            DocumentFactory[] factory = new DocumentFactory[5];

            for(int i = 0; i < 5; ++i) {
                try {
                    factory[i] = PropertyBasedDocumentFactory.getInstance(new it.unimi.di.mg4j.document.tika.AutoDetectDocumentFactory().getClass());
                }
                catch (Exception e){

                }
            }
            System.out.println(factory.length);
            BinIO.storeObject(new CSVDocumentCollection("yago/yagoTransitiveType.tsv", "\\t", new java.lang.String[]{"ab","ab2","ab3","ab4","ab5"}, 1,CompositeDocumentFactory.getFactory(factory,new java.lang.String[]{"ab","ab2","ab3","ab4","ab5"})), yagocollectionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try{
//        IndexBuilder.main(new java.lang.String[]{
//                "-S",yagocollectionFile.getAbsolutePath(),
//                (new File(indexDir,"yago-collection")).getAbsolutePath()
//        });}
//        catch (Exception e)
//        {
//            System.out.println(e.toString());
//        }

//      yagoco
//        DocumentCollection dc = ;
        System.out.println(yagocollectionFile.toString());
        try {
            it.unimi.di.mg4j.query.Query.main(new java.lang.String[]{
                    "-h",
                    "-c",yagocollectionFile.getAbsolutePath(),
                    new File(indexDir, "yago-collection-entity").getAbsolutePath(),
                    new File(indexDir, "yago-collection-type").getAbsolutePath()


            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}





















