# Kotlin 학습 프로젝트

단계별 코틀린 학습을 위한 예제 코드 모음입니다.

## 📁 프로젝트 구조

```
src/main/kotlin/
├── basics/              # 코틀린 기초 문법
├── oop/                 # 객체지향 프로그래밍
├── collections/         # 컬렉션과 함수형 프로그래밍
├── coroutines/          # 코루틴 (비동기 프로그래밍)
├── advanced/            # 고급 기능들
└── src/                 # 기존 예제들 (패턴, SOLID 원칙 등)
```

## 🎯 학습 순서

### 1. 기초 문법 (basics/)
- **01_Variables.kt** - 변수 선언, 타입 시스템, null 안전성
- **02_Functions.kt** - 함수 정의, 파라미터, 람다
- **03_ControlFlow.kt** - 조건문, 반복문, when 표현식
- **04_StringTemplates.kt** - 문자열 템플릿, 멀티라인 문자열

### 2. 객체지향 프로그래밍 (oop/)
- **01_Classes.kt** - 클래스, 프로퍼티, 상속, 데이터 클래스
- **02_Interfaces.kt** - 인터페이스, 추상 클래스, 다형성
- **03_ObjectsAndCompanions.kt** - object 선언, companion object

### 3. 컬렉션과 함수형 프로그래밍 (collections/)
- **01_Lists.kt** - 리스트 조작, 필터링, 변환, 정렬
- **02_FunctionalProgramming.kt** - 고차함수, 람다, 스코프 함수
- **03_MapsAndSets.kt** - 맵과 셋 활용법

### 4. 코루틴 (coroutines/)
- **01_BasicCoroutines.kt** - 기본 코루틴, async/await, 예외 처리
- **02_Channels.kt** - 채널을 통한 데이터 전달, 파이프라인

### 5. 고급 기능 (advanced/)
- **01_Generics.kt** - 제네릭, 공변성/반공변성
- **02_Annotations.kt** - 애노테이션, 리플렉션 활용
- **03_DelegatedProperties.kt** - 프로퍼티 위임 패턴
- **04_ReflectionAndMetaprogramming.kt** - 리플렉션과 메타프로그래밍

### 6. 기존 예제들 (src/)
- **design_pattern/** - 디자인 패턴 (데코레이터, 옵저버 등)
- **solid/** - SOLID 원칙 예제
- **sealed/** - sealed class 활용
- **functional/** - 함수형 인터페이스

## 🚀 실행 방법

각 파일은 독립적으로 실행 가능한 main 함수를 포함하고 있습니다.

1. IntelliJ IDEA에서 프로젝트 열기
2. 원하는 예제 파일 선택
3. main 함수 왼쪽의 실행 버튼 클릭 또는 `Ctrl+Shift+F10`

## 💡 학습 팁

1. **순서대로 학습**: 기초부터 고급까지 순서대로 진행하세요
2. **실습 중심**: 코드를 직접 실행하고 수정해보세요
3. **주석 활용**: 각 예제의 주석을 참고하세요
4. **응용해보기**: 예제를 변형해서 새로운 기능을 만들어보세요

## 📚 추가 학습 자료

- [Kotlin 공식 문서](https://kotlinlang.org/docs/)
- [Kotlin Playground](https://play.kotlinlang.org/)
- [Android Kotlin 가이드](https://developer.android.com/kotlin)