
package com.example.aulabd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.aulabd.model.Usuario;
import com.example.aulabd.model.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(@RequestParam String login, 
                             @RequestParam String senha,
                             HttpSession session,
                             RedirectAttributes redirect) {
        Usuario user = usuarioService.autenticar(login, senha);
        if (user != null) {
            session.setAttribute("usuario", user);
            return "redirect:/landing";
        }
        redirect.addFlashAttribute("erro", "Login ou senha inválidos");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registrar";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestParam String login,
                                   @RequestParam String senha,
                                   @RequestParam String confirmar,
                                   RedirectAttributes redirect) {
        if (!senha.equals(confirmar)) {
            redirect.addFlashAttribute("erro", "As senhas não coincidem");
            return "redirect:/registrar";
        }
        Usuario user = new Usuario(login, senha);
        usuarioService.criarUsuario(user);
        redirect.addFlashAttribute("sucesso", "Usuário criado com sucesso! Faça login.");
        return "redirect:/login";
    }
}
