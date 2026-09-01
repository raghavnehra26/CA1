package com.example.ca1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ca1.ui.theme.CA1Theme

data class StudentInfo(
    val name: String,
    val regNo: String,
    val department: String,
    val bloodGroup: String,
    val contactNo: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CA1Theme {
                StudentIDCardApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentIDCardApp() {
    // Initial State - Change these to your actual information
    var studentInfo by remember {
        mutableStateOf(
            StudentInfo(
                name = "RAGHAV",
                regNo = "12313844",
                department = "CSE",
                bloodGroup = "B+",
                contactNo = "9692400026"
            )
        )
    }

    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Student ID Card", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showEditDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Details")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            StudentIDCard(studentInfo = studentInfo)
        }

        if (showEditDialog) {
            EditStudentDialog(
                initialInfo = studentInfo,
                onDismiss = { showEditDialog = false },
                onSave = { updatedInfo ->
                    studentInfo = updatedInfo
                    showEditDialog = false
                }
            )
        }
    }
}

@Composable
fun StudentIDCard(studentInfo: StudentInfo) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Student Photo Placeholder
            Surface(
                modifier = Modifier.size(110.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                shadowElevation = 4.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "LOVELY PROFESSIONAL UNIVERSITY",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Academic Task - 1",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // Student Details Section
            DetailRow(label = "Name", value = studentInfo.name)
            DetailRow(label = "Reg. No.", value = studentInfo.regNo)
            DetailRow(label = "Department", value = studentInfo.department)
            DetailRow(label = "Blood Group", value = studentInfo.bloodGroup)
            DetailRow(label = "Contact No.", value = studentInfo.contactNo)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "TRANSFORMING EDUCATION TRANSFORMING INDIA",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentDialog(
    initialInfo: StudentInfo,
    onDismiss: () -> Unit,
    onSave: (StudentInfo) -> Unit
) {
    var name by remember { mutableStateOf(initialInfo.name) }
    var regNo by remember { mutableStateOf(initialInfo.regNo) }
    var department by remember { mutableStateOf(initialInfo.department) }
    var bloodGroup by remember { mutableStateOf(initialInfo.bloodGroup) }
    var contactNo by remember { mutableStateOf(initialInfo.contactNo) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                "Edit ID Card Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            ) 
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = regNo,
                    onValueChange = { regNo = it },
                    label = { Text("Registration Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = department,
                    onValueChange = { department = it },
                    label = { Text("Department") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = bloodGroup,
                    onValueChange = { bloodGroup = it },
                    label = { Text("Blood Group") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = contactNo,
                    onValueChange = { contactNo = it },
                    label = { Text("Contact Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(StudentInfo(name, regNo, department, bloodGroup, contactNo))
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun StudentIDCardPreview() {
    CA1Theme {
        StudentIDCardApp()
    }
}
