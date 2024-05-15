# Desafio Backend WishList

## Sumário
- **Objetivo e requisitos**
- **Solução**
- **Testes Unitários**
- **Features Implementadas**
- **Tecnologias**
- **Build e execução**
- **Observações e Orientações**

## Objetivo e requisitos
O objetivo é que seja desenvolvido um serviço HTTP resolvendo a funcionalidade de Wishlist do cliente. Esse micro serviço deve atender os seguintes requisitos:
- **Adicionar** um produto na Wishlist do cliente;
- **Remover** um produto da Wishlist do cliente;
- **Consultar todos** os produtos da **Wishlist** do cliente;
- **Consultar** se um **determinado produto** está na **Wishlist** do cliente;

Além dos requisitos, devem ser considerados também os seguintes tópicos:
- Plataforma construída em uma arquitetura baseada em micro-serviços;
- Não considerar a gestão das informações de Produtos e/ou Clientes, portanto o foco deve ser apenas no serviço da Wishlist;
- Limite máximo de **20 produtos** para cada Wishlist;

## Solução
Para solucionar este problema, foi adotado um modelo de projeto cujo funcionamento ocorre em camadas. Deste modo, compreende-se uma abordagem limpa e baseada em features, sendo possível interpretar o desenvolvimento por meio de fluxos de caso de uso.

Foram criados três pacotes para organizar as classes, sendo eles: *core*, *exception* e *gateway*, além dos demais subpacotes.

- **core**: Este pacote foi criado para envolver as classes da camada de negócio;
- **exception**: Este pacote foi criado para gerenciar e controlar as exceções que envolvem o projeto;
- **gateway**: Este pacote foi criado para conter as camadas externas que se comunicam com a camada de negócio, isto é, a subcamada **controller** que envolve os controladores, bem como a camada de persistência denominada por **database**.

## Testes Unitários
Foram desenvolvidos testes unitários para todas as camadas da aplicação, ou seja, factory, usecase, controller e datasource.

## Features implementadas
#### Cliente
- Buscar um cliente pelo nome informado;
- Buscar todos os clientes;
#### Produto
- Buscar um produto pelo nome informado;
#### Lista de Desejos
- Criar uma nova wishlist;
- Adicionar um produto na wishlist do cliente;
- Buscar todos os produtos da wishlist do cliente;
- Verificar se um determinado produto está na wishlist do cliente;
- Buscar uma wishlist pelo id informado;
- Remover um produto da wishlist do cliente;

## Tecnologias
- Java 17
- Springboot 3.1.11
- MongoDB 4.1.11
- Maven 3.8.6
- Docker 26.1.1
- Lombok 1.18.32
- JUnit 5.9.3
- Swagger OpenAPI Specification 3.1.0

## Build e execução
A seguir, serão explicadas três diferentes formas de buildar e executar a aplicação:
- **Deploy Manual**
- **Deploy na IDE IntelliJ IDEA**
- **Deploy Docker**

### Deploy Manual
1. É possível subir localmente a aplicação para fins de inspeção de código e validação dos fluxos. Para isso, é necessário subir apenas o serviço do **MongoDB**. Para subir apenas o container do **MongoDB** deve-se utilizar o arquivo **backup_docker-compose.yml** que foi inserido na raiz do projeto, este arquivo deve ser renomeado para o nome padrão, ou seja, **docker-compose.yml** ao passo que o arquivo original **docker-compose.yml** seja inutilizado. Uma sugestão é renomear o arquivo original para um nome qualquer enquanto que o arquivo **backup_docker-compose.yml** torna-se apenas **docker-compose.yml**. Por fim, deve ser executado o comando a seguir para iniciar o serviço do **MongoDB**.

`docker compose up`

2. Uma vez iniciado o container do **MongoDB**, deve-se então buildar a aplicação wishlist. Para buildar a aplicação, deve-se utilizar o comando **maven** a seguir:

`mvn clean package` ou `.mvnw clean package`

