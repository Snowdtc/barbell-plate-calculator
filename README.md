# Barbell Plate Calculator

## Project Goal
To build a simple Java desktop application that automatically calculates the exact combination of standard weight plates needed for each side of a barbell to reach a user's target lift weight, eliminating the need for manual gym math.

## MVP Scope (Do Quadrant)
*   **Inputs:** Target weight and bar weight via a simple Swing GUI.
*   **Calculation Logic:** Algorithm to subtract the bar weight, divide by two, and greedily match remaining weight with standard plates (45, 35, 25, 10, 5, 2.5 lbs).
*   **Outputs:** Clear display of the required plates per side.
*   **Validation:** Error handling for invalid inputs (e.g., negative numbers, target weight less than bar weight).

## Future Plans (Schedule Quadrant)
*   Custom plate inventory selection (e.g., toggling off 35 lb plates if the gym doesn't have them).
*   Kilogram to pound conversion toggle.
*   Visual graphical representation of the plates on the barbell.

## Tech Stack
*   **Language:** Java
*   **Libraries:** `javax.swing.*`, `java.awt.*`, `java.util.*`
*   **Environment:** Visual Studio Code
*   **Data Storage:** Local `.txt` configuration file to save the default barbell weight.
