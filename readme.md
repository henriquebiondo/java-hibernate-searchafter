# Hibernate Search with `searchAfter` - Usage Example

This repository contains an example project that demonstrates how to use the `searchAfter` functionality from the `org.hibernate.search` library to implement efficient pagination for large datasets with Hibernate Search.

## Overview

The `searchAfter` method provides a way to paginate through large datasets efficiently. Instead of using index-based pagination (`offset`), it allows you to start a new page of results from the last item of the previous page, avoiding the inefficiencies of reprocessing earlier pages. This is particularly useful in scenarios with large data volumes.

This example uses:

- **Hibernate Search** for indexing and searching.
- **OpenSearch or Elasticsearch** as the backend provider.
- **MySQL** as the relational database.


## Prerequisites
Before starting, ensure the following components are installed on your machine:

- **JDK 11+**
- **Maven**
- **Docker and Docker Compose (to run OpenSearch and MySQL)**         


## Setup

### 1. Clone the repository:
```bash
git clone https://github.com/henriquenicolli/java-hibernate-searchafter
cd java-hibernate-searchafter
```

### 2. Start the OpenSearch and MySQL containers:
```bash
docker-compose up -d
```

### 3. Run the project:
```bash
mvn spring-boot:run
```


## Usage Examples       

### 1. Save Product

**Endpoint:** `GET /save`  
**Description:** Saves a mock list of products into the database.

#### Example Request:

```bash
curl -X GET "http://localhost:8080/save"
```


### 2. Find Products by Name (Paginated)

**Endpoint:** `GET /find/{name}`  
**Description:** Searches for products by name with traditional pagination.

#### Example Request:

```bash
curl -X GET "http://localhost:8080/find/exampleProduct?page=0&pageSize=10"
```


### 3. Find Products by Name (Search After)

**Endpoint:** `GET /find/after/{name}`  
**Description:** Searches for products by name using searchAfter for efficient pagination.

#### Example Request:

```bash
curl -X GET "http://localhost:8080/find/after/exampleProduct?lastId=5&pageSize=10"
```


## How It Works ##
- The service uses the Hibernate Search API to perform the search.
- The results list is sorted by a specified field.
- The value of the last entity’s field in the current page is returned as searchAfter for the next request.


## How to Contribute ##
- Fork this repository.
- Create a branch for your feature or fix: git checkout -b my-feature.
- Commit your changes: git commit -m 'Add new feature'.
- Push your branch: git push origin my-feature.
- Open a Pull Request.


## Author
This project was developed by **Henrique Biondo Nicolli Soares**.  
Feel free to reach out for collaboration or questions regarding the project!  

- GitHub: [henriquenicolli](https://github.com/henriquenicolli)  
- Email: henrique.nicolli@gmail.com
- LinkedIn: [Henrique Biondo Nicolli Soares](https://www.linkedin.com/in/henrique-biondo-nicolli-soares-4aa408106/)  