3. Após a finalização do build da aplicação, basta executar o artefato java que foi construído. O artefato buildado fica disponível no diretório **/target** com nome **wishlist-0.0.1-SNAPSHOT.jar**. Ao subir a aplicação, é necessário também definir um profile de execução, para este caso iremos utilizar o profile dev. Portanto, o comando a seguir deve ser utilizado para executar o artefato java no profile dev:

`java -jar -Dspring.profiles.active=dev wishlist-0.0.1-SNAPSHOT.jar`

4. Após executar a aplicação, a mesma deve estar disponível em:

[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

### Deploy na IDE IntelliJ IDEA
1. Para subir a aplicação no IntelliJ IDEA deve-se subir primeiro o serviço do MongoDB. Para subir apenas o container do MongoDB deve-se utilizar o arquivo backup_docker-compose.yml que foi inserido na raiz do projeto, este arquivo deve ser renomeado para o nome padrão, ou seja, docker-compose.yml ao passo que o arquivo original docker-compose.yml seja inutilizado. Uma sugestão é renomear o arquivo original para um nome qualquer enquanto que o arquivo backup_docker-compose.yml torna-se apenas docker-compose.yml. Por fim, deve ser executado o comando a seguir para iniciar o serviço do MongoDB.

`docker compose up`

2. Uma vez iniciado o container do **MongoDB**, deve-se então configurar o **IntelliJ IDEA** para subir a aplicação por meio dele. Para isso, os seguintes parâmetros devem ser definidos na **IDE IntelliJ IDEA**:

`Build and run:` Java 17

`Main class:` com.wishlist.App

`Active profiles:` dev

3. Uma vez configurada e iniciada no IntelliJ IDEA, a aplicação deve subir e ficar disponível em:

[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

### Deploy Docker
1. Considerando que a imagem docker desta aplicação não está hospedada em nenhum sistema cloud de imagem (como o dockerhub, por exemplo), é necessário buildar esta imagem antes de usar o docker-compose.yml Para isso, é necessário executar o comando a seguir:

`docker build -t wishlist .`

2. Uma vez concluída a construção da imagem docker, é possível executar o arquivo **docker-compose.yml** para que a aplicação suba totalmente, conforme o comando a seguir:

`docker compose up`

3. Opcionalmente, o comando abaixo executa as duas funções que foram citadas acima. Ou seja, as imagens são conteinerizadas automaticamente e caso não exista a imagem construída, o build é realizado por meio do arquivo Dockerfile.

`docker compose up --build`

4. Será conteinerizada a imagem da aplicação **wishlist** bem como as instancias necessárias do **MongoDB**. Uma vez concluído o processo de conteinerização, a aplicação já estará disponível em:

[`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html)

5. Para encerrar a execução pode-se usar *ctrl+c* no termial que está executando, seguido do comando para remover os containers:

`docker-compose down`

## Observações e orientações
Foi adicionada uma pré carga na base de dados deste projeto, portanto o arquivo **mongo-init.js** realiza previamente vários inserts de dados no **MongoDB** de modo a facilitar os testes.

Deste modo, foram configuradas previamente duas wishlists, sendo uma delas totalmente cheia, ou seja, com 20 produtos e a outra totalmente vazia, à qual permite adição de novos produtos.

`@GET /wishlists/1` sendo wishlidtId = 1 uma lista cheia e `@GET /wishlists/2` sendo wishlidtId = 2 uma lista vazia.

Uma forma de validar os primeiros testes é realizando a chamada `@GET /clients`, a resposta esperada é um retorno de 4 registros de clientes.

Com base nestas informações, é possível realizar todo o ciclo de vida de gestão de Lista de Desejos, Produtos e Clientes.

Ao todo foram adicionados 25 produtos na base de dados, sendo eles enumerados com id entre 1 e 25. Esses produtos podem ser utilizados para fazer parte de qualquer wishlist, dede que seja respeitado o limite máximo de 20 itens.