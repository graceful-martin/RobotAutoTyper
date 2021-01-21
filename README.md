# [RobotAutoTyper](https://github.com/in-genieur/RobotAutoTyper)

[![License](https://img.shields.io/badge/license-AGPL%20v3.0%2B-brightgreen.svg)](https://www.gnu.org/licenses/agpl-3.0.html)
[![Language](https://img.shields.io/badge/openjdk-14-yellowgreen)](https://openjdk.java.net/projects/jdk/14/)

조금은 위험한 자동 타이핑 애플리케이션. <br>
윈도우 기준으로 작성하였으며 다른 OS에서는 테스트하지 않았습니다. <br>
이 프로그램 사용으로 따라오는 결과는 본인이 일체의 책임을 지지 않습니다. <br>

## 작동 원리

* 텍스트 파일로부터 문자열을 파싱 후 메모리에 상주시킵니다.
* 백그라운드에서 프로그램이 동작하면서 클립보드에 텍스트 파일의 문자열과 일치하는 텍스트가 들어오면
자동으로 텍스트 파일에서 해당 내용을 찾아 입력해줍니다.

## 올바른 실행 방법

백그라운드에서 동작하기 위해

> $ java -jar app.jar &
 
## 사용 방법 

텍스트 파일의 Default Path는 D:/Data.txt이며 다른 디렉토리거나 다른 파일 명일 시 수정이 필요합니다.

텍스트 파일의 내용 예시

> ABCD;;;안녕하세요

ABCD가 PC 클립보드에 저장되면 자동으로 안녕하세요를 자음/모음 분리해서 입력해줌. 
(약간 사람이 치는 것처럼 인터벌도 조절함.)

## 핫키

| **핫키** | **기능**      |
|----------|---------------|
| F7       | 일시중지/재개 |
| F8       | 정지          |

## Dependencies

| **Dependency**                                             |
|------------------------------------------------------------|
| [Gradle 6.8](https://www.google.com/search?client=safari&rls=en&q=gradle+6.8&ie=UTF-8&oe=UTF-8)                   | 
| [jnativehook 2.0.2](https://jar-download.com/artifacts/com.1stleg/jnativehook/2.0.2/source-code)    | 
| [jna 5.6.0](https://mvnrepository.com/artifact/net.java.dev.jna/jna/5.6.0)                    | 
