# UserController API 명세서

## 1. 상품 목록 조회 API

- **HTTP 메서드**: `GET`
- **요청 경로**: `/v1/product`
- **설명**: 상품 목록을 조회하는 API입니다.
- **요청 파라미터**:
    - `page`: `Int` (optional, default: `0`) - 페이지 번호.
    - `size`: `Int` (optional, default: `20`) - 페이지당 상품 수.

### 응답 예시

```json
[
  {
    "id": 1,
    "title": "Product 1",
    "inventory": 10,
    "price": 100
  },
  {
    "id": 2,
    "title": "Product 2",
    "inventory": 20,
    "price": 300
  },
  {
    "id": 3,
    "title": "Product 3",
    "inventory": 30,
    "price": 200
  }
]

```

---

## 2. 상품 상세 조회 API

- **HTTP 메서드**: `GET`
- **요청 경로**: `/v1/product/{productId}`
- **설명**:  상품의 상세 정보를 조회하는 API입니다.

### 응답 예시

```json
{
  "id": 3,
  "title": "Product 3",
  "inventory": 30,
  "price": 200
}

```

---

## 3. 인기 상품 상위 5개 조회 API

- **HTTP 메서드**: `GET`
- **요청 경로**: `/v1/product/popular`
- **설명**:  인기 상품 상위 5개를 조회하는 API입니다.
- **요청 파라미터**:
    - `startDate`: `String` (optional, default: 3일 전) - 시작 날짜 (형식: yyyy-MM-dd).
    - `endDate`: `String` (optional, default: 현재 날짜) - 종료 날짜 (형식: yyyy-MM-dd).
    - `page`: `Int` (optional, default: `0`) - 페이지 번호.
    - `size`: `Int` (optional, default: `20`) - 페이지당 상품 수.

### 응답 예시

```json
[
  {
    "id": 1,
    "title": "Product 1",
    "price": 100,
    "inventory": 10,
    "rank": 1,
    "orderCount": 10
  },
  {
    "id": 2,
    "title": "Product 2",
    "price": 300,
    "inventory": 20,
    "rank": 2,
    "orderCount": 9
  },
  {
    "id": 3,
    "title": "Product 3",
    "price": 200,
    "inventory": 30,
    "rank": 3,
    "orderCount": 8
  },
  {
    "id": 4,
    "title": "Product 5",
    "price": 200,
    "inventory": 40,
    "rank": 4,
    "orderCount": 4
  },
  {
    "id": 5,
    "title": "Product 4",
    "price": 200,
    "inventory": 50,
    "rank": 5,
    "orderCount": 1
  }
]

```
