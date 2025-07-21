package com.example.trabalhonrainhas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class TabuleiroRainhas {
        private int tamanho;
        private final ArrayList<int[]> posicoesRainhas;
        private final Set<String> conflitos;

        public TabuleiroRainhas(int tamanhoInicial) {
            this.tamanho = tamanhoInicial;
            this.posicoesRainhas = new ArrayList<>();
            this.conflitos = new HashSet<>();
        }

        public void alternarRainha(int linha, int coluna) {
            int indiceRainha = encontrarIndiceRainha(linha, coluna);

            if (indiceRainha >= 0) {
                // Se encontrou a rainha, remove
                posicoesRainhas.remove(indiceRainha);
            } else {
                // Se não encontrou, adiciona
                posicoesRainhas.add(new int[]{linha, coluna});
            }
            verificarConflitos();
        }

        public void reiniciar(int novoTamanho) {
            this.tamanho = novoTamanho;
            this.posicoesRainhas.clear();
            this.conflitos.clear();
        }


        private void verificarConflitos() {
            conflitos.clear();
            for (int i = 0; i < posicoesRainhas.size(); i++) {
                for (int j = i + 1; j < posicoesRainhas.size(); j++) {
                    int[] r1 = posicoesRainhas.get(i);
                    int[] r2 = posicoesRainhas.get(j); // rainha 2

                    if (r1[0] == r2[0] || r1[1] == r2[1] ||
                            Math.abs(r1[0] - r2[0]) == Math.abs(r1[1] - r2[1])) {
                        conflitos.add(r1[0] + "-" + r1[1]);
                        conflitos.add(r2[0] + "-" + r2[1]);
                    }
                }
            }
        }

        private int encontrarIndiceRainha(int linha, int coluna) {
            for (int i = 0; i < posicoesRainhas.size(); i++) {
                int[] r = posicoesRainhas.get(i);
                if (r[0] == linha && r[1] == coluna) {
                    return i;
                }
            }
            return -1;
        }

        public int getTamanho() {
            return tamanho;
        }

        public boolean possuiRainha(int linha, int coluna) {
            return encontrarIndiceRainha(linha, coluna) >= 0;
        }

        public boolean temConflito(int linha, int coluna) {
            return conflitos.contains(linha + "-" + coluna);
        }

        public boolean estaResolvido() {
            return conflitos.isEmpty() && posicoesRainhas.size() == tamanho;
        }

        public boolean temConflitos() {
            return !conflitos.isEmpty();
        }
    }
