## Introduction

### Storage Control App

The **Storage Control App** is a user-friendly application designed to efficiently manage and monitor warehouse inventory.
This tool allows you to track products, update their status, and oversee orders seamlessly.
It is built with Kotlin and utilizes Gradle for build and dependency management, ensuring a modern and flexible development process.
Whether you're managing a small warehouse or a large-scale storage facility, the Storage Control App helps streamline operations and maintain order accuracy.

## Key Features of the App

- Convenient addition, deletion, display, and updating of products and orders.
- Dynamic data processing with a user-friendly interface.
- Search for products and orders using various criteria (categories, price, size, and other parameters).
- Integration with XMLSerializer for saving and loading data.
- Management menu with easy navigation through the app's features.
- Order cost calculation.
- Quick stock availability check

  ## Code Quality and Testing

- Logical unit tests have been conducted to ensure the correctness of all functions.
- Manual code review and linting have been performed to improve code quality.
- Automatic linting with Ktlint has been applied to enforce modern coding standards.

   ## Documentation

- Every line of code is thoroughly documented to enhance readability and maintainability.
- For all functions, detailed **KDoc** comments have been created, providing clear descriptions of their purposes and functionalities.
- HTML documentation has been generated using KDoc, making it easy to browse and reference the codebase.
- A **License Report** is included to ensure clarity on third-party dependencies


   ## Libraries and Plugins Used

   ### Libraries
- [JUnit 5](https://junit.org/junit5/) - A framework for writing and running unit tests.
- [XStream 1.4.18](https://x-stream.github.io/) - A library for converting objects to XML and vice versa.
- [Dokka](https://kotlinlang.org/docs/dokka-overview.html) - A Kotlin documentation generator.

  ### Plugins
- [Dokka Plugin (1.9.20)](https://kotlinlang.org/docs/dokka-overview.html) - For generating project documentation in various formats, including HTML.
- [Ktlint Plugin (11.5.1)](https://github.com/JLLeitschuh/ktlint-gradle) - For enforcing Kotlin code style and standards.
- [Dependency License Report (2.4)](https://github.com/jk1/Gradle-License-Report) - For generating a report of dependencies and their licenses.
