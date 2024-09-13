# DOCUMENTAÇÃO DO BANCO DE DADOS 
## SISTEMA DE GESTÃO DE PROJETOS DO GRUPO BRASILEIRO  

## Elaborado por:

Carlos Dias  
Darley Souza  
Laio Rodrigues  
Mikaelle Rubia  
Renata Moreno  

**Versão 1.0**  
**Ano: 2024**

---
# Introdução

Este documento fornece uma descrição detalhada do esquema de banco de dados utilizado no Sistema de Gestão de Projetos do Grupo Brasileiro. O objetivo é oferecer uma visão clara e abrangente das tabelas, relacionamentos e estruturas de dados que suportam o funcionamento do sistema, facilitando a compreensão e manutenção do banco de dados por parte dos desenvolvedores, administradores e outros profissionais envolvidos no projeto.

---

# Sumário

- **RELATÓRIO DETALHADO DAS TABELAS**
  - Tabela do Usuario
    1. Tb_Profile
    2. Tb_Users
    3. Tb_Employees
    4. Tb_RecoveryTokens
    5. Tb_Projects
    6. Tb_Versions
    7. Tb_DialogBoxes
    8. Tb_BriefingType
    9. Tb_Briefings
- **Tabela de Medidas**
    10. Tb_Measurement
- **Briefing de Brindes**
    11. Tb_GiftType
    12. Tb_BGifts
    13. Tb_CalendarType
    14. Tb_Stamps
    15. Tb_PrintingShirtType
- **Briefing de Impressos**
    16. Tb_PrintingType
    17. Tb_BPrinteds
    18. Tb_PrintedType
- **Briefing de Placa de Agência**
    19. Tb_BAgencyBoards
    20. Tb_BoardType
    21. Tb_OthersRoutes
    22. Tb_Routes
    23. Tb_CompaniesCitie
    24. Tb_Cities
    25. Tb_Companies
    26. Tb_AgencyBoardTypes
- **Briefing de Comunicados**
    27. Tb_Handout
    28. Tb_HandoutType
    29. Tb_BInternalCompaigns
    30. Tb_StationeryType
    31. Tb_OtherItems
- **Briefing de Placa de Sinalização**
    32. Tb_Signpost
    33. Tb_Material
- **Briefing de Adesivos**
    34. Tb_BStickers
    35. Tb_StickerInformationType
    36. Tb_StickerTypes
- **Empresas de um Projeto**
    37. Tb_CompaniesBriefings
- **Conclusão**

---
# RELATÓRIO DETALHADO DAS TABELAS

---

## Tabela do Usuario

### 1. Tb_Profile

**DESCRIÇÃO:**  
A tabela `Tb_Profile` armazena os diferentes perfis de usuários, como "Colaborador", "Supervisor" ou "Cliente". Esta tabela define o tipo de acesso e permissões que um usuário terá no sistema.

**Colunas**

| Coluna      | Tipo    | Restrições                                | Descrição                                   |
|-------------|---------|-------------------------------------------|---------------------------------------------|
| id          | BIGINT  | PRIMARY KEY, AUTO_INCREMENT               | Identificador único do perfil               |
| description | TEXT    | NOT NULL                                  | Descrição do tipo de perfil                 |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Users`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um perfil pode estar associado a vários usuários. Ou seja, um id em `Tb_Profile` pode ser associado a múltiplos registros em `Tb_Users`.

---

### 2. Tb_Users

**DESCRIÇÃO:**  
A tabela `Tb_Users` armazena informações sobre os usuários do sistema. Cada usuário tem um perfil associado, bem como informações como e-mail, senha e status de ativação. Esta tabela é fundamental para o gerenciamento de acessos e permissões no sistema.

**Colunas**

| Coluna       | Tipo    | Restrições                                         | Descrição                                     |
|--------------|---------|----------------------------------------------------|-----------------------------------------------|
| id           | BIGINT  | PRIMARY KEY, AUTO_INCREMENT                        | Identificador único do usuário                |
| id_profile   | BIGINT  | NOT NULL, FOREIGN KEY (referenciando Tb_Profile.id) | Identificador do perfil do usuário            |
| email        | VARCHAR(255) | NOT NULL, UNIQUE                               | Endereço de e-mail do usuário                 |
| password     | VARCHAR(255) | NOT NULL                                      | Senha do usuário                             |
| disabled     | TINYINT | DEFAULT 0                                         | Indicador de status de ativação do usuário (0 = ativo, 1 = inativo) |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Profile`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos usuários podem estar associados a um perfil.

- Relacionamento com `Tb_Employees`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um usuário pode estar associado a um funcionário. O campo `id_user` na tabela `Tb_Employees` faz referência ao `id` da tabela `Tb_Users`.

---

### 3. Tb_Employees

**DESCRIÇÃO:**  
A tabela `Tb_Employees` armazena os dados dos colaboradores, incluindo informações pessoais, contato, setor, função e agência. Cada funcionário está vinculado a um usuário e pode estar associado a vários projetos.

**Colunas**

