# frogs_product_manager
Product Manager for SpringBoot/Java studies

# Arquitetura do Sistema

A arquitetura do sistema funciona da seguinte maneira:

## Envio de Requisição pelo Cliente:

- O cliente envia uma solicitação para a primeira API para comprar um produto, fornecendo o ID do cliente e o ID do produto.

## Validação Inicial na Primeira API:

- A primeira API faz chamadas ao MongoDB para verificar as informações do cliente e confirmar se o produto está disponível.
- Esta primeira validação cria um cache no Redis para o produto solicitado.

## Envio de Mensagem para RabbitMQ:

- Após a validação, a primeira API envia uma mensagem para o RabbitMQ.

## Processamento pela Segunda API:

- A segunda API lê a mensagem do RabbitMQ.
- Em seguida, ela cria a transação do produto no MongoDB e marca o produto como indisponível.

## Notificação ao Cliente:

- Por fim, a segunda API envia uma mensagem via SNS (Simple Notification Service) para notificar o cliente por email.

Com esta arquitetura, asseguramos que cada etapa do processo de compra, utilizando os IDs do cliente e do produto, seja validada e registrada corretamente, garantindo a integridade dos dados e uma comunicação eficiente entre os diferentes componentes do sistema.