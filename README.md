# 오픈소스SW개론 팀프로젝트


## contributor

- 22013560 신성휘
- 21013313 김예지
- 22013527 조용준
- 21011786 김도현


## 앱이름

세종대학교 오픈소스sw개론 (팀이름) 안드로이드 프로젝트 레포지토리 입니다

(간단설명, 스크린샷)


## 사용된 기술스택, 오픈소스 목록

어쩌구저쩌구

## 커밋 룰

GitFlow를 사용하며 master, develop, release, feature, hotfix 브랜치를 사용합니다.

master, develop에 직접 커밋 할 수 없습니다

| 태그 이름 | 설명 |
| --- | --- |
| [Feat] | 새로운 기능을 추가할 경우 |
| [Fix] | 버그를 고친경우 |
| [Design] | xml 등 사용자 UI 디자인 변경 |
| [!HOTFIX] | 급하게 치명적인 버그를 고쳐야하는 경우 |
| [Style] | 코드 포맷 변경, 린트 수정, 코드 수정이 없는 경우 |
| [Comment] | 필요한 주석 추가 및 변경 |
| [Docs] | 문서를 수정한 경우 |
| [Test] | 테스트 추가, 테스트 리팩토링(프로덕션 코드 변경 X) |
| [Chore] | 빌드 태스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경 X) |
| [Rename] | 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우 |
| [Remove] | 파일을 삭제하는 작업만 수행한 경우 |
| [Setting] | Gradle, Manifest 등 파일에 세팅 추가 |

예시) 

```kotlin
- [Feat] 회원가입 기능 추가
- [Docs] Readme 수정
```

이슈에 올라온 버그 수정은 커밋 뒤에 Resolves:#이슈번호 를 붙여줍니다.

```kotlin
[Fix] 회원가입 닉네임 중복확인 로직 이슈 수정
Resolves: #123
```

## 코드컨벤션

| 이름 | 예시 |
| --- | --- |
| 클래스 이름 : PascalCase | class MainActivity |
| 함수 이름 : camelCase | fun setPost |
| 변수 이름 : camelCase | private var userData |
| 레이아웃 이름 : 속성_페이지_위치 | layout_main |
| 뷰 이름 : 속성_페이지_용도(_사용방법) | tv_login_id |
| num class 내 변수 이름 : 모두 다 대문자 | enum class Type {A, B, C, D} |
| drawable 내 리소스 이름 : 리소스타입(_위치)_용도 | img_main_like |

커밋 전 수정된 코드 ctrl(cmd) + alt + L 필수

klint를 사용하며 githut action을 통해 자동으로 확인합니다.

린트에 맞지 않는 코드는 pr approve를 하지 않습니다.

## PR 룰

어쩌구저쩌구