O projeto Zeladoria Urbana Colaborativa (sugestão de nome: ZelaCidade ou CuidaBairro) é uma solução de CitizenTech focada em conectar moradores e gestores públicos para acelerar a manutenção e a conservação de espaços urbanos.
Abaixo está o resumo completo e estruturado da ideia, já desenhado para você colar no seu README.md e apresentar na gravação do vídeo.

1. O Problema Real

Nas grandes e médias cidades, problemas cotidianos de infraestrutura — como buracos no asfalto, postes de iluminação queimados, vazamentos de água, bueiros entupidos e descarte irregular de lixo — afetam diretamente a segurança, a mobilidade e a saúde da população. Atualmente, os canais tradicionais de atendimento são burocráticos, difíceis de acessar e não oferecem transparência sobre o andamento dos reparos, gerando descrédito e demora na resolução.

2. A Solução Proposta

Uma API RESTful em nuvem que serve como núcleo de uma plataforma colaborativa de zeladoria urbana. Através dela, qualquer cidadão pode se cadastrar, registrar ocorrências geolocalizadas no seu bairro e acompanhar em tempo real a evolução do chamado (de Aberto para Em Análise até Concluído). Isso transforma o morador em um agente ativo de fiscalização e fornece dados organizados para que o poder público priorize áreas críticas. [1]

3. Público Beneficiado e Ecossistema

• Cidadão Beneficiado: Moradores de áreas urbanas e periféricas que sofrem com a falta de manutenção nas ruas, calçadas e praças, ganhando voz ativa e transparência sobre os serviços públicos. [1]
• Participantes do Ecossistema:
• Cidadãos: Reportam problemas, atualizam informações e validam se o serviço foi realmente concluído.
• Prefeituras / Subprefeituras / Concessionárias: Consomem os dados das ocorrências para organizar equipes de manutenção e atualizar o status dos reparos. [1]

4. Conexão com os ODS da ONU

O projeto atende diretamente às metas da Agenda 2030 da ONU através de dois objetivos principais:
• ODS 11 (Cidades e Comunidades Sustentáveis): Aumenta a urbanização inclusiva e sustentável, fortalecendo a gestão participativa dos espaços públicos e reduzindo impactos ambientais negativos nas cidades (como lixo acumulado e bueiros entupidos que causam enchentes). [1]
• ODS 9 (Indústria, Inovação e Infraestrutura): Utiliza tecnologia acessível em nuvem para modernizar a gestão da infraestrutura urbana básica, tornando-a mais resiliente e confiável para toda a comunidade. [1]

5. Modelagem de Dados (2 Tabelas Relacionadas)

Para atender à regra da disciplina (mínimo de 2 tabelas com operações CRUD completas), a estrutura do banco de dados é simples e funcional:
• Tabela 1: CIDADAO (Quem reporta)
• id (PK, Auto-incremento)
• nome (Texto obrigatório)
• email (Texto único obrigatório)
• bairro (Texto obrigatório)
• Tabela 2: OCORRENCIA (O problema reportado)
• id (PK, Auto-incremento)
• titulo (Texto obrigatório — ex: "Poste apagado na Rua X")
• descricao (Texto detalhando o problema)
• categoria (Texto — ex: Iluminação, Asfalto, Lixo, Saneamento)
• status (Texto — padrão: "ABERTO", podendo ser atualizado para "EM_ANDAMENTO" ou "CONCLUIDO")
• data_criacao (Data/Hora automática)
• cidadao_id (FK referenciando CIDADAO.id)

6. Escopo Técnico (DevOps & Cloud)

• Backend: API RESTful desenvolvida em Java (Spring Boot) ou .NET (Minimal API) com documentação interativa via Swagger/OpenAPI.
• Testes Automatizados: Testes unitários/integração (JUnit ou xUnit) executados automaticamente durante a pipeline de CI. [1]
• Infraestrutura em Nuvem (Azure): Provisionada integralmente via script Azure CLI (scripts/infra.sh ou .ps1), hospedando a aplicação (App Service ou Container) e o banco de dados relacional (Azure SQL ou Oracle). [1]
• Fluxo CI/CD (Azure DevOps):
• Gestão de tarefas no Azure Boards vinculadas a branches e Pull Requests.
• Repositório privado no Azure Repos com branch main protegida por políticas de revisão.
• Pipeline de Build (CI): Acionada após merge via PR, executa os testes e publica o artefato.
• Pipeline de Release (CD): Acionada automaticamente após o CI, realiza o deploy contínuo na Azure utilizando variáveis de ambiente. [1]
Para eu gerar agora os scripts prontos (script-bd.sql, o script de Azure CLI e o arquivo azure-pipeline.yml), me confirme qual arquitetura você vai montar:
• Linguagem da API: Java (Spring Boot) ou .NET (C#)
• Banco de Dados na Azure: Azure SQL ou Container (SQL Server/PostgreSQL/Oracle)
• Hospedagem da API na Azure: App Service (Serviço de Aplicativo) ou Container (ACI/ACR)
