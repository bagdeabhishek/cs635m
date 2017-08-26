
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.*;

import org.apache.jena.tdb.*;
import java.io.FileInputStream;

import static org.apache.jena.enhanced.BuiltinPersonalities.model;

public class yagocheck {
    //    Model m2 = RDFDataMgr.loadModel("/media/abhishek/DataStore/Projects/cs635m/yagoTypes.ttl")

//    Dataset dataset = RDFDataMgr.loadDataset("dat.trig")
//    RDFDataMgr.read(model
//    mdel.read("/media/abhishek/DataStore/Projects/cs635m/yagoTypes.ttl");
public static void main(String args[]) throws Exception
{
    Model model = ModelFactory.createDefaultModel();
    model.read("/nfs/Projects/cs635m/yago/yagoTypes.ttl");
//
//    System.out.println(model.listNameSpaces());
//    Dataset dataset = TDBFactory.createDataset("/media/abhishek/DataStore/Projects/cs635m/yagoTypes.ttl");
//    dataset.begin(ReadWrite.READ);
//    Model model = dataset.getDefaultModel();
//    dataset.end();
//    TDBLoader.

}
}