| Coluna       | Tipo de Dados  | Restrições                                | Descrição                                    |
|--------------|----------------|-------------------------------------------|----------------------------------------------|
| id           | BIGINT         | PRIMARY KEY, AUTO_INCREMENT               | Identificador único do funcionário           |
| id_user      | BIGINT         | FOREIGN KEY, NOT NULL                     | Chave estrangeira que referencia `Tb_Users.id` |
| name         | VARCHAR(225)   | NOT NULL                                  | Nome do funcionário                         |
| lastname     | VARCHAR(255)   | NOT NULL                                  | Sobrenome do funcionário                    |
| phonenumber  | VARCHAR(255)   | NOT NULL                                  | Número de telefone do funcionário           |
| email        | VARCHAR(255)   | UNIQUE, NOT NULL                          | Endereço de e-mail do funcionário           |
| sector       | VARCHAR(255)   | NOT NULL                                  | Setor ao qual o funcionário pertence         |
| occupation   | VARCHAR(255)   | NOT NULL                                  | Cargo ou função do funcionário               |
| agency       | VARCHAR(255)   | NOT NULL                                  | Agência associada ao funcionário            |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Users`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um funcionário está associado a um único usuário. O campo `id_user` na tabela `Tb_Employees` faz referência ao `id` da tabela `Tb_Users`.

- Relacionamento com `Tb_Projects`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um funcionário pode estar associado a vários projetos. O relacionamento é representado por um "0" (zero) na ponta de `Tb_Employees` e um "para muitos" na ponta de `Tb_Projects`.

- Relacionamento com `Tb_DialogBoxes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um funcionário pode estar associado a várias caixas de diálogo.

---

### 4. Tb_RecoveryTokens

**DESCRIÇÃO:**  
A tabela `Tb_RecoveryTokens` armazena os tokens de recuperação de senha gerados para usuários que solicitam a redefinição de suas senhas. Cada token é associado a um usuário específico e tem uma validade limitada.

**Colunas**

| Coluna       | Tipo de Dados  | Descrição                                    |
|--------------|----------------|----------------------------------------------|
| id           | BIGINT         | Identificador único do token. Chave primária |
| token        | VARCHAR(255)   | Token de recuperação de senha                |
| id_user      | BIGINT         | Identificador do usuário associado. Chave estrangeira referenciando `Tb_Users.id` |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Users`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Vários tokens podem estar associados a um único usuário. O campo `id_user` na tabela `Tb_RecoveryTokens` faz referência ao `id` da tabela `Tb_Users`, indicando que cada token pertence a um usuário específico.

---
## Tabela de Dados Gerais dos Projetos

### 5. Tb_Projects

**DESCRIÇÃO:**  
A tabela `Tb_Projects` armazena os dados dos projetos no sistema, incluindo informações como título, descrição, status, progresso, tipo de briefing e o status de habilitação. Cada projeto pode estar vinculado a um colaborador e a um cliente, e pode ter várias versões.

**Colunas**

| Coluna         | Tipo de Dados  | Restrições                                        | Descrição                                      |
|----------------|----------------|---------------------------------------------------|------------------------------------------------|
| id             | BIGINT         | PRIMARY KEY, AUTO_INCREMENT                       | Identificador único do projeto                 |
| id_collaborator | BIGINT         | FOREIGN KEY (referência `Tb_Employees.id`)       | Identificador do colaborador associado ao projeto |
| id_client      | BIGINT         | FOREIGN KEY (referência `Tb_Clients.id`)         | Identificador do cliente associado ao projeto  |
| title          | VARCHAR(255)   | NOT NULL                                          | Título do projeto                              |
| description    | VARCHAR(255)   |                                                   | Descrição do projeto                           |
| status         | VARCHAR(255)   |                                                   | Status atual do projeto (por exemplo, "Em andamento", "Finalizado") |
| progress       | INT            |                                                   | Percentual de progresso do projeto             |
| briefing_type  | INT            |                                                   | Tipo de briefing associado ao projeto          |
| disabled       | TINYINT        |                                                   | Status de habilitação do projeto (0 para ativo, 1 para desativado) |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Employees`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos projetos podem estar associados a um único colaborador. O campo `id_collaborator` na tabela `Tb_Projects` faz referência ao `id` da tabela `Tb_Employees`. Nota: No diagrama de relacionamento, o "0" associado ao relacionamento "Um para Muitos" entre `Tb_Employees` e `Tb_Projects` indica que um colaborador (registro em `Tb_Employees`) pode não estar associado a nenhum projeto ou pode estar associado a vários projetos. Portanto, é um relacionamento opcional, permitindo flexibilidade na associação de colaboradores a projetos.

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um projeto pode ter um briefing associado. O campo `id_project` em `Tb_Briefings` faz referência ao `id` na tabela `Tb_Projects`.

- Relacionamento com `Tb_Versions`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um projeto pode ter várias versões. O campo `id_project` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Projects`.

---

### 6. Tb_Versions

**DESCRIÇÃO:**  
A tabela `Tb_Versions` armazena as diferentes versões de um projeto, incluindo informações detalhadas, como o título, feedback recebido, datas de início e término esperadas e reais, número da versão, link do produto, e o status de aprovação por parte do cliente e do supervisor. A tabela também pode armazenar informações adicionais sobre o colaborador responsável pela versão e qualquer empresa terceirizada envolvida.

**Colunas**

| Coluna              | Tipo de Dados  | Descrição                                        |
|---------------------|----------------|--------------------------------------------------|
| id                  | BIGINT         | Identificador único da versão. Chave primária    |
| id_project          | BIGINT         | Identificador do projeto associado. Chave estrangeira referenciando `Tb_Projects.id` |
| title               | VARCHAR(255)   | Título da versão                                |
| feedback            | TEXT           | Feedback recebido sobre a versão                |
| begin               | DATE           | Data de início da versão                        |
| expected_done       | DATE           | Data esperada para a conclusão da versão        |
| real_done           | DATE           | Data real de conclusão da versão                |
| num_version         | INT            | Número da versão                                |
| product_link        | VARCHAR(255)   | Link para o produto associado à versão          |
| client_approve      | TINYINT        | Status de aprovação do cliente (0 para não aprovado, 1 para aprovado) |
| detailed_description | TEXT          | Descrição detalhada da versão                   |
| supervisor_approve  | TINYINT        | Status de aprovação do supervisor (0 para não aprovado, 1 para aprovado) |
| collaborator        | VARCHAR(255)   | Nome do colaborador associado à versão          |
| other_company       | VARCHAR(255)   | Nome de outra empresa envolvida, se aplicável   |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Projects`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitas versões podem estar associadas a um único projeto. O campo `id_project` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Projects`.

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um briefing pode estar associado a várias versões. O campo `id_briefing` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Briefings`.

