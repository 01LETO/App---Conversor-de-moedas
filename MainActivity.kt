package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    YouTubeHomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeHomeScreen() {
    Scaffold(
        topBar = { YouTubeTopBar() }
    ) { paddingValues ->
        VideoList(modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YouTubeTopBar() {
    TopAppBar(
        title = {
            Text(text = "YouTube", fontWeight = FontWeight.Bold)
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
            }
        }
    )
}

data class VideoItem(val title: String, val channel: String)

val sampleVideos = listOf(
    VideoItem("Como aprender Jetpack Compose", "Canal Android"),
    VideoItem("Top 10 recursos do Kotlin", "Dev Mobile"),
    VideoItem(title = "melhores videos do YouTube", channel = "Compose Master"),
    VideoItem("Dicas para Android Studio", "Studio Tips")
)

@Composable
fun VideoList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(sampleVideos) { video ->
            VideoCard(video)
        }
    }
}

@Composable
fun VideoCard(video: VideoItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ) {}
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = video.title, fontWeight = FontWeight.Bold)
        Text(text = video.channel, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun YouTubeHomePreview() {
    MyApplicationTheme {
        YouTubeHomeScreen()
    }
}
