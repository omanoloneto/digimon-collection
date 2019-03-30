package co.hillstech.digicollection.models

import co.hillstech.enums.DigiDataType

/**
 * Created by Gunther Ribak on 25/03/2019.
 * for Copyright 2019 outdabox.in. All rights reserved.
 * you can contact me at: gribak@outdabox.in
 */
data class DigiDataEntry(
        val title: String,
        val monster: Monster,
        val type: DigiDataType
)