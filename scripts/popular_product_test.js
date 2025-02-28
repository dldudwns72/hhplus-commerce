import http from 'k6/http';
import { check, sleep } from 'k6';

// 부하 테스트 옵션 설정
export let options = {
    stages: [
        { duration: '10s', target: 100 },  // 10초 동안 10명의 가상 유저(VU) 도달
        { duration: '30s', target: 500 },  // 30초 동안 50명까지 증가
        { duration: '20s', target: 10 },  // 20초 동안 다시 10명으로 감소
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],  // 95% 요청이 500ms 이하인지 확인
        http_req_failed: ['rate<0.01'],    // 실패율 1% 미만 유지
    },
};

// 부하 테스트 실행
export default function () {
    let params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let url = 'http://host.docker.internal:8080/api/v1/product/popular?startDate=2023-01-12T14:30:00&endDate=2024-12-31T23:59:59';

    let res = http.get(url, params);

    // 응답 상태 코드 및 응답 시간 체크
    check(res, {
        '✅ 응답 코드 200': (r) => r.status === 200,
        '⚡ 응답 시간 < 500ms': (r) => r.timings.duration < 500,
    });

    // 요청 간격 조절 (부하 조절)
    sleep(1);
}
