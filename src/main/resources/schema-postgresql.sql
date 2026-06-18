CREATE TABLE IF NOT EXISTS aluno(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    cpf  VARCHAR(11)  NOT NULL 
);
CREATE TABLE IF NOT EXISTS usuario(
    login VARCHAR(50) PRIMARY KEY,
    senha VARCHAR(100) NOT NULL
);

-- Insere um usuário padrão para teste
INSERT INTO usuario (login, senha) VALUES ('admin', '1234') ON CONFLICT (login) DO NOTHING;
