# Arabus

Um aplicativo Android moderno para gerenciamento e acompanhamento de rotas de ônibus em Arapiraca.

## 🚀 Tecnologias

O projeto foi desenvolvido utilizando as seguintes tecnologias:

- **Kotlin** - Linguagem principal do projeto
- **Android SDK** - Framework para desenvolvimento Android
- **Retrofit** - Cliente HTTP para consumo de APIs
- **Coroutines** - Para programação assíncrona
- **Clean Architecture** - Padrão de arquitetura do projeto

## 🏗️ Arquitetura

O projeto segue os princípios da Clean Architecture, organizado nas seguintes camadas:

### Core
- **Domain** - Contém as entidades principais do sistema (User, Route, Favorite, etc.)
- **DTOs** - Objetos de transferência de dados
- **Interfaces** - Contratos para repositories e APIs
- **Network** - Configuração do Retrofit e gerenciamento de usuários

### Features
- **Repository** - Implementação dos repositories para acesso a dados
- **Request/Response** - Models para requisições e respostas da API

## 📱 Funcionalidades Principais

- Autenticação de usuários
- Visualização de rotas de ônibus
- Gerenciamento de favoritos
- Sistema de feedback
- Histórico de viagens
- Sistema de notificações
- Perfil de usuário

## 🔧 Configuração do Projeto

1. Clone o repositório
2. Abra o projeto no Android Studio
3. Sincronize o projeto com os arquivos Gradle
4. Execute o aplicativo em um emulador ou dispositivo físico

## 🏛️ Padrões de Projeto

- **Repository Pattern** - Para abstração da camada de dados
- **Dependency Injection** - Para gerenciamento de dependências
- **MVVM** - Para a camada de apresentação
- **Interface Segregation** - Separação clara de responsabilidades através de interfaces

## 🔄 Fluxo de Dados

1. UI solicita dados através do ViewModel
2. ViewModel aciona o Repository apropriado
3. Repository utiliza as interfaces da API para comunicação com o backend
4. Dados são convertidos de DTOs para Domain Objects
5. Informações são apresentadas de volta para a UI

## 🛠️ Estrutura de Pastas

```
app/src/main/java/com/example/arabus/
├── core/
│   ├── domain/         # Entidades do domínio
│   ├── dtos/           # Objetos de transferência de dados
│   ├── interfaces/     # Contratos da aplicação
│   ├── network/        # Configuração de rede
│   ├── request/        # Models de requisição
│   └── response/       # Models de resposta
└── repository/         # Implementação dos repositories
```

## 🤝 Contribuição

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Notas Adicionais

- O projeto utiliza Kotlin como linguagem principal, aproveitando seus recursos modernos
- Implementação robusta de chamadas assíncronas usando Coroutines
- Sistema modular e facilmente extensível
- Foco em manutenibilidade e testabilidade do código
