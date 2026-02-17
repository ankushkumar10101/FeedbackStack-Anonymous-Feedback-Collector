# FeedbackStack: Anonymous Feedback Collection Platform

A full-stack anonymous feedback collection system built with **Spring Boot** and **React**.

## Features

- **Anonymous Submission**: Users can submit feedback without logging in.
- **Rating System**: Interactive 5-star rating component.
- **MongoDB Storage**: All feedback data is stored in a MongoDB database (`feedback_db`).
- **Responsive UI**: Built with React and Bootstrap, featuring a glassmorphism design.
- **Admin Dashboard**: Frontend interface for viewing feedback analytics.

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.4.2 (Web, MongoDB, Lombok).
- **Database**: MongoDB (Localhost).
- **Frontend**: React (Vite), Axios, Bootstrap, React Router.

## Setup Instructions

### Prerequisites
- Java 21+
- Node.js 16+
- MongoDB installed and running on `localhost:27017`
- Maven (optional, if not using wrapper)

### Backend Setup
1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Run the application (using Maven Wrapper if available, or installed Maven):
   ```bash
   # If you have Maven installed:
   mvn spring-boot:run
   
   # Or using the wrapper (if generated):
   # ./mvnw spring-boot:run
   ```
   The backend will start at `http://localhost:8000` with context path `/api`.

### Frontend Setup
1. Open a new terminal.
2. Navigate to `frontend`:
   ```bash
   cd frontend
   ```
3. Install dependencies:
   ```bash
   npm install
   ```
4. Start Dev Server:
   ```bash
   npm run dev
   ```

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/feedback/` | Submit feedback |
| GET | `/api/analytics/` | Get feedback statistics |

## Notes
- **Frontend Only**: This project is currently a frontend demonstration. The backend has been removed, so API calls (submission, login) will not persist data but the UI is fully functional for preview.
- **Admin Access**: You can visit `/login` to see the admin login interface (use `admin`/`admin` for client-side demo validation).
