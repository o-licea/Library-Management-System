## Overview

This program acts as a simulation of a Library Management System using a Console-based interface with a built-in menu for navigation
It allows users to add, delete, edit, and list books and borrowers to the console, search for books within the database of the system, and
allows users to check out and check in books within the system. All changes and decisions made are recored onto the books.txt files and
borrowers.txt files so that all the data is reloaded upon closure

## How to Run

Open the built-in terminal, navigate to the project folder, and run the following commands in the project directory:
javac -d bin src/models/*.java src/services/*.java src/ui/*.java
cd bin
java ui.App

If the books.txt and/or borrowers.txt files are not found but the program still runs, the program will automatically generate them when 
data is saved to the application, so it will still work regardless

## Author

- Oracio L.