//
//import org.apache.jena.query.Dataset;
//import org.apache.jena.query.ReadWrite;
//import org.apache.jena.rdf.model.*;
//import org.apache.jena.riot.*;
import com.univocity.parsers.tsv.*;
//import org.apache.jena.tdb.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

//import static org.apache.jena.enhanced.BuiltinPersonalities.model;

public class yagocheck {
    //    Model m2 = RDFDataMgr.loadModel("/media/abhishek/DataStore/Projects/cs635m/yagoTypes.ttl")

//    Dataset dataset = RDFDataMgr.loadDataset("dat.trig")
//    RDFDataMgr.read(model
//    mdel.read("/media/abhishek/DataStore/Projects/cs635m/yagoTypes.ttl");
public static void main(String args[]) throws Exception
{
//    Model model = ModelFactory.createDefaultModel();
//    model.read("/media/abhishek/DataStore/Projects/cs635m/yago/instance-types-transitive_en (1).ttl");
//    System.out.println(model.listNameSpaces());
//
//    System.out.println(model.listNameSpaces());
//    Dataset dataset = TDBFactory.createDataset("/media/abhishek/DataStore/Projects/cs635m/yagoTypes.ttl");
//    dataset.begin(ReadWrite.READ);
//    Model model = dataset.getDefaultModel();
//    dataset.end();
//    TDBLoader
    TsvParserSettings settings = new TsvParserSettings();
//    settings.selectFields("")
    File fi = new File("/media/abhishek/DataStore/Projects/cs635m/yago/yagoTransitiveType.tsv");
    settings.getFormat().setLineSeparator("\n");
    System.out.println("Starting ");
    TsvParser parser = new TsvParser(settings);
    List<String[]> allrows = parser.parseAll(fi);
    System.out.println(allrows);
    System.out.println("Done");

















}
}



