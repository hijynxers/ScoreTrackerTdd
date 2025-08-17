package com.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val sailing: ImageVector
    get() {
        if (_sailing != null) return _sailing!!

        _sailing = ImageVector.Builder(
            name = "sailing",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFe3e3e3))
            ) {
                moveToRelative(120f, -420f)
                lineToRelative(320f, -460f)
                verticalLineToRelative(460f)
                horizontalLineTo(120f)
                close()
                moveToRelative(153f, -80f)
                horizontalLineToRelative(87f)
                verticalLineToRelative(-125f)
                lineToRelative(-87f, 125f)
                close()
                moveToRelative(227f, 80f)
                quadToRelative(12f, -28f, 26f, -98f)
                reflectiveQuadToRelative(14f, -142f)
                quadToRelative(0f, -72f, -13.5f, -148f)
                reflectiveQuadTo(500f, 40f)
                quadToRelative(61f, 18f, 121.5f, 67f)
                reflectiveQuadToRelative(109f, 117f)
                quadToRelative(48.5f, 68f, 79f, 149.5f)
                reflectiveQuadTo(840f, 540f)
                horizontalLineTo(500f)
                close()
                moveToRelative(104f, -80f)
                horizontalLineToRelative(148f)
                quadToRelative(-17f, -77f, -55.5f, -141f)
                reflectiveQuadTo(615f, 210f)
                quadToRelative(2f, 21f, 3.5f, 43.5f)
                reflectiveQuadTo(620f, 300f)
                quadToRelative(0f, 47f, -4.5f, 87f)
                reflectiveQuadTo(604f, 460f)
                close()
                moveTo(360f, 760f)
                quadToRelative(-36f, 0f, -67f, -17f)
                reflectiveQuadToRelative(-53f, -43f)
                quadToRelative(-14f, 15f, -30.5f, 28f)
                reflectiveQuadTo(173f, 749f)
                quadToRelative(-35f, -26f, -59.5f, -64.5f)
                reflectiveQuadTo(80f, 600f)
                horizontalLineToRelative(800f)
                quadToRelative(-9f, 46f, -33.5f, 84.5f)
                reflectiveQuadTo(787f, 749f)
                quadToRelative(-20f, -8f, -36.5f, -21f)
                reflectiveQuadTo(720f, 700f)
                quadToRelative(-23f, 26f, -53.5f, 43f)
                reflectiveQuadTo(600f, 760f)
                quadToRelative(-36f, 0f, -67f, -17f)
                reflectiveQuadToRelative(-53f, -43f)
                quadToRelative(-22f, 26f, -53f, 43f)
                reflectiveQuadToRelative(-67f, 17f)
                close()
                moveTo(80f, 920f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(40f)
                quadToRelative(32f, 0f, 62.5f, -10f)
                reflectiveQuadToRelative(57.5f, -30f)
                quadToRelative(27f, 20f, 57.5f, 29.5f)
                reflectiveQuadTo(360f, 839f)
                quadToRelative(32f, 0f, 62f, -9.5f)
                reflectiveQuadToRelative(58f, -29.5f)
                quadToRelative(27f, 20f, 57.5f, 29.5f)
                reflectiveQuadTo(600f, 839f)
                quadToRelative(32f, 0f, 62f, -9.5f)
                reflectiveQuadToRelative(58f, -29.5f)
                quadToRelative(28f, 20f, 58f, 30f)
                reflectiveQuadToRelative(62f, 10f)
                horizontalLineToRelative(40f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(-40f)
                quadToRelative(-31f, 0f, -61f, -7.5f)
                reflectiveQuadTo(720f, 890f)
                quadToRelative(-29f, 15f, -59f, 22.5f)
                reflectiveQuadTo(600f, 920f)
                quadToRelative(-31f, 0f, -61f, -7.5f)
                reflectiveQuadTo(480f, 890f)
                quadToRelative(-29f, 15f, -59f, 22.5f)
                reflectiveQuadTo(360f, 920f)
                quadToRelative(-31f, 0f, -61f, -7.5f)
                reflectiveQuadTo(240f, 890f)
                quadToRelative(-29f, 15f, -59f, 22.5f)
                reflectiveQuadTo(120f, 920f)
                horizontalLineTo(80f)
                close()
                moveToRelative(280f, -460f)
                close()
                moveToRelative(244f, 0f)
                close()
            }
        }.build()
        
        return _sailing!!
    }

private var _sailing: ImageVector? = null