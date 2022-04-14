package toyproject.instragram.common.response;

import lombok.Getter;

@Getter
public class CommonSliceResponse<T> {
    private T data;
    private SliceInfo sliceInfo;

    public CommonSliceResponse(T data, SliceInfo sliceInfo) {
        this.data = data;
        this.sliceInfo = sliceInfo;
    }
}
