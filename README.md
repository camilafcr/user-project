# API Rest para cadastro de usuários

# 1. Introdução

### A API desenvolvida tem como objetivo realizar um cadastro de usuários. 
### Tem como funcionalidades o cadastro, a atualização, exclusão e busca de um ou mais registros.

# 2. Tecnologias
### A API foi desenvolvida utilizando Spring Boot e e Java 8. Utilizou-se as seguintes ferramentas e tecnologias:
#### Maven - gerenciador de dependência;
#### PostgreSQL - banco de dados;
#### Docker - o projeto possui o arquivo docker-compose.yml para facilitar a criação do banco e execução de scripts;
#### Spring Data JPA - persistência dos dados;
#### JUnit5 - testes;
#### Mockito - testes;
#### Spring MockMVC - testes;
#### SpringFox - para documentar os endpoints no Swagger;
#### Lombok - para auxiliar no desenvolvimento. É necessário baixar um plugin para utilizá-lo.

# 3. Instalação
#### 3.1 Banco de dados
#### Para facilitar a execução, é importante que se tenha o docker instalado e seguir os passos abaixo:
#### 3.1.1 Baixar o arquivo com o projeto e descompactar no caminho desejado;
#### 3.1.2 Abrir o prompt de comando, e navegar até a pasta raíz do arquivo descompactado;
#### 3.1.3 Digitar o comando 'docker-compose up' e esperar até que seja apresentada a indicação de que o banco está pronto.
#### 3.2 Aplicação
#### 3.2.1 Caso ainda não tenha sido realizado, baixar e descompactar o arquivo no caminho desejado;
#### 3.2.2 Abrir o prompt de comando, e navegar até a pasta 'target' do arquivo descompactado;
#### 3.2.3 Digitar o comando 'java -jar usuarios-0.0.1-SNAPSHOT.jar' e esperar até que a aplicação seja inicializada

# 4. Informações
### - Para documentação dos endpoints, iniciar a aplicação e acessar o endereço: http://localhost:8080/swagger-ui.html#.
### - Os scripts do banco estão no pacote database-scripts e são executados pelo docker-compose.

