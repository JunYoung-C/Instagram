package toyproject.instragram.common.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import toyproject.instragram.common.exception.CustomException;
import toyproject.instragram.common.exception.api.httpstatusexception.BadRequestException;
import toyproject.instragram.common.exception.api.httpstatusexception.ForbiddenException;
import toyproject.instragram.common.exception.api.httpstatusexception.NotFoundException;
import toyproject.instragram.common.exception.api.httpstatusexception.UnauthorizedException;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionType {

    NOT_FOUND_MEMBER(new NotFoundException("존재하지 않는 회원입니다.")),

    REQUIRED_POST_ID(new BadRequestException("게시물 고유 번호는 필수입니다.")),
    NOT_FOUND_POST(new NotFoundException("해당 게시물이 존재하지 않습니다.")),
    FORBIDDEN_POST(new ForbiddenException("해당 게시물에 대한 접근 권한이 없습니다.")),

    REQUIRED_COMMENT_ID(new BadRequestException("댓글 고유 번호는 필수입니다.")),
    NOT_FOUND_COMMENT(new NotFoundException("해당 댓글이 존재하지 않습니다.")),
    FORBIDDEN_COMMENT(new ForbiddenException("해당 댓글에 대한 접근 권한이 없습니다.")),

    REQUIRED_REPLY_ID(new BadRequestException("답글 고유 번호는 필수입니다.")),
    NOT_FOUND_REPLY(new NotFoundException("해당 답글이 존재하지 않습니다.")),
    FORBIDDEN_REPLY(new ForbiddenException("해당 답글에 대한 접근 권한이 없습니다.")),

    EXPIRED_SESSION(new UnauthorizedException("세션이 만료되었습니다. 다시 로그인하세요.")),
    ;

    private final CustomException exception;
}
