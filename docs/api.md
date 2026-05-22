# API dokumentace pro React frontend

Base URL pro lokální vývoj:

```text
http://localhost:8080
```

Backend používá session/cookie autentizaci přes Spring Security. Všechny požadavky z Reactu, které mají pracovat se session, musí posílat cookies:

```js
credentials: "include"
```

## CSRF

Projekt má zapnutou CSRF ochranu pro API. To znamená, že pro `POST`, `PUT`, `PATCH` a `DELETE` požadavky musí frontend poslat CSRF token.

Nejdřív si frontend vyžádá token:

```http
GET /api/csrf
```

Příklad odpovědi:

```json
{
  "parameterName": "_csrf",
  "token": "abc123...",
  "headerName": "X-XSRF-TOKEN"
}
```

React helper:

```js
export async function getCsrfToken() {
  const response = await fetch("/api/csrf", {
    credentials: "include"
  });

  if (!response.ok) {
    throw new Error("Nepodarilo se nacist CSRF token");
  }

  return response.json();
}
```

Použití u `POST` požadavku:

```js
const csrf = await getCsrfToken();

await fetch("/api/login", {
  method: "POST",
  credentials: "include",
  headers: {
    "Content-Type": "application/json",
    [csrf.headerName]: csrf.token
  },
  body: JSON.stringify(data)
});
```

Bez CSRF hlavičky může Spring Security vrátit:

```text
403 Forbidden
```

## Autentizace

### Registrace

```http
POST /api/register
```

Endpoint je veřejný, ale požaduje CSRF token.

Request body:

```json
{
  "firstname": "Jan",
  "surname": "Novak",
  "email": "jan.novak@example.com",
  "password": "Password1@"
}
```

Validace:

| Pole | Pravidla |
| --- | --- |
| `firstname` | povinné |
| `surname` | povinné |
| `email` | povinné, formát emailu |
| `password` | povinné, 8-20 znaků, alespoň jedno malé písmeno, velké písmeno, číslo a speciální znak z `@#$%^&+=` |

Úspěšná odpověď:

```json
{
  "id": 1,
  "name": "Jan",
  "email": "jan.novak@example.com"
}
```

Možné chyby:

| Status | Význam |
| --- | --- |
| `403` | chybí nebo je neplatný CSRF token |
| `409` | nevalidní registrační formulář, duplicitní email nebo uživatel už je přihlášený |

### Přihlášení

```http
POST /api/login
```

Endpoint je veřejný, ale požaduje CSRF token.

Request body:

```json
{
  "email": "jan.novak@example.com",
  "password": "Password1@"
}
```

Úspěšná odpověď:

```json
{
  "id": 1,
  "name": "Jan",
  "email": "jan.novak@example.com"
}
```

Po úspěšném přihlášení backend uloží autentizaci do HTTP session. Frontend musí u dalších requestů dál používat:

```js
credentials: "include"
```

Příklad v Reactu:

```js
export async function login(email, password) {
  const csrf = await getCsrfToken();

  const response = await fetch("/api/login", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      [csrf.headerName]: csrf.token
    },
    body: JSON.stringify({ email, password })
  });

  if (!response.ok) {
    throw new Error("Prihlaseni selhalo");
  }

  return response.json();
}
```

Možné chyby:

| Status | Význam |
| --- | --- |
| `401` | špatný email nebo heslo |
| `403` | chybí nebo je neplatný CSRF token |
| `405` | endpoint byl otevřen přes `GET`; login podporuje pouze `POST` |
| `409` | uživatel už je přihlášený |

### Odhlášení

```http
POST /api/logout
```

Endpoint zpracovává Spring Security. Požaduje CSRF token a session cookie.

Příklad:

```js
export async function logout() {
  const csrf = await getCsrfToken();

  const response = await fetch("/api/logout", {
    method: "POST",
    credentials: "include",
    headers: {
      [csrf.headerName]: csrf.token
    }
  });

  if (!response.ok) {
    throw new Error("Odhlaseni selhalo");
  }
}
```

