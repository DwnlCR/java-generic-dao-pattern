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
│   ├── GenericDomain.java      # Classe base abstrata com id genérico
│   ├── UserDomain.java         # Entidade de usuário (id: Integer)
│   └── ClientDomain.java       # Entidade de cliente (id: String)
├── dao/
│   ├── GenericDAO.java         # DAO genérico com operações CRUD
│   ├── UserDAO.java            # DAO específico de usuário
│   └── ClientDAO.java          # DAO específico de cliente
└── test/
    └── DAOTest.java            # Testes com assert para todas as operações
```

### Funcionalidades

- `save(T)` — salva uma entidade
- `saveAll(T...)` — salva múltiplas entidades
- `findById(ID)` — busca por id com retorno `Optional<T>`
- `find(Predicate<T>)` — busca por qualquer critério via lambda
- `exists(ID)` — verifica se um id existe
- `update(ID, T)` — atualiza uma entidade, lançando `EntityNotFoundException` se o id não existir
- `delete(T)` — remove por objeto
- `deleteById(ID)` — remove por id
- `findAll()` — retorna lista imutável de todas as entidades
- `count()` — retorna o total de entidades salvas

### Decisões de design

- Armazenamento interno via `LinkedHashMap<ID, T>` para busca em O(1) e preservação de ordem de inserção
- `findAll()` retorna `Collections.unmodifiableList` para proteger o estado interno
- `EntityNotFoundException` customizada para erros de id inexistente
- `@SafeVarargs` em `saveAll` para suprimir warning de heap pollution em generics

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
│   ├── GenericDomain.java      # Abstract base class with generic id
│   ├── UserDomain.java         # User entity (id: Integer)
│   └── ClientDomain.java       # Client entity (id: String)
├── dao/
│   ├── GenericDAO.java         # Generic DAO with full CRUD operations
│   ├── UserDAO.java            # User-specific DAO
│   └── ClientDAO.java          # Client-specific DAO
└── test/
    └── DAOTest.java            # Assert-based tests for all operations
```

### Features

- `save(T)` — persists an entity
- `saveAll(T...)` — persists multiple entities at once
- `findById(ID)` — finds by id, returns `Optional<T>`
- `find(Predicate<T>)` — finds by any criteria using a lambda
- `exists(ID)` — checks whether an id exists
- `update(ID, T)` — updates an entity, throwing `EntityNotFoundException` if id is not found
- `delete(T)` — removes by object
- `deleteById(ID)` — removes by id
- `findAll()` — returns an unmodifiable list of all entities
- `count()` — returns the total number of stored entities

### Design decisions

- Internal storage uses `LinkedHashMap<ID, T>` for O(1) lookups and insertion-order preservation
- `findAll()` returns `Collections.unmodifiableList` to protect internal state
- Custom `EntityNotFoundException` for missing id errors
- `@SafeVarargs` on `saveAll` to suppress heap pollution warning on generic varargs

### Tech

- Java 17+
- No external dependencies

---

[github.com/DwnlCR](https://github.com/DwnlCR)