---
### 7. Tb_DialogBoxes

**DESCRIÇÃO:**  
A tabela `Tb_DialogBoxes` armazena registros de diálogos ou conversas relacionados a um briefing e a um colaborador. Cada entrada na tabela contém informações como o identificador do colaborador, o identificador do briefing associado, a data e hora do diálogo, e o conteúdo textual da conversa. Essa tabela é útil para rastrear e gerenciar comunicações e interações durante o processo de desenvolvimento de projetos.

**Colunas**

| Coluna           | Tipo de Dados | Descrição                                       |
|------------------|---------------|-------------------------------------------------|
| id               | BIGINT        | Identificador único do diálogo. Chave primária  |
| id_employee      | BIGINT        | Identificador do colaborador associado. Chave estrangeira referenciando `Tb_Employees.id` |
| id_briefing      | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| time             | DATETIME      | Data e hora em que o diálogo ocorreu           |
| dialog           | TEXT          | Conteúdo textual do diálogo ou conversa         |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Employees`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos diálogos podem estar associados a um único colaborador. O campo `id_employee` na tabela `Tb_DialogBoxes` faz referência ao `id` da tabela `Tb_Employees`.

- Relacionamento com `Tb_Briefings`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos diálogos podem estar associados a um único briefing. O campo `id_briefing` na tabela `Tb_DialogBoxes` faz referência ao `id` da tabela `Tb_Briefings`.

---

### 8. Tb_BriefingType

**DESCRIÇÃO:**  
A tabela `Tb_BriefingType` armazena os diferentes tipos de briefings utilizados no sistema. Os tipos de briefing ajudam a categorizar e organizar os briefings de acordo com sua natureza ou finalidade, como "Marketing", "Desenvolvimento de Produto", "Pesquisa", entre outros. Essa categorização permite uma melhor gestão e organização dos briefings dentro da empresa.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                               |
|----------------|---------------|-----------------------------------------|
| id             | BIGINT        | Identificador único do tipo de briefing. Chave primária |
| description    | VARCHAR(255)  | Descrição do tipo de briefing (ex.: "Marketing", "Pesquisa") |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de briefing pode estar associado a vários briefings. O campo `id_briefing_type` na tabela `Tb_Briefings` faz referência ao `id` da tabela `Tb_BriefingType`.

---

### 9. Tb_Briefings

**DESCRIÇÃO:**  
A tabela `Tb_Briefings` armazena informações detalhadas sobre briefings que são realizados em projetos. Um briefing é um documento ou reunião que especifica os requisitos, objetivos, cronograma e outras informações relevantes sobre um projeto. Os briefings podem estar associados a projetos, tipos de briefings, campanhas internas, itens impressos, medições e outros elementos, conforme necessário.

**Colunas**

| Coluna               | Tipo de Dados | Descrição                                       |
|----------------------|---------------|-------------------------------------------------|
| id                   | BIGINT        | Identificador único do briefing. Chave primária |
| id_project           | BIGINT        | Identificador do projeto associado. Chave estrangeira referenciando `Tb_Projects.id` |
| id_briefing_types    | BIGINT        | Identificador do tipo de briefing. Chave estrangeira referenciando `Tb_BriefingTypes.id` |
| start_time           | DATE          | Data de início do briefing                      |
| finish_time          | DATE          | Data de término do briefing                     |
| detailed_description | TEXT          | Descrição detalhada do briefing                 |
| other_company        | VARCHAR(255)  | Nome de outra empresa envolvida, se aplicável   |

**RELACIONAMENTOS**

- Relacionamento com `Tb_DialogBoxes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um briefing pode ter várias entradas de diálogos associadas. O campo `id_briefing` na tabela `Tb_DialogBoxes` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_BriefingTypes`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos briefings podem estar associados a um tipo de briefing. O campo `id_briefing_types` na tabela `Tb_Briefings` faz referência ao `id` da tabela `Tb_BriefingTypes`.

- Relacionamento com `Tb_BPrinteds`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter um item impresso associado. O campo `id_briefing` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_Measureme`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter uma medida associada. O campo `id_briefing` na tabela `Tb_Measureme` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_Versions`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um briefing pode ter várias versões associadas. O campo `id_briefing` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_Projects`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos briefings podem estar associados a um único projeto. O campo `id_project` na tabela `Tb_Briefings` faz referência ao `id` da tabela `Tb_Projects`.

