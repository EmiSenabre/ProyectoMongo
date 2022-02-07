import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.gte;

public class Model {
    public void registro(Usuario usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost/biblio")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            Document book = new Document("_id", new ObjectId());
            book.append("name", usuario.getName())
                    .append("password", usuario.getPassword());

            booksCollection.insertOne(book);
        }
    }

    public boolean existeUsuario(String usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost/biblio")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            FindIterable<Document> iterable = booksCollection.find(gte("name",usuario));
            MongoCursor<Document> cursor = iterable.iterator();
            return cursor.hasNext();

        }
    }

    /*public Usuario porUsuario(String usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost/biblio")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            FindIterable<Document> iterable = booksCollection.find(gte("name",usuario));
            MongoCursor<Document> cursor = iterable.iterator();

            Usuario usr = null;
            while(cursor.hasNext()){
                usr = new Usuario();
            }

        }
    }*/

}
