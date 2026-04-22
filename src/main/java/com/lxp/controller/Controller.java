package com.lxp.controller;

import com.lxp.Service.Service;
import com.lxp.domain.Content;
import com.lxp.domain.Content.ContentType;
import com.lxp.domain.Course;
import com.lxp.domain.Course.CourseLevel;
import com.lxp.domain.CourseSection;
import com.lxp.repository.ContentRepository;
import com.lxp.repository.CourseRepository;
import com.lxp.repository.CourseSectionRepository;
import com.lxp.repository.JdbcContentRepository;
import com.lxp.repository.JdbcCourseRepository;
import com.lxp.repository.JdbcCourseSectionRepository;
import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {

        CourseRepository courseRepository = new JdbcCourseRepository();
        CourseSectionRepository courseSectionRepository = new JdbcCourseSectionRepository();
        ContentRepository contentRepository = new JdbcContentRepository();

        Service service = new Service(courseRepository, courseSectionRepository,
            contentRepository);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("===== LXP 학습 관리 시스템 =====");
            System.out.println("1.강좌 관리 | 2.섹션 관리 | 3.콘텐츠 관리 | 4.종료");
            System.out.print("선택: ");
            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    System.out.println("1.생성 | 2.조회 | 3.업데이트 | 4.삭제");
                    String courseMenu = sc.nextLine();
                    switch (courseMenu) {
                        case "1":
                            try {
                                System.out.println("강좌를 생성합니다");
                                System.out.print("제목을 입력해주세요 : ");
                                String title = sc.nextLine();
                                System.out.print("내용을 입력해주세요 : ");
                                String description = sc.nextLine();
                                System.out.print("id(숫자)를 입력해주세요 : ");
                                Long courseId = Long.parseLong(sc.nextLine());
                                System.out.println(" 강좌 레벨을 입력해주세요 ");
                                System.out.print("1 .BEGINNER 2.INTERMEDIATE 3.ADVANCED : ");
                                int levelInput = Integer.parseInt(sc.nextLine());
                                CourseLevel courseLevel;
                                switch (levelInput) {
                                    case 1:
                                        courseLevel = CourseLevel.BEGINNER;
                                        break;
                                    case 2:
                                        courseLevel = CourseLevel.INTERMEDIATE;
                                        break;
                                    case 3:
                                        courseLevel = CourseLevel.ADVANCED;
                                        break;
                                    default:
                                        System.out.println("잘못된 번호입니다 기본 값인 BEGINNER로 설정됩니다.");
                                        courseLevel = CourseLevel.BEGINNER;

                                }

                                service.createCourse(title, description, courseId, courseLevel);
                                System.out.println("저장이 완료 되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }

                            break;
                        case "2":
                            try {
                                System.out.println("강좌를 조회합니다");
                                System.out.print("ID를 입력해주세요 : ");
                                Long readId = Long.parseLong(sc.nextLine());
                                System.out.println("조회 결과 : " + service.readCourse(readId));
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "3":
                            try {
                                System.out.println("강좌를 업데이트합니다");
                                System.out.print("업데이트할 강좌 ID(숫자)를 입력해주세요 : ");
                                Long updateId = Long.parseLong(sc.nextLine());
                                System.out.print("바꿀 제목을 입력해주세요 : ");
                                String updateTitle = sc.nextLine();
                                System.out.print("바꿀 내용을 입력해주세요 : ");
                                String updateDescription = sc.nextLine();
                                System.out.println(" 강좌 레벨을 입력해주세요 ");
                                System.out.print("1 .BEGINNER 2.INTERMEDIATE 3.ADVANCED : ");
                                int updeatelevelInput = Integer.parseInt(sc.nextLine());
                                CourseLevel updatecourseLevel;
                                switch (updeatelevelInput) {
                                    case 1:
                                        updatecourseLevel = CourseLevel.BEGINNER;
                                        break;
                                    case 2:
                                        updatecourseLevel = CourseLevel.INTERMEDIATE;
                                        break;
                                    case 3:
                                        updatecourseLevel = CourseLevel.ADVANCED;
                                        break;
                                    default:
                                        System.out.println("잘못된 번호입니다 기본 값인 BEGINNER로 설정됩니다.");
                                        updatecourseLevel = CourseLevel.BEGINNER;
                                        break;
                                }
                                Course updateData = new Course(updateTitle, updateDescription,
                                    updateId, updatecourseLevel);
                                service.updateCourse(updateData);
                                System.out.println("변경이 완료되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "4":
                            try {
                                System.out.println("강좌를 삭제합니다");
                                System.out.print("삭제할 강좌 ID(숫자)를 입력해주세요 : ");
                                Long deleteId = Long.parseLong(sc.nextLine());
                                service.deleteCourse(deleteId);
                                System.out.println("삭제가 완료 되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                    }
                    break;
                case "2":
                    System.out.println("1.생성 | 2.조회 | 3.업데이트 | 4.삭제");
                    String sectionMenu = sc.nextLine();
                    switch (sectionMenu) {
                        case "1":
                            try {
                                System.out.println("섹션을 생성합니다");
                                System.out.print("타이틀을 입력해주세요 : ");
                                String title = sc.nextLine();
                                System.out.print("id(숫자)를 입력해주세요 : ");
                                Long courseId = Long.parseLong(sc.nextLine());
                                service.createCourseSection(courseId, title);
                                System.out.println("저장이 완료 되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "2":
                            try {
                                System.out.println("조회 방식 선택");
                                System.out.println("1. 단일 조회 2. 단체 조회");
                                String readchoice = sc.nextLine();
                                if (readchoice.equals("1")) {
                                    System.out.print("ID를 입력해주세요 : ");
                                    Long readId = Long.parseLong(sc.nextLine());
                                    System.out.println(
                                        "조회 결과 : " + service.readCourseSection(readId));
                                } else if (readchoice.equals("2")) {
                                    System.out.println("강좌의 섹션 목록");
                                    System.out.print("조회할 강좌의 ID(숫자)를 입력해주세요 : ");
                                    Long courseIdForSections = Long.parseLong(sc.nextLine());
                                    java.util.List<CourseSection> sections = service.CourseSectionAll(
                                        courseIdForSections);
                                    if (sections.isEmpty()) {
                                        System.out.println("등록된 섹션가 없습니다");
                                    } else {
                                        for (CourseSection section : sections) {
                                            System.out.println(section);
                                        }
                                    }
                                } else {
                                    System.out.println("잘못된 선택");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "3":
                            try {
                                System.out.println("섹션을 업데이트합니다");
                                System.out.print("업데이트할 섹션 ID(숫자)를 입력해주세요 : ");
                                Long updateId = Long.parseLong(sc.nextLine());
                                System.out.print("바꿀 타이틀을 입력해주세요 : ");
                                String updateTitle = sc.nextLine();
                                CourseSection updateData = new CourseSection(updateId, updateTitle);
                                service.updateCourseSection(updateData);
                                System.out.println("변경이 완료되었습니다");

                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;

                        case "4":
                            try {
                                System.out.println("섹션을 삭제합니다");
                                System.out.print("삭제할 섹션 ID(숫자)를 입력해주세요 : ");
                                Long deleteId = Long.parseLong(sc.nextLine());
                                service.deleteCourseSection(deleteId);
                                System.out.println("삭제가 완료 되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                    }
                    break;
                case "3":
                    System.out.println("1.생성 | 2.조회 | 3.업데이트 | 4.삭제");
                    String contentMenu = sc.nextLine();
                    switch (contentMenu) {
                        case "1":
                            try {
                                System.out.println("콘텐츠를 생성합니다");
                                System.out.print("콘텐츠를 입력해주세요 : ");
                                String title = sc.nextLine();
                                System.out.print("id(숫자)를 입력해주세요 : ");
                                Long sectionId = Long.parseLong(sc.nextLine());
                                System.out.print("1.VIDEO  2.DOCUMENT : ");
                                int contentTypeChoice = Integer.parseInt(sc.nextLine());
                                ContentType contentUpdate;
                                switch (contentTypeChoice) {
                                    case 1:
                                        contentUpdate = ContentType.VIDEO;
                                        break;
                                    case 2:
                                        contentUpdate = ContentType.DOCUMENT;
                                        break;
                                    default:
                                        System.out.println("잘못된 번호입니다. 기본값인 VIDEO로 설정됩니다.");
                                        contentUpdate = ContentType.VIDEO;
                                        break;
                                }
                                service.createContent(sectionId, title, contentUpdate);
                                System.out.println("저장이 완료 되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "2":
                            try {
                                System.out.println("조회 방식 선택");
                                System.out.println("1. 단일 조회 2. 단체 조회");
                                String readChoice = sc.nextLine();
                                if (readChoice.equals("1")) {
                                    System.out.print("ID를 입력해주세요 : ");
                                    Long sectionId = Long.parseLong(sc.nextLine());
                                    System.out.println("조회 결과 : " + service.readContent(sectionId));
                                } else if (readChoice.equals("2")) {
                                    System.out.println("섹션의 콘텐츠 목록");
                                    System.out.print("조회할 섹션의 ID(숫자)를 입력해주세요 : ");
                                    Long sectionId = Long.parseLong(sc.nextLine());
                                    java.util.List<Content> contents = service.ContentAll(
                                        sectionId);
                                    if (contents.isEmpty()) {
                                        System.out.println("등록된 콘텐츠가 없습니다");
                                    } else {
                                        for (Content content : contents) {
                                            System.out.println(content);
                                        }
                                    }
                                } else {
                                    System.out.println("잘못된 선택");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "3":
                            try {
                                System.out.println("콘텐츠를 업데이트합니다");
                                System.out.print("업데이트할 콘텐츠 ID(숫자)를 입력해주세요 : ");
                                Long updateId = Long.parseLong(sc.nextLine());
                                System.out.print("바꿀 콘텐츠을 입력해주세요 : ");
                                String updateTitle = sc.nextLine();
                                System.out.println("콘텐츠 타입을 선택해주세요");
                                System.out.print("1.VIDEO  2.DOCUMENT : ");
                                int contentTypeChoice = Integer.parseInt(sc.nextLine());
                                ContentType contentUpdate;
                                switch (contentTypeChoice) {
                                    case 1:
                                        contentUpdate = ContentType.VIDEO;
                                        break;
                                    case 2:
                                        contentUpdate = ContentType.DOCUMENT;
                                        break;
                                    default:
                                        System.out.println("잘못된 번호입니다. 기본값인 VIDEO로 설정됩니다.");
                                        contentUpdate = ContentType.VIDEO;
                                        break;
                                }
                                Content updateData = new Content(updateId, updateTitle,
                                    contentUpdate);
                                service.updateContent(updateData);
                                System.out.println("콘텐츠 변경이 완료되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                        case "4":
                            try {
                                System.out.println("콘텐츠를 삭제합니다");
                                System.out.print("삭제할 콘텐츠 ID(숫자)를 입력해주세요 : ");
                                Long deleteId = Long.parseLong(sc.nextLine());
                                service.deleteContent(deleteId);
                                System.out.println("삭제가 완료 되었습니다");
                            } catch (NumberFormatException e) {
                                System.out.println("ID는 숫자만 가능");
                            } catch (IllegalArgumentException e) {
                                System.out.println("경고 : " + e.getMessage());
                            } catch (Exception e) {
                                System.out.println("에러");
                            }
                            break;
                    }
                    break;
                case "4":
                    System.out.println("종료합니다");
                    return;
            }


        }
    }
}
