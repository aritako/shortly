# Shortly - URL Shortener

A modern URL shortening service built with Next.js and Spring Boot, inspired by Bitly. Shortly helps you create short, memorable links from long URLs.

## Features

- 🔗 URL shortening with custom aliases
- 📊 Click tracking and analytics
- 🎨 Modern, responsive UI
- 🔒 Secure link generation
- 📱 Mobile-friendly design

## Tech Stack

### Frontend

- Next.js 14
- TypeScript
- Tailwind CSS
- Shadcn UI Components

### Backend

- Spring Boot
- Java
- Maven

## Project Structure

```
shortly/
├── frontend/           # Next.js frontend application
│   ├── src/           # Source code
│   ├── public/        # Static assets
│   └── components/    # React components
│
└── backend/           # Spring Boot backend application
    └── src/           # Java source code
```

## Getting Started

### Prerequisites

- Node.js 18+ (for frontend)
- Java 17+ (for backend)
- Maven (for backend)

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## Environment Variables

### Frontend

Create a `.env` file in the frontend directory with:

```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

### Backend

Configure the `env.properties` file in the backend directory with your database and other settings.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
