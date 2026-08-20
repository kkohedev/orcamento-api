# Orçamento API

Projeto feito durante o bootcamp Santander 2026 - AI Java Back-end, onde o objetivo era fazerr uma API de orçamento pessoal e evoluir ela pra usar IA, permitindo registrar e consultar gastos por comando de voz.

## O que o projeto faz

Basicamente, dá pra falar com a API em vez de mandar um JSON pronto. Por exemplo, eu gravo um áudio dizendo:

"Registre um gasto de 30 reais em transporte"

E a API:
1. Transcreve esse áudio pra texto (usando o Whisper da OpenAI);
2. Manda esse texto pra uma IA entender o que eu quero;
3. A IA registra a transação de verdade no banco de dados;
4. Ela responde falando (converte a resposta em áudio de novo);
5. E ainda guarda um histórico de tudo que foi pedido, pra eu poder consultar depois.

Também tem os endpoints normais de CRUD, criar e listar transações via JSON, do jeito tradicional.

## Tecnologias usadas:

- Java + Spring Boot;
- Spring AI, pra conectar com a IA;
- OpenAI (GPT pra entender os comandos, Whisper pra transcrever áudio, TTS pra gerar voz);
- JPA + banco de dados relacional;
- Um pouco de HTML/JavaScript, só pra fazer um site para testar.

## Como rodar o projeto:

1. Clona o repositório
2. Copia o arquivo application.properties.example e renomeia pra application.properties
3. Coloca sua própria chave da OpenAI nele:
   ```
   spring.ai.openai.api-key=sua_chave_aqui
   ```
4. Roda com ./mvnw spring-boot:run
5. Abre http://localhost:8080 no navegador

## O que eu implementei de melhoria:

O enunciado pedia pra evoluir a API com IA, e o que eu fiz foi:
- Transcrição de áudio, a fala virando texto
- A IA conseguindo executar ações de verdade no sistema, não só responder texto. Ela realmente cria e consulta as transações usando Tool Calling
- Geração de voz na resposta, o texto virando fala de novo
- Um log salvando cada interação: o que foi pedido, o que foi respondido, quando
- Uma página simples no navegador pra eu conseguir testar tudo isso sem precisar ficar usando o Postman toda hora, feito com ajuda de IA.

## Como testar:

Maneira mais fácil que criei: abre http://localhost:8080, clica em "Gravar comando", fala alguma coisa tipo "registre um gasto de 30 reais em transportes" e clica em parar. Ele já mostra o que você falou, o que a IA respondeu, e toca o áudio de volta.

Se preferir usar Postman, como usei também:
- POST /comando, manda um texto puro no body
- POST /audio/comando, manda um arquivo de áudio (form-data, key = file)
- GET /transacoes, lista as transações
- GET /logs, lista o histórico de comandos

## O que eu aprendi fazendo esse projeto:

Foi bem mais difícil do que eu esperava, principalmente a parte de configuração. Tomei vários erros diferentes até conseguir conectar direito com a IA: chave errada, sem crédito, nome de modelo errado, entre outros. Aprendi:

- Como configurar o Spring AI de verdade e entender os erros que ele dá. A maioria dos problemas foi bobagem de configuração, tipo colocar a chave no lugar errado
- O que é Tool Calling e como isso deixa a IA capaz de fazer coisas reais no sistema, não só responder texto
- Que bibliotecas mudam de versão pra versão e isso pode quebrar código que "deveria" funcionar. Troquei os imports dos pacotes de áudio umas 3 vezes por causa disso
- A importância de nunca subir uma chave de API pro GitHub, e como usar .gitignore junto com um arquivo de exemplo pra proteger isso
- Como fazer commits organizados no Git, indo por partes em vez de tentar fazer tudo de uma vez