- Relacionamento com `Tb_BInternalCampaigns`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter uma campanha interna associada. O campo `id_briefing` na tabela `Tb_BInternalCampaigns` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_CompaniesBriefings`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um briefing pode estar associado a várias companhias. O campo `id_briefing` na tabela `Tb_CompaniesBriefings` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_BHandouts`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter um folheto associado. O campo `id_briefing` na tabela `Tb_BHandouts` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_Signposts`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter uma sinalização associada. O campo `id_briefing` na tabela `Tb_Signposts` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_BStickers`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter um adesivo associado. O campo `id_briefing` na tabela `Tb_BStickers` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_BGifts`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter um presente associado. O campo `id_briefing` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_BAgencyBoards`:
  - Tipo: Um para Um (1:1)
  - Descrição: Um briefing pode ter um quadro de agência associado. O campo `id_briefing` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_Briefings`.

---

### 10. Tb_Measuremet

**DESCRIÇÃO:**  
A tabela `Tb_Measuremet` armazena as medições relacionadas a um briefing específico. Isso pode incluir medidas de altura e comprimento, úteis para itens que exigem especificações detalhadas, como banners, impressos, sinalizações, entre outros.

**Colunas**

| Coluna      | Tipo de Dados | Descrição                                             |
|-------------|---------------|-------------------------------------------------------|
| id          | INT           | Identificador único da medição. Chave primária        |
| id_briefing  | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| height       | FLOAT(53)     | Altura da medida associada ao briefing               |
| length       | FLOAT(53)     | Comprimento da medida associada ao briefing           |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada medição está associada a um único briefing. O campo `id_briefing` na tabela `Tb_Measuremet` faz referência ao `id` da tabela `Tb_Briefings`.

---

### 11. Tb_GiftType

**DESCRIÇÃO:**  
A tabela `Tb_GiftType` armazena os diferentes tipos de presentes que podem ser utilizados nas campanhas ou atividades da empresa. Isso permite categorizar os presentes de acordo com suas características, como brindes personalizados, cartões de agradecimento, entre outros.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                      |
|----------------|---------------|------------------------------------------------|
| id             | BIGINT        | Identificador único do tipo de presente. Chave primária |
| description    | VARCHAR(255)  | Descrição do tipo de presente                  |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BGifts`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de presente pode estar associado a vários presentes específicos registrados na tabela `Tb_BGifts`. O campo `id_gift_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_GiftType`.

---

### 12. Tb_BGifts

**DESCRIÇÃO:**  
A tabela `Tb_BGifts` armazena informações sobre presentes específicos associados a campanhas, incluindo detalhes sobre o modelo do presente, tipo de impressão e outros atributos relacionados.

**Colunas**

| Coluna                  | Tipo de Dados | Descrição                                                      |
|-------------------------|---------------|------------------------------------------------------------------|
| id                      | BIGINT        | Identificador único do presente. Chave primária                 |
| id_briefing             | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| id_gift_type            | BIGINT        | Identificador do tipo de presente. Chave estrangeira referenciando `Tb_GiftType.id` |
| id_printing_type        | BIGINT        | Identificador do tipo de impressão. Chave estrangeira referenciando `Tb_PrintingType.id` |
| id_printing_shirt_type  | BIGINT        | Identificador do tipo de impressão de camiseta. Chave estrangeira referenciando `Tb_PrintingShirtType.id` |
| id_stamp                | BIGINT        | Identificador do carimbo associado. Chave estrangeira referenciando `Tb_Stamps.id` |
| id_calendar_type        | BIGINT        | Identificador do tipo de calendário. Chave estrangeira referenciando `Tb_CalendarType.id` |
| gift_model              | VARCHAR(255)  | Modelo do presente                                              |
| link_model              | VARCHAR(255)  | Link para o modelo do presente                                 |

**RELACIONAMENTOS**

- Relacionamento com `Tb_GiftType`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos presentes podem estar associados a um tipo de presente específico. O campo `id_gift_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_GiftType`. Este relacionamento é obrigatório.

- Relacionamento com `Tb_CalendarType`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos presentes podem estar associados a um tipo de calendário específico. O campo `id_calendar_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_CalendarType`. Este relacionamento é opcional.

- Relacionamento com `Tb_Stamps`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos presentes podem estar associados a um carimbo específico. O campo `id_stamp` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_Stamps`. Este relacionamento é opcional.

- Relacionamento com `Tb_PrintingShirtType`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos presentes podem estar associados a um tipo específico de impressão em camiseta. O campo `id_printing_shirt_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_PrintingShirtType`. Este relacionamento é opcional.

---

### 13. Tb_CalendarType

**DESCRIÇÃO:**  
A tabela `Tb_CalendarType` armazena informações sobre os tipos de calendários disponíveis, que podem ser associados a vários presentes.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                 |
|----------------|---------------|-------------------------------------------|
| id             | BIGINT        | Identificador único do tipo de calendário. Chave primária |
| description    | VARCHAR(255)  | Descrição do tipo de calendário            |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BGifts`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de calendário pode estar associado a vários presentes. O campo `id_calendar_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_CalendarType`.
  - Opcionalidade: Nem todos os registros em `Tb_BGifts` precisam ter um `id_calendar_type` associado.

---

### 14. Tb_Stamps

**DESCRIÇÃO:**  
A tabela `Tb_Stamps` armazena informações sobre os tipos de selos disponíveis, que podem ser associados a vários presentes.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                   |
|---------------|---------------|---------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de selo. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de selo                   |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BGifts`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de selo pode estar associado a vários presentes. O campo `id_stamp` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_Stamps`.
  - Opcionalidade: Nem todos os registros em `Tb_BGifts` precisam ter um `id_stamp` associado.

---

### 15. Tb_PrintingShirtType

