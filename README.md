# legacy-mongo-repository-adapter

[![Build Status](https://travis-ci.org/msievers/legacy-mongo-repository-adapter.svg?branch=master)](https://travis-ci.org/msievers/legacy-mongo-repository-adapter)

This adapter is meant is a plugin replacement for `MongoRepository` providing combatibility with Spring Data MongoDB  1.x repository methods. It can be used with either Spring Data MongoDB 1.x or 2.x. When used with a 2.x version, missing methods will be emulated.

## Usage

### Add dependency

```xml
<dependency>
    <groupId>com.github.msievers</groupId>
    <artifactId>legacy-mongo-repository-adapter</artifactId>
</dependency>
```

### Replace `MongoRepository` with `MongoRepositoryAdatper`

```java
@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
}
```

```java
@Repository
public interface PetRepository extends LegacyMongoRepositoryAdapter<Pet, String> {
}
```

## Motivation

Migrating Spring Boot applications from Spring Boot 1.x to 2.x is often hard because of the breaking changes introduced with Spring Data 2. Many people postpone this step, because migrating all repository related code is an expensive task. With this package, you can just leave your code as-is.

For Spring Data MongoDB 1.x the original methods will be used. For version 2.x missing methods are emulated so that existing code won't break.

## Support

`MongoRepository<T, ID>`

| 1.x                                                          | 2.x                                                          |
| ---                                                          | ---                                                          |
| `count(): long`                                              | `count(): long`                                              |
| `count(Example<S extends T>): long`                          | `count(Example<S extends T>): long`                          |
| **`delete(ID): void`**                                       | -                                                            |
| **`delete(Iterable<? extends T>)`**                          | -                                                            |
| `delete(T): void`                                            | `delete(T): void`                                            |
| `deleteAll(): void`                                          | `deleteAll(): void`                                          |
| -                                                            | `deleteAll(Iterable<? extends T>): void`                     |
| -                                                            | `deleteById(ID): void`                                       |
| `exists(Example<S extends T>): boolean`                      | `exists(Example<S extends T>): boolean`                      |
| **`exists(ID): boolean`**                                    | -                                                            |
| -                                                            | `existsById(ID): boolean`                                    |
| `findAll(): List<T>`                                         | `findAll(): List<T>`                                         |
| `findAll(Example<S extends T>): Iterable<S extends T>`       | `findAll(Example<S extends T>): Iterable<S extends T>`       |
| `findAll(Example<S extends T>, Pageable): Page<S extends T>` | `findAll(Example<S extends T>, Pageable): Page<S extends T>` |
| `findAll(Example<S extends T>, Sort): Iterable<S extends T>` | `findAll(Example<S extends T>, Sort): Iterable<S extends T>` |
| `findAll(Example<S extends T>): List<S extends T>`           | `findAll(Example<S extends T>): List<S extends T>`           |
| `findAll(Example<S extends T>, Sort): List<S extends T>`     | `findAll(Example<S extends T>, Sort): List<S extends T>`     |
| **`findAll(Iterable<ID>): Iterable<T>`**                     | -                                                            |
| `findAll(Pageable): Page<T>`                                 | `findAll(Pageable): Page<T>`                                 |
| `findAll(Sort): List<T>`                                     | `findAll(Sort): List<T>`                                     |
| -                                                            | `findAllById(Iterable<ID>): Iterable<T>`                     |
| -                                                            | `findById(ID): Optional<T>`                                  |
| `findOne(Example<S extends T>): S extends T`                 | `findOne(Example<S extends T>): Optional<S extends T>`       |
| **`findOne(ID): T`**                                         | -                                                            |
| `insert(Iterable<S extends T>): List<S extends T>`           | `insert(Iterable<S extends T>): List<S extends T>`           |
| `insert(S extends T): S extends T`                           | `insert(S extends T): S extends T`                           |
| **`save(Iterable<S extends T>): List<S extends T>`**         | -                                                            |
| `save(S extends T): S extends T`                             | `save(S extends T): S extends T`                             |
| -                                                            | `saveAll(Iterable<S extends T>): Iterable<S extends T>`      |
| -                                                            | `saveAll(Iterable<S extends T>): List<S extends T>`          |

## Known limitations

### `findOne(Example<S extends T>)` is missing

Due to the same signature except the changed return type, this method cannot be emulated.

### `save(Iterable<S extends T>)` lacks bulk optimization
