import java.util.Scanner;

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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
