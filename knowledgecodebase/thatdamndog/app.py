import streamlit as st
from PIL import Image
from image_converter import ListToImage, ImageToList
from io import BytesIO

from dogjail import jail as jail 
from grayscale import grayscale as grayscale
from norak import norak as norak
from screamodog import screamo as screamo


FILTERS = {
    "screamo": screamo,
    "grayscale": grayscale,
    "jail": jail,
    "norak": norak
}



st.set_page_config(
    page_title="tdd filters",
    page_icon="assets/favicon.ico",
    layout="centered",
)

def upload_image():
    uploaded_file = st.file_uploader("upload an image", type=["png", "jpg"])
    if uploaded_file is not None:
        st.image(uploaded_file, caption="uploaded image", width=150)
    return uploaded_file


title_row = st.container(vertical_alignment="top", horizontal_alignment="center")
with title_row:
    st.header("upload an image :arrow_right: apply a filter", text_alignment="center")
    user_image = upload_image()
    with st.container(horizontal_alignment="left"):
        filter_options = st.pills(
            label="choose a filter",
            options=list(FILTERS.keys()),
            key="filter_options"
            )

st.divider()

def format_img(user_image):
    if user_image is not None:
        image = Image.open(user_image).convert("RGB")
        max_size = 800
        w, h = image.size
        if max(w, h) > max_size:
            scale = max_size / max(w, h)
            image = image.resize((int(w * scale), int(h * scale)))
        user_pixels = ImageToList(image)
        return user_pixels


if user_image and filter_options is not None:
    user_pix = format_img(user_image)
    filtered_pixels = FILTERS[filter_options](user_pix)
    filtered_image = ListToImage(filtered_pixels)

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
    



