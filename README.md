# Space Invaders Plus

A Java implementation of the classic Space Invaders game with extended features, built using JGameGrid framework.

<img width="748" alt="image" src="https://github.com/user-attachments/assets/f8757555-72ad-4332-be78-41c865940b2c" />


## Features

- Classic Space Invaders gameplay
- Multiple alien types (Normal, Powerful, Invulnerable, Multiple)
- Configurable game settings via properties files
- Auto-testing capabilities for automated gameplay
- Modern Java implementation with Gradle build system

<img width="822" alt="image" src="https://github.com/user-attachments/assets/20bc2b53-ba6c-4c37-b356-ad3128f36230" />

## Game Controls

- **Arrow Keys (← →)**: Move spacecraft left/right
- **Spacebar**: Fire bomb
- **Any Key**: Start the game (when on start screen)

## Game Instructions

1. Use the arrow keys to move your spacecraft
2. Press spacebar to shoot bombs at the aliens
3. Destroy all aliens to win
4. Avoid letting aliens reach your spacecraft


## Prerequisites

- Java 18 or higher
- No additional dependencies required for running the JAR file

## Running the Game

### Basic Execution
Run the game with default settings:
```bash
java -jar space-invaders-plus-1.0.jar
```

### Custom Configuration
Run with a specific properties file:
```bash
java -jar space-invaders-plus-1.0.jar properties/game2.properties
```

### Available Configurations
The game includes several built-in configurations:
- `properties/game1.properties` - Simple version with normal aliens
- `properties/game2.properties` - Extended version with Powerful, Invulnerable and Multiple aliens
- `properties/game3.properties` - Extended version with Powerful, Invulnerable and Multiple aliens

## Development

### Project Structure
```
app/
├── src/main/java/          # Java source code
├── src/main/resources/     # Game resources
│   ├── properties/         # Game configuration files
│   └── sprites/           # Game images
├── lib/                   # External JAR dependencies
└── build.gradle          # Build configuration
```

### Building for Development
```bash
./gradlew build
```

### Running Tests
```bash
./gradlew test
```

## Distribution

The generated JAR file (`space-invaders-plus-1.0.jar`) is completely self-contained and can be distributed as a single file. Recipients only need Java 18+ installed to run the game.
