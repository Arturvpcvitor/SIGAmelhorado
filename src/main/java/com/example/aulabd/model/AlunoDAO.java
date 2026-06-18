package com.example.aulabd.model;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

@Repository
public class AlunoDAO {

    @Autowired
    DataSource dataSource;
    JdbcTemplate jdbc;

   private final RowMapper<Aluno> alunoMapper = (rs, rowNum) -> {
    Aluno a = new Aluno();
    a.setId(rs.getString("id"));
    a.setNome(rs.getString("nome"));
    a.setCpf(rs.getString("cpf"));
    a.setFoto(rs.getString("foto"));
    return a;
};

    @PostConstruct
    private void initialize() { jdbc = new JdbcTemplate(dataSource); }

   public void inserirAluno(Aluno aluno) {
    String sql = "INSERT INTO aluno (id, nome, cpf, foto) VALUES (gen_random_uuid(), ?, ?, ?)";
    jdbc.update(sql, aluno.getNome(), aluno.getCpf(), aluno.getFoto());
}

    public void atualizarAluno(Aluno aluno) {
    String sql = "UPDATE aluno SET nome=?, cpf=?, foto=? WHERE id=?::uuid";
    jdbc.update(sql, aluno.getNome(), aluno.getCpf(), aluno.getFoto(), aluno.getId());
}

    public void excluirAluno(String id) {
        jdbc.update("DELETE FROM aluno WHERE id = ?::uuid", id);
    }

    public Aluno buscarPorIdExato(String id) {
        List<Aluno> result = jdbc.query(
            "SELECT id, nome, cpf FROM aluno WHERE id = ?::uuid", alunoMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Aluno> listarTodos() {
        return jdbc.query("SELECT id, nome, cpf FROM aluno ORDER BY nome", alunoMapper);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome",
            alunoMapper, "%" + nome + "%");
    }

    public List<Aluno> buscarPorCpf(String cpf) {
        String limpo = cpf.replaceAll("[^0-9]", "");
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE cpf LIKE ? ORDER BY nome",
            alunoMapper, "%" + limpo + "%");
    }

    public List<Aluno> buscarPorId(String id) {
        return jdbc.query("SELECT id, nome, cpf FROM aluno WHERE CAST(id AS TEXT) LIKE ? ORDER BY nome",
            alunoMapper, "%" + id + "%");
    }

    public int contarAlunos() {
        Integer t = jdbc.queryForObject("SELECT COUNT(*) FROM aluno", Integer.class);
        return t != null ? t : 0;
    }
}