Aktuální backend po logoutu používá `logoutSuccessUrl("/")`, takže odpověď může být redirect na `/`.

## Veřejná data

### Domovská stránka

```http
GET /api/home
```

Endpoint je veřejný.

Odpověď:

```json
{
  "news": [
    {
      "title": "Titulek aktuality",
      "content": "Obsah aktuality",
      "createdAt": "2026-05-21T13:00:00",
      "createdById": 1
    }
  ],
  "events": [
    {
      "title": "Nazev udalosti",
      "description": "Popis udalosti",
      "date": "2026-06-01T18:00:00",
      "departmentId": 1,
      "createdById": 1
    }
  ]
}
```

### Aktuality

```http
GET /api/news
```

Endpoint je veřejný.

Odpověď:

```json
{
  "news": [
    {
      "title": "Titulek aktuality",
      "content": "Obsah aktuality",
      "createdAt": "2026-05-21T13:00:00",
      "createdById": 1
    }
  ],
  "count": 1
}
```

### Události

```http
GET /api/events
```

Endpoint je veřejný.

Odpověď podle aktuální implementace:

```json
{
  "events": [
    {
      "title": "Nazev udalosti",
      "description": "Popis udalosti",
      "date": "2026-06-01T18:00:00",
      "departmentId": 1,
      "createdById": 1
    }
  ],
  "count": [
    {
      "title": "Nazev udalosti",
      "description": "Popis udalosti",
      "date": "2026-06-01T18:00:00",
      "departmentId": 1,
      "createdById": 1
    }
  ]
}
```

Poznámka pro backend: `count` teď vrací znovu seznam událostí. Pravděpodobně má volat `apiService.getEventsSize()` a vracet číslo.

## Role a chráněné cesty

Backend používá role ve formátu Spring Security authority:

```text
ROLE_ADMIN
ROLE_CHILD
ROLE_MEMBER
ROLE_UNDEFINED
```

Aktuální pravidla v `SecurityConfig`:

| Cesta | Přístup |
| --- | --- |
| `/admin/**`, `/api/admin/**` | pouze `ADMIN` |
| `/youth/**`, `/api/youth/**` | `CHILD`, `MEMBER`, `ADMIN` |
| ostatní cesty | aktuálně veřejné kvůli `.anyRequest().permitAll()` |

Pro produkční režim bude vhodnější změnit poslední pravidlo na:

```java
.anyRequest().authenticated()
```

Frontend nemá posílat roli jako důkaz oprávnění. Role musí rozhodovat backend podle session. Frontend může roli použít pouze pro zobrazení/skrytí prvků v UI.

## Doporučený React API klient

```js
const API_BASE = "";

async function request(path, options = {}) {
  const method = options.method ?? "GET";
  const headers = new Headers(options.headers);

  if (options.body && !headers.has("Content-Type")) {
    headers.set("Content-Type", "application/json");
  }

  if (!["GET", "HEAD", "OPTIONS"].includes(method.toUpperCase())) {
    const csrf = await getCsrfToken();
    headers.set(csrf.headerName, csrf.token);
  }

  const response = await fetch(`${API_BASE}${path}`, {
    ...options,
    method,
    credentials: "include",
    headers
  });

  if (!response.ok) {
    throw new Error(`API error ${response.status}`);
  }

  const contentType = response.headers.get("content-type") ?? "";
  if (contentType.includes("application/json")) {
    return response.json();
  }

  return null;
}

export const api = {
  csrf: () => getCsrfToken(),
  home: () => request("/api/home"),
  news: () => request("/api/news"),
  events: () => request("/api/events"),
  register: (data) => request("/api/register", {
    method: "POST",
    body: JSON.stringify(data)
  }),
  login: (data) => request("/api/login", {
    method: "POST",
    body: JSON.stringify(data)
  }),
  logout: () => request("/api/logout", {
    method: "POST"
  })
};
```
