# App de Login - Captura e Retorno de Avatar

Este projeto é um aplicativo de Login para Android desenvolvido em Java. A funcionalidade mais recente introduzida permite que o usuário tire uma foto na tela de boas-vindas e essa imagem seja enviada de volta e exibida em formato circular na tela principal do aplicativo.

## Nova Funcionalidade

* **Captura de Foto:** Integração com a câmera nativa do dispositivo para tirar uma foto na tela de Bem-Vindo.
* **Retorno de Dados entre Telas:** A foto capturada é enviada de volta para a tela de Login (MainActivity) após o usuário retornar.
* **Exibição Circular (Material Design):** A imagem recebida é exibida de forma circular e centralizada na interface principal.

## O Que Foi Alterado

Para implementar essa funcionalidade, foram feitas alterações em 5 arquivos principais do projeto:

1. **`SimpleContract.java` (O Contrato):**
   * **Alteração:** O tipo de dado de retorno do contrato foi alterado de `String` para `Bitmap`.
   * **Motivo:** Permitir que o contrato consiga trafegar a imagem em vez de apenas um texto.

2. **`WelcomeActivity.java` (A Tela de Câmera):**
   * **Alteração:** Atualização do método `onActivityResult` para interceptar a foto tirada, extrair o `Bitmap` e anexá-lo ao `Intent` de resultado (usando a chave `"foto"`).
   * **Motivo:** Preparar a "encomenda" (a foto) e registrar o status de sucesso (`setResult(Activity.RESULT_OK)`) antes que o usuário feche a tela no botão de voltar (Up Navigation).

3. **`MainActivity.java` (A Tela Principal):**
   * **Alteração:** O `ActivityResultLauncher` foi atualizado para esperar um objeto `Bitmap`. Quando o resultado chega, ele injeta a imagem no componente visual e altera a visibilidade dele para `VISIBLE`.
   * **Motivo:** Processar o recebimento da foto e atualizar a interface do usuário dinamicamente.

4. **`activity_main.xml` (O Layout):**
   * **Alteração:** Inserção do componente `ShapeableImageView` do pacote Material Design, posicionado abaixo dos botões com regras de `ConstraintLayout` para mantê-lo centralizado.
   * **Motivo:** Criar o espaço na tela onde o avatar do usuário vai aparecer.

5. **`themes.xml` (Os Estilos):**
   * **Alteração:** Criação do estilo `<style name="CircleImage">` com a propriedade `<item name="cornerSize">50%</item>`.
   * **Motivo:** Forçar o `ShapeableImageView` a recortar a imagem perfeitamente em formato de círculo.

## Como Funciona o Processo (Fluxo de Dados)

O fluxo da imagem acontece graças à **Activity Result API**, que é a forma moderna e recomendada pelo Google para trocar dados entre telas no Android:

1. **Disparo:** Quando o usuário acerta o login e senha, a `MainActivity` chama o método `.launch()` do nosso contrato (`SimpleContract`).
2. **Abertura:** O contrato cria um `Intent` e abre a `WelcomeActivity`.
3. **Ação:** Na tela de bem-vindo, o usuário clica no botão, a câmera abre e ele tira a foto.
4. **Empacotamento:** A `WelcomeActivity` pega essa foto recém-tirada, empacota num `Intent` chamado `resultIntent` com o nome de `"foto"`, e avisa o sistema que deu tudo certo (`setResult`).
5. **Retorno:** O usuário clica na seta de voltar (`onSupportNavigateUp`). A tela fecha.
6. **Entrega:** O sistema Android pega aquele pacote que deixamos preparado e entrega para o `SimpleContract`. O contrato desembrulha o pacote, pega o `Bitmap` e entrega na porta da `MainActivity`.
7. **Exibição:** O `ActivityResultLauncher` da `MainActivity` recebe o `Bitmap`, coloca na tela e torna o componente de imagem visível para o usuário.