**DESCRIÇÃO:**  
A tabela `Tb_PrintingShirtType` armazena informações sobre os tipos de camisetas para impressão disponíveis, que podem ser associados a presentes.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                       |
|---------------|---------------|-------------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de camiseta. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de camiseta para impressão    |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BGifts`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de camiseta para impressão pode estar associado a vários presentes. O campo `id_printing_shirt_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_PrintingShirtType`.
  - Opcionalidade: Nem todos os registros em `Tb_BGifts` precisam ter um `id_printing_shirt_type` associado.

---

### 16. Tb_PrintingType

**DESCRIÇÃO:**  
A tabela `Tb_PrintingType` armazena informações sobre os tipos de impressão disponíveis, que podem ser associados a vários presentes e também a impressões de brindes.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                 |
|---------------|---------------|-------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de impressão. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de impressão             |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BGifts`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de impressão pode estar associado a vários presentes. O campo `id_printing_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_PrintingType`.
  - Opcionalidade: Nem todos os registros em `Tb_BGifts` precisam ter um `id_printing_type` associado.

- Relacionamento com `Tb_BPrinteds`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de impressão pode estar associado a várias impressões de brindes. O campo `id_printing_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintingType`.
  - Opcionalidade: Nem todos os registros em `Tb_BPrinteds` precisam ter um `id_printing_type` associado.

  ---
  ### 17. Tb_BPrinteds

**DESCRIÇÃO:**  
A tabela `Tb_BPrinteds` armazena informações sobre as impressões de brindes, incluindo o tipo de impressão, o tipo de papel e as especificações da impressão. Cada impressão está associada a um briefing e pode ter um tipo de impressão específico.

**Colunas**

| Coluna          | Tipo de Dados | Descrição                                           |
|-----------------|---------------|-----------------------------------------------------|
| id              | BIGINT        | Identificador único da impressão. Chave primária    |
| id_briefing     | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| id_printed_type | BIGINT        | Identificador do tipo de impressão. Chave estrangeira referenciando `Tb_PrintingType.id` |
| id_printing_type | BIGINT        | Identificador do tipo de impressão. Chave estrangeira referenciando `Tb_PrintingType.id` |
| paper_type      | VARCHAR(80)   | Tipo de papel usado na impressão                   |
| folds           | INT           | Número de dobras na impressão                      |
| pages           | INT           | Número de páginas na impressão                     |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Versions`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada impressão de brinde pode estar associada a uma versão específica. O campo `id` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_Versions`.

- Relacionamento com `Tb_PrintingType`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Vários registros em `Tb_BPrinteds` podem estar associados a um tipo de impressão específico. O campo `id_printing_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintingType`.
  - Opcionalidade: Nem todos os registros em `Tb_BPrinteds` precisam ter um `id_printing_type` associado.

- Relacionamento com `Tb_PrintedType`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Vários registros em `Tb_BPrinteds` podem estar associados a um tipo de impresso específico. O campo `id_printed_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintedType`.

---

### 18. Tb_PrintedType

**DESCRIÇÃO:**  
A tabela `Tb_PrintedType` armazena informações sobre os tipos de impressões que podem ser aplicadas aos produtos impressos.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                   |
|---------------|---------------|---------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de impressão. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de impressão              |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BPrinteds`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de impressão pode estar associado a várias impressões de brindes. O campo `id_printed_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintedType`.

---

### 19. Tb_BAgencyBoards

**DESCRIÇÃO:**  
A tabela `Tb_BAgencyBoards` armazena informações sobre os painéis de agência, incluindo o tipo de painel, a localização e observações relacionadas. Cada painel pode estar associado a um briefing e pode estar relacionado a várias rotas e outras rotas.

**Colunas**

| Coluna            | Tipo de Dados | Descrição                                         |
|-------------------|---------------|---------------------------------------------------|
| id                | BIGINT        | Identificador único do tipo de agência de quadro. Chave primária |
| description       | VARCHAR(255)  | Descrição do tipo de agência de quadro            |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Versions`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada painel de agência pode estar associado a uma versão específica. O campo `id` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_Versions`.

- Relacionamento com `Tb_Routes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um painel de agência pode estar associado a várias rotas. O campo `id` na tabela `Tb_Routes` faz referência ao `id_board_type` da tabela `Tb_BAgencyBoards`.

- Relacionamento com `Tb_OtherRoutes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um painel de agência pode estar associado a várias outras rotas. O campo `id` na tabela `Tb_OtherRoutes` faz referência ao `id_board_type` da tabela `Tb_BAgencyBoards`.

- Relacionamento com `Tb_AgencyBoardTypes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de painel de agência pode estar associado a vários painéis de agência. O campo `id_agency_board_type` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_AgencyBoardTypes`.

- Relacionamento com `Tb_BoardTypes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de painel pode estar associado a vários painéis de agência. O campo `id_board_type` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_BoardTypes`.
  - Opcionalidade: Nem todos os registros em `Tb_BAgencyBoards` precisam ter um `id_board_type` associado.

---
### 20. Tb_BoardType

**DESCRIÇÃO:**  
A tabela `Tb_BoardType` armazena informações sobre os tipos de painéis disponíveis, que podem ser associados a vários painéis de agência.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                   |
|---------------|---------------|---------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de painel. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de painel                  |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BAgencyBoards`:
  - Tipo: Zero ou Um para Muitos (0:N)
  - Descrição: Nem todos os tipos de painéis precisam estar associados a um painel de agência. O campo `id_board_type` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_BoardType`.
  - Opcionalidade: Nem todos os registros em `Tb_BAgencyBoards` precisam ter um `id_board_type` associado.

---

### 21. Tb_OthersRoutes

**DESCRIÇÃO:**  
A tabela `Tb_OthersRoutes` armazena informações sobre rotas adicionais associadas a painéis de agência, incluindo dados sobre a empresa, cidade e tipo de rota.

**Colunas**

| Coluna            | Tipo de Dados | Descrição                                        |
|-------------------|---------------|--------------------------------------------------|
| id                | BIGINT        | Identificador único da rota adicional. Chave primária |
| id_b_agency_board | BIGINT        | Identificador do painel de agência associado. Chave estrangeira referenciando `Tb_BAgencyBoards.id` |
| company           | VARCHAR(50)   | Nome da empresa associada à rota                |
| city              | VARCHAR(50)   | Cidade associada à rota                         |
| type              | VARCHAR(30)   | Tipo de rota adicional                           |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BAgencyBoards`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um painel de agência pode estar associado a várias rotas adicionais. O campo `id_b_agency_board` na tabela `Tb_OthersRoutes` faz referência ao `id` da tabela `Tb_BAgencyBoards`.

