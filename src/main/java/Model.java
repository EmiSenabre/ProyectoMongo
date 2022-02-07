import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Model {
    public int registro(Usuario usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost/biblio")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            Document book = new Document("_id", new ObjectId());
            book.append("name",usuario.name)
                    .append("password",usuario.password);

            booksCollection.insertOne(book);
        }
    }

}
