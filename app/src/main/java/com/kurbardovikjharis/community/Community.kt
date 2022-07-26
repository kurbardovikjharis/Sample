package com.kurbardovikjharis.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.Scaffold
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun Community() {
    Community(hiltViewModel())
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun Community(viewModel: CommunityViewModel) {
    val state = viewModel.viewState.collectAsState().value
    val pagerState = rememberPagerState()

    if (state.tabs.isNotEmpty()) {
        Scaffold {
            Divider(modifier = Modifier.height(it.calculateTopPadding()))
            Column {
                Tabs(tabs = state.tabs, pagerState = pagerState)
                TabsContent(tabs = state.tabs, pagerState = pagerState, data = state.data)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<TabItem>, pagerState: PagerState, data: HashMap<Int, List<Tile>>) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        data[page]?.let { CommunityContent(it) }
    }
}

@Composable
fun CommunityContent(data: List<Tile>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        data.forEach {
            Text(
                text = it.title,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    // OR ScrollableTabRow()
    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
        // Add tabs for all of our pages
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_android_black_24dp),
                        contentDescription = ""
                    )
                },
                text = { Text(tab.name) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}
