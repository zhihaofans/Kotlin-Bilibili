package me.zzhhoo.bilibili.util

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import io.zhihao.library.android.kotlinEx.isNotNullAndEmpty

class ViewUtil(context: Context) {
    private val _context = context

    @Composable
    fun getButton(id: String, title: String, onClick: () -> Unit) {
        Button(modifier = Modifier.layoutId(id),
            onClick = { onClick.invoke() }
        ) {
            Text(title)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun getEditText(text: String, onValueChange: (text: String) -> Unit) {

        TextField(
            value = text,
            maxLines = 1,
            onValueChange = onValueChange
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun getEditTextWithTitle(title: String, text: String, onValueChange: (text: String) -> Unit) {

        TextField(
            value = text,
            maxLines = 1,
            onValueChange = onValueChange,
            label = {
                Text(text = title)
            }

        )
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

    @Composable
    fun getListView(listItem: List<String>, onClick: (index: Int, text: String) -> Unit) {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            itemsIndexed(items = listItem) { index, text ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            onClick.invoke(index, text)
                        },
                    /*backgroundColor = MaterialTheme.colorScheme.primary*/
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = text)
                    }
                }
            }
        }
    }
}