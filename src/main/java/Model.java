import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.eq;

public class Model {
    public void registro(Usuario usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            Document book = new Document("_id", new ObjectId());
            book.append("name", usuario.getName())
                    .append("password", usuario.getPassword());

            booksCollection.insertOne(book);
        }
    }

    public boolean existeUsuario(String usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            Document document = booksCollection.find(eq("name",usuario)).first();
            return document != null;

        }
    }

    public Usuario porUsuario(String usuario){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("usuarios");

            FindIterable<Document> iterable = booksCollection.find(gte("name",usuario));
            MongoCursor<Document> cursor = iterable.iterator();

            Usuario usr = null;
            while(cursor.hasNext()){
                Document actual = cursor.next();
                usr = new Usuario();
                usr.setName(actual.getString("name"));
                usr.setPassword(actual.getString("password"));
            }
            return usr;

        }
    }

}
