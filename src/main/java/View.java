import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Updates.set;

public class View {
    public static Scanner ent = new Scanner(System.in);
    public static void main(String[] args) {
        int n;
        do{
            System.out.println("ELIJA UNA OPCIÓN");
            System.out.println("1. Registrarse");
            System.out.println("2. Logearse");
            System.out.println("3. Salir");
            n = ent.nextInt();

            switch (n){
                case 1->registrarUsuario();
                case 2->logearUsuario();
                case 3-> System.out.println("Cerrando");
                default-> System.out.println("Opcion no valida");
            }
        }while (n != 3);

    }

    public static void registrarUsuario(){
        System.out.println("Introduce un nuevo nombre de usuario");
        String name = ent.next();
        System.out.println("Introduzca una contraseña");
        String password = ent.next();
        System.out.println("Confirma contraseña");
        String conpassword = ent.next();

        Usuario usuario = new Usuario();
        usuario.setName(name);
        usuario.setPassword(password);
        usuario.setConpassword(conpassword);

        try {
            Control control = new Control();
            String respuesta = control.ctrlRegistro(usuario);
            if(respuesta.length()>0){
                System.out.println(respuesta);
            }else{
                System.out.println("Usuario registrado");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void logearUsuario(){
        System.out.println("Introduzca su nombre de usuario");
        String name = ent.next();
        System.out.println("Introduzca su contraseña");
        String password = ent.next();
        try{
            Control control = new Control();
            String respuesta = control.ctrlLogin(name,password);
            if(respuesta.length()>0){
                System.out.println(respuesta);
            }else{
                System.out.println("Usuario logeado con exito");
                logeoExitoso();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void logeoExitoso(){
        int n;
        do{
            System.out.println("ELIJA UNA OPCIÓN");
            System.out.println("1. Ver Libros");
            System.out.println("2. Buscar Libros");
            System.out.println("3. Añadir Libros");
            System.out.println("4. Modificar Libros");
            System.out.println("5. Eliminar Libros");
            System.out.println("6. Cerrar sesión");
            n = ent.nextInt();

            switch (n){
                case 1->verLibros();
                case 2->buscarLibros();
                case 3->crearLibros();
                case 4->modificarLibros();
                case 5->borrarLibros();
                case 6-> System.out.println("Cerrando sesión");
                default-> System.out.println("Opcion no valida");
            }
        }while (n != 6);
    }

    public static void buscarLibros(){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("libro");

            FindIterable<Document> iterable = booksCollection.find();
            MongoCursor<Document> cursor = iterable.iterator();
            List<String> libros = new ArrayList<String>();


            Libro lbr = null;
            while (cursor.hasNext()) {
                Document actual = cursor.next();
                lbr = new Libro();
                lbr.setName(actual.getString("name"));
                libros.add(lbr.getName());
            }
            System.out.println("Busqueda: ");
            ent.nextLine();
            String busqueda = ent.nextLine();
            for (String n:libros){
                if(n.equals(busqueda)){
                    Document resultado = booksCollection.find(eq("name",busqueda)).first();
                    Libro lb = new Libro();
                    lb.setName(resultado.getString("name"));
                    lb.setAutor(resultado.getString("autor"));
                    lb.setPaginas(resultado.getInteger("paginas"));
                    lb.setId(resultado.getInteger("id"));
                    System.out.println(lb.getId() + ". Libro: " + lb.getName() + ", Autor: " + lb.getAutor() + ", Páginas: " + lb.getPaginas());

                }
            }
        }
    }

    public static void verLibros(){
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("libro");

            FindIterable<Document> iterable = booksCollection.find();
            MongoCursor<Document> cursor = iterable.iterator();
            Libro lbr = null;
            while(cursor.hasNext()) {
                Document actual = cursor.next();
                lbr = new Libro();
                lbr.setName(actual.getString("name"));
                lbr.setAutor(actual.getString("autor"));
                lbr.setPaginas(actual.getInteger("paginas"));
                lbr.setId(actual.getInteger("id"));
                System.out.println(lbr.getId() + ". Libro: " + lbr.getName() + ", Autor: " + lbr.getAutor() + ", Páginas: " + lbr.getPaginas());
            }
        }

    }

    public static void crearLibros(){
        System.out.println("Introduzca el nombre del nuevo libro");
        ent.nextLine();
        String name = ent.nextLine();
        System.out.println("Introduzca el nombre del autor");
        String autor = ent.nextLine();
        System.out.println("Introduzca numero de paginas");
        int paginas = ent.nextInt();

        Libro libro = new Libro();
        libro.setName(name);
        libro.setAutor(autor);
        libro.setPaginas(paginas);

        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("libro");

            int l = (int) booksCollection.countDocuments();
            libro.setId(l + 1);

            Document book = new Document("_id", new ObjectId());
            book.append("name", libro.getName())
                    .append("autor", libro.getAutor())
                            .append("paginas",libro.getPaginas())
                                    .append("id",libro.getId());

            booksCollection.insertOne(book);
        }
    }

    public static void modificarLibros(){
        System.out.println("Elige libro a modificar");
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")){
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("libro");

            FindIterable<Document> iterable = booksCollection.find();
            MongoCursor<Document> cursor = iterable.iterator();

            Libro lbr = null;
            while(cursor.hasNext()){
                Document actual = cursor.next();
                lbr = new Libro();
                lbr.setName(actual.getString("name"));
                lbr.setAutor(actual.getString("autor"));
                lbr.setPaginas(actual.getInteger("paginas"));
                lbr.setId(actual.getInteger("id"));
                System.out.println(lbr.getId() + ". Libro: " + lbr.getName() +", Autor: " + lbr.getAutor() +", Páginas: "+ lbr.getPaginas() );
            }
            int n = ent.nextInt();
            System.out.println("Introduzca el nuevo nombre del libro");
            ent.nextLine();
            String name = ent.nextLine();
            System.out.println("Introduzca el nuevo autor del libro");
            String autor = ent.nextLine();
            System.out.println("Introduzca el nuevo numero de paginas");
            int paginas = ent.nextInt();

            Bson filter = eq("id",n);
            Bson updateName = set("name",name);
            Bson updateAutor = set("autor",autor);
            Bson updatePaginas = set("paginas",paginas);
            UpdateResult updateResult = booksCollection.updateOne(filter,updateName);
            UpdateResult updateResult1 = booksCollection.updateOne(filter,updateAutor);
            UpdateResult updateResult2 = booksCollection.updateOne(filter,updatePaginas);

            System.out.println("Libro modificado");


        }
    }

    public static void borrarLibros(){
        System.out.println("Elige libro a eliminar");
        try(MongoClient mongoClient = MongoClients.create("mongodb://localhost")) {
            MongoDatabase sampleTrainingDB = mongoClient.getDatabase("proyecto");
            MongoCollection<Document> booksCollection = sampleTrainingDB.getCollection("libro");

            FindIterable<Document> iterable = booksCollection.find();
            MongoCursor<Document> cursor = iterable.iterator();

            Libro lbr = null;
            while (cursor.hasNext()) {
                Document actual = cursor.next();
                lbr = new Libro();
                lbr.setName(actual.getString("name"));
                lbr.setAutor(actual.getString("autor"));
                lbr.setPaginas(actual.getInteger("paginas"));
                lbr.setId(actual.getInteger("id"));
                System.out.println(lbr.getId() + ". Libro: " + lbr.getName() + ", Autor: " + lbr.getAutor() + ", Páginas: " + lbr.getPaginas());
            }
            int n = ent.nextInt();
            Bson filter = eq("id", n);
            DeleteResult dResult = booksCollection.deleteOne(filter);
            System.out.println("Libro eliminado");
        }
    }
}
