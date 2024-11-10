package org.example;

import com.google.common.hash.Hashing;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.DAO.UsuariosDAO;
import org.example.domain.Usuario;
import org.example.clases.ConexionBBDD;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

          Connection con;
          boolean existeUsuario;
         con=ConexionBBDD.conectar();
        String contraseña="ana";

        String sha256hex1 = DigestUtils.sha256Hex(contraseña);
        System.out.println(sha256hex1);
        Usuario usuario1=new Usuario("luis3@gmail.com",sha256hex1);
        if(UsuariosDAO.guardarUsuario(con,usuario1))
            System.out.println("Usuario INSERTADO CORRECTAMENTE");

        existeUsuario=UsuariosDAO.existeUsuario(con,"ana@gmail.com");
        if(existeUsuario)
            System.out.println("El usuario EXISTE en la BD");




        //String sha512hex=DigestUtils.sha3_512Hex(originalString);


        


        }
    }
