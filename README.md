# Jogo das N-Rainhas (N-Queens Puzzle)

Este é um aplicativo Android que implementa o clássico quebra-cabeça das N-Rainhas. O objetivo do jogo é posicionar N rainhas em um tabuleiro de xadrez de dimensão NxN de forma que nenhuma rainha ataque outra.

## 📋 Sobre o Projeto

O problema das N-Rainhas é um desafio matemático e computacional onde o jogador deve dispor N peças (rainhas) no tabuleiro sem que elas compartilhem a mesma linha, coluna ou diagonal. Este aplicativo oferece uma interface interativa para resolver esse desafio, com feedback visual de conflitos e validação de vitória.

## ✨ Funcionalidades

- **Tabuleiro Interativo**: Toque nas células para adicionar ou remover rainhas.
- **Validação de Conflitos**: O jogo destaca visualmente (em vermelho) as rainhas que estão se atacando.
- **Tamanhos Configuráveis**: Escolha o tamanho do tabuleiro entre 4x4 e 8x8 através da tela de configurações.
- **Feedback de Vitória**: Mensagem de parabenização ao completar o desafio corretamente, com opção de avançar para o próximo nível (tamanho maior).
- **Áudio de Fundo**: Música ambiente para acompanhar o jogo (pode ser ativada/desativada).
- **Persistência de Estado**: O jogo salva o progresso atual e as configurações ao girar a tela ou fechar o app.

## 🚀 Como Executar

Para rodar este projeto, você precisará do Android Studio instalado em sua máquina.

1.  **Clone o repositório** (ou baixe os arquivos do projeto).
2.  **Abra o Android Studio**.
3.  Selecione **"Open an Existing Project"** e navegue até a pasta raiz deste projeto (`trabalhoNRainhas`).
4.  Aguarde o Gradle sincronizar as dependências.
5.  Conecte um dispositivo Android físico ou inicie um emulador.
6.  Clique no botão **"Run"** (ícone de play verde) ou pressione `Shift + F10`.

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **Framework**: Android SDK
- **IDE**: Android Studio
- **Layout**: XML (GridLayout, LinearLayout)
- **Gerenciamento de Dependências**: Gradle

## 🎮 Como Jogar

1.  O jogo inicia com um tabuleiro 4x4 (padrão).
2.  Toque em uma casa vazia para colocar uma rainha.
3.  Toque em uma rainha existente para removê-la.
4.  Se uma rainha ficar vermelha, significa que ela está em conflito com outra (mesma linha, coluna ou diagonal).
5.  O objetivo é colocar N rainhas (onde N é o tamanho do tabuleiro) sem que nenhuma fique vermelha.
6.  Ao vencer, você pode optar por aumentar o desafio para um tabuleiro maior.
7.  Use o botão de configurações (engrenagem) para mudar o tamanho do tabuleiro ou controlar a música.
8.  Use o botão "Reiniciar" para limpar o tabuleiro atual.

## 📂 Estrutura do Projeto

- `MainActivity.java`: Lógica principal da interface e interação com o usuário.
- `TabuleiroRainhas.java`: Lógica do jogo (regras, verificação de conflitos, estado do tabuleiro).
- `ConfigActivity.java`: Tela de configurações.
- `AudioService.java`: Serviço para reprodução de música de fundo.
