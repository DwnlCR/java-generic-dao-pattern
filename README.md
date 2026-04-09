# java-generic-dao-pattern
> 🇧🇷 [Português](#português) | 🇺🇸 [English](#english)

---

## Português

### Sobre o projeto

Implementação do padrão de projeto **DAO (Data Access Object)** com **Generics** em Java puro, sem frameworks externos. O objetivo é demonstrar como abstrair operações de persistência de forma reutilizável, tipada e extensível.

### Estrutura

```
src/
├── domain/
│   ├── GenericDomain.java          # Classe base abstrata com id genérico
│   ├── UserDomain.java             # Entidade de usuário (id: Integer, gerado automaticamente)
│   ├── ClientDomain.java           # Entidade de cliente (id: String)
│   └── MenuOption.java             # Enum com as opções do menu interativo
├── dao/
│   ├── GenericDAO.java             # DAO genérico com operações CRUD
│   ├── UserDAO.java                # DAO específico de usuário (auto-incremento de id)
│   └── ClientDAO.java              # DAO específico de cliente
├── Exceptions/
│   ├── EntityNotFoundException.java  # Lançada quando um id não é encontrado
│   ├── EmptyStorageException.java    # Lançada quando o armazenamento está vazio
│   └── ValidatorException.java       # Lançada quando a validação de entidade falha
├── UserValidator/
│   ├── Validator.java              # Interface genérica de validação
│   └── UserDomainValidator.java    # Validador de regras de negócio para UserDomain
└── test/
    └── DAOTest.java                # Menu interativo via terminal + testes com assert
```

### Funcionalidades

- `save(T)` — salva uma entidade
- `saveAll(T...)` — salva múltiplas entidades
- `findById(ID)` — busca por id com retorno `Optional<T>`
- `findByIdOrThrow(ID)` — busca por id, lançando `EntityNotFoundException` se não encontrado
- `find(Predicate<T>)` — busca por qualquer critério via lambda
- `exists(ID)` — verifica se um id existe
- `update(ID, T)` — atualiza uma entidade, lançando `EntityNotFoundException` se o id não existir
- `delete(T)` — remove por objeto
- `deleteById(ID)` — remove por id
- `findAll()` — retorna lista imutável de todas as entidades (retorna lista vazia se o storage estiver vazio)
- `count()` — retorna o total de entidades salvas
- `verifyStorage()` — lança `EmptyStorageException` se o armazenamento estiver vazio

### Decisões de design

- Armazenamento interno via `LinkedHashMap<ID, T>` para busca em O(1) e preservação de ordem de inserção
- `findAll()` retorna `Collections.unmodifiableList` para proteger o estado interno; retorna lista vazia em vez de lançar exceção
- Exceções customizadas: `EntityNotFoundException`, `EmptyStorageException` e `ValidatorException`
- `@SafeVarargs` em `saveAll` para suprimir warning de heap pollution em generics
- `UserDAO` implementa auto-incremento de id via campo `nextId`
- Camada de validação desacoplada via interface genérica `Validator<T>` e implementação `UserDomainValidator`
- Menu interativo via terminal com `Scanner` e enum `MenuOption` para navegação por operações CRUD

### Tecnologias

- Java 21
- Sem dependências externas

---

## English

### About

Implementation of the **DAO (Data Access Object)** design pattern using **Generics** in plain Java, with no external frameworks. The goal is to demonstrate how to abstract persistence operations in a reusable, type-safe, and extensible way.

### Structure

```
src/
├── domain/
│   ├── GenericDomain.java          # Abstract base class with generic id
│   ├── UserDomain.java             # User entity (id: Integer, auto-generated)
│   ├── ClientDomain.java           # Client entity (id: String)
│   └── MenuOption.java             # Enum with interactive menu options
├── dao/
│   ├── GenericDAO.java             # Generic DAO with full CRUD operations
│   ├── UserDAO.java                # User-specific DAO (auto-increment id)
│   └── ClientDAO.java              # Client-specific DAO
├── Exceptions/
│   ├── EntityNotFoundException.java  # Thrown when an id is not found
│   ├── EmptyStorageException.java    # Thrown when storage is empty
│   └── ValidatorException.java       # Thrown when entity validation fails
├── UserValidator/
│   ├── Validator.java              # Generic validation interface
│   └── UserDomainValidator.java    # Business rule validator for UserDomain
└── test/
    └── DAOTest.java                # Interactive terminal menu + assert-based tests
```

### Features

- `save(T)` — persists an entity
- `saveAll(T...)` — persists multiple entities at once
- `findById(ID)` — finds by id, returns `Optional<T>`
- `findByIdOrThrow(ID)` — finds by id, throws `EntityNotFoundException` if not found
- `find(Predicate<T>)` — finds by any criteria using a lambda
- `exists(ID)` — checks whether an id exists
- `update(ID, T)` — updates an entity, throwing `EntityNotFoundException` if id is not found
- `delete(T)` — removes by object
- `deleteById(ID)` — removes by id
- `findAll()` — returns an unmodifiable list of all entities (returns empty list if storage is empty)
- `count()` — returns the total number of stored entities
- `verifyStorage()` — throws `EmptyStorageException` if storage is empty

### Design decisions

- Internal storage uses `LinkedHashMap<ID, T>` for O(1) lookups and insertion-order preservation
- `findAll()` returns `Collections.unmodifiableList` to protect internal state; returns empty list instead of throwing
- Custom exceptions: `EntityNotFoundException`, `EmptyStorageException`, and `ValidatorException`
- `@SafeVarargs` on `saveAll` to suppress heap pollution warning on generic varargs
- `UserDAO` implements auto-increment id via `nextId` field
- Decoupled validation layer via generic `Validator<T>` interface and `UserDomainValidator` implementation
- Interactive terminal menu using `Scanner` and `MenuOption` enum for CRUD navigation

### Tech

- Java 21
- No external dependencies

---

[github.com/DwnlCR](https://github.com/DwnlCR)
