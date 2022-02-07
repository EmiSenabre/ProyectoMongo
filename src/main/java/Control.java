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
}
