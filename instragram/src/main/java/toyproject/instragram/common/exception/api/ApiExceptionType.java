package toyproject.instragram.common.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.CustomException;
import toyproject.instragram.common.exception.api.httpstatusexception.ForbiddenException;
import toyproject.instragram.common.exception.api.httpstatusexception.NotFoundException;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionType {

    NOT_FOUND_MEMBER(new NotFoundException("존재하지 않는 회원입니다.")),

    NOT_FOUND_POST(new NotFoundException("해당 게시물이 존재하지 않습니다.")),
    FORBIDDEN_POST(new ForbiddenException("해당 게시물에 대한 접근 권한이 없습니다.")),

    NOT_FOUND_COMMENT(new NotFoundException("해당 댓글이 존재하지 않습니다.")),
    FORBIDDEN_COMMENT(new ForbiddenException("해당 댓글에 대한 접근 권한이 없습니다.")),

    NOT_FOUND_REPLY(new NotFoundException("해당 답글이 존재하지 않습니다.")),
    FORBIDDEN_REPLY(new ForbiddenException("해당 답글에 대한 접근 권한이 없습니다.")),
    ;

    private final CustomException exception;
}
