# MS User

O MS User é um microserviço dedicado à gestão de dados de usuários em um sistema. Ele é responsável por armazenar informações dos usuários, como nome, sobrenome, CPF, data de nascimento, e-mail, senha, entre outros dados relevantes.

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
