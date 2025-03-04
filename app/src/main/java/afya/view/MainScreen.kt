package afya.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.afya.R
import com.example.afya.model.Drug
import com.example.afya.model.Post
import com.example.afya.viewmodel.DrugViewModel
import com.example.afya.viewmodel.PostViewModel
import androidx.compose.ui.window.Dialog

sealed class Screen {
    data object Posts : Screen()
    data object Drugs : Screen()
    data object Profile : Screen()
    data object Messages : Screen()
    data object Calls : Screen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    postViewModel: PostViewModel,
    drugViewModel: DrugViewModel
) {
    val posts by postViewModel.uiState.collectAsState()
    val drugs by drugViewModel.drugState.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Posts) }
    var searchQuery by remember { mutableStateOf("") }

    // Filtered lists
    val filteredPosts = remember(posts.posts, searchQuery) {
        posts.posts.filter { post ->
            post.title.contains(searchQuery, ignoreCase = true) ||
                    post.content.contains(searchQuery, ignoreCase = true)
        }
    }

    val filteredDrugs = remember(drugs.drugs, searchQuery) {
        drugs.drugs.filter { drug ->
            drug.name.contains(searchQuery, ignoreCase = true) ||
                    drug.details.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            if (currentScreen !is Screen.Profile && currentScreen !is Screen.Calls && currentScreen !is Screen.Messages) {
                Column {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Afya",
                                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search...") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(ImageVector.vectorResource(R.drawable.ic_posts), contentDescription = null) },
                    label = { Text("Posts") },
                    selected = currentScreen == Screen.Posts,
                    onClick = { currentScreen = Screen.Posts }
                )
                NavigationBarItem(
                    icon = { Icon(ImageVector.vectorResource(R.drawable.ic_drugs), contentDescription = null) },
                    label = { Text("Drugs") },
                    selected = currentScreen == Screen.Drugs,
                    onClick = { currentScreen = Screen.Drugs }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = currentScreen == Screen.Profile,
                    onClick = { currentScreen = Screen.Profile }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Email, contentDescription = "Msgs") },
                    label = { Text("Msgs") },
                    selected = currentScreen == Screen.Messages,
                    onClick = { currentScreen = Screen.Messages }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Call, contentDescription = "Calls") },
                    label = { Text("Calls") },
                    selected = currentScreen == Screen.Calls,
                    onClick = { currentScreen = Screen.Calls }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                is Screen.Posts -> PostList(
                    posts = filteredPosts,
                )
                is Screen.Drugs -> DrugList(
                    drugs = filteredDrugs,
                )
                is Screen.Profile -> ProfileScreen()
                is Screen.Messages -> MessagesScreen()
                is Screen.Calls -> CallsScreen()
            }
        }
    }
}@Composable
fun CallsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No Calls", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun MessagesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No Messages", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun ProfileScreen() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var birthDay by remember { mutableStateOf("") }
    var birthMonth by remember { mutableStateOf("") }
    var birthYear by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Date of Birth",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = birthDay,
                onValueChange = { birthDay = it },
                label = { Text("Day") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = birthMonth,
                onValueChange = { birthMonth = it },
                label = { Text("Month") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = birthYear,
                onValueChange = { birthYear = it },
                label = { Text("Year") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { }) {
            Text("Save Informations")
        }
    }
}@Composable
fun DrugList(drugs: List<Drug>, modifier: Modifier = Modifier) {
    val showAddDrugDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { showAddDrugDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add informations about a drug")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add informations about a drug")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(drugs) { drug ->
                DrugCard(drug)
            }
        }
    }

    if (showAddDrugDialog.value) {
        AddDrugDialog(onDismiss = { showAddDrugDialog.value = false })
    }
}

@Composable
fun DrugCard(drug: Drug) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = drug.image,
                contentDescription = "Drug Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = drug.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = drug.details,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun AddDrugDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add New Drug", style = MaterialTheme.typography.headlineSmall)
                // Add your input fields for name, details, image, etc. here.
                // ...
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        // Handle adding the drug here.
                        onDismiss()
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}@Composable
fun PostList(posts: List<Post>, modifier: Modifier = Modifier) {
    val showAddPostDialog = remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Button(
            onClick = { showAddPostDialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Post")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Post")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(posts) { post ->
                PostCard(post)
            }
        }
    }

    if (showAddPostDialog.value) {
        AddPostDialog(onDismiss = { showAddPostDialog.value = false })
    }
}

@Composable
fun PostCard(post: Post) {
    var showContactOptions by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = post.image,
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(12.dp))

            post.postType.let { postType ->
                Surface(
                    modifier = Modifier.padding(bottom = 8.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Text(
                        text = postType.name,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { showContactOptions = !showContactOptions },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Contact")
            }

            if (showContactOptions) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { /* إرسال رسالة */ }) {
                        Icon(Icons.Filled.Email, contentDescription = "Send Email", tint = Color.White)
                    }
                    IconButton(onClick = { /* الاتصال */ }) {
                        Icon(Icons.Filled.Phone, contentDescription = "Call", tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AddPostDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add New Post", style = MaterialTheme.typography.headlineSmall)
                // Add your input fields for title, content, image, etc. here.
                // ...
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        // Handle adding the post here.
                        onDismiss()
                    }) {
                        Text("Add")
                    }
                }
            }
        }
    }
}