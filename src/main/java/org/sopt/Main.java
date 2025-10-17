package org.sopt;

import org.sopt.controller.MemberController;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.MemoryMemberRepository;
import org.sopt.service.MemberServiceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        MemberServiceImpl memberService = new MemberServiceImpl();
        MemberController memberController = new MemberController();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. 회원 탈퇴");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("등록할 회원 이름을 입력하세요: ");
                    String name = scanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("⚠️ 이름을 입력해주세요.");
                        continue;
                    }

                    System.out.print("등록할 회원 이메일을 입력하세요: ");
                    String email = scanner.nextLine();
                    if (email.trim().isEmpty()) {
                        System.out.println("⚠️ 이메일을 입력해주세요.");
                        continue;
                    }

                    System.out.print("등록할 회원 생년월일을 입력하세요: yyyy-mm-dd");
                    String birthDateStr = scanner.nextLine();
                    Timestamp birthDate;
                    try {
                        birthDate = Timestamp.valueOf(birthDateStr + " 00:00:00");
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ 올바른 날짜 형식이 아닙니다. 예: 2000-01-01");
                        continue;
                    }

                    System.out.print("등록할 회원 성별(MALE/FEMALE)을 입력하세요: ");
                    String genderStr = scanner.nextLine();
                    Gender gender;
                    try {
                        gender = Gender.valueOf(genderStr.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ 올바른 성별을 입력해주세요 (MALE/FEMALE)");
                        continue;
                    }

                    Long createdId = memberController.createMember(name, email, birthDate, gender);
                    if (createdId != null) {
                        System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                    } else {
                        System.out.println("❌ 회원 등록 실패");
                    }
                    break;

                case "2":
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        Optional<Member> foundMember = memberController.findMemberById(id);
                        if (foundMember.isPresent()) {
                            Member m = foundMember.get();
                            System.out.println("✅ 조회된 회원: ID=" + m.getId() +
                                    ", 이름=" + m.getName() +
                                    ", 이메일=" + m.getEmail() +
                                    ", 생년월일=" + m.getBirthDate() +
                                    ", 성별=" + m.getGender());
                        } else {
                            System.out.println("⚠️ 해당 ID의 회원을 찾을 수 없습니다.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ 유효하지 않은 ID 형식입니다. 숫자를 입력해주세요.");
                    }
                    break;

                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    } else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
                            System.out.println("👤 ID=" + member.getId() +
                                    ", 이름=" + member.getName() +
                                    ", 이메일=" + member.getEmail() +
                                    ", 생년월일=" + member.getBirthDate() +
                                    ", 성별=" + member.getGender());
                        }
                        System.out.println("--------------------------");
                    }
                    break;


                case "4":
                    // 이메일로 회원 탈퇴
                    System.out.print("탈퇴할 회원 이메일을 입력하세요: ");
                    String emailToDelete = scanner.nextLine();
                    if (emailToDelete.trim().isEmpty()) {
                        System.out.println("⚠️ 이메일을 입력해주세요.");
                        continue;
                    }
                    boolean result = memberController.deleteMemberByEmail(emailToDelete);
                    if (result) {
                        System.out.println("✅ 회원 탈퇴 완료 (이메일: " + emailToDelete + ")");
                    } else {
                        System.out.println("⚠️ 해당 이메일의 회원을 찾을 수 없습니다.");
                    }
                    break;

                case "5":
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;
                default:
                    System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }
}