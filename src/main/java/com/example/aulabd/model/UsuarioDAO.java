package com.example.aulabd.model;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

@Repository
public class UsuarioDAO {

    @Autowired
    DataSource dataSource;
    JdbcTemplate jdbc;

    private final RowMapper<Usuario> usuarioMapper = (rs, rowNum) ->
        new Usuario(rs.getString("login"), rs.getString("senha"));

    @PostConstruct
    private void initialize() { jdbc = new JdbcTemplate(dataSource); }

    public Usuario buscarPorLogin(String login) {
        String sql = "SELECT login, senha FROM usuario WHERE login = ?";
        try {
            return jdbc.queryForObject(sql, usuarioMapper, login);
        } catch (Exception e) {
            return null;
        }
    }

    public void inserir(Usuario usuario) {
        jdbc.update("INSERT INTO usuario (login, senha) VALUES (?, ?)", 
            usuario.getLogin(), usuario.getSenha());
    }
}
