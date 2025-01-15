# MediaTracker

MediaTracker is a REST API built with Java Spring that allows users to track the media they consume. It supports creating, reading, updating, and deleting (CRUD) operations for media items and their respective types. The data is stored in a PostgreSQL database.

## Technologies

- **Back-End:** Java with Spring Boot
- **Database:** PostgreSQL
- **Validation:** Hibernate Validator

## Models

### MediaType

Represents a category or type of media, such as "Anime," "Games," or "Movies."

Fields:
- `id` (Long): Auto-generated unique identifier.
- `name` (String): Name of the media type (e.g., "Anime").

### MediaItem

Represents a specific media item consumed by the user.

Fields:
- `id` (Long): Auto-generated unique identifier.
- `title` (String): Title of the media item.
- `rating` (Integer): Rating given by the user (1 to 10).
- `startDate` (LocalDate): Date the user started consuming the media.
- `finishDate` (LocalDate): Date the user finished consuming the media.
- `status` (MediaStatus): Current status of the media item (e.g., WISHLIST, IN_PROGRESS, COMPLETED, ON_HOLD, DROPPED).
- `mediaType` (MediaType): Associated media type.
- `notes` (String): Additional notes about the media item (max 2000 characters).


## Endpoints

### MediaType Endpoints

- **GET `/media-type`**
  - Retrieves a paginated list of all media types.
  - **Query Parameters:**
    - `page` (default: 0) - Page number.
    - `size` (default: 20) - Number of items per page.

- **GET `/media-type/{id}`**
  - Retrieves a media type by its ID.
  - **Path Parameters:**
    - `id` - The ID of the media type.

- **POST `/media-type`**
  - Creates a new media type.
  - **Request Body:**
    ```json
    {
      "name": "string"
    }
    ```

- **PUT `/media-type/{id}`**
  - Updates an existing media type by its ID.
  - **Path Parameters:**
    - `id` - The ID of the media type.
  - **Request Body:**
    ```json
    {
      "name": "string"
    }
    ```

- **DELETE `/media-type/{id}`**
  - Deletes a media type by its ID.
  - **Path Parameters:**
    - `id` - The ID of the media type.

---

### MediaItem Endpoints

- **GET `/media-item`**
  - Retrieves a paginated list of media items with optional filters.
  - **Query Parameters:**
    - `title` - Filter by a title containing this string.
    - `rating` - Filter by rating (1–10).
    - `status` - Filter by status (`WISHLIST`, `IN_PROGRESS`, `COMPLETED`, `ON_HOLD`, `DROPPED`).
    - `mediaTypeId` - Filter by media type ID.
    - `page` (default: 0) - Page number.
    - `size` (default: 20) - Number of items per page.

- **GET `/media-item/{id}`**
  - Retrieves a media item by its ID.
  - **Path Parameters:**
    - `id` - The ID of the media item.

- **POST `/media-item`**
  - Creates a new media item.
  - **Request Body:**
    ```json
    {
      "title": "string",
      "rating": 1,
      "startDate": "YYYY-MM-DD",
      "finishDate": "YYYY-MM-DD",
      "status": "WISHLIST",
      "mediaTypeId": 1,
      "notes": "string"
    }
    ```

- **PUT `/media-item/{id}`**
  - Updates an existing media item by its ID.
  - **Path Parameters:**
    - `id` - The ID of the media item.
  - **Request Body:**
    ```json
    {
      "title": "string",
      "rating": 1,
      "startDate": "YYYY-MM-DD",
      "finishDate": "YYYY-MM-DD",
      "status": "WISHLIST",
      "mediaTypeId": 1,
      "notes": "string"
    }
    ```

- **DELETE `/media-item/{id}`**
  - Deletes a media item by its ID.
  - **Path Parameters:**
    - `id` - The ID of the media item.

# Related Repositories

- [MediaTrackerWithAuth](https://github.com/Lemersom/MediaTrackerWithAuth)

