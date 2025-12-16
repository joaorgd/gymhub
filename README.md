# GymHub 🏋️‍♂️

> Seu parceiro de treino inteligente para monitorar a evolução de cargas e performance.

O **GymHub** é um aplicativo Android nativo desenvolvido para auxiliar praticantes de musculação a registrar, organizar e visualizar o progresso dos seus exercícios. O foco principal é o histórico de cargas e a análise visual da evolução através de gráficos.

## 📱 Funcionalidades

* **Gestão de Exercícios:** Cadastro completo de exercícios com nome, grupo muscular e observações técnicas (ex: ajuste do banco).
* **Histórico de Cargas:** Registro detalhado de cada série com data, carga utilizada e número de repetições.
* **Análise Visual:** Gráficos interativos (LineChart) que mostram a curva de evolução de força ao longo do tempo.
* **Feedback Inteligente:** O sistema analisa a relação entre carga e repetições e sugere ajustes:
    * 🚀 **Aumentar carga:** Se as repetições forem altas (>12).
    * ⚠️ **Atenção à técnica:** Se as repetições forem muito baixas (<6).
    * ✅ **Manter:** Para o intervalo ideal de hipertrofia.
* **Persistência de Dados:** Todos os dados são salvos localmente no dispositivo, garantindo privacidade e acesso offline.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** [Java](https://www.java.com/)
* **Android SDK:** Min SDK 24 / Target SDK 34
* **Arquitetura:** Conceitos de MVVM (Model-View-ViewModel)
* **Banco de Dados:** [Room Database](https://developer.android.com/training/data-storage/room) (Abstração do SQLite)
* **Interface:** XML Layouts & Material Design 3
* **Gráficos:** [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

## 🚀 Como Executar o Projeto

### Pré-requisitos
* [Android Studio](https://developer.android.com/studio) (versão Iguana ou superior recomendada)
* JDK 1.8 ou superior

### Passo a passo
1.  **Clonar o repositório:**
    ```bash
    git clone [https://github.com/joaorgd/gymhub.git](https://github.com/joaorgd/gymhub.git)
    ```
2.  **Abrir no Android Studio:**
    * Inicie o Android Studio e selecione "Open".
    * Navegue até a pasta onde você clonou o projeto.
3.  **Sincronizar o Gradle:**
    * Aguarde o Android Studio baixar as dependências listadas no `libs.versions.toml`.
4.  **Executar:**
    * Conecte um dispositivo físico ou inicie um Emulador (AVD).
    * Clique no botão "Run" (▶️).

## 📂 Estrutura do Projeto

* `database/`: Contém as entidades (`Exercicio`, `HistoricoCarga`), o DAO e a configuração do Room Database.
* `activities/`: Contém a lógica de apresentação (`MainActivity`, `DetalhesExercicioActivity`, etc.).
* `res/layout/`: Arquivos XML da interface do usuário.

## 🤝 Contribuição

Contribuições são bem-vindas! Se você tem ideias para novas funcionalidades (como cronômetro de descanso, exportação de dados, etc.):

1.  Faça um Fork do projeto.
2.  Crie uma Branch para sua Feature (`git checkout -b feature/NovaFuncionalidade`).
3.  Faça o Commit das suas alterações (`git commit -m 'Adiciona NovaFuncionalidade'`).
4.  Faça o Push para a Branch (`git push origin feature/NovaFuncionalidade`).
5.  Abra um Pull Request.

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).

---
Desenvolvido por [João Roberto](https://github.com/joaorgd) | Felipe Cardoso | Guilherme Barros
