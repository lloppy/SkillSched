package com.example.skills.ui.master.a

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun MasterGallery(images: MutableList<Bitmap>) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 95.dp),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(6.dp),
        content = {
            items(images.size) { index ->
                Image(
                    painter = rememberAsyncImagePainter(model = images[index]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
    )
}
