//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edit_doc_coll;

import it.unimi.di.mg4j.index.Index;
import it.unimi.di.mg4j.index.TermProcessor;
import it.unimi.di.mg4j.query.IntervalSelector;
import it.unimi.di.mg4j.query.QueryEngine;
import it.unimi.di.mg4j.query.SelectedInterval;
import it.unimi.di.mg4j.query.parser.SimpleParser;
import it.unimi.di.mg4j.search.DocumentIteratorBuilderVisitor;
import it.unimi.di.mg4j.search.score.BM25Scorer;
import it.unimi.di.mg4j.search.score.DocumentScoreInfo;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import java.util.Iterator;

public class RunQuery {
    public RunQuery() {
    }

    public static void main(String[] arg) throws Exception {
        Index text = Index.getInstance(arg[0] + "-token", true, true);
        Index title = Index.getInstance(arg[0] + "-WSJ", true, true);
        Object2ReferenceOpenHashMap<String, Index> indexMap = new Object2ReferenceOpenHashMap(new String[]{"token", "WSJ"}, new Index[]{text, title});
        Object2ReferenceOpenHashMap<String, TermProcessor> termProcessors = new Object2ReferenceOpenHashMap(new String[]{"token", "WSJ"}, new TermProcessor[]{text.termProcessor, title.termProcessor});
        QueryEngine engine = new QueryEngine(new SimpleParser(indexMap.keySet(), "token", termProcessors), new DocumentIteratorBuilderVisitor(indexMap, text, 1000), indexMap);
        engine.score(new BM25Scorer());
        engine.setWeights(new Reference2DoubleOpenHashMap(new Index[]{text, title}, new double[]{1.0D, 2.0D}));
        engine.intervalSelector = new IntervalSelector();
        ObjectArrayList<DocumentScoreInfo<Reference2ObjectMap<Index, SelectedInterval[]>>> result = new ObjectArrayList();
        engine.process(arg[1], 0, 20, result);
        Iterator i$ = result.iterator();

        while(i$.hasNext()) {
            DocumentScoreInfo<Reference2ObjectMap<Index, SelectedInterval[]>> dsi = (DocumentScoreInfo)i$.next();
            System.out.println(dsi.document + " " + dsi.score + dsi.toString());
        }

    }
}
