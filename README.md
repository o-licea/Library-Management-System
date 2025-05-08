## Overview

This program acts as a simulation of a Library Management System using a Console-based interface with a built-in menu for navigation
It allows users to add, delete, edit, and list books and borrowers to the console, search for books within the database of the system, and
allows users to check out and check in books within the system. All changes and decisions made are recorded onto the books.txt files and
borrowers.txt files so that all the data is reloaded upon closure.

## How to Run

Open the built-in terminal, navigate to the project folder, and run the following commands in the project directory:
javac -d bin src/models/*.java src/services/*.java src/ui/*.java
cd bin
java ui.App

If the books.txt and/or borrowers.txt files are not found but the program still runs, the program will automatically generate them when 
data is saved to the application, so it will still work regardless.

The way that the file is appended to and updated is very specific and it is highly recommended to not manually update it as it is could
cause possible errors and break certain parts of the functions. If there are any issues when it comes to appending and updating, leave an
issue and I will look into it as soon as possible

## Features

Books and Borrowers can be added, removed, updated, and listed without having to go into the .txt file via the console, and all data that
is saved onto the current instance of the application is automatically saved onto the file, so it can be modified again upon starting a new
instance. Books can also be borrowed and returned using this interface, changes to this are also reflected within the file.
