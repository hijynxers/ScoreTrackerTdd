package com.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val crown: ImageVector
    get() {
        if (_crown != null) return _crown!!

        _crown = ImageVector.Builder(
            name = "crown",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFe3e3e3))
            ) {
                moveTo(200f, 800f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(80f)
                horizontalLineTo(200f)
                close()
                moveToRelative(0f, -140f)
                lineToRelative(-51f, -321f)
                quadToRelative(-2f, 0f, -4.5f, 0.5f)
                reflectiveQuadToRelative(-4.5f, 0.5f)
                quadToRelative(-25f, 0f, -42.5f, -17.5f)
                reflectiveQuadTo(80f, 280f)
                quadToRelative(0f, -25f, 17.5f, -42.5f)
                reflectiveQuadTo(140f, 220f)
                quadToRelative(25f, 0f, 42.5f, 17.5f)
                reflectiveQuadTo(200f, 280f)
                quadToRelative(0f, 7f, -1.5f, 13f)
                reflectiveQuadToRelative(-3.5f, 11f)
                lineToRelative(125f, 56f)
                lineToRelative(125f, -171f)
                quadToRelative(-11f, -8f, -18f, -21f)
                reflectiveQuadToRelative(-7f, -28f)
                quadToRelative(0f, -25f, 17.5f, -42.5f)
                reflectiveQuadTo(480f, 80f)
                quadToRelative(25f, 0f, 42.5f, 17.5f)
                reflectiveQuadTo(540f, 140f)
                quadToRelative(0f, 15f, -7f, 28f)
                reflectiveQuadToRelative(-18f, 21f)
                lineToRelative(125f, 171f)
                lineToRelative(125f, -56f)
                quadToRelative(-2f, -5f, -3.5f, -11f)
                reflectiveQuadToRelative(-1.5f, -13f)
                quadToRelative(0f, -25f, 17.5f, -42.5f)
                reflectiveQuadTo(820f, 220f)
                quadToRelative(25f, 0f, 42.5f, 17.5f)
                reflectiveQuadTo(880f, 280f)
                quadToRelative(0f, 25f, -17.5f, 42.5f)
                reflectiveQuadTo(820f, 340f)
                quadToRelative(-2f, 0f, -4.5f, -0.5f)
                reflectiveQuadToRelative(-4.5f, -0.5f)
                lineToRelative(-51f, 321f)
                horizontalLineTo(200f)
                close()
                moveToRelative(68f, -80f)
                horizontalLineToRelative(424f)
                lineToRelative(26f, -167f)
                lineToRelative(-105f, 46f)
                lineToRelative(-133f, -183f)
                lineToRelative(-133f, 183f)
                lineToRelative(-105f, -46f)
                lineToRelative(26f, 167f)
                close()
                moveToRelative(212f, 0f)
                close()
            }
        }.build()
        
        return _crown!!
    }

private var _crown: ImageVector? = null