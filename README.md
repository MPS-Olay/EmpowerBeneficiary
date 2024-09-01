# Beneficiary Management Application

This project is a robust Android application designed to manage beneficiary information effectively. It uses Clean Architecture principles to ensure clear separation of concerns and easier maintenance and scalability.

## Project Structure

The project is structured into several key directories, each serving a distinct role within the application:

- **data**
    - **dto**: Contains `BeneficiaryDTO.kt` for data transfer objects.
    - **entity**: Contains `BeneficiaryEntity.kt` for defining the entity models.
- **domain**
    - Contains business logic and use cases such as `GetAllBeneficiariesUseCase`.
- **presentation**
    - **beneficiaryDetail**: Manages the UI logic for the beneficiary detail view.
    - **beneficiaryList**: Manages the UI logic for displaying a list of beneficiaries.
        - Includes `BeneficiaryAdapter` for handling RecyclerView adapter logic.
        - Includes `BeneficiaryListFragment`, `BeneficiaryListState`, `BeneficiaryListViewModel`, and `BeneficiaryListViewModelFactory` for managing view states and interactions.
- **repo**
    - Contains `BeneficiaryRepository` for handling data retrieval and storage operations.
- **util**
    - Contains utilities like `BeneficiaryMapper` for transforming DTOs to domain entities, `JsonManager` for handling JSON operations, and general utility classes like `Logger` and `ThemeColor`.

## Features

- **List View**: Displays a comprehensive list of beneficiaries.
- **Detail View**: Offers detailed information about each beneficiary.
- **Robust Data Handling**: Uses `JsonManager` and `BeneficiaryMapper` to handle and transform JSON data efficiently.
- **Logging**: Implements a custom `Logger` to facilitate debugging and monitoring of application behavior.

## Implementation Details

### Data Management

- **BeneficiaryRepository**: Fetches data through `JsonManager` and uses `BeneficiaryMapper` to convert JSON objects into domain entities.
- **JsonManager**: Handles raw JSON operations, ensuring data is fetched and parsed correctly.
- **BeneficiaryMapper**: Provides robust mapping from `BeneficiaryDTO` to `Beneficiary`, encapsulating the logic necessary for data transformation.

### Presentation Layer

- **MainActivity** and Fragments: Serve as the main entry points for the user interface.
- **ViewModels and State Management**: Leverage modern Android architecture components to manage UI state reactively.

## Testing Strategy

- **Unit Testing**: Focused on individual components using MockK to mock dependencies.
- **Integration Testing**: Combines components under near-real scenarios to ensure they interact correctly.

## Fun

### Emojis in Log

This is just for fun and would not be in added in a non sample application. 

## Usage

### Prerequisites

- Android Studio Arctic Fox or later
- Min SDK version: 21

### Running the App

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle files and run the project on an emulator or physical device.

## Future Improvements

- **Dependency Injection**: Investigate using Dagger or Hilt to manage dependency injection, enhancing testability and scalability.
- **UI/UX Enhancements**: Improve user interface designs and interactions to enhance user experience.
- **Expanded Testing**: Increase test coverage and include more comprehensive integration tests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact Information

For more information or to report issues, please contact olay-recruitment@multiplatformservices.com.