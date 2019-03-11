# Kalah

Rules can be found at https://en.wikipedia.org/wiki/Kalah

# Run Application

mvn spring-boot:run

# Play Game

## Create Game

### Request
```bash
curl --header "Content-Type: application/json" --request POST http://localhost:8080/games
```
### Response
```json
{
  "uri": "http://localhost/games/1234",
  "id": "1234"
}
```

## Make A Move

### Request
```bash
curl --header "Content-Type: application/json" --request PUT http://localhost:8080/games/{gameId}/pits/{pitId}
```
### Response
```json
{
  "status": {
    "1": "4",
    "2": "4",
    "3": "4",
    "4": "4",
    "5": "4",
    "6": "4",
    "7": "0",
    "8": "4",
    "9": "4",
    "10": "4",
    "11": "4",
    "12": "4",
    "13": "4",
    "14": "0"
  },
  "url": "http://localhost/games/1234",
  "id": "1234"
}
```