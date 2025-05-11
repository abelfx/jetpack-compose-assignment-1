package com.abebe.mobile_assignment

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import com.abebe.mobile_assignment.ui.theme.Mobile_assignmentTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mobile_assignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class CourseInfo(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

@Composable
fun Course(
    courseTitle: String,
    courseCode: String,
    creditHours: Int,
    description: String,
    prerequisites: String,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = courseTitle, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Code: $courseCode")
                    Text(text = "Credit Hours: $creditHours")
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand"
                    )
                }
            }

            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Prerequisites: $prerequisites", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

// Welcome page
@Composable
fun WelcomePage(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to Courses List",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(24.dp))
            ElevatedButton(onClick = onContinueClicked) {
                Text("Continue")
            }
        }
    }
}

val courseList = listOf(
    CourseInfo(
        title = "Mobile Development",
        code = "CS301",
        creditHours = 3,
        description = "This course focuses on designing and developing applications for mobile platforms using tools like Android Studio and Flutter.",
        prerequisites = "Object-Oriented Programming"
    ),
    CourseInfo(
        title = "Web Development",
        code = "CS302",
        creditHours = 3,
        description = "Covers frontend and backend web technologies including HTML, CSS, JavaScript, Node.js, and modern frameworks like React.",
        prerequisites = "Introduction to Programming"
    ),
    CourseInfo(
        title = "Cyber Security",
        code = "CS303",
        creditHours = 4,
        description = "Introduces principles of computer security, including network security, cryptography, risk management, and ethical hacking.",
        prerequisites = "Computer Networks"
    ),
    CourseInfo(
        title = "Operating System",
        code = "CS304",
        creditHours = 4,
        description = "Covers concepts such as process management, memory management, file systems, and concurrency in modern operating systems.",
        prerequisites = "Computer Architecture"
    ),
    CourseInfo(
        title = "Artificial Intelligence",
        code = "CS305",
        creditHours = 3,
        description = "Introduces AI techniques such as search algorithms, machine learning, natural language processing, and expert systems.",
        prerequisites = "Data Structures and Algorithms"
    ),
    CourseInfo(
        title = "Computer Graphics",
        code = "CS306",
        creditHours = 3,
        description = "Covers basic concepts in 2D/3D graphics, rendering pipelines, modeling, and animation using tools like OpenGL or Unity.",
        prerequisites = "Linear Algebra, Programming Fundamentals"
    )
)

@Composable
fun CourseList(modifier: Modifier = Modifier, courses: List<CourseInfo>) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(courses) { course ->
            Course(
                courseTitle = course.title,
                courseCode = course.code,
                creditHours = course.creditHours,
                description = course.description,
                prerequisites = course.prerequisites
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowWelcome by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current

    if (shouldShowWelcome) {
        WelcomePage(onContinueClicked = { shouldShowWelcome = false })
    } else {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = { Text("Course List") },
                    actions = {
                        IconButton(onClick = {
                            Toast.makeText(context, "Adding not supported yet", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Course")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                CourseList(courses = courseList)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun WelcomePreview() {
    Mobile_assignmentTheme {
        WelcomePage(onContinueClicked = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun GreetingPreview() {
    Mobile_assignmentTheme {
        CourseList(courses = courseList)
    }
}

@Preview
@Composable
fun MyAppPreview() {
    Mobile_assignmentTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}
