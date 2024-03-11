# MS User

O MS User desempenha um papel fundamental na gestão eficiente de dados de usuários em sistemas complexos. Responsável por armazenar e gerenciar informações vitais dos usuários, como nome, sobrenome, CPF, data de nascimento, e-mail, cep, senha e o status da conta, se está ativa (1) ou inativa (0) e tambem um sistema de Role separado entre Admin e Usuário.

## Autenticação e Documentação

Este microserviço implementa um sistema de autenticação robusto, baseado em JWT (JSON Web Tokens), garantindo uma camada de segurança sólida para autenticar usuários e autorizar o acesso aos recursos do sistema. Além disso, a documentação completa e abrangente deste microserviço foi elaborada utilizando o Swagger, facilitando a compreensão, integração e desenvolvimento de novos recursos por outros sistemas.

## Comunicação com RabbitMQ

Para garantir uma comunicação eficaz e assíncrona com outros microsserviços, como o MS Notification e o MS Address, o MS User utiliza o RabbitMQ. Essa integração permite uma troca de mensagens eficiente entre os diferentes componentes do sistema, garantindo um fluxo de dados consistente e confiável.

## Suporte a Banco de Dados

O MS User utiliza o banco de dados MySQL para armazenar e gerenciar os dados dos usuários. Isso proporciona uma base de dados sólida e confiável, adequada para sistemas que exigem escalabilidade, desempenho e integridade dos dados.

## Endpoints

O microserviço MS User oferece os seguintes endpoints para interação com os dados de usuário:

- **POST - /v1/login**: Permite que os usuários realizem login no sistema.
- **POST - /v1/users**: Utilizado para cadastrar novos usuários.
- **GET - /v1/users/:id**: Permite buscar um usuário específico com base no seu ID.
- **PUT - /v1/users/:id**: Oferece a funcionalidade de atualizar os dados de um usuário existente.
- **PUT - /v1/users/:id/password**: Utilizado para alterar a senha de um usuário.

## Exemplo de Payload para Cadastro de Usuário

```json
{
  "firstName": "Maria",
  "lastName": "Oliveira",
  "cpf": "000.000.000-00",
  "birthdate": "0000-00-00",
  "email": "maria@email.com",
  "cep": "69999-999",
  "password": "12345678",
  "active": true
}
```

### Validações Necessárias

- Os campos `firstName` e `lastName` devem conter no mínimo 3 caracteres.
- O campo `email` precisa estar no formato de um e-mail válido e não deve permitir e-mails duplicados.
- O campo `cpf` deve seguir o padrão `xxx-xxx-xxx.xx` e não pode ser duplicado.
- A senha (`password`) deve ter no mínimo 6 caracteres e ser armazenada de forma criptografada no banco de dados.
- O campo `birthdate` deve ser salvo no formato ISO-8601 no banco de dados, mas deve ser enviado no formato `dd/mm/aaaa` no payload de resposta.
- O campo `active` deve aceitar somente valores booleanos.

## Exemplo de Payload para Login

```json
{
  "email": "maria@email.com",
  "password": "12345678"
}
```

Após o processo de login bem-sucedido, o MS User gera um token de acesso. Esse token concede ao usuário a liberdade de acessar todos os outros endpoints disponíveis no sistema de forma segura e autorizada.
