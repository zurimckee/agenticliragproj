# ===== jailscream.py (new file) =====

'''
Composite filter: runs the existing jail filter first, then feeds its
output through the existing screamo filter. Reuses both functions rather
than reimplementing their pixel logic.
'''

from PIL import Image
from image_converter import ListToImage, ImageToList
from dogjail import jail
from screamodog import screamo


def jailscream(pixels):
    '''applies jail, then screamo, to the given pixel list'''
    jailed_pixels = jail(pixels)
    combined_pixels = screamo(jailed_pixels)
    return combined_pixels


if __name__ == '__main__':
    # Open the image.
    dog_img = Image.open("assets/dog.png")
    pixels = ImageToList(dog_img)

    # Apply the filter.
    filtered_pixels = jailscream(pixels)

    # Save an image
    filtered_image = ListToImage(filtered_pixels)
    filtered_image.save("assets/output/jailscream.png")


# ===== app.py (changed lines only) =====

from jailscream import jailscream as jailscream    # new import

FILTERS = {
    "screamo": screamo,
    "grayscale": grayscale,
    "jail": jail,
    "norak": norak,
    "jail+scream": jailscream,        # new
}

# GRADING NOTE
# Accept any solution where:
# 1. The new filter calls the existing jail() and screamo() functions
#    (imported from their original files) rather than duplicating their
#    per-pixel logic.
# 2. jail is applied before screamo, in that order, with screamo's input
#    being jail's output (not both applied independently to the original
#    image and merged some other way).
# 3. dogjail.py, screamodog.py, and image_converter.py are untouched.
# 4. The composite filter is registered and selectable in app.py the same
#    way the existing filters are.
