# `legacy-mongo-repository-adapter`

## Support

`MongoRepository<T, ID>`

| 1.x                                                          | 2.x                                                          |
| ---                                                          | ---                                                          |
| `count(): long`                                              | `count(): long`                                              |
| `count(Example<S extends T>): long`                          | `count(Example<S extends T>): long`                          |
| `delete(ID): void`                                           | -                                                            |
| `delete(Iterable<? extends T>)`                              | -                                                            |
| `delete(T): void`                                            | `delete(T): void`                                            |
| `deleteAll(): void`                                          | `deleteAll(): void`                                          |
| -                                                            | `deleteAll(Iterable<? extends T>): void`                     |
| -                                                            | `deleteById(ID): void`                                       |
| `exists(Example<S extends T>): boolean`                      | `exists(Example<S extends T>): boolean`                      |
| `exists(ID): boolean`                                        | -                                                            |
| -                                                            | `existsById(ID): boolean`                                    |
| `findAll(): List<T>`                                         | `findAll(): List<T>`                                         |
| `findAll(Example<S extends T>): Iterable<S extends T>`       | `findAll(Example<S extends T>): Iterable<S extends T>`       |
| `findAll(Example<S extends T>, Pageable): Page<S extends T>` | `findAll(Example<S extends T>, Pageable): Page<S extends T>` |
| `findAll(Example<S extends T>, Sort): Iterable<S extends T>` | `findAll(Example<S extends T>, Sort): Iterable<S extends T>` |
| `findAll(Example<S extends T>): List<S extends T>`           | `findAll(Example<S extends T>): List<S extends T>`           |
| `findAll(Example<S extends T>, Sort): List<S extends T>`     | `findAll(Example<S extends T>, Sort): List<S extends T>`     |
| `findAll(Iterable<ID>): Iterable<T>`                         | -                                                            |
| `findAll(Pageable): Page<T>`                                 | `findAll(Pageable): Page<T>`                                 |
| `findAll(Sort): List<T>`                                     | `findAll(Sort): List<T>`                                     |
| -                                                            | `findAllById(Iterable<ID>): Iterable<T>`                     |
| -                                                            | `findById(ID): Optional<T>`                                  |
| `findOne(Example<S extends T>): S extends T`                 | `findOne(Example<S extends T>): Optional<S extends T>`       |
| `findOne(ID): T`                                             | -                                                            |
| `insert(Iterable<S extends T>): List<S extends T>`           | `insert(Iterable<S extends T>): List<S extends T>`           |
| `insert(S extends T): S extends T`                           | `insert(S extends T): S extends T`                           |
| `save(Iterable<S extends T>): Iterable<S extends T>`         | -                                                            |
| `save(Iterable<S extends T>): List<S extends T>`             | -                                                            |
| `save(S extends T): S extends T`                             | `save(S extends T): S extends T`                             |
| -                                                            | `saveAll(Iterable<S extends T>): Iterable<S extends T>`      |
| -                                                            | `saveAll(Iterable<S extends T>): List<S extends T>`          |

## Known limitations

### `findOne(Example<S extends T>)`