---

### 22. Tb_Routes

**DESCRIÇÃO:**  
A tabela `Tb_Routes` armazena informações sobre rotas associadas a painéis de agência, incluindo dados sobre o painel de agência, a empresa ou cidade e o tipo de rota.

**Colunas**

| Coluna             | Tipo de Dados | Descrição                                        |
|--------------------|---------------|--------------------------------------------------|
| id                 | INT           | Identificador único da rota. Chave primária     |
| id_b_agency_board  | BIGINT        | Identificador do painel de agência associado. Chave estrangeira referenciando `Tb_BAgencyBoards.id` |
| id_city_company    | BIGINT        | Identificador da empresa ou cidade associada. Chave estrangeira referenciando `Tb_CompaniesCities.id` |
| type               | VARCHAR(255)  | Tipo de rota                                    |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BAgencyBoards`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um painel de agência pode estar associado a várias rotas. O campo `id_b_agency_board` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_BAgencyBoards`.

- Relacionamento com `Tb_CompaniesCities`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa ou cidade pode estar associada a várias rotas. O campo `id_city_company` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_CompaniesCities`.

---

### 23. Tb_CompaniesCities

**DESCRIÇÃO:**  
A tabela `Tb_CompaniesCities` armazena a relação entre empresas e cidades, associando cada cidade a uma empresa específica. Esta tabela é usada para gerenciar as rotas associadas às empresas e cidades.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                           |
|---------------|---------------|-----------------------------------------------------|
| id            | BIGINT        | Identificador único da relação empresa-cidade. Chave primária |
| id_city       | BIGINT        | Identificador da cidade associada. Chave estrangeira referenciando `Tb_Cities.id` |
| id_company    | BIGINT        | Identificador da empresa associada. Chave estrangeira referenciando `Tb_Companies.id` |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Routes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma cidade ou empresa pode estar associada a várias rotas. O campo `id_city_company` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_CompaniesCities`.

- Relacionamento com `Tb_Cities`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma cidade pode estar associada a várias relações empresa-cidade. O campo `id_city` na tabela `Tb_CompaniesCities` faz referência ao `id` da tabela `Tb_Cities`.

- Relacionamento com `Tb_Companies`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa pode estar associada a várias relações empresa-cidade. O campo `id_company` na tabela `Tb_CompaniesCities` faz referência ao `id` da tabela `Tb_Companies`.

---
### 24. Tb_Cities

**DESCRIÇÃO:**  
A tabela `Tb_Cities` contém informações sobre as cidades. Cada cidade pode estar associada a várias empresas por meio da tabela `Tb_CompaniesCities`, que relaciona cidades e empresas para operações logísticas e outros fins.

**Colunas**

| Coluna | Tipo de Dados | Descrição                                 |
|--------|---------------|-------------------------------------------|
| id     | INT           | Identificador único da cidade. Chave primária |
| name   | VARCHAR(255)  | Nome da cidade                            |

**RELACIONAMENTOS**

- Relacionamento com `Tb_CompaniesCities`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma cidade pode estar associada a várias empresas. O campo `id_city` na tabela `Tb_CompaniesCities` faz referência ao `id` da tabela `Tb_Cities`, indicando que várias entradas na tabela `Tb_CompaniesCities` podem estar associadas a uma única cidade.

---

### 25. Tb_Companies

**DESCRIÇÃO:**  
A tabela `Tb_Companies` armazena informações sobre as empresas. Esta tabela é usada para associar empresas a cidades e briefings.

**Colunas**

| Coluna | Tipo de Dados | Descrição                                 |
|--------|---------------|-------------------------------------------|
| id     | BIGINT        | Identificador único da empresa. Chave primária |
| name   | VARCHAR(255)  | Nome da empresa                           |

**RELACIONAMENTOS**

- Relacionamento com `Tb_CompaniesCities`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa pode estar associada a várias cidades. O campo `id_company` na tabela `Tb_CompaniesCities` faz referência ao `id` da tabela `Tb_Companies`, indicando que uma entrada na tabela `Tb_Companies` pode estar associada a várias entradas na tabela `Tb_CompaniesCities`.

- Relacionamento com `Tb_CompaniesBriefings`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa pode estar associada a vários briefings. O campo `id_company` na tabela `Tb_CompaniesBriefings` faz referência ao `id` da tabela `Tb_Companies`.

---

### 26. Tb_AgencyBoardTypes

