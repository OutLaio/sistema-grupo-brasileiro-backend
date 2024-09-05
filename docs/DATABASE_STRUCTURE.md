# DOCUMENTAÇÃO DO BANCO DE DADOS 

## Elaborado por:

Carlos Dias  
Dan Souza  
Laio Rodrigues  
Mikaelle Rubia  
Renata Moreno  

**Versão 1.0**  
**Ano: 2024**

---

## Índice

1. [Tb_Profile](#tb_profile)
2. [Tb_Users](#tb_users)
3. [Tb_Employees](#tb_employees)
4. [Tb_Projects](#tb_projects)
5. [Tb_Versions](#tb_versions)
6. [Tb_BInternalCompany](#tb_binternalcompany)
7. [Tb_StationeryType](#tb_stationerytype)
8. [Tb_OtherItems](#tb_otheritems)
9. [Tb_Measuremet](#tb_measuremet)
10. [Tb_GiftType](#tb_gifttype)
11. [Tb_BGifts](#tb_bgifts)
12. [Tb_CalendarType](#tb_calendartype)
13. [Tb_PrintingLocation](#tb_printinglocation)
14. [Tb_PrintingShirtType](#tb_printingshirttype)
15. [Tb_PrintingType](#tb_printingtype)
16. [Tb_BPrinteds](#tb_bprinteds)
17. [Tb_PrintingType](#tb_printingtype-2)
18. [Tb_PaperType](#tb_papertype)
19. [Tb_BoardAgencyType](#tb_boardagencytype)
20. [Tb_BoardType](#tb_boardtype)
21. [Tb_BAgencyBoards](#tb_bagencyboards)
22. [Tb_OtherRoutes](#tb_otherroutes)
23. [Tb_Routes](#tb_routes)
24. [Tb_Cities](#tb_cities)
25. [Tb_Companies](#tb_companies)
26. [Tb_Companies](#tb_companies-2) 
27. [Tb_CompaniesVersion](#tb_companiesversion)
28. [Tb_Handout](#tb_handout)
29. [Tb_HandoutType](#tb_handouttype)
30. [Tb_Signpost](#tb_signpost)
31. [Tb_Material](#tb_material)
32. [Tb_Bstickers](#tb_bstickers)
33. [Tb_StickerTypes](#tb_stickertypes)



---

## 1. Introdução

Este documento fornece uma descrição detalhada do esquema de banco de dados utilizado no Sistema de Gestão de Projetos do Grupo Brasileiro. O objetivo é oferecer uma visão clara e abrangente das tabelas, relacionamentos e estruturas de dados que suportam o funcionamento do sistema, facilitando a compreensão e manutenção do banco de dados por parte dos desenvolvedores, administradores e outros profissionais envolvidos no projeto.

---


## 1. Tb_Profile

### Descrição

A tabela `Tb_Profile` armazena os diferentes perfis de usuários, como "Colaborador", "Supervisor" ou "Cliente". Esta tabela define o tipo de acesso e permissões que um usuário terá no sistema.

### Colunas

| Coluna       | Tipo    | Restrições                            | Descrição                                |
|--------------|---------|--------------------------------------|------------------------------------------|
| id           | BIGINT  | PRIMARY KEY, AUTO_INCREMENT           | Identificador único do perfil             |
| description  | TEXT    | NOT NULL                              | Descrição do tipo de perfil               |

### Relacionamentos

- **Relacionamento com `Tb_Users`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um perfil pode estar associado a vários usuários.

---

## 2. Tb_Users

### Descrição

A tabela `Tb_Users` contém dados dos usuários do sistema, incluindo informações de autenticação e status de habilitação. Cada usuário pode estar vinculado a um perfil e pode ser associado a vários funcionários.

### Colunas

| Coluna       | Tipo        | Descrição                                              |
|--------------|-------------|--------------------------------------------------------|
| id           | BIGINT      | Identificador único do usuário. Chave primária.        |
| id_profile   | BIGINT      | Identificador do perfil associado. Chave estrangeira referenciando `Tb_Profile.id`. |
| email        | VARCHAR(255)| Endereço de e-mail do usuário.                        |
| password     | VARCHAR(255)| Senha do usuário.                                     |
| disabled     | TINYINT     | Status de habilitação do usuário (0 para ativo, 1 para desativado). |

### Relacionamentos

- **Relacionamento com `Tb_Profile`:**
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos usuários podem estar associados a um perfil.

- **Relacionamento com `Tb_Employees`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Um usuário pode estar associado a um funcionário. O campo `id_user` na tabela `Tb_Employees` faz referência ao `id` da tabela `Tb_Users`.

---

## 3. Tb_Employees

### Descrição

A tabela `Tb_Employees` armazena os dados dos colaboradores, incluindo informações pessoais, contato, setor, função e agência. Cada funcionário está vinculado a um usuário e pode estar associado a vários projetos.

### Colunas

| Coluna       | Tipo        | Descrição                                              |
|--------------|-------------|--------------------------------------------------------|
| id           | BIGINT      | Identificador único do funcionário. Chave primária.    |
| id_user      | BIGINT      | Identificador do usuário associado. Chave estrangeira referenciando `Tb_Users.id`. |
| name         | VARCHAR(225)| Nome do funcionário.                                  |
| lastname     | VARCHAR(255)| Sobrenome do funcionário.                             |
| phonenumber  | VARCHAR(255)| Número de telefone do funcionário.                    |
| email        | VARCHAR(255)| Endereço de e-mail do funcionário.                    |
| sector       | VARCHAR(255)| Setor onde o funcionário atua.                        |
| occupation   | VARCHAR(255)| Cargo ou função do funcionário.                       |
| agency       | VARCHAR(255)| Agência à qual o funcionário está associado.          |

### Relacionamentos

- **Relacionamento com `Tb_Users`:**
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos funcionários podem estar associados a um único usuário. O campo `id_user` na tabela `Tb_Employees` faz referência ao `id` da tabela `Tb_Users`.

- **Relacionamento com `Tb_Projects`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um funcionário pode estar associado a muitos projetos. O campo `id_employee` na tabela `Tb_Projects` faz referência ao `id` da tabela `Tb_Employees`.



---

## 4. Tb_Projects

### Descrição

A tabela `Tb_Projects` armazena os dados dos projetos no sistema, incluindo informações como título, descrição, status, progresso, tipo de briefing e o status de habilitação. Cada projeto pode estar vinculado a um colaborador e a um cliente, e pode ter várias versões.

### Colunas

| Coluna           | Tipo       | Descrição                                           |
|------------------|------------|-----------------------------------------------------|
| id               | BIGINT     | Identificador único do projeto. Chave primária.    |
| id_collaborator   | BIGINT     | Identificador do colaborador associado. Chave estrangeira referenciando `Tb_Employees.id`. |
| id_client        | BIGINT     | Identificador do cliente associado. Chave estrangeira referenciando `Tb_Users.id`. |
| title            | VARCHAR(255)| Título do projeto.                                |
| description      | VARCHAR(255)| Descrição breve do projeto.                        |
| status           | VARCHAR(255)| Status atual do projeto (e.g., "Em andamento", "Concluído"). |
| progress         | INT        | Percentual de progresso do projeto (0 a 100).      |
| briefing_type    | INT        | Tipo de briefing do projeto.                       |
| disabled         | TINYINT     | Status de habilitação do projeto (0 para ativo, 1 para desativado). |

### Relacionamentos

- **Relacionamento com `Tb_Employees`:**
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos projetos podem estar associados a um único colaborador. O campo `id_collaborator` na tabela `Tb_Projects` faz referência ao `id` da tabela `Tb_Employees`.

- **Relacionamento com `Tb_Users` (Clientes):**
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos projetos podem estar associados a um único cliente. O campo `id_client` na tabela `Tb_Projects` faz referência ao `id` da tabela `Tb_Users`.

- **Relacionamento com `Tb_Versions`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um projeto pode ter várias versões. O campo `id_project` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Projects`.

---

## 5. Tb_Versions

### Descrição

A tabela `Tb_Versions` armazena as versões dos projetos, incluindo informações detalhadas sobre o feedback, datas e status de aprovação. Cada versão está vinculada a um projeto e pode ter vários relacionamentos com outras tabelas que representam diferentes aspectos da versão.

### Colunas

| Coluna              | Tipo        | Descrição                                              |
|---------------------|-------------|--------------------------------------------------------|
| id                  | BIGINT      | Identificador único da versão. Chave primária.         |
| id_project          | BIGINT      | Identificador do projeto associado. Chave estrangeira referenciando `Tb_Projects.id`. |
| title               | VARCHAR(255)| Título da versão.                                    |
| feedback            | TEXT        | Feedback recebido sobre a versão.                    |
| begin               | DATE        | Data de início da versão.                            |
| expected_done       | DATE        | Data esperada para a conclusão da versão.            |
| real_done           | DATE        | Data real de conclusão da versão.                    |
| num_version         | INT         | Número da versão.                                    |
| product_link        | VARCHAR(255)| Link para o produto associado à versão.              |
| client_approve      | TINYINT     | Status de aprovação do cliente (0 para não aprovado, 1 para aprovado). |
| detailed_description| TEXT        | Descrição detalhada da versão.                       |
| supervisor_approve  | TINYINT     | Status de aprovação do supervisor (0 para não aprovado, 1 para aprovado). |
| collaborator        | VARCHAR(255)| Nome do colaborador associado à versão.              |
| other_company       | VARCHAR(255)| Nome de outra empresa envolvida, se aplicável.       |

### Relacionamentos

- **Relacionamento com `Tb_BPrinteds`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_BPrinteds`. O campo `id_version` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_Bgifts`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_Bgifts`. O campo `id_version` na tabela `Tb_Bgifts` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_BAgencyBoards`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_BAgencyBoards`. O campo `id_version` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_BStickers`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_BStickers`. O campo `id_version` na tabela `Tb_BStickers` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_Signpost`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_Signpost`. O campo `id_version` na tabela `Tb_Signpost` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_Handout`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_Handout`. O campo `id_version` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_BInternalCompanies`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada versão pode estar associada a um item na tabela `Tb_BInternalCompanies`. O campo `id_version` na tabela `Tb_BInternalCompanies` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_CompaniesVersion`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Cada versão pode ter várias entradas associadas na tabela `Tb_CompaniesVersion`. O campo `id_version` na tabela `Tb_CompaniesVersion` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_Measureme`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Cada versão pode ter várias medições associadas na tabela `Tb_Measureme`. O campo `id_version` na tabela `Tb_Measureme` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_Projects`:**
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitas versões podem estar associadas a um único projeto. O campo `id_project` na tabela `Tb_Versions` faz referência ao `id` da tabela `Tb_Projects`.

---

## 6. Tb_BInternalCompany

### Descrição

A tabela `Tb_BInternalCompany` armazena informações relacionadas a itens internos de empresas, incluindo campanhas e tipos de material. Cada entrada nesta tabela está associada a uma versão específica e pode estar vinculada a diferentes tipos de material e itens adicionais.

### Colunas

| Coluna            | Tipo        | Descrição                                              |
|-------------------|-------------|--------------------------------------------------------|
| id                | BIGINT      | Identificador único do item interno da empresa. Chave primária. |
| id_version        | BIGINT      | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |
| id_stationery_type| BIGINT      | Identificador do tipo de material associado. Chave estrangeira referenciando `Tb_StationeryType.id`. |
| id_other_items    | BIGINT      | Identificador de outros itens relacionados. Chave estrangeira referenciando `Tb_OtherItems.id` (se aplicável). |
| campaign_theme    | VARCHAR(255)| Tema da campanha associado ao item interno da empresa. |

### Relacionamentos

- **Relacionamento com `Tb_Versions`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada item interno da empresa pode estar associado a uma única versão. O campo `id_version` na tabela `Tb_BInternalCompany` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_StationeryType`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material pode estar associado a vários itens internos da empresa. O campo `id_stationery_type` na tabela `Tb_BInternalCompany` faz referência ao `id` da tabela `Tb_StationeryType`.

- **Relacionamento com `Tb_OtherItems`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um item adicional pode estar associado a vários itens internos da empresa. O campo `id_other_items` na tabela `Tb_BInternalCompany` faz referência ao `id` da tabela `Tb_OtherItems`.

---

## 7. Tb_StationeryType

### Descrição

A tabela `Tb_StationeryType` armazena diferentes tipos de material de papelaria utilizados em campanhas e projetos internos. Cada tipo de material pode estar associado a vários itens internos de empresas.

### Colunas

| Coluna      | Tipo        | Descrição                             |
|-------------|-------------|---------------------------------------|
| id          | BIGINT      | Identificador único do tipo de material. Chave primária. |
| description | VARCHAR(255)| Descrição do tipo de material.         |

### Relacionamentos

- **Relacionamento com `Tb_BInternalCompany`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material pode estar associado a vários itens internos de empresas. O campo `id_stationery_type` na tabela `Tb_BInternalCompany` faz referência ao `id` da tabela `Tb_StationeryType`.

---

## 8. Tb_OtherItems

### Descrição

A tabela `Tb_OtherItems` armazena informações sobre itens adicionais ou acessórios que podem ser associados a projetos e campanhas. Cada item adicional pode estar vinculado a vários itens internos de empresas.

### Colunas

| Coluna      | Tipo        | Descrição                             |
|-------------|-------------|---------------------------------------|
| id          | BIGINT      | Identificador único do item adicional. Chave primária. |
| description | VARCHAR(255)| Descrição do item adicional.           |

### Relacionamentos

- **Relacionamento com `Tb_BInternalCompany`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um item adicional pode estar associado a vários itens internos de empresas. O campo `id_other_items` na tabela `Tb_BInternalCompany` faz referência ao `id` da tabela `Tb_OtherItems`.

---
## 9. Tb_Measuremet

### Descrição

A tabela `Tb_Measuremet` armazena medidas relacionadas a versões de projetos, incluindo altura e comprimento. Cada conjunto de medições está associado a uma versão específica.

### Colunas

| Coluna    | Tipo        | Descrição                                    |
|-----------|-------------|----------------------------------------------|
| id        | INT         | Identificador único da medição. Chave primária. |
| height    | FLOAT(53)   | Altura medida.                              |
| length    | FLOAT(53)   | Comprimento medido.                         |
| id_version| BIGINT      | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |

### Relacionamentos

- **Relacionamento com `Tb_Versions`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma versão pode ter várias medições associadas. O campo `id_version` na tabela `Tb_Measuremet` faz referência ao `id` da tabela `Tb_Versions`.

---
## 10. Tb_GiftType

### Descrição

A tabela `Tb_GiftType` armazena informações sobre os tipos de presentes relacionados a versões de projetos. Cada tipo de presente pode ser associado a diferentes tipos de brindes.

### Colunas

| Coluna          | Tipo    | Descrição                                      |
|-----------------|---------|------------------------------------------------|
| id              | BIGINT  | Identificador único do tipo de presente. Chave primária. |
| description     | VARCHAR(255) | Descrição do tipo de presente.                  |

### Relacionamentos

- **Relacionamento com `Tb_BGifts`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de presente pode estar associado a vários brindes. O campo `id_gift_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_GiftType`.

---

## 11. Tb_BGifts

### Descrição

A tabela `Tb_BGifts` armazena informações sobre os brindes relacionados a versões de projetos, incluindo tipos de impressão, camisetas, calendários e outros tipos de presentes. Cada brinde está associado a uma versão específica.

### Colunas

| Coluna                   | Tipo    | Descrição                                      |
|--------------------------|---------|------------------------------------------------|
| id                       | BIGINT  | Identificador único do brinde. Chave primária. |
| id_version               | BIGINT  | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |
| id_printing_type          | BIGINT  | Identificador do tipo de impressão do brinde. Chave estrangeira referenciando `Tb_PrintingType.id`. |
| id_printing_shirt_type    | BIGINT  | Identificador do tipo de impressão para camisetas. Chave estrangeira referenciando `Tb_PrintingShirtType.id`. |
| id_calendar_type          | BIGINT  | Identificador do tipo de calendário. Chave estrangeira referenciando `Tb_CalendarType.id`. |
| id_gift_type              | BIGINT  | Identificador do tipo de presente. Chave estrangeira referenciando `Tb_GiftType.id`. |
| descript_gift            | VARCHAR(255) | Descrição do brinde.                          |
| link_model               | VARCHAR(255) | Link para o modelo do brinde.                 |

### Relacionamentos

- **Relacionamento com `Tb_Versions`:**
  - Tipo: Um para Um (1:1)
  - Descrição: Cada brinde está associado a uma única versão. O campo `id_version` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_Versions`.

---

## 12. Tb_CalendarType

### Descrição

A tabela `Tb_CalendarType` armazena informações sobre os tipos de calendários disponíveis, que podem ser associados a presentes em versões de projetos.

### Colunas

| Coluna          | Tipo    | Descrição                                      |
|-----------------|---------|------------------------------------------------|
| id              | BIGINT  | Identificador único do tipo de calendário. Chave primária. |
| description     | VARCHAR(255) | Descrição do tipo de calendário.               |

### Relacionamentos

- **Relacionamento com `Tb_BGifts`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de calendário pode estar associado a vários brindes. O campo `id_calendar_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_CalendarType`.

---

## 13. Tb_PrintingLocation

### Descrição

A tabela `Tb_PrintingLocation` armazena informações sobre os locais de impressão disponíveis, que podem ser associados a presentes em versões de projetos.

### Colunas

| Coluna          | Tipo    | Descrição                                      |
|-----------------|---------|------------------------------------------------|
| id              | BIGINT  | Identificador único do local de impressão. Chave primária. |
| description     | VARCHAR(255) | Descrição do local de impressão.              |

### Relacionamentos

- **Relacionamento com `Tb_BGifts`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um local de impressão pode estar associado a vários brindes. O campo `id_printing_location` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_PrintingLocation`.

---

## 14. Tb_PrintingShirtType

### Descrição

A tabela `Tb_PrintingShirtType` armazena informações sobre os tipos de camisetas para impressão disponíveis, que podem ser associados a presentes em versões de projetos.

### Colunas

| Coluna          | Tipo    | Descrição                                      |
|-----------------|---------|------------------------------------------------|
| id              | BIGINT  | Identificador único do tipo de camiseta. Chave primária. |
| description     | VARCHAR(255) | Descrição do tipo de camiseta para impressão. |

### Relacionamentos

- **Relacionamento com `Tb_BGifts`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de camiseta para impressão pode estar associado a vários brindes. O campo `id_printing_shirt_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_PrintingShirtType`.

---

## 15. Tb_PrintingType

### Descrição

A tabela `Tb_PrintingType` armazena informações sobre os tipos de impressão disponíveis, que podem ser associados tanto a presentes quanto a impressões de materiais em versões de projetos.

### Colunas

| Coluna          | Tipo    | Descrição                                      |
|-----------------|---------|------------------------------------------------|
| id              | BIGINT  | Identificador único do tipo de impressão. Chave primária. |
| description     | VARCHAR(255) | Descrição do tipo de impressão.                |

### Relacionamentos

- **Relacionamento com `Tb_BGifts`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de impressão pode estar associado a vários brindes. O campo `id_printing_type` na tabela `Tb_BGifts` faz referência ao `id` da tabela `Tb_PrintingType`.

- **Relacionamento com `Tb_BPrinteds`:**
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de impressão pode estar associado a várias impressões de materiais. O campo `id_printing_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintingType`.

---

### 16. Tb_BPrinteds

**Descrição**  
A tabela `Tb_BPrinteds` armazena informações sobre os materiais impressos relacionados a uma versão específica de um projeto. Inclui detalhes sobre o tipo de papel, tipo de impressão, e características da impressão, como dobras e páginas.

| Coluna         | Tipo de Dados | Descrição                                                                                     |
|----------------|---------------|-----------------------------------------------------------------------------------------------|
| `id`           | BIGINT        | Identificador único da impressão. Chave primária.                                             |
| `id_version`   | BIGINT        | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`.          |
| `id_paper_type`| BIGINT        | Identificador do tipo de papel utilizado. Chave estrangeira referenciando `Tb_PaperType.id`.  |
| `id_printed_type`| BIGINT      | Identificador do tipo de impressão. Chave estrangeira referenciando `Tb_PrintingType.id`.     |
| `folds`        | INT           | Número de dobras no material impresso.                                                        |
| `pages`        | INT           | Número de páginas no material impresso.                                                       |

**Relacionamentos**
- **Relacionamento com `Tb_Versions`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada registro em `Tb_BPrinteds` está associado a uma única versão de projeto. O campo `id_version` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_Versions`.
  
- **Relacionamento com `Tb_PaperType`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos materiais impressos podem usar o mesmo tipo de papel. O campo `id_paper_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PaperType`.
  
- **Relacionamento com `Tb_PrintingType`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitos materiais impressos podem usar o mesmo tipo de impressão. O campo `id_printed_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintingType`.

---

### 17. Tb_PrintingType

**Descrição**  
A tabela `Tb_PrintingType` armazena informações sobre os diferentes tipos de impressão disponíveis para os materiais. Cada tipo de impressão pode ser associado a vários materiais impressos.

| Coluna         | Tipo de Dados | Descrição                           |
|----------------|---------------|-------------------------------------|
| `id`           | BIGINT        | Identificador único do tipo de impressão. Chave primária. |
| `description`  | VARCHAR(255)  | Descrição do tipo de impressão.      |

**Relacionamentos**
- **Relacionamento com `Tb_BPrinteds`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de impressão pode ser utilizado em vários materiais impressos. O campo `id_printed_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PrintingType`.

---

### 18. Tb_PaperType

**Descrição**  
A tabela `Tb_PaperType` armazena informações sobre os diferentes tipos de papel disponíveis para os materiais impressos. Cada tipo de papel pode ser associado a vários materiais impressos.

| Coluna         | Tipo de Dados | Descrição                           |
|----------------|---------------|-------------------------------------|
| `id`           | BIGINT        | Identificador único do tipo de papel. Chave primária. |
| `description`  | VARCHAR(255)  | Descrição do tipo de papel.          |

**Relacionamentos**
- **Relacionamento com `Tb_BPrinteds`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de papel pode ser utilizado em vários materiais impressos. O campo `id_paper_type` na tabela `Tb_BPrinteds` faz referência ao `id` da tabela `Tb_PaperType`.

---



### 19. Tb_BoardAgencyType

**Descrição**  
A tabela `Tb_BoardAgencyType` armazena informações sobre os diferentes tipos de agências de quadros disponíveis. Cada tipo de agência de quadro pode ser associado a vários quadros de agência.

| Coluna         | Tipo de Dados | Descrição                           |
|----------------|---------------|-------------------------------------|
| `id`           | BIGINT        | Identificador único do tipo de agência de quadro. Chave primária. |
| `description`  | VARCHAR(255)  | Descrição do tipo de agência de quadro. |

**Relacionamentos**
- **Relacionamento com `Tb_BAgencyBoards`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de agência de quadro pode ser utilizado em vários quadros de agência. O campo `id_board_agency_type` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_BoardAgencyType`.

---

### 20. Tb_BoardType

**Descrição**  
A tabela `Tb_BoardType` contém informações sobre os tipos de placas utilizados pela agência. Esta tabela é usada para categorizar diferentes tipos de placas que podem ser associadas a uma determinada agência de publicidade.

| Coluna         | Tipo de Dados | Descrição                           |
|----------------|---------------|-------------------------------------|
| `id`           | BIGINT        | Identificador único do tipo de placa. Chave primária. |
| `description`  | VARCHAR(255)  | Descrição do tipo de placa.          |

**Relacionamentos**
- **Relacionamento com `Tb_BAgencyBoards`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de placa pode estar associado a muitas entradas na tabela `Tb_BAgencyBoards`.

---

### 21. Tb_BAgencyBoards

**Descrição**  
A tabela `Tb_BAgencyBoards` armazena informações sobre os quadros de agência. Cada quadro de agência está associado a um tipo de agência de quadro, um tipo de quadro específico e a uma versão de projeto. Além disso, um quadro de agência pode estar associado a várias rotas e outras rotas.

| Coluna            | Tipo de Dados | Descrição                                                   |
|-------------------|---------------|-------------------------------------------------------------|
| `id`              | BIGINT        | Identificador único do quadro de agência. Chave primária.   |
| `id_board_agency_type` | BIGINT    | Identificador do tipo de agência de quadro. Chave estrangeira referenciando `Tb_BoardAgencyType.id`. |
| `id_board_type`   | BIGINT        | Identificador do tipo de quadro. Chave estrangeira referenciando `Tb_BoardType.id`. |
| `id_version`      | BIGINT        | Identificador da versão do projeto. Chave estrangeira referenciando `Tb_Versions.id`. |
| `board_location`  | VARCHAR(255)  | Localização do quadro de agência.                          |
| `observations`    | TEXT          | Observações adicionais sobre o quadro de agência.          |

**Relacionamentos**
- **Relacionamento com `Tb_Versions`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada quadro de agência está associado a uma versão específica. O campo `id_version` na tabela `Tb_BAgencyBoards` faz referência ao `id` da tabela `Tb_Versions`.
  
- **Relacionamento com `Tb_Routes`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um quadro de agência pode estar associado a várias rotas. O campo `id_board_agency_board` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_BAgencyBoards`.
  
- **Relacionamento com `Tb_OtherRoutes`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um quadro de agência pode estar associado a várias outras rotas. O campo `id_board_agency_board` na tabela `Tb_OtherRoutes` faz referência ao `id` da tabela `Tb_BAgencyBoards`.

---

### 22. Tb_OthersRoutes

**Descrição**  
A tabela `Tb_OthersRoutes` armazena informações sobre rotas alternativas associadas a quadros de agência. Cada rota alternativa está vinculada a um quadro de agência específico.

| Coluna           | Tipo de Dados | Descrição                                                     |
|------------------|---------------|---------------------------------------------------------------|
| `id`             | BIGINT        | Identificador único da rota alternativa. Chave primária.       |
| `id_BAgenctBoard`| BIGINT        | Identificador do quadro de agência ao qual a rota está associada. Chave estrangeira referenciando `Tb_BAgencyBoards.id`. |

**Relacionamentos**
- **Relacionamento com `Tb_BAgencyBoards`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitas rotas alternativas podem estar associadas a um único quadro de agência. O campo `id_BAgenctBoard` na tabela `Tb_OthersRoutes` faz referência ao `id` da tabela `Tb_BAgencyBoards`.

---

### 23. Tb_Routes

**Descrição**  
A tabela `Tb_Routes` armazena informações sobre rotas associadas a quadros de agência e cidades. Cada rota está vinculada a um quadro de agência específico e pode estar associada a várias cidades.

| Coluna           | Tipo de Dados | Descrição                                                     |
|------------------|---------------|---------------------------------------------------------------|
| `id`             | BIGINT        | Identificador único da rota. Chave primária.                  |
| `id_board_agency_board` | BIGINT  | Identificador do quadro de agência associado. Chave estrangeira referenciando `Tb_BAgencyBoards.id`. |
| `route`          | VARCHAR(255)  | Descrição da rota.                                           |
| `id_city`        | BIGINT        | Identificador da cidade associada. Chave estrangeira referenciando `Tb_Cities.id`. |

**Relacionamentos**
- **Relacionamento com `Tb_BAgencyBoards`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitas rotas podem estar associadas a um único quadro de agência. O campo `id_board_agency_board` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_BAgencyBoards`.
  
- **Relacionamento com `Tb_Cities`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Muitas rotas podem estar associadas a uma única cidade. O campo `id_city` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_Cities`.

---

### 24. Tb_Cities

**Descrição**  
A tabela `Tb_Cities` armazena informações sobre as cidades associadas às rotas e outros dados relacionados. Cada cidade pode estar associada a várias rotas.

| Coluna         | Tipo de Dados | Descrição                           |
|----------------|---------------|-------------------------------------|
| `id`           | BIGINT        | Identificador único da cidade. Chave primária. |
| `name`         | VARCHAR(255)  | Nome da cidade.                      |
| `state`        | VARCHAR(255)  | Estado ao qual a cidade pertence.    |

**Relacionamentos**
- **Relacionamento com `Tb_Routes`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma cidade pode estar associada a várias rotas. O campo `id_city` na tabela `Tb_Routes` faz referência ao `id` da tabela `Tb_Cities`.

---

### 25. Tb_Companies

**Descrição**  
A tabela `Tb_Companies` armazena informações sobre as empresas. Cada empresa pode estar associada a várias cidades e ter uma versão específica associada a ela.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único da empresa. Chave primária. |
| `name` | VARCHAR(255) | Nome da empresa. |

**Relacionamentos**  
- **Relacionamento com `Tb_CompaniesCitie`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa pode estar associada a várias cidades. O campo `id_company` na tabela `Tb_CompaniesCitie` faz referência ao `id` da tabela `Tb_Companies`, indicando que uma entrada na tabela `Tb_Companies` pode estar associada a várias entradas na tabela `Tb_CompaniesCitie`.

- **Relacionamento com `Tb_CompaniesVersion`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada empresa pode ter uma versão específica associada. O campo `id_company` na tabela `Tb_CompaniesVersion` faz referência ao `id` da tabela `Tb_Companies`, indicando que cada entrada na tabela `Tb_CompaniesVersion` está associada a uma única empresa.

---

### 26. Tb_Companies

**Descrição**  
A tabela `Tb_Companies` armazena informações sobre as empresas. Cada empresa pode estar associada a várias cidades e ter uma versão específica associada a ela.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único da empresa. Chave primária. |
| `name` | VARCHAR(255) | Nome da empresa. |

**Relacionamentos**  
- **Relacionamento com `Tb_CompaniesCitie`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma empresa pode estar associada a várias cidades. O campo `id_company` na tabela `Tb_CompaniesCitie` faz referência ao `id` da tabela `Tb_Companies`, indicando que uma entrada na tabela `Tb_Companies` pode estar associada a várias entradas na tabela `Tb_CompaniesCitie`.

- **Relacionamento com `Tb_CompaniesVersion`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada empresa pode ter uma versão específica associada. O campo `id_company` na tabela `Tb_CompaniesVersion` faz referência ao `id` da tabela `Tb_Companies`, indicando que cada entrada na tabela `Tb_CompaniesVersion` está associada a uma única empresa.

---

### 27. Tb_CompaniesVersion

**Descrição**  
A tabela `Tb_CompaniesVersion` armazena informações sobre as versões específicas associadas a cada empresa. Cada empresa pode ter várias versões associadas, e cada versão pode estar vinculada a diferentes empresas.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único da versão da empresa. Chave primária. |
| `id_company` | BIGINT | Identificador da empresa associada. Chave estrangeira referenciando `Tb_Companies.id`. |
| `id_version` | BIGINT | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |

**Relacionamentos**  
- **Relacionamento com `Tb_Versions`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Uma versão pode estar associada a várias empresas. O campo `id_version` na tabela `Tb_CompaniesVersion` faz referência ao `id` da tabela `Tb_Versions`, indicando que uma entrada na tabela `Tb_Versions` pode estar associada a várias entradas na tabela `Tb_CompaniesVersion`.

- **Relacionamento com `Tb_Companies`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Cada versão específica está associada a uma única empresa. O campo `id_company` na tabela `Tb_CompaniesVersion` faz referência ao `id` da tabela `Tb_Companies`, indicando que uma entrada na tabela `Tb_CompaniesVersion` está associada a uma única empresa.

---

### 28. Tb_Handout

**Descrição**  
A tabela `Tb_Handout` armazena informações sobre os materiais de distribuição (handouts) associados a uma versão específica de um projeto. Cada handout é vinculado a uma versão e a um tipo de material de distribuição.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único do material de distribuição. Chave primária. |
| `id_version` | BIGINT | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |
| `id_handout_type` | BIGINT | Identificador do tipo de material de distribuição. Chave estrangeira referenciando `Tb_HandoutType.id`. |

**Relacionamentos**  
- **Relacionamento com `Tb_Versions`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada material de distribuição está associado a uma única versão. O campo `id_version` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_Versions`, indicando que uma entrada na tabela `Tb_Handout` está vinculada a uma única versão.

- **Relacionamento com `Tb_HandoutType`**:
  - Tipo: Muitos para Um (N:1)
  - Descrição: Vários materiais de distribuição podem pertencer ao mesmo tipo. O campo `id_handout_type` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_HandoutType`, indicando que cada entrada na tabela `Tb_Handout` está associada a um tipo específico de material de distribuição.

---

### 29. Tb_HandoutType

**Descrição**  
A tabela `Tb_HandoutType` armazena os diferentes tipos de materiais de distribuição (handouts) disponíveis no sistema. Cada tipo de handout pode ser associado a múltiplos handouts específicos.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único do tipo de material de distribuição. Chave primária. |
| `description` | VARCHAR(255) | Descrição do tipo de material de distribuição. |

**Relacionamentos**  
- **Relacionamento com `Tb_Handout`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de material de distribuição pode estar associado a vários handouts. O campo `id_handout_type` na tabela `Tb_Handout` faz referência ao `id` da tabela `Tb_HandoutType`, indicando que vários handouts podem pertencer a um único tipo de material de distribuição.

---

### 30. Tb_Signpost

**Descrição**  
A tabela `Tb_Signpost` armazena informações sobre sinalizações específicas associadas às versões dos projetos. Cada sinalização pode ter um material específico e uma localização no setor.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único da sinalização. Chave primária. |
| `id_version` | BIGINT | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |
| `id_material` | BIGINT | Identificador do material utilizado na sinalização. Chave estrangeira referenciando a tabela correspondente (não especificada aqui). |
| `board_location` | VARCHAR(255) | Localização da sinalização no setor. |
| `setor` | VARCHAR(255) | Setor onde a sinalização está localizada. |

**Relacionamentos**  
- **Relacionamento com `Tb_Versions`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada sinalização está associada a uma única versão do projeto. O campo `id_version` na tabela `Tb_Signpost` faz referência ao `id` da tabela `Tb_Versions`.

---

### 31. Tb_Material

**Descrição**  
A tabela `Tb_Material` armazena os tipos de materiais disponíveis para sinalização. Cada material pode ser associado a várias sinalizações.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único do material. Chave primária. |
| `description` | VARCHAR(255) | Descrição do material. |

**Relacionamentos**  
- **Relacionamento com `Tb_Signpost`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um material pode ser associado a várias sinalizações. O campo `id_material` na tabela `Tb_Signpost` faz referência ao `id` da tabela `Tb_Material`.

---

### 32. Tb_Bstickers

**Descrição**  
A tabela `Tb_Bstickers` armazena informações sobre adesivos relacionados a uma versão específica de um projeto. Cada adesivo está associado a um tipo específico e a uma versão do projeto.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único do adesivo. Chave primária. |
| `id_sticker_type` | BIGINT | Identificador do tipo de adesivo. Chave estrangeira referenciando `Tb_StickerTypes.id`. |
| `id_version` | BIGINT | Identificador da versão associada. Chave estrangeira referenciando `Tb_Versions.id`. |
| `sector` | VARCHAR(255) | Setor ou área onde o adesivo será aplicado. |
| `observations` | TEXT | Observações adicionais sobre o adesivo. |

**Relacionamentos**  
- **Relacionamento com `Tb_Versions`**:
  - Tipo: Um para Um (1:1)
  - Descrição: Cada adesivo está associado a uma versão específica de um projeto. O campo `id_version` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_Versions`.

- **Relacionamento com `Tb_StickerTypes`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de adesivo pode estar associado a vários adesivos. O campo `id_sticker_type` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_StickerTypes`.

---

### 33. Tb_StickerTypes

**Descrição**  
A tabela `Tb_StickerTypes` armazena informações sobre os diferentes tipos de adesivos disponíveis. Cada tipo pode ser associado a vários adesivos em projetos diferentes.

| Coluna | Tipo de Dados | Descrição |
|--------|---------------|-----------|
| `id` | BIGINT | Identificador único do tipo de adesivo. Chave primária. |
| `description` | VARCHAR(255) | Descrição do tipo de adesivo. |

**Relacionamentos**  
- **Relacionamento com `Tb_Bstickers`**:
  - Tipo: Um para Muitos (1:N)
  - Descrição: Um tipo de adesivo pode estar associado a vários adesivos. O campo `id_sticker_type` na tabela `Tb_Bstickers` faz referência ao `id` da tabela `Tb_StickerTypes`.

