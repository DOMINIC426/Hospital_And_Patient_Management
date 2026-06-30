# Project Tracking for Table and Repository Creation

## What was created
- Added JPA entities for the full healthcare schema:
  - `User`
  - `MedicalHistory`
  - `Visit`
  - `NutritionAssessment`
  - `Anthropometrics`
  - `MentalHealthScreen`
  - `LaboratoryResults`
  - `NutritionDiagnosis`
  - `Appointment`
  - `DrugNutritionInteraction`
  - `FoodRecommendation`
  - `PopulationAnalytics`
  - `SecurityLog`
- Created supporting enums for domain values:
  - `Role`, `Sex`, `TriageLevel`, `AppointmentStatus`, `FoodCategory`, `RecommendationType`
- Added Spring Data JPA repository interfaces for all entities.

## Relationships handled
- `Users` → `MedicalHistory`: One-to-One
- `Users` → `Visits`: One-to-Many
- `Visits` → `NutritionAssessment`, `Anthropometrics`, `MentalHealthScreen`, `LaboratoryResults`, `NutritionDiagnosis`: One-to-One
- `Users` → `Appointments`: One-to-Many (patient bookings)
- `Appointments` → `Users` (clinician): Many-to-One
- `Users` → `SecurityLogs`: One-to-Many

## Notes
- All tables and repository interfaces were created as requested.
- Added phone-based authentication and registration using JWT.
- Removed `username` from the user model and login flow.
- Added `LoginRequest` and `AuthenticationResponse` DTOs for authentication.
- Updated Flyway migration path with a new version to remove `username` from `users`.

## Flyway setup
- Added Flyway dependency to the project.
- Configured datasource and Flyway settings in the application properties.
- Added the initial migration file at [src/main/resources/db/migration/V1__create_health_management_schema.sql](src/main/resources/db/migration/V1__create_health_management_schema.sql).
- Flyway will create and manage the flyway_schema_history table automatically during migrations.
