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
        MemberServiceImpl memberService = new MemberServiceImpl(memberRepository);
        MemberController memberController = new MemberController(memberService);

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
                    // 회원 등록
                    System.out.print("등록할 회원 이름을 입력하세요: ");
                    String name = scanner.nextLine();

                    System.out.print("등록할 회원 이메일을 입력하세요: ");
                    String email = scanner.nextLine();

                    System.out.print("등록할 회원 생년월일을 입력하세요: yyyy-mm-dd ");
                    String birthDateInput = scanner.nextLine();

                    System.out.print("등록할 회원 성별(MALE/FEMALE)을 입력하세요: ");
                    String genderInput = scanner.nextLine();

                    Long memberId = memberController.createMember(name, email, birthDateInput, genderInput);
                    if (memberId != null) {
                        System.out.println("✅ 회원 등록 완료 (ID: " + memberId + ")");
                    } else {
                        System.out.println("❌ 회원 등록 실패");
                    }
                    break;

                case "2":
                    // ID로 회원 조회
                    System.out.print("조회할 회원 ID를 입력하세요: ");
                    String id = scanner.nextLine();
                    Optional<Member> foundMember = memberController.findMemberById(id);
                    if (foundMember.isPresent()) {
                        Member m = foundMember.get();
                        System.out.println("✅ 조회된 회원: ID=" + m.getId() +
                                ", 이름=" + m.getName() +
                                ", 이메일=" + m.getEmail() +
                                ", 생년월일=" + m.getBirthDate() +
                                ", 성별=" + m.getGender());
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
