# Java Developer Toolbox

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-21-007ACC)

Projeto prático de estudo de JavaFX, desenvolvido de forma incremental como uma aplicação desktop que reúne ferramentas úteis para o dia a dia de desenvolvimento Java em uma única interface.

> [!IMPORTANT]
> Este projeto ainda está em desenvolvimento. A interface do cliente HTTP já está disponível, mas o envio das requisições e os demais módulos ainda não foram concluídos.

## Sobre o projeto

O **Java Developer Toolbox** está sendo construído como uma central de utilitários para tarefas comuns durante o desenvolvimento. A aplicação possui uma navegação lateral preparada para reunir ferramentas como cliente HTTP, acesso a banco de dados, manipulação de JWT, RabbitMQ e monitoramento da JVM.

No estágio atual, o trabalho está concentrado na interface e no comportamento reativo do cliente HTTP.

## Contexto de aprendizado

Este repositório é utilizado como projeto prático em uma trilha de estudos de JavaFX. Em vez de criar exemplos isolados, cada novo conceito é aplicado e integrado à mesma aplicação, permitindo acompanhar sua evolução aula após aula.

Entre os assuntos praticados até o momento estão:

- ciclo de vida de uma aplicação JavaFX;
- composição de layouts e componentes visuais;
- tratamento, captura e propagação de eventos;
- listas e propriedades observáveis;
- bindings unidirecionais e bidirecionais;
- validação reativa do estado da interface;
- organização da aplicação em views e modelos de propriedades.

Por esse motivo, o projeto prioriza o aprendizado e a evolução progressiva do código. Algumas funcionalidades visíveis na interface são preparações para conteúdos futuros e ainda não representam ferramentas finalizadas para uso em produção.

## Funcionalidades atuais

- Interface desktop construída com JavaFX;
- navegação lateral entre as ferramentas;
- tela inicial do cliente HTTP;
- seleção dos métodos `GET`, `POST`, `PUT`, `DELETE` e `PATCH`;
- validação reativa de URLs HTTP e HTTPS;
- habilitação do botão de envio apenas quando a URL é válida;
- adição e remoção dinâmica de headers;
- sincronização dos campos de headers com propriedades observáveis do JavaFX;
- área preparada para exibir a resposta da requisição.

## Em desenvolvimento

- [ ] Executar requisições HTTP reais;
- [ ] exibir status, headers e corpo das respostas;
- [ ] tratar erros e estados de carregamento;
- [ ] implementar a ferramenta de banco de dados;
- [ ] implementar a ferramenta para JWT;
- [ ] implementar a integração com RabbitMQ;
- [ ] implementar o monitor da JVM;
- [ ] adicionar testes automatizados e aprimorar a experiência visual.

## Tecnologias

- Java 21;
- JavaFX 21;
- Apache Maven;

## Pré-requisitos

Antes de executar o projeto, tenha instalado:

- [JDK 21](https://adoptium.net/temurin/releases/?version=21) ou uma distribuição compatível;
- [Apache Maven](https://maven.apache.org/download.cgi).

Confirme as instalações com:

```bash
java --version
mvn --version
```

## Como executar

Clone o repositório e acesse a pasta do projeto Maven:

```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_REPOSITORIO>/java-developer-toolbox
```

Execute a aplicação por meio do plugin do JavaFX:

```bash
mvn clean javafx:run
```

> Substitua os valores entre `< >` pela URL e pelo nome do repositório depois de publicá-lo no GitHub.

## Estrutura do projeto

```text
.
├── README.md
└── java-developer-toolbox/
    ├── pom.xml
    └── src/main/java/dev/toolbox/
        ├── DeveloperToolboxApplication.java
        ├── Launcher.java
        ├── property/
        │   └── HttpHeader.java
        └── view/
            ├── BuilderView.java
            └── HttpClientView.java
```

## Observações

- O botão **Send** ainda não dispara uma requisição; atualmente ele demonstra a validação e o binding do estado da interface.
- Os botões **Database**, **JWT**, **RabbitMQ** e **JVM Monitor** representam módulos planejados e ainda não possuem telas funcionais.
- A área **Response** já faz parte da interface, mas será preenchida quando a execução das requisições for implementada.
---

Desenvolvido para explorar Java moderno, JavaFX e programação reativa de interfaces desktop.
