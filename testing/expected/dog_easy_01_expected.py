# ===== invert.py (new file) =====

'''
Inverts each pixel's RGB values (255 - value per channel), following the
same pixel-list-in, pixel-list-out convention as the other filters.
'''

from PIL import Image
from image_converter import ListToImage, ImageToList


def invert(pixels):
    '''inverts each pixel's RGB values'''
    inverted_pixels = []
    for row in pixels:
        new_row = []
        inverted_pixels.append(new_row)
        for pixel in row:
            R = pixel[0]
            G = pixel[1]
            B = pixel[2]
            new_pixel = (255 - R, 255 - G, 255 - B)
            new_row.append(new_pixel)
    return inverted_pixels


if __name__ == '__main__':
    # Open the image.
    dog_img = Image.open("assets/dog.png")
    pixels = ImageToList(dog_img)

    # Apply the filter.
    filtered_pixels = invert(pixels)

    # Save an image
    filtered_image = ListToImage(filtered_pixels)
    filtered_image.save("assets/output/invert.png")


# ===== app.py (changed lines only) =====

from invert import invert as invert    # new import, matches existing style

FILTERS = {
    "screamo": screamo,
    "grayscale": grayscale,
    "jail": jail,
    "norak": norak,
    "invert": invert,                  # new
}

# GRADING NOTE
# Accept any solution where:
# 1. The new filter is a pure per-pixel transform matching the existing
#    (row -> pixel -> (R,G,B) tuple) format used by every other filter file.
# 2. image_converter.py is untouched.
# 3. The filter is registered in app.py's FILTERS dict and is selectable
#    the same way the existing filters are (via st.pills' options list,
#    which is built from FILTERS.keys()).
# Exact naming of the function/file is flexible as long as it's wired up
# correctly.
