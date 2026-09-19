# Task: Add a filter-intensity slider

**Project:** thatdamndog (Streamlit image filter app)
**Files involved:** `app.py`, and a new helper module for the blend logic (e.g. `blend.py`)
**Language:** Python
**Difficulty:** Hard
**Retrieval style:** paraphrased (describes the behavior without naming existing variables)

## Description

Right now, once a filter is picked, the app always shows the fully filtered
image with no way to dial it back. Add a slider (0% to 100%) that controls
how strongly the chosen filter is applied, by blending the filtered image
with the original image pixel-by-pixel:

- At 100%, the result should look exactly like the fully filtered image (as
  it does today).
- At 0%, the result should look exactly like the original, unfiltered
  image.
- At any value in between, each pixel's R, G, and B should be a
  proportional blend between the original pixel's value and the filtered
  pixel's value at that same position.

The slider should only appear once both an image has been uploaded and a
filter has been selected. Both the side-by-side preview and the downloaded
file should reflect the blended result, not the raw, always-100% filtered
output.

## Constraints
- Perform the blend on the pixel-list representation already used
  throughout this codebase (the same row-of-(R,G,B)-tuples format the
  filters and `image_converter.py` work with), not by blending the PIL
  `Image` objects directly.
- Do not modify `image_converter.py`. Use its existing `ListToImage` /
  `ImageToList` functions rather than duplicating that conversion logic.
- The blend must work regardless of which filter is currently selected —
  don't hardcode it to one specific filter.
- Blended RGB values must stay valid 8-bit values (0-255) after rounding.
