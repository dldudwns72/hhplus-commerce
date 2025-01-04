# OrderController API 명세서

## 1. 상품 주문 API

- **HTTP 메서드**: `POST`
- **요청 경로**: `/v1/order/user/{userId}`
- **설명**: 사용자가 상품을 주문하는 API입니다.

### 요청 파라미터

#### PathVariable

- `userId`: `Long` - 주문을 진행하는 사용자의 ID.

#### RequestBody

- `orderRequests`: `List<OrderRequest>` - 주문할 상품 목록.
    - `OrderRequest` 필드:
        - `productId`: `Long` - 주문할 상품의 ID.
        - `quantity`: `Int` - 주문할 상품의 수량.

### 응답

#### 상태 코드

- `200 OK`: 주문이 성공적으로 처리됨.
- `400 Bad Request`: 요청 데이터가 유효하지 않음 (예: 상품 재고 부족, 잔액 부족).

#### 응답 본문

- **타입**: `OrderResponse`
    - `orderId`: `Long` - 생성된 주문 ID.
    - `totalAmount`: `Double` - 주문 총 금액.
    - `status`: `String` - 주문 상태 (예: "SUCCESS", "FAILED").

### 요청 예시

```json
[
  {
    "productId": 1,
    "quantity": 2
  },
  {
    "productId": 3,
    "quantity": 1
  }
]
```

### 응답 예시

```json
{
  "orderId": 1001,
  "totalAmount": 300.0,
  "status": "SUCCESS"
}

```

### 오류 예시

```json
{
  "message": "상품 재고가 존재하지 않습니다.",
  "status": 400
}
```

```json
{
  "message": "상품 금액보다 잔액이 부족합니다.",
  "status": 400
}
```

