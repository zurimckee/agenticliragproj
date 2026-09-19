# ===== blend.py (new file) =====

'''
Blends two pixel lists of identical dimensions channel-by-channel, based on
an intensity fraction (0.0 = fully `original`, 1.0 = fully `filtered`).
Operates on the same pixel-list format used throughout this codebase.
'''

def blend_pixels(original, filtered, intensity):
    '''blends two same-shaped pixel lists by the given intensity (0.0-1.0)'''
    blended = []
    for orig_row, filt_row in zip(original, filtered):
        new_row = []
        blended.append(new_row)
        for orig_pixel, filt_pixel in zip(orig_row, filt_row):
            new_pixel = tuple(
                round(o * (1 - intensity) + f * intensity)
                for o, f in zip(orig_pixel, filt_pixel)
            )
            # clamp defensively in case a filter produced out-of-range values
            new_pixel = tuple(max(0, min(255, v)) for v in new_pixel)
            new_row.append(new_pixel)
    return blended


# ===== app.py (changed lines only, in context) =====

from blend import blend_pixels    # new import

# ... existing FILTERS dict, upload_image(), format_img() unchanged ...

if user_image and filter_options is not None:
    user_pix = format_img(user_image)
    filtered_pixels = FILTERS[filter_options](user_pix)

    intensity_percent = st.slider(     # new
        "filter intensity",
        min_value=0,
        max_value=100,
        value=100,
    )
    intensity = intensity_percent / 100

    blended_pixels = blend_pixels(user_pix, filtered_pixels, intensity)  # new
    filtered_image = ListToImage(blended_pixels)   # changed: was ListToImage(filtered_pixels)

    col1, col2 = st.columns(2)
    with col1, col2:
        with col1:
            if user_image is not None:
                st.image(user_image, caption="uploaded image", width=300)
        with col2:
            if filtered_image is not None:
                st.image(filtered_image, caption="filtered image", width=300)

    buf = BytesIO()
    filtered_image.save(buf, format="PNG")
    byte_data = buf.getvalue()

    st.divider()
    st.write("download your filtered image below")
    st.download_button(
        label="download filtered image",
        data=byte_data,
        file_name="filtered_image.png",
        mime="image/png",
        icon="🖼️"
        )

# GRADING NOTE
# Accept any solution where:
# 1. The blend operates on pixel lists (not PIL Images) using the existing
#    ListToImage/ImageToList conversions, and image_converter.py is
#    untouched.
# 2. At intensity 0, the output is equivalent to the original image; at
#    intensity 100, equivalent to the fully filtered image; values between
#    are a proportional per-channel blend.
# 3. Resulting RGB values are valid 0-255 integers (rounded and/or clamped).
# 4. The slider only meaningfully affects output once both an image and a
#    filter are selected (matching the existing `if user_image and
#    filter_options is not None:` gating already in the file).
# 5. Both the on-screen preview and the downloaded file use the blended
#    result, not the unblended filtered_pixels.
# Exact slider widget choice/placement and function/file naming are
# flexible as long as the blending behavior is correct.
