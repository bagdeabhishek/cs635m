import it.unimi.di.mg4j.document.FileSetDocumentCollection;
import it.unimi.di.mg4j.document.WikipediaDocumentCollection;
import it.unimi.di.mg4j.query.Query;
import it.unimi.di.mg4j.tool.IndexBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

public class ab {
    /**
     * Indexes a directory of HTML document files.
     * From command line, this can be run as
     * mvn exec:java -Dexec.inClass="iitb.cs635.TryMg4j"
     *     -Dexec.args="corpus index"
     * Then visit http://localhost:4242/Query
     * @param args [1]=/path/to/corpus/dir [2]=/path/to/index/dir
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        final String corpusPath = args[0], indexPath = args[1];
        final File corpusDir = new File(corpusPath),
                indexDir = new File(indexPath);

        assert corpusDir.isDirectory() && indexDir.isDirectory();

//        Collection of files in corpus
        Collection<File> docFiles =
                FileUtils.listFiles(corpusDir, new String[]{"txt"}, false);
//        Arguments to be sent to function
        ArrayList<String> mg4jArgs = new ArrayList<String>();
        mg4jArgs.add("-f");
        mg4jArgs.add("it.unimi.di.mg4j.document.tika.TextDocumentFactory");
        final File collectionFile = new File(indexDir, "corpus.collection");
        mg4jArgs.add(collectionFile.getAbsolutePath());
//        Add all documents in source corpus directory
        for (File docFile : docFiles) {
            mg4jArgs.add(docFile.getAbsolutePath());
        }

        FileSetDocumentCollection.main(mg4jArgs.toArray(new String[]{}));
        IndexBuilder.main(new String[]{
                "-S", collectionFile.getAbsolutePath(),
                (new File(indexDir, "cs635")).getAbsolutePath()
        });
        Query.main(new String[]{
                "-h", "-i", "it.unimi.di.mg4j.query.FileSystemItem",
                "-c", collectionFile.getAbsolutePath(),
                (new File(indexDir, "cs635-text")).getAbsolutePath()
        });
    }
}