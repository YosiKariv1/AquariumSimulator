# Aquarium - Multi-Threading and Design Patterns

![ללא שם](https://github.com/YosiKariv1/AquariumSimulator/assets/93153387/28639b1e-38f3-44ce-aa94-033f31c23b75)


This Java-based project simulates an interactive aquarium environment, with various sea creatures that move in multi-threaded operations. The application also integrates numerous design patterns for efficient code structuring and organization.

## Overview

1. **Singleton Pattern**: Ensures the uniqueness of instances such as the worm in the aquarium.
2. **Abstract Factory Pattern**: Responsible for the creation of different sea creature and plant instances.
3. **Prototype Pattern**: Allows duplication or cloning of sea creatures with customizable properties.
4. **Observer/Listener Pattern**: Implements a system where the aquarium responds to the sea creatures' food requests.
5. **Decorator Pattern**: Enhances the user interface by adding a new panel displaying a list of animals and enabling color changes.
6. **Memento Pattern**: Facilitates saving and restoring the state of sea creatures.
7. **State Pattern**: Manages changes in the hunger state of the fish dynamically.

## Getting Started

Ensure you have the Java Development Kit (JDK) installed on your system. To run the application:

1. Clone or download the project onto your local system.
2. Open your terminal and navigate to the project directory.
3. Compile and run the `AquaFrame.java` file.

## Functionality

The GUI provides several interactive buttons and menu options:

- `Add Animal`: Introduces a new sea creature into the aquarium.
- `Sleep`: Pauses all creatures.
- `Wake up`: Resumes movement for all creatures.
- `Reset`: Clears all creatures from the aquarium.
- `Food`: Places a worm into the aquarium for the creatures.
- `Info`: Shows data about all the creatures in the aquarium.
- `Exit`: Closes the application.
- `Duplicate Animal`: Clones a selected sea creature.
- `Change Color`: Alters the color of all sea creatures.

This project serves as a comprehensive example for understanding how various design patterns can be implemented in a real-world Java application. It provides a hands-on approach to learning these patterns and their practical use cases.
