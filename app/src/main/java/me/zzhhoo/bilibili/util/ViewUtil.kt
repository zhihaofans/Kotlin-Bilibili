package me.zzhhoo.bilibili.util

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId

class ViewUtil (context: Context) {
    private val _context = context

    @Composable
    fun getButton(id: String, title: String, onClick: () -> Unit) {
        Button(modifier = Modifier.layoutId(id),
            onClick = { onClick.invoke() }
        ) {
            Text(title)
        }
    }

    @Composable
    fun getFab(
        icon: ImageVector? = Icons.Filled.Add,
        description: String? = "Floating action button.",
        onClick: () -> Unit
    ) {
        FloatingActionButton(
            onClick = { onClick.invoke() },
        ) {
            Icon(icon ?: Icons.Filled.Add, description)
        }
    }
}