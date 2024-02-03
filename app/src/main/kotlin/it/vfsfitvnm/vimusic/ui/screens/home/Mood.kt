package it.vfsfitvnm.vimusic.ui.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import it.vfsfitvnm.vimusic.R
import it.vfsfitvnm.vimusic.ui.components.themed.FloatingActionsContainerWithScrollToTop

@SuppressLint("SetTextI18n", "InflateParams")
@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun Mood(
    onSearchClick: () -> Unit,
) {
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()

    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(0.dp,100.dp,0.dp,0.dp)){

        AndroidView(factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.activity_main, null)
        }, modifier = Modifier
            .wrapContentHeight()
            .then(Modifier.size(700.dp))) { view ->
            val button = view.findViewById<TextView>(R.id.textView)
            button.text = "Welcome!"
        }

        LaunchedEffect(Unit){
            context.let{
                startActivity(it, Intent(it, MoodView::class.java), Bundle())
            }
        }

        FloatingActionsContainerWithScrollToTop(
            lazyListState = lazyListState,
            iconId = R.drawable.search,
            onClick = onSearchClick
        )
    }
}





