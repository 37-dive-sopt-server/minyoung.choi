package org.sopt.member.repository;

import java.util.*;

import org.sopt.member.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
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

    public void deleteByEmail(String email) {
        store.remove(findAllByEmail(email));
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}