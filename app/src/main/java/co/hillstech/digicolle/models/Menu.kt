package co.hillstech.digicolle.models

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import co.hillstech.digicolle.R
import co.hillstech.digicolle.activities.LoginActivity

class Menu(
        var title: String,
        var icon: Drawable?,
        var onClickAction: () -> Unit
) { }