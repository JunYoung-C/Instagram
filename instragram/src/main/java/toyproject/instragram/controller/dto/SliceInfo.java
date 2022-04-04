package toyproject.instragram.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SliceInfo {
    private boolean hasNext;
    private int currentSliceSize;
}
