# MediClinicManager

[![Project Status](https://www.repostatus.org/badges/latest/wip.svg)](https://www.repostatus.org/#wip)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue)](#license)

## Project Description

**MediClinicManager** is a clinic management application designed to assist receptionists and HR staff in their daily tasks. The application allows for patient registration, managing doctor data, scheduling work shifts, and booking medical appointments.

## Features

1. **Patient Management:**
   - Creating profiles for new patients with personal information.
   - Searching for patients by PESEL number.
   - Searching for patients by last name.

2. **Doctor Management:**
   - Creating doctor profiles with contact information and specializations.
   - Adding new specializations to existing doctor profiles.
   - Searching for doctors by ID and specializations.

3. **Doctor Schedule:**
   - Creating work schedules for doctors.
   - Retrieving doctors' schedules for the upcoming week.

4. **Medical Appointments:**
   - Booking patient appointments at specific times.
   - Validating appointment times against the doctor's schedule and availability.

## Technologies

- **Programming Language:** Java
- **IDE:** IntelliJ IDEA
- **Libraries:** JUnit (unit testing)
- **Diagram Tool:** Excalidraw

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/YourRepository/MediClinicManager.git
   cd MediClinicManager
   ```

2. **Set up the project in IntelliJ IDEA:**
   - Open the project in IntelliJ IDEA.
   - Ensure the project compiles successfully.
   - If using the provided starter project, make sure all dependencies are correctly installed.

## Running the Application

1. **Start the application:**
   - Open IntelliJ IDEA and import the project.
   - Configure the main class, e.g., `Main.java`, and run the application.

2. **Console Interface:**
   - The application provides a console interface allowing management of patients, doctors, and schedules, where you can choose actions and execute operations.

## Tests

1. **Running tests:**
   - The project includes unit tests that can be run via IntelliJ IDEA or the command line.
   - All unit tests are written using the **JUnit** framework.
   - To run tests:
     TODO

2. **Sample tests:**
   - Tests include patient and doctor creation, schedule validation, and appointment booking.

## Class Diagram

The application's class diagram is located in the `docs` folder and illustrates the relationships between classes, their attributes, and dependencies. The diagram was created using **Excalidraw**.

![Class Diagram](link_to_diagram)

## Authors

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.