# Boardcamp
## Descrição
Este é um projeto de aplicação desenvolvido em Java com Spring Boot. Ele é projetado para controlar um sistema de locação jogos.

## ROTAS

Rota: POST /customers
```jsx
{
  name: 'João Alfredo',
  cpf: '01234567890'
}
```
Rota: GET /customers/:id
```jsx
{
  id: 1,
  name: 'João Alfredo',
  cpf: '01234567890'
}
```
Rota: POST /games
```jsx
{
  name: 'Banco Imobiliário',
  image: 'http://www.imagem.com.br/banco_imobiliario.jpg',
  stockTotal: 3,
  pricePerDay: 1500
}
```
Rota: GET /games
```jsx
[
  {
    id: 1,
    name: 'Banco Imobiliário',
    image: 'http://',
    stockTotal: 3,
    pricePerDay: 1500
  },
  {
    id: 2,
    name: 'Detetive',
    image: 'http://',
    stockTotal: 1,
    pricePerDay: 2500
  },
]
```
Rota: POST /rentals
```jsx
{
  customerId: 1,
  gameId: 1,
  daysRented: 3
}
```
Rota: GET /rentals
```jsx
[
  {
    id: 1,
    rentDate: '2021-06-20',
    daysRented: 3,
    returnDate: null,
    originalPrice: 4500,
    delayFee: 0, 
    customer: {
      id: 1,
      name: 'João Alfredo',
		  cpf: '01234567890'
    },
    game: {
      id: 1,
		  name: 'Banco Imobiliário',
		  image: 'http://www.imagem.com.br/banco.jpg',
		  stockTotal: 3,
		  pricePerDay: 1500
    }
  }
]
```
Rota: PUT /rentals/:id/return
```jsx
{
    id: 1,
    rentDate: '2021-06-20',
    daysRented: 3,
    returnDate: '2021-06-25', 
    originalPrice: 4500,
    delayFee: 3000, 
    customer: {
      id: 1,
      name: 'João Alfredo',
		  cpf: '01234567890'
    },
    game: {
      id: 1,
		  name: 'Banco Imobiliário',
		  image: 'http://www.imagem.com.br/banco.jpg',
		  stockTotal: 3,
		  pricePerDay: 1500
    }
  }
```



## Executando o projeto local:

Para fazer o build e execução da aplicação serão necessários:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 4](https://maven.apache.org)
- [Postgress](https://www.postgresqltutorial.com)

## Setup inicial
1. Com o postgress configurado, criar usuário, senha e uma database.
2. Preencher as variáveis de ambiente conforme descrito no arquivo [.env.example](https://github.com/thiagomayrink/boardcamp/blob/main/.env.example)
3. Renomear o arquivo **.env.example** para **.env**

## Executando a aplicação local

Existem várias formas de executar um app Spring Boot localmente. Uma delas é através do [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) executando o comando:

```shell
mvn spring-boot:run
```


## Contato
Se você tiver alguma dúvida, sinta-se à vontade para entrar em contato com Leonardo Yusuke Hirano em leoyuhirano@gmail.com .
