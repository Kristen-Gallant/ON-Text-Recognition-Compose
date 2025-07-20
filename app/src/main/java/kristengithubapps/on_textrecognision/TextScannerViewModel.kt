package kristengithubapps.on_textrecognision

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class TextScannerViewModel(
    private val appContext: Context
) : ViewModel() {

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    private val _recognizedText = MutableStateFlow<String?>(null)
    val recognizedText: StateFlow<String?> = _recognizedText

    fun onImagePicked(uri: Uri) {
        _imageUri.value = uri

        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        try {
            val inputImage = InputImage.fromFilePath(appContext, uri)
            recognizer.process(inputImage)
                .addOnSuccessListener { result ->
                    _recognizedText.value = result.text
                }
                .addOnFailureListener {
                    _recognizedText.value = "Failed to recognize text."
                }
        } catch (e: Exception) {
            _recognizedText.value = "Error loading image."
        }
    }
}



