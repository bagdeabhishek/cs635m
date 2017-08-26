import it.unimi.di.mg4j.document.FileSetDocumentCollection;
import it.unimi.di.mg4j.document.WikipediaDocumentCollection;
import it.unimi.di.mg4j.query.Query;
import it.unimi.di.mg4j.tool.IndexBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

public class wiki {
    public static void main(String args[]) throws Exception {
//        final String corpusPath = args[0], indexPath = args[1], wikipath = "/nfs/Projects/cs635m/dataset/test_wiki";
        final String corpusPath = args[0], indexPath = args[1], wikipath = "/media/abhishek/DataStore/Projects/cs635m/dataset/test_wiki";
        final File corpusDir = new File(corpusPath),
                indexDir = new File(indexPath),
                wikiDir = new File(wikipath);
        assert corpusDir.isDirectory() && indexDir.isDirectory() && wikiDir.isDirectory();
        Collection<File> docFiles =
                FileUtils.listFiles(corpusDir, new String[]{"txt"}, false);
        Collection<File> wikidocFiles =
                FileUtils.listFiles(wikiDir, new String[]{"txt"}, false);
        ArrayList<String> mg4jArgs = new ArrayList<String>();
//        wiki code
        final File wikicollectionFile = new File(indexDir, "wiki.collection");
        mg4jArgs.add(wikicollectionFile.getAbsolutePath());
        for (File wikidocFile : wikidocFiles) {
            mg4jArgs.add(wikidocFile.getAbsolutePath());
        }
        WikipediaDocumentCollection.main(mg4jArgs.toArray(new String[]{}));



//
//        mg4jArgs.add("-f");
//        mg4jArgs.add("it.unimi.di.mg4j.document.tika.TextDocumentFactory");
//        final File collectionFile = new File(indexDir, "corpus.collection");
//        mg4jArgs.add(collectionFile.getAbsolutePath());
//        for (File docFile : docFiles) {
//            mg4jArgs.add(docFile.getAbsolutePath());
//        }
//        FileSetDocumentCollection.main(mg4jArgs.toArray(new String[]{}));
        IndexBuilder.main(new String[]{
                "-S", wikicollectionFile.getAbsolutePath(),
                (new File(indexDir, "wiki-cs635")).getAbsolutePath()
        });
//        Query.GenericItem();
        Query.main(new String[]{
                "-h",
                "-c", wikicollectionFile.getAbsolutePath(),
                (new File(indexDir, "wiki-cs635-token")).getAbsolutePath(),
                (new File(indexDir, "wiki-cs635-WSJ")).getAbsolutePath()


        });
    }
}
