//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package edit_doc_coll;

import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.SimpleJSAP;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;
import it.unimi.di.mg4j.document.*;
import it.unimi.di.mg4j.document.PropertyBasedDocumentFactory.MetadataKeys;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.io.BinIO;
import it.unimi.dsi.fastutil.io.FastBufferedInputStream;
import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.io.FastBufferedReader;
import it.unimi.dsi.io.MultipleInputStream;
import it.unimi.dsi.logging.ProgressLogger;
import it.unimi.dsi.sux4j.util.EliasFanoMonotoneLongBigList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikipediaDocumentCollection2 extends AbstractDocumentCollection implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikipediaDocumentCollection2.class);
    private static final long serialVersionUID = 1L;
    private static final byte[] META_MARKER = "%%#".getBytes();
    private static final byte[] DOC_MARKER = "%%#DOC".getBytes();
    private static final byte[] PAGE_MARKER = "%%#PAGE".getBytes();
    private static final byte[] SENTENCE_MARKER = "%%#SEN".getBytes();
    private static final int NUM_FIELDS = 11;
    private static final String[] FIELD_NAME = new String[]{"token","token2","POS", "lemma", "CONL", "WNSS", "WSJ", "ana", "head", "deplabel", "link"};
    private final String[] file;
    private boolean gzipped;
    private final DocumentFactory factory;
    private final ObjectArrayList< EliasFanoMonotoneLongBigList > pointers;
    private final int size;
    private final boolean phrase;
    private final int[] firstDocument;
    private transient byte[][] buffer;
    private transient byte[] lineBuffer;
    private transient int[] bufferSize;
    private transient Reference2ObjectMap<Enum<?>, Object> metadata;
    private transient int lastDocument;

    private final void initBuffers() {
        this.bufferSize = new int[NUM_FIELDS];
        this.buffer = new byte[NUM_FIELDS][];
        this.lineBuffer = ByteArrays.EMPTY_ARRAY;
        this.lastDocument = -1;
        this.metadata = new Reference2ObjectArrayMap();

        for(int i = NUM_FIELDS; i-- != 0; this.buffer[i] = ByteArrays.EMPTY_ARRAY) {
            ;
        }

    }

    public WikipediaDocumentCollection2(String[] file, DocumentFactory factory, boolean phrase) throws IOException {
        this(file, factory, phrase, false);
    }

    public WikipediaDocumentCollection2(String[] file, DocumentFactory factory, boolean phrase, boolean gzipped) throws IOException {
        this.file = file;
        this.factory = factory;
        this.gzipped = gzipped;
        this.phrase = phrase;
        this.initBuffers();
        LongArrayList p = new LongArrayList();
        this.pointers = new ObjectArrayList(file.length);
        this.firstDocument = new int[file.length + 1];
        int count = 0;
        ProgressLogger pl = new ProgressLogger(LOGGER);
        pl.expectedUpdates = (long)file.length;
        pl.itemsName = "files";
        pl.start("Scanning files...");
        String[] arr$ = file;
        int len$ = file.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String f = arr$[i$];
            p.clear();
            FastBufferedInputStream fbis = gzipped ? new FastBufferedInputStream(new GZIPInputStream(new FileInputStream(f))) : new FastBufferedInputStream(new FileInputStream(f));

            while(true) {
                long position = fbis.position();
                if (this.readLine(fbis) == -1) {
                    count += p.size();
                    p.add(fbis.position());
                    fbis.close();
                    this.pointers.add(new EliasFanoMonotoneLongBigList(p));
                    this.firstDocument[this.pointers.size()] = count;
                    pl.update();
                    break;
                }

                if (startsWith(this.lineBuffer, DOC_MARKER)) {
                    p.add(position);
                }

                if (phrase && startsWith(this.lineBuffer, SENTENCE_MARKER)) {
                    p.add(position);
                }
            }
        }

        pl.done();
        this.size = count;
    }

    private final int readLine(FastBufferedInputStream fbis) throws IOException {
        int start;
        int len;
        for(start = 0; (len = fbis.readLine(this.lineBuffer, start, this.lineBuffer.length - start, FastBufferedInputStream.ALL_TERMINATORS)) == this.lineBuffer.length - start; this.lineBuffer = ByteArrays.grow(this.lineBuffer, this.lineBuffer.length + 1)) {
            start += len;
        }

        if (len != -1) {
            start += len;
        }

        return len == -1 ? -1 : start;
    }

    protected WikipediaDocumentCollection2(String[] file, DocumentFactory factory, ObjectArrayList<EliasFanoMonotoneLongBigList> pointers, int size, int[] firstDocument, boolean phrase, boolean gzipped) {
        this.file = file;
        this.factory = factory;
        this.pointers = pointers;
        this.size = size;
        this.firstDocument = firstDocument;
        this.gzipped = gzipped;
        this.phrase = phrase;
        this.initBuffers();
    }

    private static boolean startsWith(byte[] array, byte[] pattern) {
        int length = pattern.length;
        if (array.length < length) {
            return false;
        } else {
            do {
                if (length-- == 0) {
                    return true;
                }
            } while(array[length] == pattern[length]);

            return false;
        }
    }

    public DocumentFactory factory() {
        return this.factory;
    }

    public int size() {
        return this.size;
    }

    public Reference2ObjectMap<Enum<?>, Object> metadata(int index) throws IOException {
        this.readDocument(index, -1, (FastBufferedInputStream)null);
        if (!this.metadata.containsKey(MetadataKeys.TITLE)) {
            this.metadata.put(MetadataKeys.TITLE, "Sentence #" + (index + 1));
        }

        return this.metadata;
    }

    public Document document(int index) throws IOException {
        return this.factory.getDocument(this.stream(index), this.metadata(index));
    }

    public InputStream stream(int index) throws IOException {
        this.readDocument(index, -1, (FastBufferedInputStream)null);
        FastByteArrayInputStream[] is = new FastByteArrayInputStream[NUM_FIELDS];

        for(int i = 0; i < NUM_FIELDS; ++i) {
            is[i] = new FastByteArrayInputStream(this.buffer[i], 0, this.bufferSize[i]);
        }

        return MultipleInputStream.getStream(is);
    }

    public DocumentIterator iterator() throws IOException {
        return new AbstractDocumentIterator() {
            private int index = 0;
            private int f = 0;
            private FastBufferedInputStream fbis;

            {
                this.fbis = new FastBufferedInputStream(new FileInputStream(WikipediaDocumentCollection2.this.file[0]));
            }

            public void close() throws IOException {
                super.close();
                if (this.fbis != null) {
                    this.fbis.close();
                    this.fbis = null;
                }

            }

            public Document nextDocument() throws IOException {
                if (this.index == WikipediaDocumentCollection2.this.size) {
                    return null;
                } else {
                    if (this.index == WikipediaDocumentCollection2.this.firstDocument[this.f + 1]) {
                        this.fbis.close();
                        this.fbis = new FastBufferedInputStream(new FileInputStream(WikipediaDocumentCollection2.this.file[++this.f]));
                    }

                    WikipediaDocumentCollection2.this.readDocument(this.index, this.f, this.fbis);
                    return WikipediaDocumentCollection2.this.document(this.index++);
                }
            }
        };
    }

    private void readDocument(int index, int f, FastBufferedInputStream fbis) throws IOException {
        this.ensureDocumentIndex(index);
        if (index != this.lastDocument) {
            boolean openStream = fbis == null;
            if (openStream) {
                f = Arrays.binarySearch(this.firstDocument, index);
                if (f < 0) {
                    f = -f - 2;
                }

                fbis = new FastBufferedInputStream(new FileInputStream(this.file[f]));
            }

            long start = ((EliasFanoMonotoneLongBigList)this.pointers.get(f)).getLong(index - this.firstDocument[f]);
            fbis.position(start);
            long end = ((EliasFanoMonotoneLongBigList)this.pointers.get(f)).getLong(index - this.firstDocument[f] + 1);
            IntArrays.fill(this.bufferSize, 0);
            this.metadata.clear();

            while(true) {
                boolean startOfPage;
                boolean startOfSentence;
                int i;
                do {
                    label84:
                    do {
                        while(fbis.position() < end) {
                            int l = this.readLine(fbis);
                            if (startsWith(this.lineBuffer, META_MARKER)) {
                                startOfSentence = false;
                                startOfPage = false;
                                if (startsWith(this.lineBuffer, DOC_MARKER) && this.phrase) {
                                    return;
                                }

                                if (startsWith(this.lineBuffer, PAGE_MARKER)) {
                                    startOfPage = true;
                                } else if (startsWith(this.lineBuffer, SENTENCE_MARKER)) {
                                    startOfSentence = true;
                                }

                                if (startOfPage) {
                                    String title = (new String(this.lineBuffer, Math.min(PAGE_MARKER.length + 1, l), Math.max(l - PAGE_MARKER.length - 1, 0), "UTF-8")).trim();
                                    this.metadata.put(MetadataKeys.TITLE, title);
                                    this.metadata.put(MetadataKeys.URI, "http://en.wikipedia.org/wiki/" + URLEncoder.encode(title, "UTF-8"));
                                }
                                continue label84;
                            }

                            int field = 0;

                            for(i = 0; i < l; ++i) {
                                if (this.lineBuffer[i] == 9) {
                                    ++field;
                                } else {
                                    this.buffer[field] = ByteArrays.grow(this.buffer[field], this.bufferSize[field] + 2);
                                    this.buffer[field][this.bufferSize[field]++] = this.lineBuffer[i];
                                    if (i == l - 1 || this.lineBuffer[i + 1] == 9) {
                                        this.buffer[field][this.bufferSize[field]++] = 32;
                                    }
                                }
                            }
                        }

                        if (openStream) {
                            fbis.close();
                        }

                        return;
                    } while(!startOfPage && !startOfSentence);
                } while(this.phrase);

                for(i = 0; i < NUM_FIELDS; ++i) {
                    this.buffer[i] = ByteArrays.grow(this.buffer[i], this.bufferSize[i] + 3);
                    this.buffer[i][this.bufferSize[i]++] = -62;
                    this.buffer[i][this.bufferSize[i]++] = -74;
                    this.buffer[i][this.bufferSize[i]++] = 10;
                }
            }
        }
    }

    public WikipediaDocumentCollection2 copy() {
        return new WikipediaDocumentCollection2(this.file, this.factory.copy(), this.pointers, this.size, this.firstDocument, this.phrase, this.gzipped);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.initBuffers();
    }

    public static void main(String[] arg) throws IOException, JSAPException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SimpleJSAP jsap = new SimpleJSAP(WikipediaDocumentCollection2.class.getName(), "Saves a serialised document collection based on a set of files.", new Parameter[]{new Switch("sentence", 's', "sentence", "Index sentences rather than documents."), new Switch("gzipped", 'z', "gzipped", "The files are gzipped."), new UnflaggedOption("collection", JSAP.STRING_PARSER, true, "The filename for the serialised collection."), new UnflaggedOption("file", JSAP.STRING_PARSER, JSAP.NO_DEFAULT, false, true, "A list of files that will be indexed. If missing, a list of files will be read from standard input.")});
        JSAPResult jsapResult = jsap.parse(arg);
        if (!jsap.messagePrinted()) {
            DocumentFactory factory = new IdentityDocumentFactory(new Reference2ObjectOpenHashMap(new MetadataKeys[]{MetadataKeys.ENCODING, MetadataKeys.WORDREADER}, new Object[]{"UTF-8", WikipediaDocumentCollection2.WhitespaceWordReader.class.getName()}));
            String[] file = (String[])((String[])jsapResult.getObjectArray("file", new String[0]));
            if (file.length == 0) {
                ObjectArrayList<String> files = new ObjectArrayList();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                String s;
                while((s = bufferedReader.readLine()) != null) {
                    files.add(s);
                }

                file = (String[])files.toArray(new String[0]);
            }

            if (file.length == 0) {
                System.err.println("WARNING: empty file set.");
            }

            BinIO.storeObject(new WikipediaDocumentCollection2(file, ReplicatedDocumentFactory.getFactory(factory, NUM_FIELDS, FIELD_NAME), jsapResult.getBoolean("sentence"), jsapResult.getBoolean("gzipped")), jsapResult.getString("collection"));
        }
    }

    public static class WhitespaceWordReader extends FastBufferedReader {
        private static final long serialVersionUID = 1L;

        public WhitespaceWordReader() {
        }

        protected boolean isWordConstituent(char c) {
            return !Character.isWhitespace(c);
        }
    }
}
