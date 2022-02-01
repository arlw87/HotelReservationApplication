# Hotel Reservation System Application

## Description

A console based Hotel Resrvation System written in Java 11. The application deals with any incorrect inputs from the 
user gracefully.

## Overview

The program is written in several layers. 

1. The UI layer - Console based interface for the user
2. The API - The interface between the user interface and the backend services
3. The Services - Stateful that provide the storage of the systems data and the logic to manipulate it
4. The Model - Models of data objects for the application

## Using the Program

The user interface has two menu systems the main menu and the admin menu.

### Main Menu
There are three main options here: 
1. **Create a new customer** The user interface will ask for the customers email address, which is checked against existing
to ensure that it isnt already registered and is also if it is in the correct format. The first name and last namne of 
the customer are also entered
2. **Reserve a Room** The customer will enter their check in and out dates. These must be in the future and the check in date 
must be before the checkout, if they are not, the customer is asked to re-enter the dates. The apllication will then display
the available rooms for thoses dates, if there are none, it will check for rooms 7 days after the dates specified and offer those.
If the customer wants to continue with the booking they enter their email, if they have registered an account, if not then they
are given the option to create one. Finally they select the room they want to reserve and are given a summary of their reservation.
3. **Look up reservations** The customer can type in their email address to view all their current reservations. The email 
that is entered is validated and checked it is registered

### Admin Menu
There are four main options for the Admin Menu:
1. **Create a room** An administrator can create records of rooms for the hotel here. They are asked for the room number, 
room price per night and type of room (Single or Double). Any incorrect input is handled gracefully and requested for re-entry.
The system will also check if the room is already registered. Room numbers must be numeric and above -1. Price must be 0 or greater.
2. **View All Reservations** View all the reservation for the Hotel
3. **View All Customers** View all the customers registered in the Hotel system
4. **View All Rooms** View all the rooms registered on the system