package org.sopt.common.validator;

import org.sopt.common.exception.CustomException;
import org.sopt.common.exception.ErrorCode;
import org.sopt.member.domain.Member;

public class Validator {

    public static void validateEmailFormat(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_INPUT);
        }
    }

    public static void validateAge(Member member) {
        if (member.getAge() < 20) {
            throw new CustomException(ErrorCode.UNDERAGE_MEMBER);
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
    }

    public static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_TITLE_INPUT);
        }
    }

    public static void validateCommentLength(String content) {
        if (content.length() > 300) {
            throw new CustomException(ErrorCode.INVALID_COMMENT_LENGTH_INPUT);
        }
    }
}
