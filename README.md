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

master, develop에 직접 커밋 할 수 없습니다.

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

커밋 전 수정된 코드 ctrl(cmd) + alt + L 필수 (코드 정리)

klint를 사용하여 일관화 된 코드를 작성합니다.

## PR 룰

PR의 제목은
 
```kotlin
[Feat] 회원가입 기능 추가
[Fix] 닉네임 특수문자 버그 수정 Resolves:#001
```

이런 스타일로 작성합니다.
이슈에서 나온 버그에 대한 fix일 경우 PR 제목 뒤에 이슈번호를 달아줍니다.


PR을 올리면 본인을 Assignees하고 본인을 제외한 세명의 Reviewer를 태그합니다. 
3명중 2명의 approve를 받아야 머지를 할 수 있습니다.
작성중인 pr에 해당하는 Labels를 달아줘야합니다.


PR을 올리게 되면 klint를 사용하며 githut action을 통해 자동으로 확인합니다.
lint에 맞지 않는 코드는 pr을 올리게 되면 github action을 통해 자동으로 피드백을 받을 수 있습니다.
린트에 맞지 않는 코드는 머지를 할 수 없습니다


## Issues 룰
버그를 발견했으면 Issues를 작성합니다.

제목
```kotlin
닉네임 특수문자 버그
```

내용

```kotlin
회원가입 버튼 클릭 -> 닉네임에 특수문자 입력 후 회원가입 -> 회원가입 처리 오류 발생
(RegisterActivity)

갤럭시 s24 안드로이드 14.0.1
```
description에 버그 재현 경로를 작성합니다
발생한 코드의 위치를 알면 작성합니다.
재현 기기의 모델명, Android 버전에 대해 작성합니다. 만약 ui이슈인 경우 해당 기기의 화면 해상도도 같이 작성합니다.
 
이슈 작성 후 수정할 담당자가 정해지면 Assignees를 달아준 후 해당 버그에 맞는 Labels를 달아줍니다.


## 버전 명 관리
1.0.0 부터 (Major, Minor, Patch) 순으로 작성합니다.
안드로이드에서는 versionCode가 존재합니다. versionCode는 매 배포시 1씩 증가해서 올립니다.
