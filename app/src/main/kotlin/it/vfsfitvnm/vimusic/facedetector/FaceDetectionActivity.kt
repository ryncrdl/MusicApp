package it.vfsfitvnm.vimusic.facedetector

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import it.vfsfitvnm.vimusic.CameraXViewModel
import it.vfsfitvnm.vimusic.R
import it.vfsfitvnm.vimusic.databinding.ActivityMainBinding
import java.util.concurrent.Executors

@ExperimentalAnimationApi
@ExperimentalFoundationApi
class FaceDetectionActivity : ComponentActivity()  {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraSelector: CameraSelector
    private lateinit var processCameraProvider: ProcessCameraProvider
    private lateinit var cameraPreview: Preview
    private lateinit var imageAnalysis: ImageAnalysis


    private val cameraXViewModel = viewModels<CameraXViewModel>()
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
        cameraXViewModel.value.processCameraProvider.observe(this) { provider ->
            processCameraProvider = provider
            bindCameraPreview()
            bindInputAnalyser()
        }

        textView = findViewById(R.id.textView)
    }

    private fun bindCameraPreview() {
        cameraPreview = Preview.Builder()
            .setTargetRotation(binding.previewView.display.rotation)
            .build()
        cameraPreview.setSurfaceProvider(binding.previewView.surfaceProvider)
        try {
            processCameraProvider.bindToLifecycle(this, cameraSelector, cameraPreview)

        } catch (illegalStateException: IllegalStateException) {
            Log.e(TAG, illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(TAG, illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    @SuppressLint("UnsafeOptInUsageError", "SetTextI18n")
    private fun processImageProxy(detector: FaceDetector, imageProxy: ImageProxy) {
        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        detector.process(inputImage).addOnSuccessListener { faces ->
            binding.graphicOverlay.clear()
            for (face in faces) {
                val landmarks = face.allLandmarks.map { it.position }
                val contours = face.allContours.map { it.points }
                val smilingprob = face.smilingProbability
                val rightEye = face.rightEyeOpenProbability


                if(smilingprob!! < 0.02 && rightEye!! < 0.996) {
                    textView.text = "Sad Emotion. Here's a song to cheer you up!"
                }
                else if (smilingprob <= 0.02 && rightEye!! >= 0.996) {
                    textView.text = "Neutral Emotion. Selecting random music for you!"
                }
                else if (smilingprob > 0.1 && rightEye!! < 0.996) {
                    textView.text = "Happy Emotion. Let's start your day with a great song!"
                }
                else{
                    textView.text = "Please, make sure your head is centered properly."
                }

                val faceBox = FaceBox(
                    binding.graphicOverlay,
                    face,
                    imageProxy.image!!.cropRect,
                    landmarks,
                    contours
                )
                binding.graphicOverlay.add(faceBox)
                binding.graphicOverlay.invalidate()
            }

        }.addOnFailureListener {
            it.printStackTrace()
        }.addOnCompleteListener {
            imageProxy.close()
        }
    }




    private fun bindInputAnalyser() {
        val detector = FaceDetection.getClient(
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()
        )
        imageAnalysis = ImageAnalysis.Builder()
            .setTargetRotation(binding.previewView.display.rotation)
            .build()

        val cameraExecutor = Executors.newSingleThreadExecutor()

        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            processImageProxy(detector, imageProxy)
        }

        try {
            processCameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis)
        } catch (illegalStateException: IllegalStateException) {
            Log.e(TAG, illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e(TAG, illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    companion object {
        private val TAG = FaceDetectionActivity::class.simpleName
        fun startActivity(context: Context) {
            Intent(context, FaceDetectionActivity::class.java).also {
                context.startActivity(it)
            }
        }
    }
}
