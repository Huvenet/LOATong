

# 2022.10.19


# 주제 : 프로젝트 구조 관련

# 회의 내용

---

## AWS 프로젝트 구조 진행 상황 공유

- AWS IAM 계정
  - Peter > Organization > IAM(log) 계정 생성 해준 상태
    - 여기서 22.10.18 대부분 AWS Resource 권한 허용 통과
      - S3 Public Access Policy Not Permmited 상태
        - Network Failure - verify permissions to access-analyzer:ValidatePolicy in IAM to use policy validation.
  - log 계정을 공유하는 형태로 관리하지말고, 각자 Peter에게 IAM 계정 생성 요청하고, log가 생성한 AWS Resource를 공유하는 형태로 진행

- API Server
  - EC2 생성해두었고, OS : RedHat으로 생성 함, API Proejct 8080 Port로 배포되고 있음, Nginx Server 80 Port로 배포되고있음
  - 키페어 `.pem`, `.ppk` 는 각각 IAM 계정 생성 되면, 해당 EC2 접근 권한 할당 받아서, <br> 자신의 키페어 등록해서 사용하는 걸로
  - nginx:80에서 Reverse_Proxy로 API:8080 redirection 설정 시도 > 실패 현재 진행 중
  - 배포 시에는 SFTP:22에서 /home/ec2-user/loatong/????.jar 안에 해당 jar 파일 존재하고,<br> nohup java -jar ????.jar &


- DNS
  - 가비아에서 loatong.me 를 1년 7900원에 결제 했음, `AWS Route 53`여기서 도메인 설정 할 수 있음
  - 현재 api.loatong.me = EC2 / loatong.me = S3 Bucket 으로 등록해둔 상태


- S3 Bucket (WebServer)
  - IAM 계정 각각 할당 받아서 해당 Bucket에 권한 할당해서 관리하는 형태로 진행
  - DNS loatong.me 연결해둔 상태
  - S3 Public Access Policy Not Permmited 상태 
    - Network Failure - verify permissions to access-analyzer:ValidatePolicy in IAM to use policy validation.
  - 정적 웹사이트 용도로 활용될 예정이라 단순 WEB 디렉토리 파일 옮기는 형태로 진행될 것 같음

- RDS (DBServer)
  - 굳이 RDS IAM 권한 공유는 딱히? 안해도 될 것 같고, 현재 API Server에서 해당 RDS 보도록 되어있음
  - 접속 정보는 #loatong 채널에 공유했음
  - 현재 0.0.0.0/0 상태지만 추후 OPENVpn 각각 고정 IP에 대해서만 접속 가능한 형태로 EC2, 개발자 VPN IP에서만 접근 가능하도록 막는 형태로 진행 예정

---

## 속도 관련

- 프로젝트 중 너무빨리, 너무천천히 그 중간의 속도를 맞추기 위해, 공부하고싶은 주제나 적용한 기술이 있을 경우,<br> `Tiki`에 올리지 않으면 적용 불가