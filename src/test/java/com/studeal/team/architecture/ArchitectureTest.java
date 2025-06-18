package com.studeal.team.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * 아키텍처 제약 테스트를 위한 클래스 이 클래스는 애플리케이션의 아키텍처 규칙이 제대로 적용되고 있는지 검증합니다.
 */
@AnalyzeClasses(packages = "com.studeal.team", importOptions = {
    ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

  // 애플리케이션 코드를 스캔하여 JavaClasses 객체로 가져옴
  private final JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("com.studeal.team");

  @Test
  @DisplayName("레이어 아키텍처 테스트")
  public void layeredArchitectureTest() {
    layeredArchitecture()
        .consideringAllDependencies()
        .layer("Controller").definedBy("..api..")
        .layer("Service").definedBy("..application..")
        .layer("Repository").definedBy("..dao..")
        .layer("Domain").definedBy("..domain..")
        .layer("Dto").definedBy("..dto..")

        // 컨트롤러는 서비스와 DTO에만 의존할 수 있음
        .whereLayer("Controller").mayOnlyAccessLayers("Service", "Dto")

        // 서비스는 도메인, 레포지토리, DTO에 의존할 수 있음
        .whereLayer("Service").mayOnlyAccessLayers("Repository", "Domain", "Dto")

        // 레포지토리는 도메인과 DTO만 액세스할 수 있음
        .whereLayer("Repository").mayOnlyAccessLayers("Domain", "Dto")

        .check(importedClasses);
  }

  @Test
  @DisplayName("명명 규칙 테스트")
  public void namingConventionTest() {
    // 컨트롤러 클래스명은 'Controller'로 끝나야 함
    classes().that().areAnnotatedWith(RestController.class).or().areAnnotatedWith(Controller.class)
        .should().haveSimpleNameEndingWith("Controller")
        .check(importedClasses);

    // 서비스 클래스명은 'Service'로 끝나야 함
    classes().that().areAnnotatedWith(Service.class)
        .should().haveSimpleNameEndingWith("Service")
        .check(importedClasses);

    // 레포지토리 클래스명은 'Repository' 또는 'RepositoryImpl'로 끝나야 함
    classes().that().areAnnotatedWith(Repository.class)
        .should().haveSimpleNameEndingWith("Repository")
        .orShould().haveSimpleNameEndingWith("RepositoryImpl")
        .check(importedClasses);
  }

  @Test
  @DisplayName("패키지 의존성 테스트")
  public void packageDependencyTest() {
    // global 패키지는 domain 패키지에 의존하지 않아야 함
    noClasses().that().resideInAPackage("..global..")
        .should().dependOnClassesThat().resideInAPackage("..domain..")
        .because("글로벌 컴포넌트는 특정 도메인에 의존해서는 안됩니다")
        .check(importedClasses);

    // API 계층은 DAO 계층에 직접 접근하지 않아야 함
    noClasses().that().resideInAPackage("..api..")
        .should().dependOnClassesThat().resideInAPackage("..dao..")
        .because("컨트롤러는 레포지토리에 직접 의존해서는 안됩니다")
        .check(importedClasses);
  }

  @Test
  @DisplayName("컨트롤러 클래스 주석 테스트")
  public void controllerClassCommentTest() {
    // 모든 Controller 클래스는 주석을 포함해야 함
    classes().that()
        .haveSimpleNameEndingWith("Controller")
        .should()
        .haveCommentContaining("컨트롤러")
        .orShould()
        .haveCommentContaining("Controller")
        .check(importedClasses);
  }

  @Test
  @DisplayName("모든 DTO 클래스는 불변성을 유지해야 함")
  public void dtoClassesShouldBeImmutable() {
    // DTO 클래스에서 setter 메서드를 금지
    classes().that()
        .haveSimpleNameEndingWith("DTO")
        .or().haveSimpleNameEndingWith("Dto")
        .should()
        .notHaveMembersThat()
        .areMethods()
        .andHaveNameMatching("set[A-Z].*")
        .because("DTO 클래스는 불변성을 유지해야 합니다")
        .check(importedClasses);
  }

  @Test
  @DisplayName("예외 클래스는 Exception으로 끝나야 함")
  public void exceptionClassNamingTest() {
    classes().that()
        .areAssignableTo(Exception.class)
        .should()
        .haveSimpleNameEndingWith("Exception")
        .because("예외 클래스의 이름은 명확하게 'Exception'으로 끝나야 합니다")
        .check(importedClasses);
  }
}
