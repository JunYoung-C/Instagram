package toyproject.instragram.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SliceInfo {
    private boolean hasNext;
    private int maxSize;
    private int page;

    public static <T> SliceInfo from (Slice<T> slice) {
        return new SliceInfo(slice.hasNext(), slice.getSize(), slice.getNumber());
    }
}
