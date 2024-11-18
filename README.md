# Notes App

This project is an Android app for managing notes. The app allows users to add, edit, and delete notes. Each note consists of a title and a description. The app uses the Room library for local database storage and Firestore for cloud synchronization.

## Functionality

The following **required** functionality is completed:
* [x] User sees a list of notes displayed in a RecyclerView.
* [x] User can add a new note.
* [x] User can edit existing notes.
* [x] User can delete notes.
* [x] Each note consists of a title and a description.
* [x] The app saves notes to a local database.
* [x] The app synchronizes notes with Firebase Firestore for cloud storage.
* [x] User-specific notes are managed, ensuring different users see their respective notes upon login.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src="https://github.com/user-attachments/assets/fb0e8174-52fa-42af-be30-b34518d09b51" width=250/>

## Extensions

- Successfully integrated Room for local database storage to ensure data persistence.
- Implemented Firestore for cloud synchronization, allowing notes to be stored and retrieved from the cloud.
- Managed user-specific notes with Firebase Authentication to ensure that different users see their respective notes.

## Notes

Describe any challenges encountered while building the app.
* Implementing the database and ensuring data persistence.
* Managing the state of the UI during note updates and deletions.
* Ensuring data synchronization between local storage and cloud storage.
* Handling user-specific data to ensure security and privacy.

## License

Copyright [2024] [Lucas Herbst]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
