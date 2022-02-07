import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);
        System.out.println("Introduce nombre de usuario");
        String name = ent.next();
        System.out.println("Introducer una contraseña");
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
}
