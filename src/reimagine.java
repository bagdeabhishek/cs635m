package indeax;

import it.unimi.di.mg4j.document.*;
import it.unimi.di.mg4j.document.tika.TextDocumentFactory;
import it.unimi.di.mg4j.examples.RunQuery;
import it.unimi.di.mg4j.index.Index;
import it.unimi.di.mg4j.index.IndexIterator;
import it.unimi.di.mg4j.index.IndexReader;
import it.unimi.di.mg4j.query.Query;
import it.unimi.di.mg4j.tool.IndexBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import it.unimi.dsi.fastutil.io.BinIO;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import org.apache.commons.io.FileUtils;
import edit_doc_coll.WikipediaDocumentCollection2;


public class reimagine {


    public static void main(String[] args) throws Exception {
        String wikipath = "dataset/test_wiki";
        String indexpath = "dataset/wikiindex";
        String mapindexpath = "dataset/mapindex";
        File mapDataset = new File("dataset/dbpedia_data.txt");
        File WikiDir = new File(wikipath);
        File indexDir = new File(indexpath);
        File mapindexDir = new File(mapindexpath);
        File collectionfile = new File(indexDir,"wiki-coll");
        Collection<File> wikifiles =
                FileUtils.listFiles(WikiDir, new String[]{"txt"}, false);
        ArrayList<String> listofiles = new ArrayList<>();


        listofiles.add(collectionfile.getAbsolutePath());
        for (File wikifile : wikifiles) {
            listofiles.add(wikifile.getAbsolutePath());
        }
//        WikipediaDocumentCollection2.main(listofiles.toArray(new String[]{}));

//        IndexBuilder.main(new String[]{
//                "-S", collectionfile.getAbsolutePath(),
//                (new File(indexDir, "wiki-cs635")).getAbsolutePath()
//        });

        edit_doc_coll.RunQuery.main(new String[]{new File(indexDir,"wiki-cs635").getAbsolutePath(),"a"});
//        Index in =Index.getInstance(new File(indexDir,"wiki-cs635-token").getAbsolutePath());
//        IndexReader ir = in.getReader();
//
//       IndexIterator it = in.getEmptyIndexIterator();
//        while(it.mayHaveNext())
//        System.out.println(it);
        File mapindexcoll = new File(mapindexDir,"map-coll");
        new InputStreamDocumentSequence(mapDataset,,"")

    }
}
