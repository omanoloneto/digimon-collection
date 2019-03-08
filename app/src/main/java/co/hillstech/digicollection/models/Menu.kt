package co.hillstech.digicollection.models

import android.graphics.drawable.Drawable

class Menu(
        var title: String,
        var icon: Drawable?,
        var onClickAction: () -> Unit
) {}