
// UsuarioService.java
package com.example.aulabd.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioDAO usuarioDAO;

    public Usuario autenticar(String login, String senha) {
        Usuario user = usuarioDAO.buscarPorLogin(login);
        if (user != null && user.getSenha().equals(senha)) {
            return user;
        }
        return null;
    }

    public void criarUsuario(Usuario usuario) {
        usuarioDAO.inserir(usuario);
    }
}