**DESCRIÇÃO:**  
A tabela `Tb_AgencyBoardTypes` armazena informações sobre os tipos de painéis de agência disponíveis. Cada tipo de painel pode estar associado a vários painéis de agência.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                       |
|---------------|---------------|-------------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de painel. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de painel                      |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BAgencyBoards`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de painel de agência pode estar associado a vários painéis de agência. O campo `id_agency_board_type` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_AgencyBoardTypes`.

---

### 27. Tb_Handout

**DESCRIÇÃO:**  
A tabela `Tb_Handout` armazena informações sobre os materiais de distribuição. Cada material de distribuição está associado a um tipo de material e a um briefing.

**Colunas**

| Coluna          | Tipo de Dados | Descrição                                  |
|-----------------|---------------|--------------------------------------------|
| id              | BIGINT        | Identificador único do material de distribuição. Chave primária |
| id_handout_type | BIGINT        | Identificador do tipo de material. Chave estrangeira referenciando `Tb_HandoutType.id` |
| id_briefing     | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada material de distribuição está associado a um único briefing. O campo `id_briefing` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_HandoutType`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material pode estar associado a vários materiais de distribuição. O campo `id_handout_type` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_HandoutType`.

  ---

  ### 28. Tb_HandoutType

**DESCRIÇÃO:**  
A tabela `Tb_HandoutType` armazena os tipos de materiais de distribuição disponíveis. Cada tipo de material pode estar associado a vários materiais de distribuição.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                    |
|----------------|---------------|----------------------------------------------|
| id             | BIGINT        | Identificador único do tipo de material. Chave primária |
| description    | VARCHAR(255)  | Descrição do tipo de material de distribuição |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Handout`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material pode estar associado a vários materiais de distribuição. O campo `id_handout_type` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_HandoutType`.

---

### 29. Tb_BInternalCampaigns

**DESCRIÇÃO:**  
A tabela `Tb_BInternalCampaigns` armazena informações sobre campanhas internas, incluindo o tipo de material de escritório, itens adicionais e detalhes da campanha. Cada campanha está vinculada a um briefing específico e pode incluir vários itens adicionais e tipos de material de escritório.

**Colunas**

| Coluna               | Tipo de Dados | Descrição                                       |
|----------------------|---------------|-------------------------------------------------|
| id                   | BIGINT        | Identificador único da campanha. Chave primária |
| id_stationery_type   | BIGINT        | Identificador do tipo de material de escritório associado. Chave estrangeira referenciando `Tb_StationeryType.id` |
| id_other_items       | BIGINT        | Identificador dos itens adicionais associados. Chave estrangeira referenciando `Tb_OtherItems.id` |
| id_briefing          | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| campaign_motto       | TEXT          | Slogan ou lema da campanha                     |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada campanha está associada a um briefing específico. O campo `id_briefing` na tabela `Tb_BInternalCampaigns` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_StationeryType`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material de escritório pode estar associado a várias campanhas internas. O campo `id_stationery_type` na tabela `Tb_BInternalCampaigns` faz referência ao `id` da tabela `Tb_StationeryType`.

- Relacionamento com `Tb_OtherItems`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Itens adicionais podem estar associados a várias campanhas internas. O campo `id_other_items` na tabela `Tb_BInternalCampaigns` faz referência ao `id` da tabela `Tb_OtherItems`.

---

### 30. Tb_StationeryType

**DESCRIÇÃO:**  
A tabela `Tb_StationeryType` armazena diferentes tipos de material de papelaria utilizados em campanhas e projetos internos. Cada tipo de material pode estar associado a vários itens internos de empresas.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                    |
|----------------|---------------|----------------------------------------------|
| id             | BIGINT        | Identificador único do tipo de material. Chave primária |
| description    | VARCHAR(255)  | Descrição do tipo de material                |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BInternalCampaigns`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material pode estar associado a várias campanhas internas. O campo `id_stationery_type` na tabela `Tb_BInternalCampaigns` faz referência ao `id` da tabela `Tb_StationeryType`.

---
### 31. Tb_OtherItems

**DESCRIÇÃO:**  
A tabela `Tb_OtherItems` armazena informações sobre itens adicionais ou acessórios que podem ser associados a campanhas internas. Cada item pode estar vinculado a várias campanhas internas.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                    |
|----------------|---------------|----------------------------------------------|
| id             | BIGINT        | Identificador único do item adicional. Chave primária |
| description    | VARCHAR(255)  | Descrição do item adicional                  |

**RELACIONAMENTOS**

- Relacionamento com `Tb_BInternalCampaigns`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um item adicional pode estar associado a vários itens internos de empresas. O campo `id_other_items` na tabela `Tb_BInternalCampaigns` faz referência ao `id` da tabela `Tb_OtherItems`.

---

### 32. Tb_Signpost

**DESCRIÇÃO:**  
A tabela `Tb_Signpost` armazena informações sobre sinalizações, incluindo sua localização e setor associado. Cada sinalização está vinculada a um briefing e pode estar associada a vários materiais.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                    |
|----------------|---------------|----------------------------------------------|
| id             | BIGINT        | Identificador único da sinalização. Chave primária |
| id_material    | BIGINT        | Identificador do material associado. Chave estrangeira referenciando `Tb_Materials.id` |
| id_briefing     | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| board_location | VARCHAR(255)  | Localização do painel                        |
| sector          | VARCHAR(255)  | Setor ao qual a sinalização está associada   |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada sinalização está associada a um briefing. O campo `id_briefing` na tabela `Tb_Signpost` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_Materials`:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Vários sinais podem estar associados a um único material. O campo `id_material` na tabela `Tb_Signpost` faz referência ao `id` da tabela `Tb_Materials`.

---

### 33. Tb_Materials

**DESCRIÇÃO:**  
A tabela `Tb_Materials` armazena informações sobre materiais que podem ser utilizados em sinalizações. Cada material pode ser associado a várias sinalizações.

**Colunas**

| Coluna         | Tipo de Dados | Descrição                                    |
|----------------|---------------|----------------------------------------------|
| id             | BIGINT        | Identificador único do material. Chave primária |
| description    | VARCHAR(255)  | Descrição do material                        |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Signpost`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um material pode ser associado a várias sinalizações. O campo `id_material` na tabela `Tb_Signpost` faz referência ao `id` da tabela `Tb_Materials`.

