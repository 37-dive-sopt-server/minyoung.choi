package org.sopt.repository;

import java.util.*;

import org.sopt.domain.Member;

public class MemoryMemberRepository {

    private static final Map<Long, Member> store = new HashMap<>();


    public Member save(Member member) {
        store.put(member.getId(), member);
        return member;

    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Member> findAllByEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean deleteByEmail(String email) {
        Optional<Member> memberOpt = findAllByEmail(email);
        if (memberOpt.isPresent()) {
            store.remove(memberOpt.get().getId());
            return true;
        }
        return false;
    }
}