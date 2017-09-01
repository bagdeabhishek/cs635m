package indeax;

import it.unimi.di.mg4j.document.*;
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
        File WikiDir = new File(wikipath);

        File indexDir = new File(indexpath);
        File collectionfile = new File(indexDir,"wiki-coll");
        Collection<File> wikifiles =
                FileUtils.listFiles(WikiDir, new String[]{"txt"}, false);
        ArrayList<String> listofiles = new ArrayList<>();
        listofiles.add(collectionfile.getAbsolutePath());
        for (File wikifile : wikifiles) {
            listofiles.add(wikifile.getAbsolutePath());
        }
        edit_doc_coll.WikipediaDocumentCollection2.main(listofiles.toArray(new String[]{}));

        IndexBuilder.main(new String[]{
                "-S", collectionfile.getAbsolutePath(),
                (new File(indexDir, "wiki-cs635")).getAbsolutePath()
        });
//        Query.main(new String[]{
//                "-h",
//                "-c", collectionfile.getAbsolutePath(),
//                (new File(indexDir, "wiki-cs635-token")).getAbsolutePath(),
//                (new File(indexDir, "wiki-cs635-WSJ")).getAbsolutePath()
//
//
//        });
    }
}
