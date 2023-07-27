package com.hr.slide_app

import android.net.Uri

class Image(uniqueID: String, width: Int, height: Int, rgb: RGB, alpha: Alpha) :
    Slide(uniqueID, width, height, rgb, alpha,type=1) {
    var img: Uri? = null
}