# Android App Architecture – Clean Architecture + Offline-First

This project follows a modular and scalable architecture using **Clean Architecture principles**, with an emphasis on **Offline-First design**. The goal is to ensure a clean separation of concerns, high testability, and maintainability of the codebase.

---

## Architecture Layers

The application is divided into 3 main layers:

### 1. **Presentation Layer**
- Contains the UI (`Fragments`, `ViewModels`, `UiModels`).
- It communicates only with the **Domain Layer**, through **Use Cases**.
- Knows nothing about data sources or implementation details.

### 2. **Domain Layer**
- The core of the application.
- Contains business logic (`UseCases`) and core models (`Entities`).
- Defines interfaces for repositories, which are implemented in the Data layer.
- Completely platform-agnostic and independent.

### 3. **Data Layer**
- Handles data from both **Remote** and **Local** sources.
- Implements the repository interfaces defined in the Domain layer.
- Responsible for mapping between DTOs, Entities, and Domain Models.

##  Model Mapping Strategy

This project uses explicit mapping between different model layers:

| Transformation                          | Location                                   | Description                              |
|-----------------------------------------|--------------------------------------------|------------------------------------------|
| `ProductDto` → `ProductEntity`          | `data/mapper/DtoToEntityMapper.kt`         | Map network response to DB entity        |
| `ProductEntity` → `Product` (Domain)    | `data/mapper/EntityToDomainMapper.kt`      | Map local DB model to domain model       |
| `Product` (Domain) → `ProductUiModel`   | `presentation/mapper/DomainToUiMapper.kt`  | Map domain model to UI model             |

##  Package Structure

app
│
├── data
│   ├── remote
│   │   ├── dto
│   │   │   └── ProductDto.kt
│   │   └── ProductApiService.kt
│   │
│   ├── local
│   │   ├── entity
│   │   │   └── ProductEntity.kt
│   │   └── ProductDao.kt
│   │
│   ├── repository
│   │   └── ProductRepositoryImpl.kt
│   │
│   └── mapper
│       ├── DtoToEntityMapper.kt         // ProductDto → ProductEntity
│       └── EntityToDomainMapper.kt      // ProductEntity → Product
├── domain
│   ├── model
│   │   └── Product.kt
│   │
│   ├── repository
│   │   └── ProductRepository.kt
│   │
│   └── usecase
│       └── GetProductUseCase.kt
├── presentation
│   ├── model
│   │   └── ProductUiModel.kt
│   │
│   ├── mapper
│   │   └── DomainToUiMapper.kt         // Product → ProductUiModel
│   │
│   └── ui
│       └── product
│           ├── ProductFragment.kt
│           └── ProductViewModel.kt
└── di
    └── AppModule.kt



---

##  Offline-First Strategy

This application is designed to **work seamlessly without internet**. The general flow is:

1. Load data from **local database** (Room).
2. Show cached data immediately in the UI.
3. If network is available, fetch latest data from API.
4. On success, update the local database with fresh data.
5. The ViewModel observes only local data source (Room).

This ensures a fast and reliable user experience even when offline.

---

##  Benefits of This Architecture

- Clear separation of concerns.
- Offline-ready and scalable.
- Easy to test and maintain.
- Plug-and-play for remote or local data sources.
- Each layer knows only what it needs to know.

---

##  Technologies Used

- Kotlin
- MVVM
- Room (Local DB)
- Retrofit (Networking)
- Hilt (DI)
- Coroutines + Flow

---

##  Example Mapping Functions

```kotlin
// DtoToEntityMapper.kt
fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.fullName
    )
}
```

```// EntityToDomainMapper.kt
fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        name = this.name
    )
}
```

```// DomainToUiMapper.kt
fun User.toUiModel(): UserUiModel {
    return UserUiModel(
        displayName = this.name.uppercase()
    )
}
```


