package edu.unlam.grupo5;

import edu.unlam.grupo5.model.Usuario;

public class App {
    public static void main(String[] args ) {
        Programa programa = new Programa();
        Usuario user = new Usuario();
        user = programa.login();
        // if(user.getAdmin()==true) no hago esto, todavia, pero yo creo que seria una idea buena
        //en caso de que definamos despues cómo manejamos el tema de los usuarios, si hacemos un admin a parte
        //o si forma parte del mismo usuario.
         programa.listarCriptomonedas();
        
    }
}
