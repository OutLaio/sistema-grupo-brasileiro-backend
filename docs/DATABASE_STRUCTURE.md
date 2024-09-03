# Relatório de Estrutura de Banco de Dados

## 1. Tb_Profile
**Descrição**  
A tabela `Tb_Profile` armazena informações sobre os perfis dos usuários no sistema. Cada perfil pode ser associado a múltiplos usuários.

**Colunas**

| Coluna       | Tipo de Dados | Descrição                                     |
|---------------|---------------|-----------------------------------------------|
| id            | BIGINT        | Identificador único do perfil. Chave primária.|
| description   | TEXT          | Descrição detalhada do perfil.                |

**Relacionamentos**
- **Relacionamento com `Tb_Users`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um perfil pode estar associado a muitos usuários. O campo `id_profile` na tabela `Tb_Users` faz referência ao `id` da tabela `Tb_Profile`.

---

## 2. Tb_Users
**Descrição**  
A tabela `Tb_Users` contém dados dos usuários do sistema, incluindo informações de autenticação e status de habilitação. Cada usuário pode estar vinculado a um perfil e pode ser associado a vários funcionários.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                         |
|-----------------|---------------|---------------------------------------------------|
| id              | BIGINT        | Identificador único do usuário. Chave primária.  |
| id_profile      | BIGINT        | Identificador do perfil associado. Chave estrangeira referenciando `Tb_Profile.id`. |
| email           | VARCHAR(255)  | Endereço de e-mail do usuário.                   |
| password        | VARCHAR(255)  | Senha do usuário.                                |
| disabled        | TINYINT       | Status de habilitação do usuário (0 para ativo, 1 para desativado). |

**Relacionamentos**
- **Relacionamento com `Tb_Profile`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um perfil pode estar associado a muitos usuários.
- **Relacionamento com `Tb_Employees`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um usuário pode estar associado a muitos funcionários. O campo `id_user` na tabela `Tb_Employees` faz referência ao `id` da tabela `Tb_Users`.

---

## 3. Tb_Employees
**Descrição**  
A tabela `Tb_Employees` armazena informações detalhadas sobre os funcionários do sistema, incluindo dados pessoais e de contato. Cada funcionário está vinculado a um usuário específico e pode estar associado a vários projetos.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                         |
|-----------------|---------------|---------------------------------------------------|
| id              | BIGINT        | Identificador único do funcionário. Chave primária.|
| id_user         | BIGINT        | Identificador do usuário associado. Chave estrangeira referenciando `Tb_Users.id`. |
| name            | VARCHAR(225)  | Nome do funcionário.                            |
| lastname        | VARCHAR(255)  | Sobrenome do funcionário.                       |
| phonemumber     | VARCHAR(255)  | Número de telefone do funcionário.              |
| email           | VARCHAR(255)  | Endereço de e-mail do funcionário.              |
| sector          | VARCHAR(255)  | Setor onde o funcionário trabalha.              |
| occupation      | VARCHAR(255)  | Cargo do funcionário.                           |
| agency          | VARCHAR(255)  | Agência associada ao funcionário.               |

**Relacionamentos**
- **Relacionamento com `Tb_Users`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um usuário pode estar associado a muitos funcionários.
- **Relacionamento com `Tb_Projects`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um funcionário pode estar associado a muitos projetos. O campo `id_employee` na tabela `Tb_Projects` faz referência ao `id` da tabela `Tb_Employees`.

---

## 4. Tb_Projects
**Descrição**  
A tabela `Tb_Projects` armazena informações sobre os projetos, incluindo detalhes sobre o colaborador, o cliente, o título, status, progresso e tipo de briefing. Cada projeto pode estar associado a várias versões.

**Colunas**

| Coluna          | Tipo de Dados | Descrição                                        |
|------------------|---------------|--------------------------------------------------|
| id               | BIGINT        | Identificador único do projeto. Chave primária. |
| id_collaborator  | BIGINT        | Identificador do colaborador associado. Chave estrangeira referenciando `Tb_Employees.id`. |
| id_client        | BIGINT        | Identificador do cliente associado. Chave estrangeira. |
| title            | VARCHAR(255)  | Título do projeto.                             |
| description      | VARCHAR(255)  | Descrição do projeto.                          |
| status           | VARCHAR(255)  | Status atual do projeto.                       |
| progress         | INT           | Progresso do projeto em percentual.            |
| brielfing_type   | INT           | Tipo de briefing associado ao projeto.         |
| disabled         | TINYINT       | Status de habilitação do projeto (0 para ativo, 1 para desativado). |

**Relacionamentos**
- **Relacionamento com `Tb_Employees`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um colaborador pode estar associado a muitos projetos.
- **Relacionamento com `Tb_Versions`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um projeto pode ter muitas versões. O campo `id_project` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Projects`.

---

## 5. Tb_Versions
**Descrição**  
A tabela `Tb_Versions` armazena informações sobre as versões dos projetos. Cada versão está associada a um projeto específico.

**Colunas**

| Coluna              | Tipo de Dados | Descrição                                           |
|----------------------|---------------|-----------------------------------------------------|
| id                   | BIGINT        | Identificador único da versão. Chave primária.    |
| id_project           | BIGINT        | Identificador do projeto associado. Chave estrangeira referenciando `Tb_Projects.id`. |
| title                | VARCHAR(255)  | Título da versão.                                |
| feedback             | TEXT          | Feedback sobre a versão.                         |
| begin                | DATE          | Data de início da versão.                        |
| expected_done        | DATE          | Data esperada de conclusão.                      |
| real_done            | DATE          | Data real de conclusão.                          |
| num_version          | INT           | Número da versão.                                |
| product_link         | VARCHAR(255)  | Link para o produto.                             |
| client_approve       | TINYINT       | Aprovação do cliente (0 para não aprovado, 1 para aprovado). |
| detailed_description | TEXT          | Descrição detalhada da versão.                   |
| supervisor_approve   | TINYINT       | Aprovação do supervisor (0 para não aprovado, 1 para aprovado). |
| collaborator         | VARCHAR(255)  | Colaborador associado.                           |
| other_company        | VARCHAR(255)  | Outra empresa associada.                         |

**Relacionamentos**
- **Relacionamento com `Tb_Projects`:**
  - Tipo: Um para Muitos (1)
  - Descrição: Um projeto pode ter muitas versões.
