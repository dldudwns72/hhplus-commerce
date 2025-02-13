# Spring Sleuth 란?
- Spring 기반 애플리케이션 분산 추적 기능 라이브러리
- 요청에 Trace 와 Span 정보를 자동으로 생성하고 전달
  - MDC.get("traceId") 자동 설정 되어 있어 별도의 UUID 생성을 하지 않고 사용할 수 있다.
- Zipkin 호환이 잘 되어 있어 같이 사용된다.

## Zipkin
- 분산 추적 데이터를 수집,저장,검색 및 시각화 하는 오픈 소스 도구

### 서버 설정
``` dockerfile
docker run -d -p 9411:9411 openzipkin/zipkin
```