---

### 34. Tb_Bstickers

**DESCRIÇÃO:**  
A tabela `Tb_Bstickers` armazena informações sobre adesivos, incluindo o tipo de adesivo, informações adicionais e o briefing associado. Cada adesivo pode estar vinculado a um briefing específico e pode ter diferentes tipos de adesivos e informações de adesivos.

**Colunas**

| Coluna                     | Tipo de Dados | Descrição                                      |
|----------------------------|---------------|------------------------------------------------|
| id                         | BIGINT        | Identificador único do adesivo. Chave primária |
| id_sticker_type            | BIGINT        | Identificador do tipo de adesivo. Chave estrangeira referenciando `Tb_StickerTypes.id` |
| id_briefing                | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |
| id_sticker_information_type| BIGINT        | Identificador do tipo de informação do adesivo. Chave estrangeira referenciando `Tb_StickerInformationTypes.id` |
| sector                     | VARCHAR(255)  | Setor onde o adesivo será utilizado            |
| observations               | TEXT          | Observações adicionais sobre o adesivo         |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada adesivo está associado a um único briefing. O campo `id_briefing` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_StickerTypes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de adesivo pode estar associado a vários adesivos. O campo `id_sticker_type` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_StickerTypes`.

- Relacionamento com `Tb_StickerInformationTypes`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de informação do adesivo pode estar associado a vários adesivos. O campo `id_sticker_information_type` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_StickerInformationTypes`.

---

### 35. Tb_StickerInformationType

**DESCRIÇÃO:**  
A tabela `Tb_StickerInformationTypes` armazena informações sobre os diferentes tipos de informações que podem ser associadas aos adesivos. Cada tipo de informação pode ser utilizado em vários adesivos.

**Colunas**

| Coluna                     | Tipo de Dados | Descrição                                      |
|----------------------------|---------------|------------------------------------------------|
| id                         | BIGINT        | Identificador único do tipo de informação do adesivo. Chave primária |
| description                | VARCHAR(255)  | Descrição do tipo de informação do adesivo      |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Bstickers`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de informação de adesivo pode estar associado a vários adesivos. O campo `id_sticker_information_type` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_StickerInformationTypes`.
  - Opcionalidade: Nem todos os adesivos precisam ter um tipo de informação associado.

---
### 36. Tb_StickerTypes

**DESCRIÇÃO:**  
A tabela `Tb_StickerTypes` armazena informações sobre os diferentes tipos de adesivos disponíveis. Cada tipo de adesivo pode ser associado a vários adesivos em projetos diferentes.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                |
|---------------|---------------|------------------------------------------|
| id            | BIGINT        | Identificador único do tipo de adesivo. Chave primária |
| description   | VARCHAR(255)  | Descrição do tipo de adesivo              |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Bstickers`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de adesivo pode estar associado a vários adesivos. O campo `id_sticker_type` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_StickerTypes`.

---

### 37. Tb_CompaniesBriefings

**DESCRIÇÃO:**  
A tabela `Tb_CompaniesBriefings` armazena a associação entre empresas e briefings. Cada empresa pode estar vinculada a vários briefings, e cada briefing pode envolver várias empresas.

**Colunas**

| Coluna        | Tipo de Dados | Descrição                                    |
|---------------|---------------|----------------------------------------------|
| id            | BIGINT        | Identificador único da associação. Chave primária |
| id_company    | BIGINT        | Identificador da empresa associada. Chave estrangeira referenciando `Tb_Companies.id` |
| id_briefing   | BIGINT        | Identificador do briefing associado. Chave estrangeira referenciando `Tb_Briefings.id` |

**RELACIONAMENTOS**

- Relacionamento com `Tb_Briefings`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um briefing pode estar associado a várias empresas. O campo `id_briefing` na tabela `Tb_CompaniesBriefings` faz referência ao `id` da tabela `Tb_Briefings`.

- Relacionamento com `Tb_Companies`:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa pode estar associada a vários briefings. O campo `id_company` na tabela `Tb_CompaniesBriefings` faz referência ao `id` da tabela `Tb_Companies`.

---
## Conclusão

Este documento forneceu uma visão detalhada do esquema do banco de dados utilizado no Sistema de Gestão de Projetos do Grupo Brasileiro. A documentação abrangeu as tabelas, colunas, tipos de dados e relacionamentos, fornecendo uma base sólida para entender e gerenciar o banco de dados.

A correta implementação e manutenção deste esquema são cruciais para garantir a integridade dos dados e o desempenho eficiente do sistema. A documentação serve como um guia essencial para desenvolvedores, administradores e outros profissionais envolvidos, facilitando a manutenção e a expansão do sistema conforme necessário.

Para quaisquer dúvidas ou necessidades de atualização, os responsáveis pela elaboração deste documento estão disponíveis para esclarecimentos e suporte.
