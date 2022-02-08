import java.util.Objects;

public class Control {
    public String ctrlRegistro(Usuario usuario){
        Model model = new Model();
        String respuesta = "";
        if(usuario.getName().isEmpty() || usuario.getPassword().isEmpty() || usuario.getConpassword().isEmpty()){
            respuesta = "Debe llenar todos los campos";
        }else{
            if (Objects.equals(usuario.getPassword(), usuario.getConpassword())){
                if (model.existeUsuario(usuario.getName())){
                    respuesta = "El usuario ya existe";
                }
                else{
                    model.registro(usuario);
                }
            }
            else{
                respuesta = "Las contraseñas no coinciden";
            }
        }
        return respuesta;
    }

    public String ctrlLogin(String name, String password){
        Model model = new Model();
        String respuesta = "";
        Usuario datosUsuario;

        if(name.isEmpty() || password.isEmpty()){
            respuesta="Debe llenar todos los campos";
        }
        else{
            datosUsuario = model.porUsuario(name);

            if(datosUsuario == null){
                respuesta = "El usuario no existe";
            }
            else{
                if(!Objects.equals(datosUsuario.getPassword(), password)){
                    respuesta = "El usuario y/o contraseña no coinciden";
                }
            }
        }
        return respuesta;
    }
}
